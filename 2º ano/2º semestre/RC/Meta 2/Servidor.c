#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <signal.h>
#include <sys/wait.h>
#include <sys/ipc.h>
#include <sys/shm.h>
#include <semaphore.h>
#include <fcntl.h>
#include <sys/stat.h>

#define BUFFER_SIZE 1024 // Tamanho do buffer para recepção de dados
#define MAX_USERS 100
#define MAX_USERNAME_LEN 50
#define MAX_PASSWORD_LEN 50
#define MAX_USERTYPE_LEN 50
#define MAX_LINE_LEN 200
#define MAX_SUBS 200
#define MAX_TOPICS 8

// Estrutura para armazenar os dados dos utilizadores
typedef struct
{
    char username[MAX_USERNAME_LEN];
    char password[MAX_PASSWORD_LEN];
    char usertype[MAX_USERTYPE_LEN];
    char topics[MAX_TOPICS];
} User;

// Estrutura para armazenar os dados dos topicos
typedef struct
{
    char titulo[256];
    char id[256];
    char subscribers[MAX_SUBS][256];
    int num_subs;
    char ip[16];
} Topic;

// Gama de endereços multicast disponiveis
char ip[MAX_TOPICS][16] = {"224.0.0.1", "224.0.0.2", "224.0.0.3", "224.0.0.4", "224.0.0.5", "224.0.0.6", "224.0.0.7", "224.0.0.8"};

// Config
char *config_file;
// PROCESSOS
pid_t pai, admin;

// SHM
int shmid;
Topic *topics;
User *users;
char (*ip_topics)[16];
int *numUsers, *numTopics;

// Semáforo
sem_t *sem_sys;

int init(char *);
int autenticar(char *, char *);
void admin_console(int);
void handleClient(int);
void cleanup();
char *subscribe(char *, char *);
void create_topic(char *, char *);
void send_news(char *, char *);

int main(int argc, char *argv[])
{
    if (argc != 4)
    {
        printf("Uso: %s {PORTO_NOTICIAS} {PORTO_CONFIG} {ficheiro configuração}\n", argv[0]);
        return 1;
    }

    int porto_noticias = atoi(argv[1]);
    int porto_config = atoi(argv[2]);
    config_file = argv[3];
    int ctrl;

    pai = getpid();

    signal(SIGINT, cleanup);

    ctrl = init(config_file);
    if (ctrl == 0)
    {
        printf("Leitura do ficheiro de configurações bem sucedida!\n");
    }
    else
    {
        printf("Erro na leitura do ficheiro de configurações!\n");
        return 1;
    }

    admin = fork();

    if (admin == -1)
    {
        perror("Erro ao criar o processo filho");
        exit(1);
    }
    else if (admin == 0)
    {
        admin_console(porto_config);
    }
    else
    {
        handleClient(porto_noticias);
    }
    return 0;
}

// Função para ler o ficheiro de configuração
int init(char *conf)
{

    int espaco = sizeof(User) * MAX_USERS + sizeof(Topic) * MAX_TOPICS + sizeof(ip_topics) + sizeof(int) * 2;
    if ((shmid = shmget(IPC_PRIVATE, espaco, IPC_CREAT | 0777)) == -1)
    {
        kill(pai, SIGINT);
    }

    // Attach shared memory
    if ((users = (User *)shmat(shmid, NULL, 0)) == (User *)-1)
    {
        perror("shm");
        kill(pai, SIGINT);
    }

    topics = (Topic *)&users[MAX_USERS];
    ip_topics = (char(*)[16]) & topics[MAX_TOPICS];
    numUsers = (int *)&ip_topics[MAX_TOPICS];
    numTopics = (int *)((char *)numUsers + sizeof(int));

    // Inicializar as variaveis em SHM
    for (int i = 0; i < MAX_TOPICS; i++)
    {
        strncpy(ip_topics[i], ip[i], 16);
    }

    *numUsers = 0;
    *numTopics = 0;

    FILE *configFile;
    char line[MAX_LINE_LEN];

    // Abrir o ficheiro de configuração para leitura
    configFile = fopen(conf, "r");
    if (configFile == NULL)
    {
        printf("Erro ao abrir o ficheiro de configuração\n");
        return 1;
    }
    // Ler os dados do ficheiro de configuração e armazenar na estrutura de utilizadores
    while (fgets(line, MAX_LINE_LEN, configFile) != NULL && *numUsers < MAX_USERS)
    {
        // Remover a quebra de linha do final da linha lida
        line[strcspn(line, "\n")] = '\0';

        // Fazer o parsing da linha para obter os dados de utilizador
        char *username = strtok(line, ";");
        char *password = strtok(NULL, ";");
        char *usertype = strtok(NULL, ";");

        // Copiar os dados para a estrutura de utilizadores
        if (username != NULL && password != NULL && usertype != NULL)
        {
            strncpy(users[*numUsers].username, username, MAX_USERNAME_LEN - 1);
            strncpy(users[*numUsers].password, password, MAX_PASSWORD_LEN - 1);
            strncpy(users[*numUsers].usertype, usertype, MAX_USERTYPE_LEN - 1);
            (*numUsers)++;
        }
    }
    fclose(configFile);

    sem_unlink("SEM_SYS");
    sem_sys = sem_open("SEM_SYS", O_CREAT | O_EXCL, 0700, 1);
    if (sem_sys == SEM_FAILED)
    {
        printf("ERRO AO ABRIR SEMAFORO\n");
        kill(pai, SIGINT);

        exit(1);
    }
    else
        printf("SEMAFORO CRIADO COM SUCESSO!\n");
    return 0;
}

// Função para receber comandos da consola de administração
void admin_console(int porto_config)
{
    int sockfd, n = 0;
    int isAdmin;
    struct sockaddr_in servaddr, cliaddr;
    socklen_t len;
    char buffer[BUFFER_SIZE], inputUsername[MAX_USERNAME_LEN], inputPassword[MAX_PASSWORD_LEN], *mensagem;

    // Criação do socket
    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0)
    {
        perror("Erro ao criar o socket");
        exit(1);
    }

    bzero(&servaddr, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(porto_config);

    // Associa o socket à porta de configuração
    if (bind(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0)
    {
        perror("Erro ao associar o socket à porta de configuração");
        exit(1);
    }

    printf("Servidor de consola de administração iniciado na porta %d\n", porto_config);

    while (1)
    {
        len = sizeof(cliaddr);

        // Solicita nome de usuário
        char usuario[] = "Digite o nome de usuário: ";

        sendto(sockfd, usuario, strlen(usuario), 0, (struct sockaddr *)&cliaddr, len);

        n = recvfrom(sockfd, buffer, BUFFER_SIZE, 0, (struct sockaddr *)&cliaddr, &len); // Recebe o nome de usuário
        if (n < 0)
        {
            perror("Erro ao receber dados");
            continue;
        }

        buffer[strcspn(buffer, "\n")] = '\0'; // Remove o caractere de nova linha de buffer
        strcpy(inputUsername, buffer);
        //-------------------

        // Solicita senha
        char senha[] = "Digite a senha: ";

        sendto(sockfd, senha, strlen(senha), 0, (struct sockaddr *)&cliaddr, len);

        n = recvfrom(sockfd, buffer, BUFFER_SIZE, 0, (struct sockaddr *)&cliaddr, &len); // Recebe a senha
        if (n < 0)
        {
            perror("Erro ao receber dados");
            continue;
        }

        buffer[strcspn(buffer, "\n")] = '\0'; // Remove o caractere de nova linha de buffer
        strcpy(inputPassword, buffer);
        //-------------------

        // Verifica se o nome de usuário e a senha estão corretos
        isAdmin = autenticar(inputUsername, inputPassword);

        if (isAdmin == 0)
        {
            char frase[] = "Username ou password incorretos!\nTente novamente!\n";
            sendto(sockfd, frase, strlen(frase), 0, (struct sockaddr *)&cliaddr, len);
        }
        else if (isAdmin == 1) // utilizador é admin
        {
            break;
        }
        else if (isAdmin != 1)
        {
            // Utilizador nao tem privilegios de administrador
            char frase[] = "O utilizador não tem privilegíos de administrador!.\nTente novamente!\n";
            sendto(sockfd, frase, strlen(frase), 0, (struct sockaddr *)&cliaddr, len);
        }
    }

    if (isAdmin == 1)
    {
        // Nome de usuário e senha estão corretos, inicia o loop de comandos
        char boas_vindas[] = "\nUsuário Autenticado com Sucesso!\nBem vindo!\n";
        sendto(sockfd, boas_vindas, strlen(boas_vindas), 0, (struct sockaddr *)&cliaddr, len);
        while (1)
        {
            char menu[] = "\n=== Menu ===\nADD_USER {username} {password} {administrador/leitor/jornalista}\nDEL {username}\nLIST\nQUIT\nQUIT_SERVER\n>";
            sendto(sockfd, menu, strlen(menu), 0, (struct sockaddr *)&cliaddr, len);

            n = recvfrom(sockfd, buffer, BUFFER_SIZE, 0, (struct sockaddr *)&cliaddr, &len); // Recebe os dados e o endereço do remetente
            if (n < 0)
            {
                perror("Erro ao receber dados");
                continue;
            }

            buffer[strcspn(buffer, "\n")] = '\0'; // Remove o caractere de nova linha de buffer
            char *token = strtok(buffer, " ");
            if (token == NULL)
            {
                mensagem = "COMANDO INVALIDO!\n";
            }

            else if (strcmp(token, "ADD_USER") == 0)
            {
                char tipo[256];
                int controlo = 0;

                sem_wait(sem_sys);
                if (*numUsers == MAX_USERS)
                {
                    mensagem = "Numero maximo de utilizadores atingido\n";
                    sendto(sockfd, mensagem, strlen(mensagem), 0, (struct sockaddr *)&cliaddr, len);
                    sem_post(sem_sys);
                    continue;
                }

                token = strtok(NULL, " ");
                strcpy(inputUsername, token);

                token = strtok(NULL, " ");
                strcpy(inputPassword, token);

                token = strtok(NULL, " ");
                strcpy(tipo, token);

                for (int i = 0; i < *numUsers; i++)
                {
                    if (strcmp(users[i].username, inputUsername) == 0)
                    {
                        controlo = 1;
                        mensagem = "Nome de utilizador inserido ja existe\n";
                        break;
                    }
                }

                if (controlo == 0)
                {
                    if (inputUsername[0] != '\0' && inputPassword[0] != '\0' && tipo[0] != '\0')
                    {
                        strcpy(users[*numUsers].username, inputUsername);
                        strcpy(users[*numUsers].password, inputPassword);
                        strcpy(users[*numUsers].usertype, tipo);
                        (*numUsers)++;
                        mensagem = "UTILIZADOR ADICIONADO COM SUCESSO!\n";
                    }
                    else
                    {
                        mensagem = "Formato invalido\n";
                    }
                }
                sem_post(sem_sys);
            }
            else if (strcmp(token, "DEL") == 0)
            {
                int index = -1;
                token = strtok(NULL, " ");
                strcpy(inputUsername, token);

                sem_wait(sem_sys);

                // Procura o índice do usuário com o nome de usuário fornecido
                for (int i = 0; i < *numUsers; i++)
                {
                    if (strcmp(users[i].username, inputUsername) == 0)
                    {
                        index = i;
                        break;
                    }
                }

                if (index != -1)
                {
                    // Move os usuários à direita do usuário removido uma posição para a esquerda
                    for (int i = index; i < *numUsers - 1; i++)
                    {
                        strcpy(users[i].username, users[i + 1].username);
                        strcpy(users[i].password, users[i + 1].password);
                        strcpy(users[i].usertype, users[i + 1].usertype);
                    }

                    (*numUsers)--;
                    mensagem = "UTILIZADOR ELIMINADO COM SUCESSO\n";
                }
                else
                {
                    mensagem = "UTILIZADOR NAO EXISTE!\n";
                }
                sem_post(sem_sys);
            }
            else if (strcmp(token, "LIST") == 0)
            {
                char dados[500];
                strcpy(dados, "\n---USERS---\n");

                sem_wait(sem_sys);

                for (int i = 0; i < *numUsers; i++)
                {
                    strcat(dados, users[i].username);
                    strcat(dados, "\n");
                }

                sem_post(sem_sys);

                mensagem = dados;
            }
            else if (strcmp(token, "QUIT") == 0)
            {
                char comando[] = "Consola a encerrar\n";
                sendto(sockfd, comando, strlen(comando), 0, (struct sockaddr *)&cliaddr, len);
                close(sockfd);
                exit(0);
            }
            else if (strcmp(token, "QUIT_SERVER") == 0)
            {
                kill(pai, SIGINT);
                close(sockfd);
                exit(0);
            }
            else
            {
                mensagem = "COMANDO INVALIDO!\n";
            }

            if (sendto(sockfd, mensagem, strlen(mensagem), 0, (struct sockaddr *)&cliaddr, len) == -1)
            {
                perror("Enviar\n");
            }
            mensagem = NULL;
        }
    }
}

// Função para autenticar e identificar o tipo de utilizador
int autenticar(char *inputUsername, char *inputPassword)
{
    int controlo, exists = 0;

    sem_wait(sem_sys);
    // Verifica se o nome de usuário e a senha estão corretos
    for (int i = 0; i < MAX_USERS; i++)
    {
        if (strcmp(users[i].username, inputUsername) == 0 && strcmp(users[i].password, inputPassword) == 0)
        {
            exists = 1;
            if (strcmp(users[i].usertype, "administrator") == 0)
            {
                controlo = 1;
            }
            else if (strcmp(users[i].usertype, "leitor") == 0)
            {
                controlo = 2;
            }
            else if (strcmp(users[i].usertype, "jornalista") == 0)
            {
                controlo = 3;
            }
            break;
        }
    }
    sem_post(sem_sys);

    if (exists == 0)
    {
        controlo = 0;
    }

    return controlo;
}

// Função para lidar com as coneções dos clientes
void handleClient(int porto_noticias)
{
    char buffer[1024];
    int bytesRead;

    int serverSocket, clientSocket;
    struct sockaddr_in serverAddr, clientAddr;
    socklen_t clientLen;
    pid_t childPid;

    // Criar o socket do servidor
    if ((serverSocket = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    {
        perror("Erro ao criar o socket");
        exit(1);
    }

    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddr.sin_port = htons(porto_noticias);

    // Associar o socket a um endereço e porta
    if (bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) == -1)
    {
        perror("Erro ao associar o socket");
        exit(1);
    }

    // Habilitar o servidor para aceitar conexões
    if (listen(serverSocket, 0) == -1)
    {
        perror("Erro ao escutar o socket");
        exit(1);
    }

    printf("\nServidor aguardando conexões TCP na porta %d...\n", porto_noticias);

    while (1)
    {
        clientLen = sizeof(clientAddr);

        // Aceitar a conexão do cliente
        if ((clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientLen)) == -1)
        {
            perror("Erro ao aceitar a conexão");
            continue;
        }

        printf("Conexão estabelecida!\n");

        // Criar um processo filho para lidar com o cliente
        if ((childPid = fork()) == -1)
        {
            perror("Erro ao criar o processo filho");
            exit(1);
        }
        else if (childPid == 0)
        {
            memset(buffer, 0, sizeof(buffer));
            char user[MAX_USERNAME_LEN], pass[MAX_PASSWORD_LEN], mensagem[BUFFER_SIZE];
            int dados;
            while ((bytesRead = recv(clientSocket, buffer, sizeof(buffer), 0)) > 0)
            {
                char *token = strtok(buffer, ";");
                strcpy(user, token);
                token = strtok(NULL, ";");
                strcpy(pass, token);

                dados = autenticar(user, pass);
                // Leitor
                if (dados == 2)
                {
                    char menu[] = "\n=== Menu ===\n->LIST_TOPICS\n->SUBSCRIBE_TOPIC <id do tópico>\n>";
                    strcpy(mensagem, "");
                    while (1)
                    {

                        strcat(mensagem, menu);
                        int menuSize = sizeof(mensagem) - 1; // Ignorar o caractere nulo no final da string
                        int bytesSent = send(clientSocket, mensagem, menuSize, 0);

                        if (bytesSent != menuSize)
                        {
                            perror("enviar");
                            kill(pai, SIGINT);
                        }

                        memset(mensagem, 0, sizeof(mensagem));
                        memset(buffer, 0, sizeof(buffer));

                        recv(clientSocket, buffer, sizeof(buffer), 0);

                        char *token = strtok(buffer, " ");

                        if (strcmp(token, "LIST_TOPICS") == 0)
                        {
                            sem_wait(sem_sys);
                            if (*numTopics == 0)
                            {
                                strcpy(mensagem, "Nao Existem Topicos Criados de Momento\n");
                            }
                            else
                            {
                                strcpy(mensagem, ""); // Inicializa a string vazia
                                for (int i = 0; i < *numTopics; i++)
                                {
                                    strcat(mensagem, topics[i].id);
                                    strcat(mensagem, " - ");
                                    strcat(mensagem, topics[i].titulo);
                                    strcat(mensagem, "\n");
                                }
                            }
                            sem_post(sem_sys);
                        }
                        else if (strcmp(token, "SUBSCRIBE_TOPIC") == 0)
                        {
                            sem_wait(sem_sys);

                            if (*numTopics == 0)
                            {
                                strcpy(mensagem, "Nao Existem Topicos Criados de Momento\n");
                                sem_post(sem_sys);
                            }
                            else
                            {
                                sem_post(sem_sys);

                                char *id = strtok(NULL, " ");
                                if (id == NULL)
                                {
                                    strcpy(mensagem, "Parametros Invalidos! Tópico NÃO subscrito!\n");
                                }
                                else
                                {
                                    char *ip = subscribe(id, user);
                                    if (ip != NULL)
                                    {
                                        sprintf(mensagem, "Topico subscrito com sucesso, com ip -%s\n", ip);
                                    }
                                    else
                                    {
                                        strcpy(mensagem, "ID INVALIDO\n");
                                    }
                                }
                            }
                        }

                        else if (strcmp(token, "EXIT") == 0)
                        {
                            break;
                        }
                        else
                        {
                            strcpy(mensagem, "COMANDO INVALIDO\n");
                        }
                        send(clientSocket, mensagem, sizeof(mensagem), 0);
                        memset(mensagem, 0, sizeof(mensagem));
                    }
                }

                // Jornalista
                else if (dados == 3)
                {
                    char menu[] = "\n=== Menu ===\n->LIST_TOPICS\n->SUBSCRIBE_TOPIC <id do tópico>\n->CREATE_TOPIC <id do tópico> <título do tópico>\n->SEND_NEWS <id do tópico> <noticia>\n>";
                    strcpy(mensagem, "");
                    while (1)
                    {

                        strcat(mensagem, menu);
                        int menuSize = sizeof(mensagem) - 1; // Ignorar o caractere nulo no final da string
                        int bytesSent = send(clientSocket, mensagem, menuSize, 0);

                        if (bytesSent != menuSize)
                        {
                            perror("enviar");
                            kill(pai, SIGINT);
                        }

                        memset(mensagem, 0, sizeof(mensagem));
                        memset(buffer, 0, sizeof(buffer));

                        recv(clientSocket, buffer, sizeof(buffer), 0);

                        char *token = strtok(buffer, " ");

                        if (strcmp(token, "LIST_TOPICS") == 0)
                        {
                            sem_wait(sem_sys);
                            if (*numTopics == 0)
                            {
                                strcpy(mensagem, "Nao Existem Topicos Criados de Momento\n");
                            }
                            else
                            {
                                strcpy(mensagem, ""); // Inicializa a string vazia
                                for (int i = 0; i < *numTopics; i++)
                                {
                                    strcat(mensagem, topics[i].id);
                                    strcat(mensagem, " - ");
                                    strcat(mensagem, topics[i].titulo);
                                    strcat(mensagem, "\n");
                                }
                            }
                            sem_post(sem_sys);
                        }
                        else if (strcmp(token, "SUBSCRIBE_TOPIC") == 0)
                        {
                            sem_wait(sem_sys);

                            if (*numTopics == 0)
                            {
                                strcpy(mensagem, "Nao Existem Topicos Criados de Momento\n");
                                sem_post(sem_sys);
                            }
                            else
                            {
                                sem_post(sem_sys);

                                char *id = strtok(NULL, " ");
                                if (id == NULL)
                                {
                                    strcpy(mensagem, "Parametros Invalidos! Tópico NÃO subscrito!\n");
                                }
                                else
                                {
                                    char *ip = subscribe(id, user);
                                    if (ip != NULL)
                                    {
                                        sprintf(mensagem, "Topico subscrito com sucesso, com ip-%s\n", ip);
                                    }
                                    else
                                    {
                                        strcpy(mensagem, "ID INVALIDO\n");
                                    }
                                }
                            }
                        }
                        else if (strcmp(token, "CREATE_TOPIC") == 0)
                        {
                            char *id = strtok(NULL, " ");
                            char *titulo = strtok(NULL, " ");
                            if (id == NULL || titulo == NULL)
                            {
                                strcpy(mensagem, "Parametros Invalidos! Tópico NÃO criado!\n");
                            }
                            else
                            {
                                create_topic(id, titulo);
                                sem_wait(sem_sys);
                                sprintf(mensagem, "Topico criado com sucesso\n");
                                sem_post(sem_sys);
                            }
                        }
                        else if (strcmp(token, "SEND_NEWS") == 0)
                        {
                            char *id = strtok(NULL, " ");
                            char *noticia = strtok(NULL, " ");
                            if (id == NULL || noticia == NULL)
                            {
                                strcpy(mensagem, "Parametros Invalidos! Noticia NÃO enviada!\n");
                            }
                            else
                            {
                                send_news(id, noticia);
                                strcpy(mensagem, "Noticia enviada com sucesso\n");
                            }
                        }
                        else if (strcmp(token, "EXIT") == 0)
                        {
                            break;
                        }
                        else
                        {
                            strcpy(mensagem, "COMANDO INVALIDO\n");
                        }

                        /*send(clientSocket, mensagem, sizeof(mensagem), 0);
                        memset(mensagem, 0, sizeof(mensagem));*/
                    }
                }

                // Admin ou dados errados
                else
                {
                    sprintf(mensagem, "%d", dados);
                    send(clientSocket, mensagem, sizeof(mensagem), 0);
                }
                memset(buffer, 0, sizeof(buffer));
            }

            // Close the client socket in the child process
            close(clientSocket);
            exit(0);
        }
    }
    // Close the client socket in the parent process
    close(clientSocket);
}

// Função para associar um cliente a um tópico
char *subscribe(char *id, char *user)
{
    char *ip;
    sem_wait(sem_sys);
    for (int i = 0; i < *numTopics; i++)
    {
        if (strcmp(topics[i].id, id) == 0)
        {
            strcpy(topics[i].subscribers[topics[i].num_subs], user);
            topics[i].num_subs++;
            ip = topics[i].ip;
            break;
        }
    }

    sem_post(sem_sys);

    return ip;
}

// Função para criar um topico
void create_topic(char *id, char *titulo)
{
    sem_wait(sem_sys);

    strcpy(topics[*numTopics].id, id);
    strcpy(topics[*numTopics].titulo, titulo);
    strcpy(topics[*numTopics].ip, ip_topics[0]);

    for (int i = 0; i < (MAX_TOPICS - (*numTopics)); i++)
    {
        strcpy(ip_topics[i], ip_topics[i + 1]);
    }

    ip_topics[*numTopics][0] = '\0';

    for (int i = 0; i < *numTopics; i++)
    {
        printf("%s\n", ip_topics[i]);
    }

    (*numTopics)++;
    sem_post(sem_sys);
}

// Função para enviar noticia
void send_news(char *topic_id, char *news)
{
    int sock;
    struct sockaddr_in addr;
    char ip[16];

    sem_wait(sem_sys);

    for (int i = 0; i < *numTopics; i++)
    {
        if (strcmp(topics[i].id, topic_id) == 0)
        {
            strcpy(ip, topics[i].ip);
            break;
        }
    }

    sem_post(sem_sys);

    // Cria a socket UDP
    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("socket");
        exit(1);
    }

    // Configura a estrutura de endereço multicast
    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr(ip);
    addr.sin_port = htons(5000);

    // Habilita o multicast na socket
    int enable = 1;
    if (setsockopt(sock, IPPROTO_IP, 4, &enable, sizeof(enable)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }

    // Envia a mensagem multicast
    if (sendto(sock, news, strlen(news), 0, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("sendto");
        exit(1);
    }

    close(sock);
}

void cleanup()
{
    kill(admin, SIGTERM);
    while (wait(NULL) < 0)
        ;

    if (shmctl(shmid, IPC_RMID, NULL) == -1)
    {
        perror("Erro ao remover a memória compartilhada");
    }

    if (sem_close(sem_sys) == -1)
    {
        perror("Erro ao fechar o semáforo");
    }

    if (sem_unlink("SEM_SYS") == -1)
    {
        perror("Erro ao remover o semáforo");
    }

    FILE *configFile = fopen(config_file, "w");
    if (configFile == NULL)
    {
        printf("Erro ao abrir o ficheiro de configuração\n");
        return;
    }

    for (int i = 0; i < *numUsers; i++)
    {
        fprintf(configFile, "%s;%s;%s\n", users[i].username, users[i].password, users[i].usertype);
    }

    fclose(configFile);

    exit(0);
}
