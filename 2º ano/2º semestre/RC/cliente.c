#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <netdb.h>

#define BUF_SIZE 1024

void erro(char *msg) {
  perror(msg); // Melhor visualização do erro
  exit(EXIT_FAILURE);
}

int main(int argc, char *argv[]) {
  char endServer[100];
  int fd;
  struct sockaddr_in addr;
  struct hostent *hostPtr;
  char buffer[BUF_SIZE];

  // Endereço e porta do servidor
  char *host = "193.137.100.1";
  char *port = "8080";

  strcpy(endServer, host);
  if ((hostPtr = gethostbyname(endServer)) == NULL) {
    erro("Não consegui obter endereço");
  }

  bzero((void *)&addr, sizeof(addr));
  addr.sin_family = AF_INET;
  addr.sin_addr.s_addr = ((struct in_addr *)(hostPtr->h_addr))->s_addr;
  addr.sin_port = htons((short)atoi(port));

  if ((fd = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
    erro("socket");
  }
  if (connect(fd, (struct sockaddr *)&addr, sizeof(addr)) < 0) {
    erro("Connect");
  }

  printf("Faça login no servidor!\nDigite 'login <usuario> <senha>' para entrar:\n");

  int logged = 0;
  while (1) {
    if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
      printf("Erro ao ler do teclado\n");
      continue;
    }

    if (write(fd, buffer, strlen(buffer)) < 0) {
      erro("Erro ao escrever no socket");
    }

    int nread = read(fd, buffer, BUF_SIZE - 1);
    if (nread < 0) {
      erro("Erro ao ler do socket");
    }
    buffer[nread] = '\0';

    if (logged == 0) {
      printf("%s\n", buffer);
      if (strcmp(buffer, "Login efetuado com sucesso\n") == 0) {
        logged = 1;
        printf("Login efetuado com sucesso. Pode efetuar outros comandos!\n");
      }
    } else {
      // Comandos para quando já está logged
      printf("%s\n", buffer);
    }
  }

  // Cleanup and exit...
  close(fd);
  exit(0);
}
