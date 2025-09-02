#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>

#define PORT_TCP 8080
#define PORT_UDP 8081
#define BUFFER_SIZE 1024

void handle_udp_server(int udp_socket) {
    struct sockaddr_in client_addr;
    socklen_t len = sizeof(client_addr);
    char buffer[BUFFER_SIZE];

    while (1) {
        int n = recvfrom(udp_socket, buffer, BUFFER_SIZE, 0, (struct sockaddr *)&client_addr, &len);
        if (n > 0) {
            buffer[n] = '\0';
            printf("UDP server received: %s\n", buffer);
            sendto(udp_socket, buffer, n, 0, (struct sockaddr *)&client_addr, len);
        }
    }
}

void handle_tcp_client(int client_socket) {
    char buffer[BUFFER_SIZE];
    int bytes_read;

    while ((bytes_read = read(client_socket, buffer, BUFFER_SIZE - 1)) > 0) {
        buffer[bytes_read] = '\0';
        printf("TCP server received: %s\n", buffer);
        write(client_socket, buffer, bytes_read);
    }

    close(client_socket);
}

void start_tcp_server() {
    int server_fd, client_socket;
    struct sockaddr_in address;
    int opt = 1;
    int addrlen = sizeof(address);

    if ((server_fd = socket(AF_INET, SOCK_STREAM, 0)) == 0) {
        perror("TCP socket creation failed");
        exit(EXIT_FAILURE);
    }

    if (setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt))) {
        perror("setsockopt");
        exit(EXIT_FAILURE);
    }

    address.sin_family = AF_INET;
    address.sin_addr.s_addr = INADDR_ANY;
    address.sin_port = htons(PORT_TCP);

    if (bind(server_fd, (struct sockaddr *)&address, sizeof(address)) < 0) {
        perror("TCP bind failed");
        exit(EXIT_FAILURE);
    }

    if (listen(server_fd, 3) < 0) {
        perror("TCP listen");
        exit(EXIT_FAILURE);
    }

    while (1) {
        if ((client_socket = accept(server_fd, (struct sockaddr *)&address, (socklen_t*)&addrlen)) < 0) {
            perror("TCP accept");
            continue;
        }

        if (fork() == 0) {
            close(server_fd);
            handle_tcp_client(client_socket);
            exit(EXIT_SUCCESS);
        }
        close(client_socket);
    }
}

void start_udp_server() {
    int udp_socket;
    struct sockaddr_in udp_server_address;

    if ((udp_socket = socket(AF_INET, SOCK_DGRAM, 0)) < 0) {
        perror("UDP socket creation failed");
        exit(EXIT_FAILURE);
    }

    memset(&udp_server_address, 0, sizeof(udp_server_address));
    udp_server_address.sin_family = AF_INET;
    udp_server_address.sin_addr.s_addr = INADDR_ANY;
    udp_server_address.sin_port = htons(PORT_UDP);

    if (bind(udp_socket, (const struct sockaddr *)&udp_server_address, sizeof(udp_server_address)) < 0) {
        perror("UDP bind failed");
        exit(EXIT_FAILURE);
    }

    handle_udp_server(udp_socket);
}

int main() {
    pid_t pid = fork();

    if (pid == 0) {
        // Child process for UDP server
        start_udp_server();
    } else {
        // Parent process for TCP server
        start_tcp_server();
    }

    return 0;
}
