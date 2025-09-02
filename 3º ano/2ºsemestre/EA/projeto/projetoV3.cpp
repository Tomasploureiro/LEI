#include <iostream>
#include <vector>
#include <string>
#include <bitset>
#include <climits>
#include <cctype>
#include <algorithm>
using namespace std;

const int MAX_CANDIDATOS = 256;
const int dLinha[4] = {-1, 0, 1, 0};
const int dColuna[4] = {0, 1, 0, -1};

struct Candidato {
    int r, c;
    bitset<MAX_CANDIDATOS> cobertura;
    bitset<MAX_CANDIDATOS> conflito;
};

struct Posto {
    int r, c, requerido;
    vector<int> indicesCandidatos;
};

int R, C;
vector<string> tabuleiro;
vector<Candidato> candidatos;
int N;
vector<vector<int>> indiceCandidato;
vector<Posto> postos;

bitset<MAX_CANDIDATOS> coberturaCompleta;
int melhor = INT_MAX;

void buscaRecursiva(bitset<MAX_CANDIDATOS> coberta, bitset<MAX_CANDIDATOS> disponivel, vector<int>& contagemPostos, int escolhidos) {
    if (escolhidos >= melhor) return;
    if (coberta == coberturaCompleta) {
        for (size_t i = 0; i < postos.size(); i++) {
            if (contagemPostos[i] != postos[i].requerido)
                return;
        }
        melhor = escolhidos;
        return;
    }
    bitset<MAX_CANDIDATOS> naoCoberta = coberturaCompleta & ~coberta;
    int numNaoCoberta = naoCoberta.count();
    int maxCobertura = 0;
    for (int i = 0; i < N; i++) {
        if (disponivel.test(i)) {
            bitset<MAX_CANDIDATOS> adicional = candidatos[i].cobertura & ~coberta;
            int numAdicional = adicional.count();
            maxCobertura = max(maxCobertura, numAdicional);
        }
    }
    if (maxCobertura == 0) return;
    int limiteInferior = (numNaoCoberta + maxCobertura - 1) / maxCobertura;
    if (escolhidos + limiteInferior >= melhor) return;
    int u = -1;
    for (int i = 0; i < N; i++) {
        if (naoCoberta.test(i)) { u = i; break; }
    }
    if (u == -1) return;
    for (int i = 0; i < N; i++) {
        if (disponivel.test(i) && candidatos[i].cobertura.test(u)) {
            bitset<MAX_CANDIDATOS> novaCoberta = coberta | candidatos[i].cobertura;
            bitset<MAX_CANDIDATOS> novoDisponivel = disponivel & ~(candidatos[i].conflito);
            novoDisponivel.reset(i);
            vector<int> novaContagem = contagemPostos;
            bool valido = true;
            for (size_t j = 0; j < postos.size(); j++) {
                for (int cand : postos[j].indicesCandidatos) {
                    if (cand == i) { novaContagem[j]++; break; }
                }
                if (novaContagem[j] > postos[j].requerido) { valido = false; break; }
                int restante = 0;
                for (int cand : postos[j].indicesCandidatos) {
                    if (novoDisponivel.test(cand)) restante++;
                }
                if (novaContagem[j] + restante < postos[j].requerido) { valido = false; break; }
            }
            if (!valido) continue;
            buscaRecursiva(novaCoberta, novoDisponivel, novaContagem, escolhidos + 1);
        }
    }
}

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

void calculaCoberturaCandidatos() {
    for (int i = 0; i < N; i++) {
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

void calculaConflitosCandidatos() {
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            if (candidatos[i].r == candidatos[j].r) {
                int linha = candidatos[i].r;
                int col1 = min(candidatos[i].c, candidatos[j].c);
                int col2 = max(candidatos[i].c, candidatos[j].c);
                bool bloqueado = false;
                for (int cc = col1 + 1; cc < col2; cc++) {
                    if (tabuleiro[linha][cc] == '#' || isdigit(tabuleiro[linha][cc])) { bloqueado = true; break; }
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
                    if (tabuleiro[rr][coluna] == '#' || isdigit(tabuleiro[rr][coluna])) { bloqueado = true; break; }
                }
                if (!bloqueado) {
                    candidatos[i].conflito.set(j, true);
                    candidatos[j].conflito.set(i, true);
                }
            }
        }
    }
}

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
