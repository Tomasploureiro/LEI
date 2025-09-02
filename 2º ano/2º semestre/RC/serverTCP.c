#include <sys/socket.h>
#include <sys/types.h>
#include <netinet/in.h>
#include <unistd.h>
#include <stdlib.h>
#include <stdio.h>
#include <netdb.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <arpa/inet.h>

#define SERVER_PORT 3080
#define BUF_SIZE 1024

typedef struct
{
  char username[BUF_SIZE];
  char password[BUF_SIZE];
  char user_type[BUF_SIZE];
} User;

int number_users = 0;
User users[20];

void process_client(int client_fd);
void erro(char *msg);

void read_registered_users()
{
  FILE *file = fopen("/gns3volumes/home/users.txt", "r");
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
    strcpy(users[number_users].username, username);
    strcpy(users[number_users].password, password);
    strcpy(users[number_users].user_type, user_type);
    number_users++;
  }

fclose(file);
}

int main()
{
  read_registered_users();

  int fd, client;
  struct sockaddr_in addr, client_addr;
  int client_addr_size;

  bzero((void *)&addr, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = htonl(INADDR_ANY);
  addr.sin_port = htons(SERVER_PORT);

  if ((fd = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    erro("na funcao socket");
  if (bind(fd, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    erro("na funcao bind");
  if (listen(fd, 5) < 0)
    erro("na funcao listen");
  client_addr_size = sizeof(client_addr);
  while (1)
  {
    // clean finished child processes, avoiding zombies
    // must use WNOHANG or would block whenever a child process was working
    while (waitpid(-1, NULL, WNOHANG) > 0)
      ;
    // wait for new connection
    client = accept(fd, (struct sockaddr *)&client_addr, (socklen_t *)&client_addr_size);

    if (client > 0)
    {
      if (fork() == 0)
      {
        close(fd);
        process_client(client);
        exit(0);
      }
      close(client);
    }
  }
  wait(NULL);
  return 0;
}

void process_client(int client_fd)
{
  char buffer[BUF_SIZE];
  int nread;
  while ((nread = read(client_fd, buffer, BUF_SIZE - 1)) > 0)
  {
    buffer[nread] = '\0';
    char command[100], username[100], password[100];
    if (sscanf(buffer, "%s %s %s", command, username, password) == 3 && strcmp(command, "LOGIN") == 0)
    {
      int user_found = 0; // Variável para indicar se o usuário foi encontrado
      for (int i = 0; i < number_users; i++)
      {
        if (strcmp(users[i].username, username) == 0 && strcmp(users[i].password, password) == 0)
        {
          char login[BUF_SIZE] = "Login efetuado com sucesso";
          write(client_fd, login, strlen(login));
          user_found = 1; // Define como verdadeiro pois o usuário foi encontrado
          break;
        }
      }
      if (!user_found)
      {
        char user_not_found[] = "Utilizador não registado\n";
        write(client_fd, user_not_found, strlen(user_not_found));
      }
    }
    else
    {
      char fail[BUF_SIZE] = "Parametros inválidos\n";
      write(client_fd, fail, strlen(fail));
    }
  }

  close(client_fd);
}

void erro(char *msg)
{
  printf("Erro: %s\n", msg);
  exit(-1);
}
