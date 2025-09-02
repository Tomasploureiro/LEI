#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <pthread.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/ipc.h>
#include <sys/msg.h>
#include <sys/wait.h>
#include <sys/shm.h>
#include <signal.h>
#include <errno.h>
#include <sys/time.h>
#include <sys/select.h>
#include <semaphore.h>
#include <stdbool.h>

#define KEY 1234
#define USER_PIPE "/tmp/user_pipe"
#define BACK_PIPE "/tmp/back_pipe"
#define MESSAGE_SIZE 1024
#define PERIODIC_STATS_INTERVAL 30

typedef struct {
    int MOBILE_USERS;
    int QUEUE_POS;
    int AUTH_SERVERS;
    int AUTH_PROC_TIME;
    int MAX_VIDEO_WAIT;
    int MAX_OTHERS_WAIT;
} Config;

typedef struct no {
    char * buffer;
    struct no* next;
} no;

typedef struct Queue {
    char buffer[MESSAGE_SIZE];
    int tempo;
    int pos;
    struct Queue *next;
}Queue;

typedef struct AuthorizationEngine {
    int estado;  // True para livre, false para ocupado.
    sem_t sem;    // Semáforo para controlar o acesso ao estado
} AuthorizationEngine;

typedef struct {
    int totalVideoData;
    int totalMusicData;
    int totalSocialData;
    int totalVideoReqs;
    int totalMusicReqs;
    int totalSocialReqs;
}Alerta;

typedef struct {
    int pid;
    int plafondInicial;
    int plafondAtual;
    int flag;
}MobileRegisto;


typedef struct{
    char statsName[20];
    int data;
    int requests;   
}statistics;

typedef struct{
    statistics statistics[3];
}stats;

typedef struct stats_msg{
    long tipo;
    stats stats;
}stats_msg;


typedef struct stats_msg2{
    long tipo;
    stats stats;
}stats_msg2;

typedef struct Memory {
    MobileRegisto *mobileRegisto;
    AuthorizationEngine *authorizationEngine;
    stats stats;
    pthread_mutex_t mutex;
    pthread_cond_t condMonitor;
}Memory;

typedef struct userData {
    int dataAmount;
    int interval;
    char serviceType[10];
    int maxRequests;
}userData;

typedef struct {
    long mtype;
    char mtext[200];
}msgBuffer;