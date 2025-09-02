#include "header.h"

int msgid;

void * readThread(){
    while(1){
        stats_msg2 message;
        if (msgrcv(msgid, &message, sizeof(stats_msg) - sizeof(long), 1, 0) == -1){
            perror("Erro a ler da message queue");
        }
        printf("Service    Total Data    Auth Reqs\n");
        printf("%s             %d             %d\n",message.stats.statistics[0].statsName,message.stats.statistics[0].data,message.stats.statistics[0].requests);
        printf("%s             %d             %d\n",message.stats.statistics[1].statsName,message.stats.statistics[1].data,message.stats.statistics[1].requests);
        printf("%s            %d             %d\n",message.stats.statistics[2].statsName,message.stats.statistics[2].data,message.stats.statistics[2].requests);
    }
    return NULL;
}



void * writeThread(){

    char buffer[MESSAGE_SIZE];
    int fd;

    if ((fd = open(BACK_PIPE, O_WRONLY)) < 0) {
        perror("Failed to open BACK_PIPE for writing");
        exit(EXIT_FAILURE);
    }
    snprintf(buffer, MESSAGE_SIZE, "%d", getpid());
    write(fd, buffer, strlen(buffer));

    while (1){
        char input[30];
        if(fgets(input, sizeof(input), stdin) == NULL){
            perror("Erro ao ler\n");
        }

        input[strcspn(input, "\n")] = '\0';

        snprintf(buffer, MESSAGE_SIZE, "1#%s", input);
        if (strcmp(buffer, "1#data_stats") == 0 || strcmp(buffer, "1#reset") == 0){
        if (write(fd, buffer, strlen(buffer) + 1) < 0) {
            perror("Failed to write to BACK_PIPE");
            close(fd);
            exit(EXIT_FAILURE);
        }
        }
        else{
            printf("Digite um comando valido\n");
        }
    }
    close(fd);
}


void create_threads(){
    pthread_t read_thread,write_thread;

    if (pthread_create(&read_thread, NULL, readThread, NULL) != 0)
    {
        perror("Erro ao criar a back office thread");
    }

    if (pthread_create(&write_thread, NULL, writeThread, NULL) != 0)
    {
        perror("Erro ao criar a back office thread");
    }

    pthread_join(read_thread, NULL);
    pthread_join(write_thread, NULL);
}

void handle_sigint(int sig) {
    (void) sig;
    msgctl(msgid, IPC_RMID, NULL);
    unlink(BACK_PIPE);
    exit(0);
}

int main() {

    signal(SIGTERM, handle_sigint);

    // Connect to the existing message queue
    if ((msgid = msgget(KEY, 0666)) == -1) {
        perror("msgget failed");
        return EXIT_FAILURE;
    }
    printf("BackOffice User running...%d\n", getpid());

    create_threads();
    return EXIT_SUCCESS;
}
