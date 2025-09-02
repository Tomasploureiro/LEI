#include "header.h"

Queue *videoQueue, *otherQueue;

Memory * sharedMemory;

int shmid, msgid; 

int ** unnamed_pipe;

sem_t log_semaphore, semQueue;

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

void cria_fila(Queue** head){
    *head = malloc(sizeof(Queue));
    if (*head == NULL) {
        exit(EXIT_FAILURE);
    }
    (*head)->inicio = NULL;
    (*head)->fim = NULL;
    (*head)->tamanho = 0;
}


no* insertQueue(Queue *head, char *s){
    sem_wait(&semQueue);
    if(head->tamanho == config.QUEUE_POS){
        return NULL;
    }

    no* n = malloc(sizeof(no));
    if(n != NULL){
        n->buffer = malloc(sizeof(char) * (strlen(s)+1));
        if( n->buffer == NULL){
            return NULL;
        }
        strcpy(n->buffer, s);
        n->next = NULL;
        if(head->fim != NULL){
            head->fim->next = n;
        }
        head->fim = n;
        if(head->inicio == NULL){
            head->inicio = n;
        }
        head->tamanho++;
    }
    printf("\nInserted into queue: %s | Size %d\n", n->buffer, head->tamanho);
    sem_post(&semQueue);
    return n;
}

char *removeQueue(Queue *head){
    if( head->inicio != NULL){
        char *res = head->inicio->buffer;  
        head->inicio = head->inicio->next;
        head->tamanho--;
        return res;
    }else {
        printf("Queue is empty but size is: %d\n", head->tamanho);
        return 0;
    }
}



void startSharedMemory() {
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

    char *base = (char*) sharedMemory;
    sharedMemory->mobileRegisto = (MobileRegisto *)(base + sizeof(Memory));
    sharedMemory->authorizationEngine = (AuthorizationEngine *)(base + sizeof(Memory) + sizeof(MobileRegisto) * config.MOBILE_USERS);
    sharedMemory->alerta = (Alerta *)(base + sizeof(Memory) + sizeof(MobileRegisto) * config.MOBILE_USERS + sizeof(AuthorizationEngine) * config.AUTH_SERVERS);

    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sharedMemory->authorizationEngine[i].estado = 1;
        sem_init(&sharedMemory->authorizationEngine[i].sem, 1, 1);
    }
        for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sharedMemory->authorizationEngine[i].estado = 1;
        sem_init(&sharedMemory->authorizationEngine[i].sem, 1, 1);
    }

    for (int i = 0; i < config.MOBILE_USERS; i++) {
        sharedMemory->mobileRegisto[i].pid = 0;
        sem_init(&sharedMemory->mobileRegisto[i].semPlafond, 1, 1);
    }
}

void startMessageQueue() {
    if ((msgid = msgget(KEY, IPC_CREAT | 0666)) < 0) {
        perror("Error creating Message Queue");
        exit(EXIT_FAILURE);
    }
}

void cleanup_resources() {
    write_log(" 5G_AUTH_PLATFORM SIMULATOR CLOSING");
    sem_destroy(&log_semaphore);
    sem_destroy(&semQueue);
    for (int i = 0; i < config.AUTH_SERVERS; i++) {
        sem_destroy(&sharedMemory->authorizationEngine[i].sem);
    }
    for (int i = 0; i < config.MOBILE_USERS; i++) {
        sem_destroy(&sharedMemory->mobileRegisto[i].semPlafond);
    }
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


}
void sigintHandler(int sig_num) {
    (void)sig_num;
    cleanup_resources();
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


void *receiverThread(void *arg) {
    write_log("THREAD RECEIVER CREATED");
    (void) arg;
    int fdUser = open(USER_PIPE, O_RDONLY | O_NONBLOCK);
    int fdBack = open(BACK_PIPE, O_RDONLY | O_NONBLOCK);
    int buff_size;
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
            if ((buff_size = read(fdUser, buffer, MESSAGE_SIZE)) <= 0){
                perror("Error reading from USER pipe ");
                exit(EXIT_FAILURE);
            }
            buffer[strcspn(buffer, "\n")] = '\0';
            printf("data-%s\n", buffer);
            if(strstr(buffer, "VIDEO") != NULL){
                //printf("data-%s\n", buffer);
                insertQueue(videoQueue, buffer);
            }
            else{
                //printf("data-%s\n", buffer);
                insertQueue(otherQueue, buffer);
            }

        }
        if(FD_ISSET(fdBack, &readfds)){
            char buffer[MESSAGE_SIZE];
            if ((buff_size = read(fdBack, buffer, MESSAGE_SIZE)) <= 0){
                perror("Error reading BACK pipe");
                exit(EXIT_FAILURE);
            }
            buffer[strcspn(buffer, "\n")] = '\0';
            insertQueue(otherQueue, buffer);

        }   
    }
    return NULL;
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
        sem_wait(&semQueue);
        if (videoQueue != NULL) {
            sendRequestToAuthorizationEngine(removeQueue(videoQueue));
        } else if (otherQueue != NULL) {
            sendRequestToAuthorizationEngine(removeQueue(otherQueue));
        }
        sem_post(&semQueue);
        usleep(100000);
    }
    return NULL;
}


void createThreads(){
    pthread_t receiver, sender;
    int result;
    result = pthread_create(&receiver, NULL, receiverThread, NULL);
    if(result != 0){
        perror("Thread creation failed");
        exit(EXIT_FAILURE);
    }
    pthread_create(&sender, NULL, senderThread, NULL);
    
    pthread_join(receiver, NULL);
    pthread_join(sender, NULL);

}


void processRequest(char *request, int engineIndex) {
    int count = 0;
    int pid, requestedPlafond = 0;
    char type[20]; 
    int i;
    char str[100];

    for(i = 0; request[i] != '\0'; i++) {
        if (request[i] == '#') count++;
    }

    sem_wait(&sharedMemory->authorizationEngine[engineIndex].sem);
    if (count == 1) {
        if (sscanf(request, "%d#%d", &pid, &requestedPlafond) == 2) {
            bool assigned = false;
            for(i = 0; i < config.MOBILE_USERS && !assigned; i++) {
                sem_wait(&sharedMemory->mobileRegisto[i].semPlafond);
                if (sharedMemory->mobileRegisto[i].pid == 0) {
                    sharedMemory->mobileRegisto[i].pid = pid;
                    sharedMemory->mobileRegisto[i].plafondInicial = requestedPlafond;
                    sharedMemory->mobileRegisto[i].plafondAtual = requestedPlafond;
                    sem_post(&sharedMemory->mobileRegisto[i].semPlafond);
                    assigned = true;
                    printf("Registro inicial: PID %d, Plafond %d\n", pid, requestedPlafond);
                }else{
                    sem_post(&sharedMemory->mobileRegisto[i].semPlafond);
                }
            }
            if (!assigned) {
                printf("Falha ao registrar: Nenhum slot disponível.\n");
            }
        }
    } else if (count == 2) {

        if (sscanf(request, "%d#%19[^#]#%d", &pid, type, &requestedPlafond) == 3) {
            bool found = false;

            for(i = 0; i < config.MOBILE_USERS && !found; i++) {
                sem_wait(&sharedMemory->mobileRegisto[i].semPlafond);
                if (sharedMemory->mobileRegisto[i].pid == pid) {
                    if (sharedMemory->mobileRegisto[i].plafondAtual >= requestedPlafond) {
                        sharedMemory->mobileRegisto[i].plafondAtual -= requestedPlafond;
                        sprintf(str,"AUTHORIZATION_ENGINE %d:VIDEO AUTHORIZATION REQUEST (ID = %d id) PROCESSING COMPLETED", i, pid);
                        write_log(str);
                        found = true;
                    } else {
                        printf("Autorização negada para %d. Plafond insuficiente.\n", pid);
                        found = true;
                    }
                    sem_post(&sharedMemory->mobileRegisto[i].semPlafond);
                }else{
                    sem_post(&sharedMemory->mobileRegisto[i].semPlafond);
                }
            }
            if (!found) {
                printf("Usuário com PID %d não encontrado.\n", pid);
            }
        }
    }

    sharedMemory->authorizationEngine[engineIndex].estado = 1;
    sem_post(&sharedMemory->authorizationEngine[engineIndex].sem);
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
    processRequest(buffer, i);
}


void send_alert(int msgid, int user_id, int percentage) {
    msgBuffer msg;
    msg.mtype = 1;
    sprintf(msg.mtext, "%d#%d", user_id, percentage);
    msgsnd(msgid, &msg, strlen(msg.mtext) + 1, 0);
}

void send_periodic_stats(int msgid) {
    msgBuffer msg;
    msg.mtype = 1;
    strcpy(msg.mtext, "Estatísticas periódicas: [dados estatísticos]");
    msgsnd(msgid, &msg, strlen(msg.mtext) + 1, 0);
}

void startMonitorEngine(){
    pid_t pid = fork();
    if (pid == -1) {
        perror("Error forking process");
        exit(EXIT_FAILURE);
    }else if (pid == 0){
        int count = 0;
        time_t last_stats_time = time(NULL);
        char str[30];
        while (1) {
            for (int i = 0; i < config.MOBILE_USERS; i++) {
                sem_wait(&sharedMemory->mobileRegisto[i].semPlafond);
                int current_usage = sharedMemory->mobileRegisto[i].plafondAtual;
                int initial_plafond = sharedMemory->mobileRegisto[i].plafondInicial;
                int id = sharedMemory->mobileRegisto[i].pid;
                if (current_usage <= 0  && id != 0) {
                    sprintf(str, "ALERT 100%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, id, 100);
                } else if (current_usage <= 0.1 * initial_plafond && id != 0 && count == 1) {
                    count ++;
                    sprintf(str, "ALERT 90%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, id, 90);
                } else if (current_usage <= 0.2 * initial_plafond && id != 0 && count == 0) {
                    count ++;
                    sprintf(str, "ALERT 80%% (USER %d) TRIGGERED", i);
                    write_log(str);
                    send_alert(msgid, id, 80);
                }
                sem_post(&sharedMemory->mobileRegisto[i].semPlafond);
            }

            if (difftime(time(NULL), last_stats_time) >= PERIODIC_STATS_INTERVAL) {
                send_periodic_stats(msgid);
                last_stats_time = time(NULL);
            }

            sleep(1); 
        }
    }else{
        write_log("PROCESS MONITOR_ENGINE CREATED");
    }
}



void startAuthorizationRequestManager(){
    pid_t pid = fork();
    if (pid == -1) {
        perror("Error forking process");
        exit(EXIT_FAILURE);
    } else if (pid == 0) {
        videoQueue = malloc(sizeof(Queue));
        otherQueue = malloc(sizeof(Queue));
        cria_fila(&videoQueue);
        cria_fila(&otherQueue);
        createNamedPipes();
        inicializar_vetor_unnamed_pipes();
        createUnnamedPipes();
        pid_t pid2;
        for(int i = 0; i<config.AUTH_SERVERS; i++){
            pid2 = fork();
            if(pid2 == -1){
                perror("Error forking process");
                exit(EXIT_FAILURE);
            }else if(pid2 == 0){
               createAuthorizationEngine(i);
                exit(0);
            }
        }
        createThreads();
        exit(0);
        
    } else {
        write_log("PROCESS AUTHORIZATION_REQUEST_MANAGER CREATED");
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
    startSharedMemory();
    startMessageQueue();
    startAuthorizationRequestManager();
    startMonitorEngine();

    while (1) {
        pause(); 
    }
    return 0;
}