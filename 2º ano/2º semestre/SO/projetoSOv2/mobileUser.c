#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <pthread.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/msg.h>


#define MESSAGE_SIZE 100
#define USER_PIPE "user_pipe"
#define key 1234


typedef struct userData{
    int dataAmount;
    int interval;
    char serviceType[10];
    int maxRequests;
    int fd;
    int msgQueueId;
}userData;

typedef struct msgBuffer{
        long mtype;
        char mText[MESSAGE_SIZE];
}msgBuffer;

void sendAuthorizaitonRequest(char *message, int fd){
    if (write(fd, message, strlen(message)) == -1){
        perror("Error writing to User Pipe\n");
    }
}

void handleSIGINT(int sig){
    (void) sig;
    printf("Shutting down...");
    exit(0);
}

void registerUser(int initialPlafond, int fd){
    int userId = getpid();
    char message[MESSAGE_SIZE];
    printf("%d", userId);
    snprintf(message, sizeof(message), "%d#%d", userId, initialPlafond);
    sendAuthorizaitonRequest(message, fd);
}

void *serviceThread(void *arg){
    userData *data = (userData*) arg;
    char message[MESSAGE_SIZE];
    int numRequests = 0;
    msgBuffer mBuf;
    while(numRequests <= data->maxRequests){
        if(msgrcv(data->msgQueueId, &mBuf, sizeof(mBuf.mText), MESSAGE_SIZE, IPC_NOWAIT) != -1){
            if(strcmp(mBuf.mText, "100") == 0){
                printf("Plafond a 100%%\n");
                pthread_exit(NULL);
            }
        }
        else{
            snprintf(message, sizeof(message), "%d#%s#%d#%d", getpid(), data->serviceType, data->dataAmount, numRequests);
            printf("ola2");
            sendAuthorizaitonRequest(message, data->fd);
            sleep(data->interval);
            printf("%d", numRequests);
            numRequests++;
        }
    }
    return NULL;
}

void startServices(int maxRequests, int videoInterval, int musicInterval, int socialInterval, int dataAmount,int fd, int msgQueueId){
    pthread_t videoThread, musicThread, socialThread;
    userData videoData = {dataAmount, videoInterval, "VIDEO", maxRequests, fd, msgQueueId};
    userData musicData = {dataAmount, musicInterval, "MUSIC", maxRequests, fd, msgQueueId};
    userData socialData = {dataAmount, socialInterval, "SOCIAL", maxRequests, fd, msgQueueId};

    pthread_create(&videoThread, NULL, serviceThread, (void *)&videoData);
    pthread_create(&musicThread, NULL, serviceThread, (void *)&musicData);
    pthread_create(&socialThread, NULL, serviceThread, (void *)&socialData);

    pthread_join(videoThread, NULL);
    pthread_join(musicThread, NULL);
    pthread_join(socialThread, NULL);
}

int main(int argc, char const *argv[]){
    if (argc != 7) {
        printf("Usage: %s <initialPlafond> <maxRequests> <videoInterval> <musicInterval> <socialInterval> <dataAmount>\n", argv[0]);
        return 1;
    }

    int initialPlafond = atoi(argv[1]);
    int maxRequests = atoi(argv[2]);
    int videoInterval = atoi(argv[3]);
    int musicInterval = atoi(argv[4]);
    int socialInterval = atoi(argv[5]);
    int dataAmount = atoi(argv[6]);

    //Open User Pipe
    int fd = open(USER_PIPE, O_WRONLY);
    if(fd == -1){
        perror("Error opening User Pipe\n");
        return -1;

    }

    //Open message queue

    int msgQueueId = msgget(key, 0666);
    if (msgQueueId == -1){
        perror("Error connecting to message queue");
        return -1;
    }

    //Signal SIGINT

    signal(SIGINT,  handleSIGINT);


    registerUser(initialPlafond, fd);
    startServices(maxRequests, videoInterval, musicInterval, socialInterval, dataAmount, fd, msgQueueId);
    close(fd);
    return 1;
}

