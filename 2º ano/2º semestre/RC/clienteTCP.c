#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>

#define BUF_SIZE 1024

void erro(char *msg);

int main(int argc)
{
  char endServer[100];
  int fd;
  struct sockaddr_in addr;
  struct hostent *hostPtr;
  char buffer[BUF_SIZE];
  if (argc != 1)
  {
    erro("Argumentos inválidos");
    exit(-1);
  }
  //para se usar nos clientes no gns3

  char *host = "193.137.100.1";
  char *port = "8008";

  strcpy(endServer, host);
  if ((hostPtr = gethostbyname(endServer)) == 0)
    erro("Não consegui obter endereço");

  bzero((void *)&addr, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = ((struct in_addr *)(hostPtr->h_addr))->s_addr;
  addr.sin_port = htons((short)atoi(port));

  if ((fd = socket(AF_INET, SOCK_STREAM, 0)) == -1)
    erro("socket");
  if (connect(fd, (struct sockaddr *)&addr, sizeof(addr)) < 0)
    erro("Connect");

  printf("Faça login no servidor!\n");

  int logged = 0;
  while (1)
  {
    fgets(buffer, sizeof(buffer), stdin);
    write(fd,buffer,strlen(buffer));

    int nread = read(fd, buffer, BUF_SIZE - 1);
    buffer[nread] = '\0';
    if (logged == 0)
    {
      printf("%s",buffer);
      if (strcmp(buffer,"Login efetuado com sucesso")==0)
      {
        logged=1;
        printf("Pode efetuar outros comandos!\n");
      }
    }
    else
    {
      //comandos para quando já está logged
    }
  }

  // Cleanup and exit...
  close(fd);

  exit(0);
}

void erro(char *msg)
{
  printf("Erro: %s\n", msg);
  exit(-1);
}
