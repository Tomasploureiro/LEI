#include <iostream>
#include <vector>
#include <string>
#include <bitset>
#include <climits>
#include <cctype>
#include <algorithm>
using namespace std;

// Define o número máximo de candidatos (células vazias) no tabuleiro.
const int MAX_CANDIDATOS = 256;
// Vetores que indicam as direções: cima, direita, baixo e esquerda.
const int dLinha[4] = {-1, 0, 1, 0};
const int dColuna[4] = {0, 1, 0, -1};

// Estrutura que representa uma célula candidata para colocar uma torre.
struct Candidato {
    int r, c;                           // Posição (linha e coluna) do candidato.
    bitset<MAX_CANDIDATOS> cobertura;     // Conjunto de candidatos (células vazias) que seriam cobertos se uma torre fosse colocada aqui.
    bitset<MAX_CANDIDATOS> conflito;      // Conjunto de candidatos que conflitam com este (torres se veriam mutuamente).
};

// Estrutura que representa um posto (célula com dígito) que exige um número exato de torres ao seu redor.
struct Posto {
    int r, c, requerido;                // Posição (linha e coluna) e número de torres requeridas.
    vector<int> indicesCandidatos;      // Índices dos candidatos imediatamente adjacentes a este posto.
};

int R, C;                               // Dimensões do tabuleiro.
vector<string> tabuleiro;               // Representação do tabuleiro.
vector<Candidato> candidatos;           // Lista de candidatos (células vazias onde torres podem ser colocadas).
int N;                                  // Número total de candidatos.
vector<vector<int>> indiceCandidato;    // Matriz que armazena, para cada célula, o índice do candidato (ou -1 se não for candidata).
vector<Posto> postos;                   // Lista de postos (células com dígito).

// Bitset que representa a cobertura total desejada (todas as células vazias devem ser cobertas).
bitset<MAX_CANDIDATOS> coberturaCompleta;
// Variável que guarda o menor número de torres encontrados para uma solução.
int melhor = INT_MAX;

// Função recursiva que implementa a busca (DFS) com poda.
// Parâmetros:
// - coberta: bitset com os candidatos já cobertos pelas torres escolhidas.
// - disponivel: bitset com os candidatos que ainda podem ser selecionados.
// - contagemPostos: vetor com a contagem atual de torres adjacentes a cada posto.
// - escolhidos: número de torres já selecionadas.
void buscaRecursiva(bitset<MAX_CANDIDATOS> coberta, bitset<MAX_CANDIDATOS> disponivel, vector<int>& contagemPostos, int escolhidos) {
    if (escolhidos >= melhor) return;  // Poda: se já escolheu mais torres que a melhor solução encontrada, interrompe.
    // Se todas as células vazias estão cobertas, verifica se os postos têm o número exato de torres requisitado.
    if (coberta == coberturaCompleta) {
        for (size_t i = 0; i < postos.size(); i++) {
            if (contagemPostos[i] != postos[i].requerido)
                return;
        }
        melhor = escolhidos;  // Atualiza a melhor solução encontrada.
        return;
    }
    // Calcula as células que ainda não foram cobertas.
    bitset<MAX_CANDIDATOS> naoCoberta = coberturaCompleta & ~coberta;
    int numNaoCoberta = naoCoberta.count();
    int maxCobertura = 0;
    // Para cada candidato disponível, calcula quantas células adicionais seriam cobertas.
    for (int i = 0; i < N; i++) {
        if (disponivel.test(i)) {
            bitset<MAX_CANDIDATOS> adicional = candidatos[i].cobertura & ~coberta;
            int numAdicional = adicional.count();
            maxCobertura = max(maxCobertura, numAdicional);
        }
    }
    if (maxCobertura == 0) return;  // Se não há como cobrir mais células, a ramificação falha.
    // Calcula um limite inferior (lower bound) do número de torres adicionais necessárias.
    int limiteInferior = (numNaoCoberta + maxCobertura - 1) / maxCobertura;
    if (escolhidos + limiteInferior >= melhor) return;  // Poda com base no limite inferior.
    int u = -1;
    // Seleciona a primeira célula ainda não coberta.
    for (int i = 0; i < N; i++) {
        if (naoCoberta.test(i)) { u = i; break; }
    }
    if (u == -1) return;
    // Tenta selecionar, entre os candidatos disponíveis, aqueles que cobrem a célula u.
    for (int i = 0; i < N; i++) {
        if (disponivel.test(i) && candidatos[i].cobertura.test(u)) {
            // Atualiza a cobertura considerando a escolha do candidato i.
            bitset<MAX_CANDIDATOS> novaCoberta = coberta | candidatos[i].cobertura;
            // Remove os candidatos que conflitam com o candidato i da lista de disponíveis.
            bitset<MAX_CANDIDATOS> novoDisponivel = disponivel & ~(candidatos[i].conflito);
            novoDisponivel.reset(i);  // Garante que o próprio candidato i não seja reutilizado.
            vector<int> novaContagem = contagemPostos;
            bool valido = true;
            // Atualiza as contagens dos postos se o candidato i estiver adjacente a algum deles.
            for (size_t j = 0; j < postos.size(); j++) {
                for (int cand : postos[j].indicesCandidatos) {
                    if (cand == i) { novaContagem[j]++; break; }
                }
                // Se a contagem exceder o requerido, esta escolha é inválida.
                if (novaContagem[j] > postos[j].requerido) { valido = false; break; }
                int restante = 0;
                // Verifica quantos candidatos disponíveis podem futuramente contribuir para o posto.
                for (int cand : postos[j].indicesCandidatos) {
                    if (novoDisponivel.test(cand)) restante++;
                }
                if (novaContagem[j] + restante < postos[j].requerido) { valido = false; break; }
            }
            if (!valido) continue;
            // Chama recursivamente a função com a nova configuração.
            buscaRecursiva(novaCoberta, novoDisponivel, novaContagem, escolhidos + 1);
        }
    }
}

// Inicializa os candidatos (células vazias) e atribui um índice para cada célula candidata.
void inicializaCandidatos() {
    indiceCandidato.assign(R, vector<int>(C, -1));
    candidatos.clear();
    N = 0;
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            if (tabuleiro[r][c] == '.') {
                indiceCandidato[r][c] = N;
                Candidato cand;
                cand.r = r;
                cand.c = c;
                cand.cobertura.reset();
                cand.conflito.reset();
                candidatos.push_back(cand);
                N++;
            }
        }
    }
}

// Calcula a cobertura de cada candidato. Cada torre cobre sua própria célula e se estende em todas as direções
// até encontrar um obstáculo (muro '#' ou posto representado por dígito).
void calculaCoberturaCandidatos() {
    for (int i = 0; i < N; i++) {
        // A torre cobre a própria célula.
        candidatos[i].cobertura.set(i, true);
        int r = candidatos[i].r, c = candidatos[i].c;
        for (int d = 0; d < 4; d++) {
            int nr = r + dLinha[d], nc = c + dColuna[d];
            while (nr >= 0 && nr < R && nc >= 0 && nc < C && tabuleiro[nr][nc] != '#' && !isdigit(tabuleiro[nr][nc])) {
                if (tabuleiro[nr][nc] == '.') {
                    int j = indiceCandidato[nr][nc];
                    if (j != -1)
                        candidatos[i].cobertura.set(j, true);
                }
                nr += dLinha[d];
                nc += dColuna[d];
            }
        }
    }
}

// Calcula os conflitos entre candidatos. Dois candidatos conflitam se estão na mesma linha ou coluna
// e não há obstáculo entre eles (muro ou posto).
void calculaConflitosCandidatos() {
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            if (candidatos[i].r == candidatos[j].r) {
                int linha = candidatos[i].r;
                int col1 = min(candidatos[i].c, candidatos[j].c);
                int col2 = max(candidatos[i].c, candidatos[j].c);
                bool bloqueado = false;
                for (int cc = col1 + 1; cc < col2; cc++) {
                    if (tabuleiro[linha][cc] == '#' || isdigit(tabuleiro[linha][cc])) { 
                        bloqueado = true; 
                        break; 
                    }
                }
                if (!bloqueado) {
                    candidatos[i].conflito.set(j, true);
                    candidatos[j].conflito.set(i, true);
                }
            }
            if (candidatos[i].c == candidatos[j].c) {
                int coluna = candidatos[i].c;
                int row1 = min(candidatos[i].r, candidatos[j].r);
                int row2 = max(candidatos[i].r, candidatos[j].r);
                bool bloqueado = false;
                for (int rr = row1 + 1; rr < row2; rr++) {
                    if (tabuleiro[rr][coluna] == '#' || isdigit(tabuleiro[rr][coluna])) { 
                        bloqueado = true; 
                        break; 
                    }
                }
                if (!bloqueado) {
                    candidatos[i].conflito.set(j, true);
                    candidatos[j].conflito.set(i, true);
                }
            }
        }
    }
}

// Constrói os postos (células com dígito) e associa a eles os índices dos candidatos adjacentes.
// Se um posto requer mais torres do que há células vazias adjacentes, lança uma exceção.
void constroiPostos() {
    postos.clear();
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            if (isdigit(tabuleiro[r][c])) {
                Posto p;
                p.r = r;
                p.c = c;
                p.requerido = tabuleiro[r][c] - '0';
                p.indicesCandidatos.clear();
                for (int d = 0; d < 4; d++) {
                    int nr = r + dLinha[d], nc = c + dColuna[d];
                    if (nr >= 0 && nr < R && nc >= 0 && nc < C && tabuleiro[nr][nc] == '.') {
                        int idx = indiceCandidato[nr][nc];
                        if (idx != -1)
                            p.indicesCandidatos.push_back(idx);
                    }
                }
                if (p.requerido > static_cast<int>(p.indicesCandidatos.size()))
                    throw string("invalid");
                postos.push_back(p);
            }
        }
    }
}

// Processa um caso de teste: lê o tabuleiro, inicializa candidatos, calcula coberturas, conflitos, constroi postos e executa a busca.
void processaCasoTeste() {
    cin >> R >> C;
    tabuleiro.clear();
    tabuleiro.resize(R);
    for (int i = 0; i < R; i++) {
        cin >> tabuleiro[i];
    }
    inicializaCandidatos();
    calculaCoberturaCandidatos();
    calculaConflitosCandidatos();
    try {
        constroiPostos();
    } catch (string err) {
        cout << "noxus will rise!" << "\n";
        return;
    }
    melhor = INT_MAX;
    coberturaCompleta.reset();
    for (int i = 0; i < N; i++) {
        coberturaCompleta.set(i, true);
    }
    bitset<MAX_CANDIDATOS> coberta;
    coberta.reset();
    bitset<MAX_CANDIDATOS> disponivel;
    disponivel.reset();
    for (int i = 0; i < N; i++) {
        disponivel.set(i, true);
    }
    vector<int> contagemPostos(postos.size(), 0);
    buscaRecursiva(coberta, disponivel, contagemPostos, 0);
    if (melhor == INT_MAX)
        cout << "noxus will rise!" << "\n";
    else
        cout << melhor << "\n";
}

// Função que processa todos os casos de teste (aqui chamada de heimerdinger).
void heimerdinger(){
    int T;
    cin >> T;
    for (int t = 0; t < T; t++) {
        processaCasoTeste();
    }
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    heimerdinger();
    return 0;
}
