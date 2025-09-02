#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <sys/msg.h>
#include <errno.h>
#include <signal.h>

#define BACK_PIPE "back_pipe"
#define MESSAGE_SIZE 256
#define MSG_KEY 1234

typedef struct {
    long msg_type;
    char message[MESSAGE_SIZE];
} msg_t;

void handle_sigint(int sig) {
    (void) sig;
    printf("Shutting down BackOffice User...\n");
    exit(0);
}

void processStatisticsRequest(int msgid) {
    msg_t msg;
    if (msgrcv(msgid, &msg, sizeof(msg.message), 1, 0) == -1) {
        perror("Failed to receive message");
        return;
    }
    printf("Received stats: %s\n", msg.message);
}

void resetStatistics(int msgid) {
    msg_t msg = {2, "reset"};
    if (msgsnd(msgid, &msg, strlen(msg.message) + 1, 0) == -1) {
        perror("Failed to send reset command");
        return;
    }
    printf("Statistics reset command sent.\n");
}

int main() {
    int msgid, fd, bytesRead;
    char buffer[MESSAGE_SIZE];

    signal(SIGINT, handle_sigint);

    // Connect to the existing message queue
    if ((msgid = msgget(MSG_KEY, 0666)) == -1) {
        perror("msgget failed");
        return EXIT_FAILURE;
    }

    // Open the named pipe for reading
    fd = open(BACK_PIPE, O_RDONLY);
    if (fd == -1) {
        perror("Failed to open pipe");
        return EXIT_FAILURE;
    }

    printf("BackOffice User running...\n");

    while (1) {
        bytesRead = read(fd, buffer, MESSAGE_SIZE - 1);
        if (bytesRead == -1) {
            perror("Read error");
            break;
        } else if (bytesRead == 0) {
            printf("Pipe closed by writer. Exiting...\n");
            break;
        }

        buffer[bytesRead] = '\0';
        if (strcmp(buffer, "data_stats") == 0) {
            processStatisticsRequest(msgid);
        } else if (strcmp(buffer, "reset") == 0) {
            resetStatistics(msgid);
        } else {
            printf("Unknown command received: %s\n", buffer);
        }
    }

    close(fd);
    return EXIT_SUCCESS;
}
