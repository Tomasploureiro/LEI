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

#define BUFF_SIZE 1024
#define MAX_CLASSES 10
#define MAX_USERS 100
#define MAX_ALUNOS 100

typedef struct
{
  char username[BUFF_SIZE];
  char password[BUFF_SIZE];
  char user_type[BUFF_SIZE];
  char classes[MAX_CLASSES];
} User;


typedef struct
{
    char name[BUFF_SIZE];
    char students[MAX_ALUNOS][BUFF_SIZE];
    int max_alunos;
    int num_alunos;
    char ip[16];
} Class;

char ip[MAX_CLASSES][16] = {"224.0.0.1", "224.0.0.2", "224.0.0.3", "224.0.0.4", "224.0.0.5", "224.0.0.6", "224.0.0.7", "224.0.0.8"};

char *config_file;

pid_t mainp, admin;

int shmid;
Class *classes;
User *users;
char (*ip_classes)[16];
int *numUsers, *numClasses;

sem_t *sem_sys;

int init(const char *);
int autenticar(char *, char *);
void start_server(int);
void handleClient(int);
char *subscribe(char *, char *);
void create_class(char *, char *);
void send_message(char *, char *);

int init(const char *file){

    int shmSize = sizeof(User) * MAX_USERS + sizeof(Class) * MAX_CLASSES + sizeof(ip_classes) + sizeof(int) * 2;
    if((shmid = shmget(IPC_PRIVATE, shmSize, IPC_CREAT | 0777)) == -1){
        kill(mainp, SIGINT);
    }

    if((users = (User *)shmat(shmid, NULL, 0)) == (User *)-1){
        perror("Error in Shared Memory");
        kill(mainp, SIGINT);
    }
    classes = (Class*) &users[MAX_USERS];
    ip_classes = (char(*)[16]) & classes[MAX_CLASSES];
    numUsers = (int *)&ip_classes[MAX_CLASSES];
    numClasses = (int *)((char *)numUsers + sizeof(int));

    for (int i = 0; i < MAX_CLASSES; i++){
        strncpy(ip_classes[i], ip[i], 16);
    }

    *numUsers = 0;
    *numClasses = 0;


    char line[BUFF_SIZE];
    FILE * configFile = fopen(file, "r");
    if(configFile == NULL){
        printf("Erro ao abrir o ficheiro de configuração\n");
        return 1;
    }

    while (fgets(line, BUFF_SIZE, configFile) != NULL && *numUsers < MAX_USERS){
        line[strcspn(line, "\n")] = '\0';

        char *username = strtok(line, ";");
        char *password = strtok(NULL, ";");
        char *usertype = strtok(NULL, ";");

        if (username != NULL && password != NULL && usertype != NULL){
            strncpy(users[*numUsers].username, username, BUFF_SIZE - 1);
            strncpy(users[*numUsers].password, password, BUFF_SIZE - 1);
            strncpy(users[*numUsers].user_type, usertype, BUFF_SIZE - 1);
            (*numUsers)++;
        }
    }
    fclose(configFile);

    sem_unlink("SEM_SYS");
    sem_sys = sem_open("SEM_SYS", O_CREAT | O_EXCL, 0700, 1);
    if (sem_sys == SEM_FAILED){
        printf("ERRO AO ABRIR SEMAFORO\n");
        kill(mainp, SIGINT);

        exit(1);
    }
    else
        printf("SEMAFORO CRIADO COM SUCESSO!\n");
    return 0;
}

void start_server(int porto_config){
    int sockfd, n = 0;
    int isAdmin;
    struct sockaddr_in servaddr, cliaddr;
    socklen_t len;
    char buffer[BUFF_SIZE], inputUsername[BUFF_SIZE], inputPassword[BUFF_SIZE], comando[BUFF_SIZE], *mensagem;

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0){
        perror("Erro ao criar o socket");
        exit(1);
    }

    bzero(&servaddr, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = INADDR_ANY;
    servaddr.sin_port = htons(porto_config);

    if (bind(sockfd, (struct sockaddr *)&servaddr, sizeof(servaddr)) < 0){
        perror("Erro ao associar o socket à porta de configuração");
        exit(1);
    }

    printf("Servidor de consola de administração iniciado na porta %d\n", porto_config);
    while (1){
        len = sizeof(cliaddr);

        char start[] = ">>";

        sendto(sockfd, start, strlen(start), 0, (struct sockaddr *)&cliaddr, len);

        n = recvfrom(sockfd, buffer, BUFF_SIZE, 0, (struct sockaddr *)&cliaddr, &len);
        if (n < 0){
            perror("Erro ao receber dados");
            continue;
        }

        if (sscanf(buffer, "%s %s %s", comando, inputUsername, inputPassword) == 3) {
            if (strcmp(comando, "LOGIN") == 0) {
                 isAdmin = autenticar(inputUsername, inputPassword);
            }else{
                isAdmin = 0;
            }
        }else{
            isAdmin = 0;
        }

        if (isAdmin == 0){
            char frase[] = "REJECTED\n";
            sendto(sockfd, frase, strlen(frase), 0, (struct sockaddr *)&cliaddr, len);
        }
        else if (isAdmin == 1){
            break;
        }
        else if (isAdmin != 1){
            char frase[] = "REJECTED : NOT ADMIN\n";
            sendto(sockfd, frase, strlen(frase), 0, (struct sockaddr *)&cliaddr, len);
        }
    }
        if (isAdmin == 1){
        char boas_vindas[] = "OK\n";
        sendto(sockfd, boas_vindas, strlen(boas_vindas), 0, (struct sockaddr *)&cliaddr, len);
        while (1){
            char menu[] = "\n=== Menu ===\nADD_USER {username} {password} {administrador/aluno/professor}\nDEL {username}\nLIST\nQUIT_SERVER\n>";
            sendto(sockfd, menu, strlen(menu), 0, (struct sockaddr *)&cliaddr, len);

            n = recvfrom(sockfd, buffer, BUFF_SIZE, 0, (struct sockaddr *)&cliaddr, &len);
            if (n < 0){
                perror("Erro ao receber dados");
                continue;
            }

            buffer[strcspn(buffer, "\n")] = '\0';
            char *token = strtok(buffer, " ");
            if (token == NULL){
                mensagem = "COMANDO INVALIDO!\n";
            }

            else if (strcmp(token, "ADD_USER") == 0){
                char tipo[256];
                int controlo = 0;

                sem_wait(sem_sys);
                if (*numUsers == MAX_USERS){
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

                for (int i = 0; i < *numUsers; i++){
                    if (strcmp(users[i].username, inputUsername) == 0){
                        controlo = 1;
                        mensagem = "Nome de utilizador inserido ja existe\n";
                        break;
                    }
                }

                if (controlo == 0){
                    if (inputUsername[0] != '\0' && inputPassword[0] != '\0' && tipo[0] != '\0'){
                        strcpy(users[*numUsers].username, inputUsername);
                        strcpy(users[*numUsers].password, inputPassword);
                        strcpy(users[*numUsers].user_type, tipo);
                        (*numUsers)++;
                        mensagem = "UTILIZADOR ADICIONADO COM SUCESSO!\n";
                    }
                    else{
                        mensagem = "Formato invalido\n";
                    }
                }
                sem_post(sem_sys);
            }
            else if (strcmp(token, "DEL") == 0){
                int index = -1;
                token = strtok(NULL, " ");
                strcpy(inputUsername, token);

                sem_wait(sem_sys);

                for (int i = 0; i < *numUsers; i++){
                    if (strcmp(users[i].username, inputUsername) == 0){
                        index = i;
                        break;
                    }
                }

                if (index != -1){
                    for (int i = index; i < *numUsers - 1; i++){
                        strcpy(users[i].username, users[i + 1].username);
                        strcpy(users[i].password, users[i + 1].password);
                        strcpy(users[i].user_type, users[i + 1].user_type);
                    }

                    (*numUsers)--;
                    mensagem = "UTILIZADOR ELIMINADO COM SUCESSO\n";
                }
                else{
                    mensagem = "UTILIZADOR NAO EXISTE!\n";
                }
                sem_post(sem_sys);
            }
            else if (strcmp(token, "LIST") == 0)
            {
                char dados[500];
                strcpy(dados, "\n---USERS---\n");

                sem_wait(sem_sys);

                for (int i = 0; i < *numUsers; i++){
                    strcat(dados, users[i].username);
                    strcat(dados, "\n");
                }

                sem_post(sem_sys);

                mensagem = dados;
            }
            else if (strcmp(token, "QUIT_SERVER") == 0){
                kill(mainp, SIGINT);
                close(sockfd);
                exit(0);
            }
            else{
                mensagem = "COMANDO INVALIDO!\n";
            }

            if (sendto(sockfd, mensagem, strlen(mensagem), 0, (struct sockaddr *)&cliaddr, len) == -1){
                perror("Enviar\n");
            }
            mensagem = NULL;
        }
    }

}

int autenticar(char *inputUsername, char *inputPassword){

    int controlo, exists = 0;

    sem_wait(sem_sys);
    for (int i = 0; i < MAX_USERS; i++){
        if (strcmp(users[i].username, inputUsername) == 0 && strcmp(users[i].password, inputPassword) == 0){
            exists = 1;
            if (strcmp(users[i].user_type, "administrator") == 0){
                controlo = 1;
            }
            else if (strcmp(users[i].user_type, "aluno") == 0){
                controlo = 2;
            }
            else if (strcmp(users[i].user_type, "professor") == 0){
                controlo = 3;
            }
            break;
        }
    }
    sem_post(sem_sys);
        if (exists == 0){
        controlo = 0;
    }

    return controlo;

}

void handleClient(int porto_turmas){
    char buffer[BUFF_SIZE];
    int bytesRead;

    int serverSocket, clientSocket;
    struct sockaddr_in serverAddr, clientAddr;
    socklen_t clientLen;
    pid_t childPid;

    if ((serverSocket = socket(AF_INET, SOCK_STREAM, 0)) == -1){
        perror("Erro ao criar o socket");
        exit(1);
    }
    memset(&serverAddr, 0, sizeof(serverAddr));
    serverAddr.sin_family = AF_INET;
    serverAddr.sin_addr.s_addr = htonl(INADDR_ANY);
    serverAddr.sin_port = htons(porto_turmas);
    if (bind(serverSocket, (struct sockaddr *)&serverAddr, sizeof(serverAddr)) == -1){
        perror("Erro ao associar o socket");
        exit(1);
    }

    if (listen(serverSocket, 0) == -1){
        perror("Erro ao escutar o socket");
        exit(1);
    }

    printf("\nServidor aguardando conexões TCP na porta %d...\n", porto_turmas);

    while(1){

        clientLen = sizeof(clientAddr);

        if ((clientSocket = accept(serverSocket, (struct sockaddr *)&clientAddr, &clientLen)) == -1){
            perror("Erro ao aceitar a conexão");
            continue;
        }

        printf("Conexão estabelecida!\n");

        if ((childPid = fork()) == -1){
            perror("Erro ao criar o processo filho");
            exit(1);
        }
        else if(childPid == 0){
            memset(buffer, 0, sizeof(buffer));
            char user[BUFF_SIZE], pass[BUFF_SIZE], mensagem[BUFF_SIZE];
            int dados;
            while ((bytesRead = recv(clientSocket, buffer, sizeof(buffer), 0)) > 0){
                char *token = strtok(buffer, ";");
                strcpy(user, token);
                token = strtok(NULL, ";");
                strcpy(pass, token);

                dados = autenticar(user, pass);
                if (dados == 2){
                    char menu[] = "\n=== Menu ===\n->LIST_CLASSES\n->LIST_SUBSCRIBED\n->SUBSCRIBE_CLASS <name>\n>";
                    strcpy(mensagem, "");
                    while(1){
                        strcat(mensagem, menu);
                        int menuSize = sizeof(mensagem) - 1;
                        int bytesSent = send(clientSocket, mensagem, menuSize, 0);

                        if (bytesSent != menuSize)
                        {
                            perror("enviar");
                            kill(mainp, SIGINT);
                        }

                        memset(mensagem, 0, sizeof(mensagem));
                        memset(buffer, 0, sizeof(buffer));

                        recv(clientSocket, buffer, sizeof(buffer), 0);

                        char *token = strtok(buffer, " ");


                        if (strcmp(token, "LIST_CLASSES") == 0){
                            sem_wait(sem_sys);
                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                            }
                            else{
                                strcpy(mensagem, "");
                                for (int i = 0; i < *numClasses; i++){
                                    strcat(mensagem, classes[i].ip);
                                    strcat(mensagem, " - ");
                                    strcat(mensagem, classes[i].name);
                                    strcat(mensagem, "\n");
                                }
                            }
                            sem_post(sem_sys);
                        }
                        else if(strcmp(token, "LIST_SUBSCRIBED") == 0){
                            sem_wait(sem_sys);
                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                                sem_post(sem_sys);
                            }
                            else{
                                strcpy(mensagem, "Classes subscritas:\n");
                                int found = 0;
                                for (int i = 0; i < *numClasses; i++) {
                                    for (int j = 0; j < classes[i].num_alunos; j++) {
                                        if (strcmp(classes[i].students[j], user) == 0) {
                                            strcat(mensagem, classes[i].name);
                                            strcat(mensagem, " - IP: ");
                                            strcat(mensagem, classes[i].ip);
                                            strcat(mensagem, "\n");
                                            found = 1;
                                            break;
                                        }
                                    }
                                }
                                if (!found) {
                                strcpy(mensagem, "Você não está inscrito em nenhuma classe.\n");
                                }
                            sem_post(sem_sys);
                            }
                        }
                        else if(strcmp(token, "SUBSCRIBE_CLASS") == 0){
                            sem_wait(sem_sys);

                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                                sem_post(sem_sys);
                            }
                            else{
                                sem_post(sem_sys);

                                char *name = strtok(NULL, " ");
                                if (name == NULL){
                                    strcpy(mensagem, "Parametros Invalidos! Classe NÃO subscrita!\n");
                                }
                                else{
                                    char *ip = subscribe(name, user);
                                    if (ip != NULL){
                                        sprintf(mensagem, "Classe subscrita com sucesso, com ip -%s\n", ip);
                                    }
                                    else{
                                        strcpy(mensagem, "ID INVALIDO\n");
                                    }
                                    
                                }
                            }
                        }
                        else if (strcmp(token, "EXIT") == 0){
                            break;
                        }
                        else{
                            strcpy(mensagem, "COMANDO INVALIDO\n");
                        }
                        send(clientSocket, mensagem, sizeof(mensagem), 0);
                        memset(mensagem, 0, sizeof(mensagem));
                    }
                }
                else if(dados == 3){
                    char menu[] = "\n=== Menu ===\n->LIST_CLASSES\n->LIST_SUBSCRIBED\n->SUBSCRIBE_CLASS <id da classe>\n->CREATE_CLASS <name> <size>\n->SEND <name> <text that server will send to subscribers>\n>";
                    strcpy(mensagem, "");
                    while(1){
                        strcat(mensagem, menu);
                        int menuSize = sizeof(mensagem) - 1;
                        int bytesSent = send(clientSocket, mensagem, menuSize, 0);

                        if (bytesSent != menuSize){
                            perror("enviar");
                            kill(mainp, SIGINT);
                        }

                        memset(mensagem, 0, sizeof(mensagem));
                        memset(buffer, 0, sizeof(buffer));

                        recv(clientSocket, buffer, sizeof(buffer), 0);

                        char *token = strtok(buffer, " ");

                        if (strcmp(token, "LIST_CLASSES") == 0){
                            sem_wait(sem_sys);
                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                            }
                            else{
                                strcpy(mensagem, "CLASS ");
                                for (int i = 0; i < *numClasses; i++){
                                    strcat(mensagem, classes[i].name);
                                    strcat(mensagem, ", ");
                                }
                                strcat(mensagem, "\n");
                            }
                            sem_post(sem_sys);
                        }
                        else if(strcmp(token, "LIST_SUBSCRIBED") == 0){
                            sem_wait(sem_sys);
                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                            }
                            else{
                                strcpy(mensagem, "CLASS:\n");
                                int found = 0;
                                for (int i = 0; i < *numClasses; i++) {
                                    for (int j = 0; j < classes[i].num_alunos; j++) {
                                        if (strcmp(classes[i].students[j], user) == 0) {
                                            strcat(mensagem, classes[i].name);
                                            strcat(mensagem, "/");
                                            strcat(mensagem, classes[i].ip);
                                            strcat(mensagem, " ");
                                            found = 1;
                                            break;
                                        }
                                    }
                                }
                                strcat(mensagem, "\n");
                                if (!found) {
                                strcpy(mensagem, "Você não está inscrito em nenhuma classe.\n");
                                }
                            }
                            sem_post(sem_sys);
                        }
                        else if(strcmp(token, "SUBSCRIBE_CLASS") == 0){
                            sem_wait(sem_sys);
                            if (*numClasses == 0){
                                strcpy(mensagem, "Nao Existem Classes Criadas de Momento\n");
                                sem_post(sem_sys);
                            }
                            else{
                                sem_post(sem_sys);

                                char *name = strtok(NULL, " ");
                                if (name == NULL){
                                    strcpy(mensagem, "Parametros Invalidos! Classe NÃO subscrita!\n");
                                }
                                else{
                                    char *ip = subscribe(name, user);
                                    if (ip != NULL){
                                        sprintf(mensagem, "ACCEPTED %s\n", ip);
                                    }
                                    else{
                                        strcpy(mensagem, "ID INVALIDO\n");
                                    }
                                    
                                }
                            }
                        }
                        else if(strcmp(token, "CREATE_CLASS") == 0){
                            char *name = strtok(NULL, " ");
                            char *size = strtok(NULL, " ");
                            if (name == NULL || size == NULL){
                                strcpy(mensagem, "Parametros Invalidos! Tópico NÃO criado!\n");
                            }   
                            else{
                                create_class(name, size);
                                sem_wait(sem_sys);
                                sprintf(mensagem, "Classe criado com sucesso\n");
                                sem_post(sem_sys);
                            }
                        }
                        else if(strcmp(token, "SEND") == 0){
                            char *name = strtok(NULL, " ");
                            char *mess = strtok(NULL, " ");
                            if (name == NULL || mess == NULL){
                                strcpy(mensagem, "Parametros Invalidos! Mensagem NÃO enviada!\n");
                            }
                            else{
                                send_message(name, mess);
                                strcpy(mensagem, "Mensagem enviada com sucesso\n");
                            }
                        }
                        else if (strcmp(token, "EXIT") == 0){
                            break;
                        }
                        else{
                            strcpy(mensagem, "COMANDO INVALIDO\n");
                        }
                        
                    }
                }        
                else{
                    sprintf(mensagem, "%d", dados);
                    send(clientSocket, mensagem, sizeof(mensagem), 0);
                }
                memset(buffer, 0, sizeof(buffer));
            }
            close(clientSocket);
            exit(0);
        }
    }
    close(clientSocket);
}

char *subscribe(char * name, char *user){
    char *ip;
    sem_wait(sem_sys);
    for (int i = 0; i < *numClasses; i++)
    {
        if (strcmp(classes[i].name, name) == 0)
        {
            strcpy(classes[i].students[classes[i].num_alunos], user);
            classes[i].num_alunos++;
            ip = classes[i].ip;
            break;
        }
    }

    sem_post(sem_sys);
    return ip;
}

void create_class(char *name, char *size){
    int sizeAux = atoi(size);
    sem_wait(sem_sys);
    if (*numClasses < MAX_CLASSES) {
        strncpy(classes[*numClasses].name, name, BUFF_SIZE - 1);
        classes[*numClasses].max_alunos = sizeAux;
        classes[*numClasses].num_alunos = 0;
        strncpy(classes[*numClasses].ip, ip[*numClasses], 16);
        (*numClasses)++;
        printf("Classe '%s' criada com sucesso.\n", name);
    } else {
        printf("Limite máximo de classes alcançado.\n");
    }
    sem_post(sem_sys);
}
void send_message(char *name, char *message){

    int sock;
    struct sockaddr_in addr;
    char ip[16];

    sem_wait(sem_sys);

    for (int i = 0; i < *numClasses; i++){
        if (strcmp(classes[i].name, name) == 0)
        {
            strcpy(ip, classes[i].ip);
            break;
        }
    }

    sem_post(sem_sys);

    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("socket");
        exit(1);
    }
    

    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = inet_addr(ip);
    addr.sin_port = htons(5000);

    int enable = 1;
    if (setsockopt(sock, IPPROTO_IP, 4, &enable, sizeof(enable)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }

    if (sendto(sock, message, strlen(message), 0, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("sendto");
        exit(1);
    }

    close(sock);


}

void cleanup(){
    kill(admin, SIGTERM);
    while (wait(NULL) < 0)
        ;

    if (shmctl(shmid, IPC_RMID, NULL) == -1){
        perror("Erro ao remover a memória compartilhada");
    }

    if (sem_close(sem_sys) == -1){
        perror("Erro ao fechar o semáforo");
    }

    if (sem_unlink("SEM_SYS") == -1){
        perror("Erro ao remover o semáforo");
    }

    FILE *configFile = fopen(config_file, "w");
    if (configFile == NULL){
        printf("Erro ao abrir o ficheiro de configuração\n");
        return;
    }
    for (int i = 0; i < *numUsers; i++){
        printf("%s;%s;%s\n",users[i].username, users[i].password, users[i].user_type);
        fprintf(configFile, "%s;%s;%s\n", users[i].username, users[i].password, users[i].user_type);
    }

    fclose(configFile);

    exit(0);
}
int main(int argc, char *argv[])
{
    if (argc != 4)
    {
        printf("Uso: %s {PORTO_TURMAS} {PORTO_CONFIG} {ficheiro configuração}\n", argv[0]);
        return 1;
    }
    int porto_turmas = atoi(argv[1]);
    int porto_config = atoi(argv[2]);
    config_file = argv[3];
    int ctrl;
    mainp = getpid();

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
        start_server(porto_config);
    }
    else
    {
        handleClient(porto_turmas);
    }
    return 0;

}
