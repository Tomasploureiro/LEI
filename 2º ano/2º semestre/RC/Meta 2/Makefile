CC = gcc
CFLAGS = -Wall -Wextra

all: servidor cliente

servidor: Servidor.c
	$(CC) $(CFLAGS) -o servidor Servidor.c -pthread

cliente: Cliente.c
	$(CC) $(CFLAGS) -o cliente Cliente.c

clean:
	rm -f servidor
	rm -f cliente
