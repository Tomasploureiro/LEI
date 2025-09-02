#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <signal.h>
#include <string.h>
#include <math.h>
#include <sys/types.h>
#include <semaphore.h>

int flag = 1;

pid_t pid1, pid2, pid3;

int comparar(const void *a, const void *b) {
    double valor1 = *(double *)a;
    double valor2 = *(double *)b;
    return (valor1 > valor2) - (valor1 < valor2);
}

char **lerValores(const char *nome_ficheiro, int coluna, double **valores, int *tamanho, char cabecalho) {
    FILE *ficheiro_csv = fopen(nome_ficheiro, "r");
    if (ficheiro_csv == NULL) {
        printf("PID: %d - Houve um erro a tentar abrir o ficheiro\n", getpid());
        return NULL;
    }

    if (cabecalho == 's') {
        char linha[500];
        fgets(linha, sizeof(linha), ficheiro_csv);
    }

    char **linhas = NULL;
    *valores = NULL;
    int contador = 0;

    while (!feof(ficheiro_csv)) {
        char linha[500];
        if (fgets(linha, sizeof(linha), ficheiro_csv) == NULL) break;

        char *token = strtok(linha, ",");
        int coluna_atual = 1;
        double valor = 0;
        char linha_completa[700];
        strcpy(linha_completa, linha);

        while (token != NULL) {
            if (coluna_atual == coluna) {
                while (*token == ' ') token++;
                valor = atof(token);
                break;
            }
            token = strtok(NULL, ",");
            coluna_atual++;
        }

        linhas = realloc(linhas, (contador + 1) * sizeof(char *));
        linhas[contador] = strdup(linha_completa);
        *valores = realloc(*valores, (contador + 1) * sizeof(double));
        (*valores)[contador] = valor;
        contador++;
    }

    fclose(ficheiro_csv);
    *tamanho = contador;
    return linhas;
}

double calcular_mediana(double *dados, int tamanho) {
    qsort(dados, tamanho, sizeof(double), comparar);
    if (tamanho % 2 == 0) {
        return (dados[tamanho / 2 - 1] + dados[tamanho / 2]) / 2.0;
    } else {
        return dados[tamanho / 2];
    }
}

double calcular_desviop(double *dados, int tamanho) {
    double soma = 0.0, media, desvio = 0.0;

    for (int i = 0; i < tamanho; i++) {
        soma += dados[i];
    }
    media = soma / tamanho;
    for (int i = 0; i < tamanho; i++) {
        desvio += pow(dados[i] - media, 2);
    }

    return sqrt(desvio / tamanho);
}

void escrever_ficha03_mediana(const char *nome_ficheiro, double mediana, double desvio) {
    FILE *f_mediana = fopen(nome_ficheiro, "a");
    if (!f_mediana) {
        perror("Houve um erro a tentar abrir o ficheiro");
        exit(EXIT_FAILURE);
    }

    fprintf(f_mediana, "Mediana: %.2f\n", mediana);
    fprintf(f_mediana, "Desvio Padrao: %.2f\n", desvio);

    fclose(f_mediana);
}

void escrever_ficha03_coluna(const char *nome_ficheiro, double *valores, int tamanho) {
    qsort(valores, tamanho, sizeof(double), comparar);

    FILE *f_coluna = fopen(nome_ficheiro, "w");
    if (!f_coluna) {
        perror("Houve um erro a tentar abrir o ficheiro");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < tamanho; i++) {
        fprintf(f_coluna, "%.2f\n", valores[i]);
    }

    fclose(f_coluna);
}

void histograma(double *dados, int tamanho, int num_bins) {
    double min = dados[0], max = dados[0];
    for (int i = 1; i < tamanho; i++) {
        if (dados[i] < min) min = dados[i];
        if (dados[i] > max) max = dados[i];
    }
    double bin_tamanho = (max - min) / num_bins;
    int *bins = (int *)calloc(num_bins, sizeof(int));

    for (int i = 0; i < tamanho; i++) {
        int bin_index = (int)((dados[i] - min) / bin_tamanho);
        if (bin_index == num_bins) bin_index--;
        bins[bin_index]++;
    }

    printf("Histogram:\n");
    for (int i = 0; i < num_bins; i++) {
        printf("Bin %d (%.2f to %.2f): ", i, min + i * bin_tamanho, min + (i + 1) * bin_tamanho);
        for (int j = 0; j < bins[i]; j++) {
            printf("*");
        }
        printf("\n");
    }

    free(bins);
}

void remover_ficheiros() {
    if (remove("ficha03_mediana.dat") == 0) {
        printf("PID: %d - ficha03_mediana.dat foi removido.\n", getpid());
    } else {
        printf("PID: %d - Não foi possível remover ficha03_mediana.dat.\n", getpid());
    }

    if (remove("ficha03_coluna.dat") == 0) {
        printf("PID: %d - ficha03_coluna.dat foi removido.\n", getpid());
    } else {
        printf("PID: %d - Não foi possível remover ficha03_coluna.dat.\n", getpid());
    }

    exit(0);
}

void handle_sigusr1_pai() {
    printf("PROCESSO PAI (PID %d): sinal SIGUSR1 recebido do processo filho A (PID %d). Sinal SIGUSR2 será de seguida enviado ao processo filho B (PID %d) para apresentação do conteúdo dos ficheiros gerados. Pressione uma tecla para continuar…\n", getpid(), pid1, pid2);
    getchar(); // Esperar por entrada do usuário
    kill(pid2, SIGUSR2);
    printf("PROCESSO PAI (PID %d): Sinal SIGUSR2 enviado ao processo filho B (PID %d)\n", getpid(), pid2);
}

void handle_sigusr2_pai() {
    printf("PROCESSO PAI (PID %d): sinal SIGUSR2 recebido do processo filho B (PID %d). Sinal SIGTERM será de seguida enviado ao processo filho C (PID %d) para geração do histograma. Pressione uma tecla para continuar…\n", getpid(), pid2, pid3);
    getchar(); // Esperar por entrada do usuário
    kill(pid3, SIGTERM);
    printf("PROCESSO PAI (PID %d): Sinal SIGTERM enviado ao processo filho C (PID %d)\n", getpid(), pid3);
}

void handle_sigterm_pai() {
    printf("PROCESSO PAI (PID %d): sinal SIGTERM recebido do processo filho C (PID %d). Retornando para o submenu da ficha 3. Pressione uma tecla para continuar…\n", getpid(), pid3);
    getchar(); // Esperar por entrada do usuário
    flag = 1;
}

void handle_sigusr1_filho_a() {
    printf("PROCESSO FILHO A (PID %d): sinal SIGUSR1 recebido!\n", getpid());

    char nome_ficheiro[100];
    char cabecalho;
    int coluna;

    // Use fgets e sscanf em vez de scanf para evitar problemas de buffer
    printf("Insira o nome do ficheiro: ");
    fflush(stdout);
    fgets(nome_ficheiro, sizeof(nome_ficheiro), stdin);
    nome_ficheiro[strcspn(nome_ficheiro, "\n")] = 0; // Remover a nova linha

    printf("O ficheiro tem cabecalho? (s/n): ");
    fflush(stdout);
    scanf(" %c", &cabecalho);

    printf("Qual a coluna a analisar? ");
    fflush(stdout);
    scanf("%d", &coluna);

    // Clear the newline character left in the input buffer
    int c;
    while ((c = getchar()) != '\n' && c != EOF) {}

    double *valores;
    int tamanho;
    char **linhas = lerValores(nome_ficheiro, coluna, &valores, &tamanho, cabecalho);

    if (linhas != NULL) {
        double mediana = calcular_mediana(valores, tamanho);
        double desvio = calcular_desviop(valores, tamanho);

        escrever_ficha03_mediana("ficha03_mediana.dat", mediana, desvio);
        escrever_ficha03_coluna("ficha03_coluna.dat", valores,tamanho);
       
        // Free memory allocated for lines
        for (int i = 0; i < tamanho; i++) {
            free(linhas[i]);
        }
        free(linhas);
        free(valores);
    }

    // Signal parent process
    kill(getppid(), SIGUSR1);
}



void handle_sigusr2_filho_b() {
    printf("PROCESSO FILHO B (PID %d): Sinal SIGUSR2 recebido. Pressione uma tecla para continuar…\n", getpid());
    getchar(); // Esperar por entrada do usuário

    FILE *f_mediana = fopen("ficha03_mediana.dat", "r");
    if (!f_mediana) {
        perror("Erro ao abrir ficha03_mediana.dat");
        return;
    }
    printf("Conteúdo de ficha03_mediana.dat:\n");
    char linha[500];
    while (fgets(linha, sizeof(linha), f_mediana)) {
        printf("%s", linha);
    }
    fclose(f_mediana);

    FILE *f_coluna = fopen("ficha03_coluna.dat", "r");
    if (!f_coluna) {
        perror("Erro ao abrir ficha03_coluna.dat");
        return;
    }
    printf("Conteúdo de ficha03_coluna.dat:\n");
    while (fgets(linha, sizeof(linha), f_coluna)) {
        printf("%s", linha);
    }
    fclose(f_coluna);

    kill(getppid(), SIGUSR2);
    printf("PROCESSO FILHO B (PID %d): Sinal SIGUSR2 enviado ao processo pai (PID %d)\n", getpid(), getppid());
}


void handle_sigterm_filho_c() {
    printf("PROCESSO FILHO C (PID %d): Sinal SIGTERM recebido. Pressione uma tecla para continuar…\n", getpid());
    getchar(); // Esperar por entrada do usuário

    FILE *f_coluna = fopen("ficha03_coluna.dat", "r");
    if (!f_coluna) {
        perror("Erro ao abrir ficha03_coluna.dat");
        return;
    }

    double *valores = NULL;
    int tamanho = 0;
    double valor;
    while (fscanf(f_coluna, "%lf", &valor) != EOF) {
        valores = realloc(valores, (tamanho + 1) * sizeof(double));
        valores[tamanho++] = valor;
    }
    fclose(f_coluna);

    histograma(valores, tamanho, 10);
    free(valores);

    kill(getppid(), SIGTERM);
    printf("PROCESSO FILHO C (PID %d): Sinal SIGTERM enviado ao processo pai (PID %d)\n", getpid(), getppid());
}



void opcao_a() {
    printf("PROCESSO PAI %d: Enviando sinal SIGUSR1 ao processo filho A %d\n", getpid(), pid1);
    kill(pid1, SIGUSR1);
}

void opcao_b() {
    remover_ficheiros();
    exit(0);
}



int main() {
    // Configurar sinais para o processo pai
    signal(SIGUSR1, handle_sigusr1_pai);
    signal(SIGUSR2, handle_sigusr2_pai);
    signal(SIGTERM, handle_sigterm_pai);


    // Criar primeiro filho
    pid1 = fork();
    if (pid1 == 0) {
        signal(SIGUSR1, handle_sigusr1_filho_a);
        while (1) pause();
    } else {
        // Criar segundo filho
        pid2 = fork();
        if (pid2 == 0) {
            signal(SIGUSR2, handle_sigusr2_filho_b);
            while (1) pause();
        } else {
            // Criar terceiro filho
            pid3 = fork();
            if (pid3 == 0) {
                signal(SIGTERM, handle_sigterm_filho_c);
                while (1) pause();
            } else {
                
            while (1) {
                if (flag == 1){
                    // Processo pai
                    printf("PROCESSO PAI %d: Criei os processos filhos A %d, B %d e C %d\n", getpid(), pid1, pid2, pid3);
                    printf("\nMenu - Ficha 3\n");
                    printf("a) Cálculo da mediana e desvio padrão\n");
                    printf("b) Apagar ficheiros de resultados\n");
                    printf("c) Sair\n");
                    printf("opcao: ");
                
                    char opcao;
                    scanf(" %c", &opcao);
                
                    switch (opcao){
                    case 'a':
                        printf("PROCESSO PAI %d: Enviando sinal SIGUSR1 ao processo filho A %d\n", getpid(), pid1);
                        kill(pid1, SIGUSR1);
                        break;
                    case 'b':
                        remover_ficheiros();
                        break;
                    case 'c':
                        printf("A sair...\n");
                        exit(0);
                    default:
                        printf("Tenta outra vez...\n");
                        break;
                    }
                        flag = 0;
                    }
                    pause();
                }
            }
        }
    }
    return 0;
}