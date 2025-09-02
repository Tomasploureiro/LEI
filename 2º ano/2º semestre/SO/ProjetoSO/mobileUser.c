#include "header.h"

int msgQueueId, fd;

int flag = 1, numRequests;

sem_t writeSem;

pthread_t videoThread, musicThread, socialThread, alertThreadt;


pthread_mutex_t mutex_requests= PTHREAD_MUTEX_INITIALIZER;

void sendAuthorizationRequest(const char *message) {
    sem_wait(&writeSem);
    if (write(fd, message, MESSAGE_SIZE) == -1) {
        perror("Error writing to User Pipe");
    }
    sem_post(&writeSem);
    numRequests++;
}

void handleSIGINT(int sig) {
    (void)sig;
    pthread_cancel(videoThread);
    pthread_cancel(musicThread);
    pthread_cancel(socialThread);
    pthread_cancel(alertThreadt);
    close(fd);
    msgctl(msgQueueId, IPC_RMID, NULL);



    printf("Shutting down...\n");
    exit(0);
}

void registerUser(int initialPlafond) {
    int userId = getpid();
    char message[MESSAGE_SIZE];
    snprintf(message, MESSAGE_SIZE, "%d#%d\n", userId, initialPlafond);
    sendAuthorizationRequest(message);
}

void *serviceThread(void *arg) {
    userData *data = (userData*) arg;
    while (numRequests <= data->maxRequests && flag == 1) {
        usleep(data->interval*1000);
        char message[MESSAGE_SIZE];
        pthread_mutex_lock(&mutex_requests);
        snprintf(message, MESSAGE_SIZE, "%d#%s#%d\n", getpid(), data->serviceType, data->dataAmount);
        printf("%s\n", message);
        sendAuthorizationRequest(message);
        pthread_mutex_unlock(&mutex_requests);
    }
    exit(0);
}

void *alertThread(void *arg){
    (void)arg;
    msgBuffer mBuf;
    int pid;
    int plafondAlert;
    while(1){
        if (msgrcv(msgQueueId, &mBuf, sizeof(mBuf.mtext), getpid(), IPC_NOWAIT) != -1) {
                if (sscanf(mBuf.mtext, "%d#%d", &pid, &plafondAlert) == 2) {
                    if(pid == getpid() && plafondAlert == 80){
                        printf("80 %% do plafon usado\n");
                    }else if(pid == getpid() && plafondAlert == 90){
                        printf("90 %% do plafon usado\n");
                    }else if(pid == getpid() && plafondAlert == 100){
                        printf("100 %% do plafon usado\n");
                        flag = 0;
                        exit(EXIT_SUCCESS);
                    } 
                }
            } else if (errno != ENOMSG) {
                perror("Error reading from message queue");
                break;
            }
    }
    return NULL;
}

void startServices(int maxRequests, int videoInterval, int musicInterval, int socialInterval, int dataAmount) {
    userData videoData = {dataAmount, videoInterval, "VIDEO", maxRequests};
    userData musicData = {dataAmount, musicInterval, "MUSIC", maxRequests};
    userData socialData = {dataAmount, socialInterval, "SOCIAL", maxRequests};

    pthread_create(&videoThread, NULL, serviceThread, (void *)&videoData);
    pthread_create(&musicThread, NULL, serviceThread, (void *)&musicData);
    pthread_create(&socialThread, NULL, serviceThread, (void *)&socialData);
    pthread_create(&alertThreadt, NULL, alertThread, NULL);

    pthread_join(videoThread, NULL);
    pthread_join(musicThread, NULL);
    pthread_join(socialThread, NULL);
    pthread_join(alertThreadt, NULL);
}

int main(int argc, char const *argv[]) {
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

    fd = open(USER_PIPE, O_WRONLY);
    if (fd == -1) {
        perror("Error opening User Pipe");
        return -1;
    }

    msgQueueId = msgget(KEY, 0666);
    if (msgQueueId == -1) {
        perror("Error connecting to message queue");
        close(fd);
        return -1;
    }

    sem_init(&writeSem, 0, 1);

    signal(SIGTERM, handleSIGINT);

    registerUser(initialPlafond);
    startServices(maxRequests, videoInterval, musicInterval, socialInterval, dataAmount);

    sem_destroy(&writeSem);
    close(fd);
    return 0;
}
