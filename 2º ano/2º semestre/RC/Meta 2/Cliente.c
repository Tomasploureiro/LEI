#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netinet/in.h>
#include <signal.h>

#define BUFFER_SIZE 1024
#define MAX_TOPICS 8

int sock;
struct sockaddr_in server_addr;
struct ip_mreq mreq;

pid_t childs[MAX_TOPICS];
int n_child = 0;

void subscribe(char *);
void cleanup_child();
void cleanup();

int autenticar()
{
    char buffer[BUFFER_SIZE], user_data[BUFFER_SIZE], buffer2[BUFFER_SIZE];

    while (1)
    {

        // Solicita nome de usuário
        memset(buffer, 0, sizeof(buffer));
        printf("Digite o nome de usuário: ");
        fgets(buffer, sizeof(buffer), stdin);

        buffer[strcspn(buffer, "\n")] = '\0'; // Remove o caractere de nova linha de buffer
        strcpy(user_data, buffer);
        //-------------------

        strcat(user_data, ";");

        // Solicita senha
        printf("Digite a senha: ");

        fgets(buffer, sizeof(buffer), stdin);

        buffer[strcspn(buffer, "\n")] = '\0'; // Remove o caractere de nova linha de buffer
        strcat(user_data, buffer);
        //-------------------

        if (send(sock, user_data, strlen(user_data), 0) < 0)
        {
            perror("Erro ao enviar mensagem para o servidor.\n");
            return 1;
        }

        memset(buffer, 0, sizeof(buffer));

        // Receber uma mensagem do servidor
        if (recv(sock, buffer, BUFFER_SIZE, 0) < 0)
        {
            printf("Erro ao receber mensagem do servidor.\n");
            return 1;
        }
        if (strcmp(buffer, "0") == 0)
        {
            printf("Dados Errados\n");
        }
        else if (strcmp(buffer, "1") == 0)
        {
            printf("Administrador! Consola errada!\n");
        }
        else
        {
            break;
        }
    }

    while (1)
    {
        printf("\n%s", buffer);

        memset(buffer, 0, sizeof(buffer));

        fgets(buffer, sizeof(buffer), stdin);

        buffer[strcspn(buffer, "\n")] = '\0';

        strcpy(buffer2, buffer);

        char *token = strtok(buffer2, " ");

        if (strcmp(token, "EXIT") == 0)
        {
            if (send(sock, buffer, strlen(buffer), 0) < 0)
            {
                perror("Erro ao enviar mensagem para o servidor.\n");
                return 1;
            }
            cleanup();
        }

        if (strcmp(token, "SUBSCRIBE_TOPIC") == 0)
        {
            if (send(sock, buffer, strlen(buffer), 0) < 0)
            {
                perror("Erro ao enviar mensagem para o servidor.\n");
                return 1;
            }
            memset(buffer, 0, sizeof(buffer));

            if (recv(sock, buffer, BUFFER_SIZE, 0) < 0)
            {
                perror("Erro ao receber mensagem do servidor.\n");
                return 1;
            }
            strcpy(buffer2, buffer);

            char *ip = strtok(buffer2, "-");

            ip = strtok(NULL, "-");

            pid_t child = fork();

            if (child == 0)
            {
                subscribe(ip);
            }
            else if (child == -1)
                printf("Erro a criar filho!\n");
            else
            {
                childs[n_child] = child;
                n_child++;
                printf("%s", buffer);
                memset(buffer, 0, sizeof(buffer));
                continue;
            }
        }

        if (send(sock, buffer, strlen(buffer), 0) < 0)
        {
            perror("Erro ao enviar mensagem para o servidor.\n");
            return 1;
        }

        memset(buffer, 0, sizeof(buffer));

        if (recv(sock, buffer, BUFFER_SIZE, 0) < 0)
        {
            perror("Erro ao receber mensagem do servidor.\n");
            return 1;
        }
    }
    return 0;
}

void subscribe(char *ip)
{
    struct sockaddr_in addr;
    char buffer[BUFFER_SIZE];
    ssize_t recv_len;

    // Configurar o tratamento do sinal SIGTERM
    signal(SIGUSR1, cleanup_child);

    // Step 1: Create an AF_INET, SOCK_DGRAM socket
    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("socket");
        exit(1);
    }

    // Step 2: Set the SO_REUSEADDR option
    int reuse = 1;
    if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }

    // Step 3: Bind the socket to a local port and INADDR_ANY address
    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons(5000);

    if (bind(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("bind");
        exit(1);
    }

    // Step 4: Join the multicast group
    mreq.imr_multiaddr.s_addr = inet_addr(ip);     // Multicast group address
    mreq.imr_interface.s_addr = htonl(INADDR_ANY); // Local interface address

    if (setsockopt(sock, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char *)&mreq, sizeof(mreq)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }
    // Lê e imprime as mensagens multicast
    while (1)
    {
        recv_len = recv(sock, buffer, BUFFER_SIZE - 1, 0);
        if (recv_len > 0)
        {
            buffer[recv_len] = '\0';
            printf("Noticia recebida: %s\n\n>", buffer);
        }
    }
}

int main(int argc, char *argv[])
{
    // Verificar se a porta e o endereço IP foram fornecidos como argumentos
    if (argc != 3)
    {
        printf("Por favor, forneça o endereço IP e a porta do servidor.\n");
        return 1;
    }
    signal(SIGINT, cleanup);
    // Criação do socket
    sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == -1)
    {
        printf("Erro ao criar socket.\n");
        return 1;
    }

    // Preparação das informações do servidor
    server_addr.sin_addr.s_addr = inet_addr(argv[1]);
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(atoi(argv[2]));

    // Conectar-se ao servidor
    if (connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0)
    {
        printf("Erro ao conectar-se ao servidor.\n");
        return 1;
    }

    autenticar();

    // Fechar o socket
    close(sock);

    return 0;
}

void cleanup_child()
{

    // Deixa o grupo multicast
    if (setsockopt(sock, IPPROTO_IP, IP_DROP_MEMBERSHIP, (char *)&mreq, sizeof(mreq)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }

    // Fecha a socket
    close(sock);

    // Encerrar o processo filho
    exit(0);
}

void cleanup()
{
    for (int i = 0; i < n_child; i++)
    {
        kill(childs[i], SIGUSR1);
    }
    exit(0);
}