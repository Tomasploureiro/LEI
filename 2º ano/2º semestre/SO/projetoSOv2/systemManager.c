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
#include <signal.h>
#include <errno.h>
#include <sys/time.h>
#include <sys/select.h>
#include <semaphore.h>
#include <stdbool.h>
#include <stdalign.h>


#define KEY 1234
#define USER_PIPE "user_pipe"
#define BACK_PIPE "back_pipe"
#define MESSAGE_SIZE 1024
#define SEM_SHARED_MEMORY "/SEMSHM"


typedef struct {
    int MOBILE_USERS;
    int QUEUE_POS;
    int AUTH_SERVERS;
    int AUTH_PROC_TIME;
    int MAX_VIDEO_WAIT;
    int MAX_OTHERS_WAIT;
} Config;

typedef struct Queue {
    char data[MESSAGE_SIZE];
    int queuePos;
    struct Queue *next;
} Queue;

typedef struct AuthorizationEngine {
    bool estado;  // True para livre, false para ocupado.
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
    int id;
    int plafondInicial;
    int plafondAtual;
}MobileRegisto;


typedef struct Memory {
    MobileRegisto *mobileRegisto;
    AuthorizationEngine *authorizationEngine;
    Alerta *alerta;
}Memory;



typedef struct {
    int totalRequests;
    int totalDataRequested;
    int plafond;
} shared_data_t;

Queue *videoQueue, *otherQueue;

Memory * sharedMemory;

Memory * sharedMemoryPointer;

int shmid, msgid; // Global IDs for shared memory and message queue

int ** unnamed_pipe;

sem_t semaphore, * semaphoreAE;

Config config;

int readLog(const char *filename, Config *config) {
    FILE *file = fopen(filename, "r");
    char line[50];
    if (file == NULL) {
        perror("Error opening file");
        return -1;
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->MOBILE_USERS) == 1){
        }
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->QUEUE_POS) == 1){
        }
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->AUTH_SERVERS) == 1){
        }
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->AUTH_PROC_TIME) == 1){
        }
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->MAX_VIDEO_WAIT) == 1){
        }
    }
    if (fgets(line, 50, file) != NULL){
        if(sscanf(line, "%d", &config->MAX_OTHERS_WAIT) == 1){
        }
    }
    return 0;
}

Queue* nodeQueue(char data[]){
    Queue* newNode = (Queue*)malloc(sizeof(Queue));
    if(newNode == NULL){
        perror("Erro ao alocar memória para o novo nó\n");
        exit(EXIT_FAILURE);
    } 
    strcpy(newNode->data, data);
    newNode->next = NULL;
    newNode->queuePos++;
    return newNode;
}

void insertQueue(Queue **head, char *data){
    sem_wait(&semaphore);
    Queue *newNode = nodeQueue(data);
    if (*head == NULL){
        *head = newNode;
    } else {
        Queue *current = *head;
        while (current->next != NULL) {
            current = current->next;
        }
        if (current->queuePos >= config.QUEUE_POS - 1) {
            // Se a fila estiver cheia, logamos e não inserimos novo nó
            printf("Queue is full, discarding request\n");
            free(newNode);
        } else {
            current->next = newNode;
            newNode->queuePos = current->queuePos + 1;
        }
    }
    sem_post(&semaphore);
}




// void startSharedMemory() {
//    //Criação da memória partilhada
//     if((shmid = shmget(IPC_PRIVATE, sizeof(Memory) + sizeof(MobileRegisto)*config.MOBILE_USERS + sizeof(AuthorizationEngine)*(config.AUTH_SERVERS + 1) + sizeof(Alerta), IPC_CREAT|0766)) < 0){
//         perror("ERRO! shmget failed!\n");
//         return 1;
//     }
//     if((sharedMemory = (Memory*) shmat(shmid,NULL,0)) == (Memory *)-1){
//         perror("ERRO! shmat failed!\n");
//         return 1;
//     }

//     // memset(sharedMemory, 0, sizeof(Memory));

//     sharedMemoryPointer = malloc(sizeof(Memory));

//     MobileRegisto * mobileAux = (MobileRegisto*) ((char*) sharedMemory + sizeof(Memory));
//     // memset(mobileAux, 0, sizeof(MobileRegisto) * config.MOBILE_USERS);

//     AuthorizationEngine * authAux = (AuthorizationEngine*) ((char*) mobileAux + sizeof(MobileRegisto) * config.MOBILE_USERS);
//     // memset(authAux, 0, sizeof(AuthorizationEngine) * (config.AUTH_SERVERS + 1));

//     Alerta * alertaAux = (Alerta*)((char*) authAux + sizeof(AuthorizationEngine) * (config.AUTH_SERVERS + 1));
//     // memset(alertaAux, 0, sizeof(Alerta));
    
//     sharedMemoryPointer->mobileRegisto = mobileAux;
//     sharedMemoryPointer->authorizationEngine = authAux;
//     sharedMemoryPointer->alerta = alertaAux;

//     AuthorizationEngine * aux = sharedMemoryPointer->authorizationEngine;
//     for(int i = 0; i < config.AUTH_SERVERS; i++){
//          aux[i].estado = true;
//     }
//     printf("Shared Memory created with id: %d\n", shmid);

    
// }
void startSharedMemory() {
    // Cálculo do tamanho necessário para a memória compartilhada
    size_t totalSize = sizeof(Memory) +
                       sizeof(MobileRegisto) * config.MOBILE_USERS +
                       sizeof(AuthorizationEngine) * config.AUTH_SERVERS +
                       sizeof(Alerta);

    if ((shmid = shmget(IPC_PRIVATE, totalSize, IPC_CREAT | 0766)) < 0) {
        perror("ERROR: shmget failed!");
        exit(EXIT_FAILURE);
    }

    if ((sharedMemory = (Memory*) shmat(shmid, NULL, 0)) == (Memory *) -1) {
        perror("ERROR: shmat failed!");
        exit(EXIT_FAILURE);
    }

    // Configurando ponteiros para cada segmento na memória compartilhada
    char *base = (char*) sharedMemory;
    sharedMemory->mobileRegisto = (MobileRegisto *)(base + sizeof(Memory));
    sharedMemory->authorizationEngine = (AuthorizationEngine *)(base + sizeof(Memory) + sizeof(MobileRegisto) * config.MOBILE_USERS);
    sharedMemory->alerta = (Alerta *)(base + sizeof(Memory) + sizeof(MobileRegisto) * config.MOBILE_USERS + sizeof(AuthorizationEngine) * config.AUTH_SERVERS);

    // Inicializando a disponibilidade de cada Authorization Engine
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sharedMemory->authorizationEngine[i].estado = true;
        sem_init(&sharedMemory->authorizationEngine[i].sem, 1, 1);  // Inicializa o semáforo como desbloqueado (1)
    }
}





// Setup message queue
void startMessageQueue() {
    if ((msgid = msgget(KEY, IPC_CREAT | 0666)) < 0) {
        perror("Error creating Message Queue");
        exit(EXIT_FAILURE);
    }
    printf("Message queue created with id: %d\n", msgid);
}

// SIGINT handler for graceful shutdown
void sigintHandler(int sig_num) {
    (void)sig_num;
    printf("Terminating program gracefully\n");
    // Limpeza dos recursos criados
    shmctl(shmid, IPC_RMID, NULL);
    msgctl(msgid, IPC_RMID, NULL);
    unlink(USER_PIPE);
    unlink(BACK_PIPE);
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        close(unnamed_pipe[i][0]);
        close(unnamed_pipe[i][1]);
        free(unnamed_pipe[i]);
    }
    free(unnamed_pipe);
    exit(0);
}




void createNamedPipes(){
    unlink(USER_PIPE);
    if (mkfifo(USER_PIPE, O_CREAT | O_EXCL | 0666) < 0 && (errno != EEXIST)){
        perror("Failed to create User Pipe");
    }
    unlink(BACK_PIPE);
    if (mkfifo(BACK_PIPE, O_CREAT | O_EXCL | 0666) < 0 && (errno != EEXIST)){
        perror("Failed to create Back Pipe");
    }
    printf("Named Pipes criados\n");
}

void createUnnamedPipes() {

    // Verificar se a alocação foi bem-sucedida
    if (unnamed_pipe == NULL) {
        perror("Erro ao alocar memória para unnamed_pipe.\n");
        //escreverLog("Erro ao alocar memória para unnamed_pipe.", NOME_FICHEIRO_LOG);
        exit(1);
    }

    for (int i = 0; i < config.AUTH_SERVERS; i++) {

        if (pipe(unnamed_pipe[i]) < 0) {
            perror("Erro ao criar o pipe.\n");
            //escreverLog("Erro ao criar o pipe.", NOME_FICHEIRO_LOG);
            // Fecha pipes criados anteriormente
            for (int j = 0; j < i; j++) {
                close(unnamed_pipe[j][0]);
                close(unnamed_pipe[j][1]);
            }
            // Libera a memória alocada até agora
            for (int j = 0; j < i; j++) {
                free(unnamed_pipe[j]);
            }
            free(unnamed_pipe);
            exit(1);
        }
    }

    printf("Todos os unnamed_pipes foram criados.\n");
    //escreverLog("Todos os unnamed_pipes foram criados.", NOME_FICHEIRO_LOG);
}

// void inicializar_vetor_unnamed_pipes() {
//     unnamed_pipe = (int**) malloc(sizeof(int*) * config.AUTH_SERVERS);

//     if (unnamed_pipe == NULL) {
//         perror("Falha ao alocar memória para a variável unnamed_pipe.\n");
//         exit(1);
//     }

//     for (int i = 0; i < config.AUTH_SERVERS; i++) {
//         unnamed_pipe[i] = (int*) malloc(sizeof(int) * 2);

//         if (unnamed_pipe[i] == NULL) {
//             perror("Erro ao alocar memória para unnamed_pipe[i].\n");
//             // Libertar a memória alocada até agora:
//             for (int j = 0; j < i; j++) free(unnamed_pipe[j]);
//             free(unnamed_pipe);
//             exit(1);
//         }
//     }
// }

void inicializar_vetor_unnamed_pipes() {
    unnamed_pipe = (int**) malloc(sizeof(int*) * config.AUTH_SERVERS);
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        unnamed_pipe[i] = (int*) malloc(sizeof(int) * 2);
        pipe(unnamed_pipe[i]);  // Criar os pipes
    }
}


void *receiverThread(void *arg) {
    (void) arg;
    printf("receiverThread\n");
    int fdUser = open(USER_PIPE, O_RDONLY);
    int fdBack = open(BACK_PIPE, O_RDONLY);
    while (1) {
        fd_set readfds;
        FD_ZERO(&readfds);
        FD_SET(fdUser, &readfds);
        FD_SET(fdBack, &readfds);

        if (select((fdBack > fdUser ? fdBack : fdUser) + 1, &readfds, NULL, NULL, NULL) == -1)
        {
            perror("Erro na chamada de select()");
        }
        if(FD_ISSET(fdUser, &readfds)){
            char buffer[MESSAGE_SIZE];
            if (read(fdUser, buffer, MESSAGE_SIZE) <= 0){
                perror("Error reading from pipe");
                exit(EXIT_FAILURE);
            }
            printf("%s\n", buffer);
            if(strstr(buffer, "VIDEO") != NULL){
                insertQueue(&videoQueue, buffer);
            }
            else{
                insertQueue(&otherQueue, buffer);
            }
            close(USER_PIPE);
            fdUser = open(USER_PIPE, O_RDONLY);

        }
        if(FD_ISSET(fdBack, &readfds)){
            char buffer[MESSAGE_SIZE];
            if (read(fdBack, buffer, MESSAGE_SIZE) <= 0){
                perror("Error reading from pipe");
                exit(EXIT_FAILURE);
            }
            insertQueue(&otherQueue, buffer);
            close(BACK_PIPE);
            fdBack = open(BACK_PIPE, O_RDONLY);
        }   
    }
    return NULL;
}

void createAuthorizationEnginePipes(int i, int pipes[][2]){
    close(unnamed_pipe[i][1]);
    //Executar rotina para madnar merdas para a shared memory

}

void sendRequestToAuthorizationEngine(char *data) {
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        if (!sharedMemory->authorizationEngine[i].estado) {
            write(unnamed_pipe[i][1], data, strlen(data) + 1);
            sharedMemory->authorizationEngine[i].estado = true; // marcando como ocupado
            break;
        }
    }
}

void *senderThread(void *arg) {
    (void)arg;
    while (1) {
        sem_wait(&semaphore);
        // Prioridade para pedidos de vídeo
        if (videoQueue != NULL) {
            Queue *request = videoQueue;
            videoQueue = videoQueue->next;
            sendRequestToAuthorizationEngine(request->data);
            free(request);
        } else if (otherQueue != NULL) {
            Queue *request = otherQueue;
            otherQueue = otherQueue->next;
            sendRequestToAuthorizationEngine(request->data);
            free(request);
        }
        sem_post(&semaphore);
        usleep(100000);
    }
    return NULL;
}


void createThreads(){
    pthread_t receiver, sender;
    pthread_create(&receiver, NULL, receiverThread, NULL);
    pthread_create(&sender, NULL, senderThread, NULL);

    // Wait for threads to finish
    pthread_join(receiver, NULL);
    pthread_join(sender, NULL);
}

void createAuthorizationEngine(int i){

    printf("AuthorizationEngine com %d criada\n", i);
    char buffer[MESSAGE_SIZE];
    if(read(unnamed_pipe[i][0], buffer, MESSAGE_SIZE) < 0){
        perror("Error reading from pipe");
        exit(EXIT_FAILURE);
    }
    //semaforo
    processRequest(buffer, shmid);
    

}

void startMonitorEngine(){
    int shmid = shmget(KEY, sizeof(shared_data_t), 0666);
    if (shmid == -1) {
        perror("shmget failed to connect to existing shared memory");
        exit(1);
    }
    shared_data_t *sharedData = (shared_data_t*)shmat(shmid, NULL, 0);
    if (sharedData == (void *) -1) {
        perror("shmat failed");
        exit(1);
    }

    char request[1024];
    while (read(unnamed_pipe, request, sizeof(request)) > 0) {
        processRequest(request, sharedData);
    }

    if (shmdt(sharedData) == -1) {
        perror("shmdt failed");
        exit(1);
    }

}

#include <stdio.h>
#include <string.h>
#include <semaphore.h>

void processRequest(char *request, Memory *sharedMemory) {
    // Encontrar um Authorization Engine disponível
    int engineIndex = -1;
    for (int i = 0; sharedMemory->authorizationEngine && i < config.AUTH_SERVERS; i++) {
        sem_wait(&sharedMemory->authorizationEngine[i].sem);
        if (sharedMemory->authorizationEngine[i].estado) { // Verifica se o engine está livre
            engineIndex = i;
            sharedMemory->authorizationEngine[i].estado = false; // Marca como ocupado
            sem_post(&sharedMemory->authorizationEngine[i].sem);
            break;
        }
        sem_post(&sharedMemory->authorizationEngine[i].sem);
    }

    if (engineIndex == -1) {
        printf("Todos os Authorization Engines estão ocupados.\n");
        return; // Se todos estão ocupados, retorna sem processar
    }

    // Processar a requisição
    if (strncmp(request, "Registo inicial", 15) == 0) {
        int initialPlafond;
        sscanf(request, "Registo inicial %d", &initialPlafond);
        sharedMemory->mobileRegisto->plafondAtual = initialPlafond;
    } else if (strncmp(request, "Pedido de autorização", 21) == 0) {
        int dataRequested;
        sscanf(request, "Pedido de autorização %d", &dataRequested);
        if (sharedMemory->mobileRegisto->plafondAtual >= dataRequested) {
            sharedMemory->mobileRegisto->plafondAtual -= dataRequested;
        } else {
            printf("Autorização negada. Plafond insuficiente.\n");
        }
    } else if (strncmp(request, "Pedido de estatísticas", 22) == 0) {
        printf("Estatísticas: Total de dados video: %d, Total de requisições video: %d\n",
               sharedMemory->alerta->totalVideoData, sharedMemory->alerta->totalVideoReqs);
    } else if (strncmp(request, "Reset de estatísticas", 21) == 0) {
        sharedMemory->alerta->totalVideoData = 0;
        sharedMemory->alerta->totalVideoReqs = 0;
    }

    // Libera o Authorization Engine
    sem_wait(&sharedMemory->authorizationEngine[engineIndex].sem);
    sharedMemory->authorizationEngine[engineIndex].estado = true;
    sem_post(&sharedMemory->authorizationEngine[engineIndex].sem);

    printf("Authorization Engine %d processou a requisição e está agora disponível.\n", engineIndex);
}



void startAuthorizationRequestManager(){
    pid_t pid = fork();
    if (pid == -1) {
        perror("Error forking process");
        exit(EXIT_FAILURE);
    } else if (pid == 0) {
        if (sem_init(&semaphore, 0, 1) != 0) {
            fprintf(stderr, "Erro ao inicializar o semáforo\n");
            exit(EXIT_FAILURE);
        }
        semaphoreAE = sem_open(SEM_SHARED_MEMORY, O_CREAT, S_IRUSR | S_IWUSR, 0);
        if(semaphoreAE == SEM_FAILED){
            fprintf(stderr, "Erro ao inicializar o semáforo\n");
            exit(EXIT_FAILURE);
        }
        createNamedPipes();
        inicializar_vetor_unnamed_pipes();
        createUnnamedPipes();
        //Fechar leitura unnamed pipe
        for(int i = 0; i<config.AUTH_SERVERS; i++){
            pid = fork();
            if(pid == -1){
                perror("Error forking process");
                exit(EXIT_FAILURE);
            }else if(pid == 0){
                createAuthorizationEngine(i);
            }
        }
        createThreads();
        sem_destroy(&semaphore);
        
    } else {
        printf("ARM created with pid %d\n", pid);
        
    }

}

int main(int argc, char *argv[]) {
    if (argc != 2) {
        printf("Usage: %s <config-file>\n", argv[0]);
        return EXIT_FAILURE;
    }

    signal(SIGINT, sigintHandler);

    if (readLog(argv[1], &config) != 0) {
        fprintf(stderr, "Failed to read config file\n");
        return EXIT_FAILURE;
    }
    
    startSharedMemory();
    startMessageQueue();
    startAuthorizationRequestManager();
    //startMonitorEngine();

    while (1) {
        pause(); 
    }
    return 0;
}