#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <netinet/in.h>
#include <signal.h>

#define BUFFER_SIZE 1024
#define MAX_CLASSES 8

struct sockaddr_in server_addr;
struct ip_mreq mreq;

pid_t childs[MAX_CLASSES];
int n_child = 0;
int sock;

void subscribe(char *);
void cleanup_child();
void cleanup();

int autenticar() {
    char buffer[BUFFER_SIZE], buffer2[BUFFER_SIZE], comando[BUFFER_SIZE], username[BUFFER_SIZE/2], password[BUFFER_SIZE/2];
    char envio[BUFFER_SIZE];

    while (1) {
        printf(">");
        fgets(buffer, BUFFER_SIZE, stdin);
        buffer[strcspn(buffer, "\n")] = '\0';

        if (sscanf(buffer, "%s %s %s", comando, username, password) == 3) {
            if (strcmp(comando, "LOGIN") == 0) {
                snprintf(envio, BUFFER_SIZE, "%s;%s", username, password);

                if (send(sock, envio, strlen(envio), 0) < 0) {
                    perror("Erro ao enviar mensagem para o servidor.\n");
                    return 1;
                }

                memset(buffer, 0, sizeof(buffer));


                if (recv(sock, buffer, BUFFER_SIZE, 0) < 0){
                    printf("Erro ao receber mensagem do servidor.\n");
                    return 1;
                    }
                if (strcmp(buffer, "0") == 0){
                    printf("REJECTED\n");
                }
                else if (strcmp(buffer, "1") == 0){
                    printf("REJECTED : ADMIN\n");
                }
                else{
                    printf("OK\n");
                    break;
                }
            } else {
                printf("Comando inválido. Por favor, comece com 'LOGIN'.\n");
            }
        } else {
            printf("Formato inválido. Use o formato 'LOGIN {username} {password}'.\n");
        }
    }

   while (1){
        printf("\n%s", buffer);

        memset(buffer, 0, sizeof(buffer));

        fgets(buffer, sizeof(buffer), stdin);

        buffer[strcspn(buffer, "\n")] = '\0';

        strcpy(buffer2, buffer);

        char *token = strtok(buffer2, " ");

        if (strcmp(token, "EXIT") == 0){
            if (send(sock, buffer, strlen(buffer), 0) < 0){
                perror("Erro ao enviar mensagem para o servidor.\n");
                return 1;
            }
            cleanup();
        }

        if (strcmp(token, "SUBSCRIBE_CLASS") == 0)
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


void subscribe(char * ip){
    struct sockaddr_in addr;
    char buffer[BUFFER_SIZE];
    ssize_t recv_len;

    signal(SIGUSR1, cleanup_child);

    if ((sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("socket");
        exit(1);
    }

    int reuse = 1;
    if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse)) < 0)
    {
        perror("setsockopt");
        exit(1);
    }

    memset(&addr, 0, sizeof(addr));
    addr.sin_family = AF_INET;
    addr.sin_addr.s_addr = htonl(INADDR_ANY);
    addr.sin_port = htons(5000);

    if (bind(sock, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    {
        perror("bind");
        exit(1);
    }

    mreq.imr_multiaddr.s_addr = inet_addr(ip);
    mreq.imr_interface.s_addr = htonl(INADDR_ANY);

    if (setsockopt(sock, IPPROTO_IP, IP_ADD_MEMBERSHIP, (char *)&mreq, sizeof(mreq)) < 0){
        perror("setsockopt");
        exit(1);
    }
        while (1){
        recv_len = recv(sock, buffer, BUFFER_SIZE - 1, 0);
        if (recv_len > 0){
            buffer[recv_len] = '\0';
            printf("Mensagem recebida: %s\n\n>", buffer);
        }
    }
}

void cleanup_child(){

    if (setsockopt(sock, IPPROTO_IP, IP_DROP_MEMBERSHIP, (char *)&mreq, sizeof(mreq)) < 0){
        perror("setsockopt");
        exit(1);
    }

    close(sock);

    exit(0);
}

void cleanup(){
    for (int i = 0; i < n_child; i++){
        kill(childs[i], SIGUSR1);
    }
    exit(0);
}

int main(int argc, char const *argv[])
{
    if (argc != 3){
        printf("Uso: %s {endereço do servidor} {PORTO_TURMAS}\n", argv[0]);
        return 1;
    }
    signal(SIGINT, cleanup);
    sock = socket(AF_INET, SOCK_STREAM, 0);
    if (sock == -1)
    {
        printf("Erro ao criar socket.\n");
        return 1;
    }

    server_addr.sin_addr.s_addr = inet_addr(argv[1]);
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(atoi(argv[2]));

    if (connect(sock, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0){
        printf("Erro ao conectar-se ao servidor.\n");
        return 1;
    }

    autenticar();

    close(sock);

    return 0;
}

