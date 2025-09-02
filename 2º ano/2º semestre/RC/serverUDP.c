#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <unistd.h>

#define BUF_SIZE 1024
#define PORT 9876 // Porto para recepção das mensagens

void erro(char *msg);

typedef struct
{
    char username[BUF_SIZE];
    char password[BUF_SIZE];
    char user_type[BUF_SIZE];
} User;

int number_users = 0;
User users[20];

void read_registered_users()
{
    FILE *file = fopen("/home/user/Desktop/RC/Projeto/users.txt", "r");
    char line[100];
    if (file == NULL)
    {
        erro("Ficheiro inexistente");
    }

    while (fgets(line, sizeof(line), file))
    {
        char *username, *password, *user_type;

        username = strtok(line, ";");
        password = strtok(NULL, ";");
        user_type = strtok(NULL, "\n");

        if (username != NULL && password != NULL && user_type != NULL)
        {
            strcpy(users[number_users].username, username);
            strcpy(users[number_users].password, password);
            strcpy(users[number_users].user_type, user_type);
            number_users++;
        }
    }

    fclose(file);
}
void erro(char *s)
{
    perror(s);
    exit(1);
}

int main(void)
{
    read_registered_users();

    int s;
    struct sockaddr_in si_minha, si_outra;
    socklen_t slen;
    char buffer[BUF_SIZE];

    // Cria um socket para recepção de pacotes UDP
    s = socket(AF_INET, SOCK_DGRAM, 0);
    if (s < 0)
    {
        erro("Criar socket");
    }

    bzero(&si_minha, sizeof(si_minha));
    si_minha.sin_family = AF_INET;
    si_minha.sin_addr.s_addr = INADDR_ANY;
    si_minha.sin_port = htons(PORT);

    // Associa o socket à informação de endereço
    if (bind(s, (struct sockaddr *)&si_minha, sizeof(si_minha)) < 0)
    {
        erro("Erro no bind");
    }

    int admin=0;
    int exist=0;
    while (1)
    {
        slen = sizeof(si_outra);
        //isto é para apenas começar a enviar e a receber mensagens depois de o cliente dar um enter na consola.
        recvfrom(s, buffer, BUF_SIZE, 0, (struct sockaddr *)&si_outra, &slen);
        
        char userText[] = "Username: ";
        sendto(s, userText, strlen(userText), 0, (struct sockaddr *)&si_outra, slen);
        if (recvfrom(s, buffer, BUF_SIZE, 0, (struct sockaddr *)&si_outra, &slen) == -1)
        {
            erro("leitura de dados");
        }
        buffer[strcspn(buffer, "\n")] = '\0';
        char username[BUF_SIZE];
        strcpy(username, buffer);

        char passText[] = "Password: ";
        sendto(s, passText, strlen(passText), 0, (struct sockaddr *)&si_outra, slen);
        if (recvfrom(s, buffer, BUF_SIZE, 0, (struct sockaddr *)&si_outra, &slen) == -1)
        {
            erro("leitura de dados");
        }
        buffer[strcspn(buffer, "\n")] = '\0';
        char pass[BUF_SIZE];
        strcpy(pass, buffer);

        for (int i = 0; i < number_users; i++)
        {
            if (strcmp(users[i].username, username) == 0 && strcmp(users[i].password, pass) == 0)
            {
                exist = 1;
                if (strcmp(users[i].user_type, "administrator") == 0)
                {
                    admin = 1;
                    char user_with_perms[] = "Você tem permissões! Efetue outros comandos!\n";
                    sendto(s, user_with_perms, strlen(user_with_perms), 0, (struct sockaddr *)&si_outra,slen);
                    break;
                }
                else
                {
                    char user_no_perms[] = "Este utilizador não tem permissões de admin\n";
                    sendto(s, user_no_perms, strlen(user_no_perms), 0, (struct sockaddr *)&si_outra, slen);
                }
            }
        }
        if (admin == 1)
        {
            break;
        }
        if (exist == 0)
        {
            char frase[] = "Dados incorretos!\nVolte a colocar.\n";
            sendto(s, frase, strlen(frase), 0, (struct sockaddr *)&si_outra, slen);
        }
    }
    if (admin != 0)
    {
        while (1)
        {
            // Aqui será feita a leitura dos próximos comandos que o admin faça 
        }
    }
    close(s);
    return 0;
}
