#include "header.h"

pid_t auth_request_manager, monitor_engine, backPid;

Queue *videoQueue, *otherQueue;

pthread_t receiver, sender;

Memory * sharedMemory;

int shmid, msgid, fdUser, fdBack, flag = 1; 

int ** unnamed_pipe;

sem_t log_semaphore, *semQueue, *semSharedMemory;



Config config;

pthread_mutexattr_t attrmutex;

pthread_condattr_t attrcondv;


pthread_cond_t queueCond = PTHREAD_COND_INITIALIZER;

pthread_mutex_t queue_mutex = PTHREAD_MUTEX_INITIALIZER;

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

void write_log(const char *message) {
    FILE *file;
    time_t now;
    char time_str[20]; 

    time(&now);
    struct tm *local_time = localtime(&now);
    strftime(time_str, sizeof(time_str), "%Y-%m-%d %H:%M:%S", local_time);

    sem_wait(&log_semaphore);


    file = fopen("log.txt", "a");
    if (!file) {
        perror("Failed to open log file");
        exit(EXIT_FAILURE);
    }


    fprintf(file, "[%s] %s\n", time_str, message);  
    fclose(file); 
    sem_post(&log_semaphore);

    printf("[%s] %s\n", time_str, message);
}


void startSharedMemory() {
    size_t totalSize = sizeof(Memory) +
                       sizeof(MobileRegisto) * config.MOBILE_USERS +
                       sizeof(AuthorizationEngine) * config.AUTH_SERVERS;
    if ((shmid = shmget(IPC_PRIVATE, totalSize, IPC_CREAT | 0766)) < 0) {
        perror("ERROR: shmget failed!");
        exit(EXIT_FAILURE);
    }

    if ((sharedMemory = (Memory*) shmat(shmid, NULL, 0)) == (Memory *) -1) {
        perror("ERROR: shmat failed!");
        exit(EXIT_FAILURE);
    }



    char *base = (char*) sharedMemory;
    sharedMemory->mobileRegisto = (MobileRegisto *)(base + sizeof(Memory));
    sharedMemory->authorizationEngine = (AuthorizationEngine *)(base + sizeof(Memory) + sizeof(MobileRegisto) * config.MOBILE_USERS);

    strcpy(sharedMemory->stats.statistics[0].statsName, "VIDEO");
    strcpy(sharedMemory->stats.statistics[1].statsName, "MUSIC");
    strcpy(sharedMemory->stats.statistics[2].statsName, "SOCIAL");

    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sharedMemory->authorizationEngine[i].estado = 1;
        sem_init(&sharedMemory->authorizationEngine[i].sem, 1, 1);
    }
        for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sharedMemory->authorizationEngine[i].estado = 1;
        sem_init(&sharedMemory->authorizationEngine[i].sem, 1, 1);
    }
    pthread_mutexattr_init(&attrmutex);
    pthread_mutexattr_setpshared(&attrmutex, PTHREAD_PROCESS_SHARED);

    pthread_condattr_init(&attrcondv);
    pthread_condattr_setpshared(&attrcondv, PTHREAD_PROCESS_SHARED);
    pthread_mutex_init(&sharedMemory->mutex, &attrmutex);
    pthread_cond_init(&sharedMemory->condMonitor, &attrcondv);
}

void startMessageQueue() {
    if ((msgid = msgget(KEY, IPC_CREAT | 0666)) < 0) {
        perror("Error creating Message Queue");
        exit(EXIT_FAILURE);
    }
}

void startSemaphores(){

    sem_unlink("semQueue");
    semQueue = sem_open("semQueue", O_CREAT | O_EXCL, 0777, 1);

    sem_unlink("semSharedMemory");
    semSharedMemory = sem_open("semSharedMemory", O_CREAT | O_EXCL, 0777, 1);
}

Queue * createQueue(){
    Queue * q = (Queue *) malloc(sizeof(Queue));
    q->pos = 0;
    q->next = NULL; 
    return q;
}

void insertQueue(Queue * q, char buffer[MESSAGE_SIZE]){
    sem_wait(semQueue);

    Queue *aux = q;

    while(aux->next != NULL){
        aux = aux->next;
    }

    aux->next = (Queue *) malloc(sizeof(Queue));

    if(aux->next == NULL){
        sem_post(semQueue);
        return;
    }

   strcpy(aux->next->buffer, buffer);
   aux->next->pos = aux->pos + 1;
   aux->next->next = NULL;
   pthread_cond_signal(&queueCond);
   sem_post(semQueue);

}

char* removeQueue(Queue * q){
    sem_wait(semQueue);

    if(q->next != NULL){
        Queue * aux = q->next;
        q->next = q->next->next;

        char *str = (char *)malloc(strlen(aux->buffer) + 1);
        if(str == NULL){
            sem_post(semQueue);
            return NULL;
        }

        strcpy(str, aux->buffer);
        free(aux);
        sem_post(semQueue);
        return str;
    }

    sem_post(semQueue);
    return NULL;
}

int emptyQueue(Queue * q){
    return q->next == NULL;
}



void cleanup_resources() {
 


}
void sigintHandler(int sig_num) {
    (void)sig_num;
    write_log("5G_AUTH_PLATFORM SIMULATOR CLOSING");
    kill(auth_request_manager, SIGINT);
    kill(monitor_engine, SIGINT);
    kill(backPid, SIGTERM);
    for(int i=0;i<config.MOBILE_USERS;i++){
        kill(sharedMemory->mobileRegisto[i].pid,SIGTERM);
    }
    pthread_cancel(receiver);
    pthread_cancel(sender);
    if (fdUser != -1)
    {
        close(fdUser);
        fdUser = -1;
    }
    if (fdBack != -1)
    {
        close(fdBack);
        fdBack = -1;
    }

       sem_destroy(&log_semaphore);
    sem_destroy(semQueue);
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sem_destroy(&sharedMemory->authorizationEngine[i].sem);
    }

    sem_destroy(semSharedMemory);
    

    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        close(unnamed_pipe[i][0]);
        close(unnamed_pipe[i][1]);
        free(unnamed_pipe[i]);
    }
    free(unnamed_pipe);

    if (shmid != -1) {
        shmctl(shmid, IPC_RMID, NULL);
    }

    if (msgid != -1) {
        msgctl(msgid, IPC_RMID, NULL);
    }

    unlink(USER_PIPE);
    unlink(BACK_PIPE);


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
}

void createUnnamedPipes() {

    if (unnamed_pipe == NULL) {
        perror("Erro ao alocar memória para unnamed_pipe.\n");
        exit(1);
    }

    for (int i = 0; i < config.AUTH_SERVERS; i++) {

        if (pipe(unnamed_pipe[i]) < 0) {
            perror("Erro ao criar o pipe.\n");
            for (int j = 0; j < i; j++) {
                close(unnamed_pipe[j][0]);
                close(unnamed_pipe[j][1]);
            }
            for (int j = 0; j < i; j++) {
                free(unnamed_pipe[j]);
            }
            free(unnamed_pipe);
            exit(1);
        }
    }

}

void inicializar_vetor_unnamed_pipes() {
    unnamed_pipe = (int**) malloc(sizeof(int*) * config.AUTH_SERVERS);

    if (unnamed_pipe == NULL) {
        perror("Falha ao alocar memória para a variável unnamed_pipe.\n");
        exit(1);
    }

    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        unnamed_pipe[i] = (int*) malloc(sizeof(int) * 2);

        if (unnamed_pipe[i] == NULL) {
            perror("Erro ao alocar memória para unnamed_pipe[i].\n");
            for (int j = 0; j < i; j++) free(unnamed_pipe[j]);
            free(unnamed_pipe);
            exit(1);
        }
    }
}

void processBackRequest(char * buffer){
    strtok(buffer, "#");
    char *command = strtok(NULL, "#");

    if(strcmp(command, "reset") == 0){
        sharedMemory->stats.statistics[0].data = 0;
        sharedMemory->stats.statistics[0].requests = 0;
        sharedMemory->stats.statistics[1].data = 0;
        sharedMemory->stats.statistics[1].requests = 0;
        sharedMemory->stats.statistics[2].data = 0;
        sharedMemory->stats.statistics[2].requests = 0;
    }else if(strcmp(command, "data_stats") == 0){
        stats_msg msgStats;
        msgStats.tipo = 1;
        msgStats.stats = sharedMemory->stats;
        msgsnd(msgid, &msgStats, sizeof(stats_msg) - sizeof(long), 0);
    }
}

void processRequest(char *buffer){

    int count = 0;
    int pid, requestedPlafond = 0;
    char type[20]; 
    char str[100];
    int i = 0;
    for(i = 0; buffer[i] != '\0'; i++) {
        if (buffer[i] == '#') count++;
    }

    if(count == 1 &&  buffer[1] == '#'){
        processBackRequest(buffer);
        return;
    }

    if(count == 1){
        if(sscanf(buffer, "%d#%d", &pid, &requestedPlafond) == 2){
            for(i = 0; i < config.MOBILE_USERS; i++) {
                sem_wait(semSharedMemory);

                if (sharedMemory->mobileRegisto[i].pid == 0) {
                    sharedMemory->mobileRegisto[i].pid = pid;
                    sharedMemory->mobileRegisto[i].plafondInicial = requestedPlafond;
                    sharedMemory->mobileRegisto[i].plafondAtual = requestedPlafond;
                    sharedMemory->mobileRegisto[i].flag = 0;
                    sem_post(semSharedMemory);
                    printf("Registro inicial: PID %d, Plafond %d\n", pid, requestedPlafond);
                    return;
                }else{
                    sem_post(semSharedMemory);
                }
            }
        }
        }else if (count == 2) {

        if (sscanf(buffer, "%d#%19[^#]#%d", &pid, type, &requestedPlafond) == 3) {
            for(i = 0; i < config.MOBILE_USERS; i++) {
                sem_wait(semSharedMemory);
                if (sharedMemory->mobileRegisto[i].pid == pid) {
                    if(strcmp(type, "VIDEO") == 0){
                        sharedMemory->stats.statistics[0].requests += 1;
                        sharedMemory->stats.statistics[0].data += requestedPlafond;
                    }else if(strcmp(type, "MUSIC") == 0){
                        sharedMemory->stats.statistics[1].requests += 1;
                        sharedMemory->stats.statistics[1].data += requestedPlafond;
                    }else if(strcmp(type, "SOCIAL") == 0){
                        sharedMemory->stats.statistics[2].requests += 1;
                        sharedMemory->stats.statistics[2].data += requestedPlafond;
                    }else{
                        continue;
                    }
                    if (sharedMemory->mobileRegisto[i].plafondAtual >= requestedPlafond) {
                        sharedMemory->mobileRegisto[i].plafondAtual -= requestedPlafond;
                        sprintf(str,"AUTHORIZATION_ENGINE %d:VIDEO AUTHORIZATION REQUEST (ID = %d id) PROCESSING COMPLETED", i, pid);
                        write_log(str);
                        pthread_cond_signal(&sharedMemory->condMonitor);
                    } else {
                        printf("Autorização negada para %d. Plafond insuficiente.\n", pid);
                    }
                    sem_post(semSharedMemory);
                }else{
                    sem_post(semSharedMemory);
                }
            }
        }
    }
}

void *receiverThread(void *arg) {
    write_log("THREAD RECEIVER CREATED");
    (void) arg;
    fdUser = open(USER_PIPE, O_RDWR);
    fdBack = open(BACK_PIPE, O_RDWR);
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
            ssize_t buff_size;

            
            buff_size = read(fdUser, buffer, MESSAGE_SIZE);

            buffer[buff_size] = '\0';

            if(strstr(buffer, "VIDEO") != NULL){
                insertQueue(videoQueue, buffer);
            }
            else{
                insertQueue(otherQueue, buffer);
            }

        }
        if(FD_ISSET(fdBack, &readfds)){
            char buffer[MESSAGE_SIZE];
            ssize_t buff_size;

            buff_size = read(fdBack, buffer, MESSAGE_SIZE);
            if(flag == 1){
                backPid = atoi(buffer);
                flag = 0;
            }
        
            buffer[buff_size] = '\0';
            insertQueue(otherQueue, buffer);
        }   
    }
}


void sendRequestToAuthorizationEngine(char *data) {
    printf("data-%s\n", data);
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        if (sharedMemory->authorizationEngine[i].estado == 1) {
            sharedMemory->authorizationEngine[i].estado = 0;
            write(unnamed_pipe[i][1], data, strlen(data) + 1);
            break;
        }
    }
}

void *senderThread(void *arg) {
    write_log("THREAD SENDER CREATED");
    (void)arg;

    while (1) {
        pthread_mutex_lock(&queue_mutex);
        pthread_cond_wait(&queueCond, &queue_mutex);
        pthread_mutex_unlock(&queue_mutex);

        while(!emptyQueue(videoQueue)){
            processRequest(removeQueue(videoQueue));
        }
        if(!emptyQueue(otherQueue)){
            processRequest(removeQueue(otherQueue));
        }

    }
    return NULL;
}


void createThreads(){
    if(pthread_create(&receiver, NULL, receiverThread, NULL) != 0){
        perror("Erro ao criar a thread receiver");
    }

    if(pthread_create(&sender, NULL, senderThread, NULL) != 0){
        perror("Erro ao criar a thread sender");
    }
    
    pthread_join(receiver, NULL);
    pthread_join(sender, NULL);

}


void createAuthorizationEngine(int i){
    char str[30];
    sprintf(str, "AUTHORIZATION_ENGINE %d READY", i);
    write_log(str);
    char buffer[MESSAGE_SIZE];
    if(read(unnamed_pipe[i][0], buffer, MESSAGE_SIZE) < 0){
        perror("Error reading from pipe");
        exit(EXIT_FAILURE);
    }
    processRequest(buffer);
}


void send_alert(int msgid, int user_id, int percentage) {
    msgBuffer msg;
    msg.mtype = user_id;
    sprintf(msg.mtext, "%d#%d", user_id, percentage);
    msgsnd(msgid, &msg, strlen(msg.mtext) + 1, 0);
}


void * alertThread(){
    char str[MESSAGE_SIZE];
    while(1){
        pthread_mutex_lock(&sharedMemory->mutex);
        pthread_cond_wait(&sharedMemory->condMonitor, &sharedMemory->mutex);
        for(int i = 0; i <config.MOBILE_USERS; i++){
            if(sharedMemory->mobileRegisto[i].pid != 0){
                float currentUsage = 1.0 - ((float) sharedMemory->mobileRegisto[i].plafondAtual / sharedMemory->mobileRegisto[i].plafondInicial);
                if(currentUsage >= 0.8 && currentUsage < 0.9 && sharedMemory->mobileRegisto[i].flag == 0){
                    sprintf(str, "ALERT 80%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, sharedMemory->mobileRegisto[i].pid, 80);
                    sharedMemory->mobileRegisto[i].flag = 80;
                }else if(currentUsage >= 0.9 && currentUsage < 1 && (sharedMemory->mobileRegisto[i].flag == 80 || sharedMemory->mobileRegisto[i].flag == 0)){
                    sprintf(str, "ALERT 90%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, sharedMemory->mobileRegisto[i].pid, 90);
                    sharedMemory->mobileRegisto[i].flag = 90;
                }else if(currentUsage == 1 && (sharedMemory->mobileRegisto[i].flag == 90 || sharedMemory->mobileRegisto[i].flag == 80 || sharedMemory->mobileRegisto[i].flag == 0)){
                    sprintf(str, "ALERT 100%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, sharedMemory->mobileRegisto[i].pid, 100);
                    sharedMemory->mobileRegisto[i].flag = 100;
                }   
            }
        }
        pthread_mutex_unlock(&sharedMemory->mutex);
    }
}

void * statsThread(){
    while(1){
        sem_wait(semSharedMemory);
        stats_msg msgStats;
        msgStats.tipo = 1;
        msgStats.stats = sharedMemory->stats;
        msgsnd(msgid, &msgStats, sizeof(stats_msg) - sizeof(long), 0);
        sem_post(semSharedMemory);
        sleep(30);
    }
}

void startMonitorEngine(){
    monitor_engine = fork();
    if (monitor_engine == -1){
        perror("Error forking process");
        exit(EXIT_FAILURE);
    }else if(monitor_engine == 0){
        write_log("PROCESS MONITOR_ENGINE CREATED");
        pthread_t alert_Thread, stats_Thread;
        if(pthread_create(&alert_Thread,NULL,alertThread,NULL)==-1){
            write_log("Error creating threads request\n");
        }
        if(pthread_create(&stats_Thread,NULL,statsThread,NULL)==-1){
            write_log("Error creating threads request\n");
        }
        if(pthread_join(alert_Thread,NULL) != 0){
            write_log("Error in join function");
        }
        if(pthread_join(stats_Thread,NULL) != 0){
            write_log("Error in join function");
        }
    }
}


void startAuthorizationRequestManager(){
    auth_request_manager = fork();
    if (auth_request_manager == -1) {
        perror("Error forking process");
        exit(EXIT_FAILURE);
    } else if (auth_request_manager == 0) {
        write_log("PROCESS AUTHORIZATION_REQUEST_MANAGER CREATED");

        createNamedPipes();

        videoQueue = createQueue();
        videoQueue->tempo = config.MAX_VIDEO_WAIT;

        otherQueue = createQueue();
        otherQueue->tempo = config.MAX_OTHERS_WAIT;

        createThreads();
        exit(0);
        
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

    

    if (sem_init(&log_semaphore, 0, 1) != 0) {
        perror("Semaphore initialization failed");
        return EXIT_FAILURE;
    }
    write_log("5G_AUTH_PLATFORM SIMULATOR STARTING");
    write_log("PROCESS SYSTEM_MANAGER CREATED");
    startSemaphores();
    startSharedMemory();
    startMessageQueue();
    startAuthorizationRequestManager();
    startMonitorEngine();

    while (1) {
        pause(); 
    }
    return 0;
}