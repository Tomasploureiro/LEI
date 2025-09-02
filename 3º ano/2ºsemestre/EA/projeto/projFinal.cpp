#include <iostream>
#include <vector>
#include <string>
#include <bitset>
#include <climits>
#include <cctype>
#include <algorithm>
using namespace std;

const int MAX_CANDIDATOS = 225;
const int candidatos_posto[][2] = {{-1, 0}, {0, 1}, {1, 0},{0, -1}};

struct Candidato {
    int r, c;
    bitset<MAX_CANDIDATOS> cobertura;
    bitset<MAX_CANDIDATOS> conflito;
};

struct Posto {
    int r, c, requerido;
    vector<int> indicesCandidatos;
};

int R, C, N;
vector<string> tabuleiro;
vector<Candidato> candidatos;
vector<vector<int>> indiceCandidato;
vector<Posto> postos;
bitset<MAX_CANDIDATOS> coberturaCompleta;
int melhor = INT_MAX;

void buscaRecursiva(bitset<MAX_CANDIDATOS> coberta, bitset<MAX_CANDIDATOS> disponivel, vector<int>& contagemPostos, int escolhidos) {
    if (escolhidos >= melhor){
        return;
    }
    if (coberta == coberturaCompleta) {
        for (int i = 0; i < int(postos.size()); i++) {
            if (contagemPostos[i] != postos[i].requerido){
                return;
            }    
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
    if (maxCobertura == 0){
        return;
    } 
    int limiteInferior = (numNaoCoberta + maxCobertura - 1) / maxCobertura;
    if (escolhidos + limiteInferior >= melhor){
        return;
    }
    int indCand = -1;
    for (int i = 0; i < N; i++) {
        if (naoCoberta.test(i)) { 
            indCand = i; 
            break; 
        }
    }
    if (indCand == -1){
        return;
    } 
    for (int i = 0; i < N; i++) {
        if (disponivel.test(i) && candidatos[i].cobertura.test(indCand)) {
            bitset<MAX_CANDIDATOS> novaCoberta = coberta | candidatos[i].cobertura;
            bitset<MAX_CANDIDATOS> novoDisponivel = disponivel & ~(candidatos[i].conflito);
            novoDisponivel.reset(i);
            vector<int> novaContagem = contagemPostos;
            bool valido = true;
            for (int j = 0; j < int(postos.size()); j++) {
                for (int cand : postos[j].indicesCandidatos) {
                    if (cand == i) { 
                        novaContagem[j]++; 
                        break; 
                    }
                }
                if (novaContagem[j] > postos[j].requerido) { 
                    valido = false; 
                    break; 
                }
                int restante = 0;
                for (int cand : postos[j].indicesCandidatos) {
                    if (novoDisponivel.test(cand)){
                        restante++;
                    } 
                }
                if (novaContagem[j] + restante < postos[j].requerido) { 
                    valido = false; 
                    break; 
                }
            }
            if (valido){
                buscaRecursiva(novaCoberta, novoDisponivel, novaContagem, escolhidos + 1);
            }
        }
    }
}

void inicializaCandidatos() {
    indiceCandidato = vector<vector<int>>(R,vector<int>(C,-1));
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

bool dentroTabuleiro(int r, int c){
    if(r >= 0 && r < R && c >= 0 && c < C){
        return true;
    }else{
        return false;
    }
}

bool verificarPos(int r,int c){
    if(dentroTabuleiro(r,c) && tabuleiro[r][c] != '#' && !isdigit(tabuleiro[r][c])){
        return true;
    }else{
        return false;
    }
}

void calculaCoberturaCandidatos() {
    for (int i = 0; i < N; i++) {
        candidatos[i].cobertura.set(i, true);
        int r = candidatos[i].r, c = candidatos[i].c;
        for (int d = 0; d < 4; d++) {
            int nr = r + candidatos_posto[d][0];
            int nc = c + candidatos_posto[d][1];
            while (dentroTabuleiro(nr,nc) && tabuleiro[nr][nc] != '#' && !isdigit(tabuleiro[nr][nc])) {
                if (tabuleiro[nr][nc] == '.') {
                    int j = indiceCandidato[nr][nc];
                    if (j != -1)
                        candidatos[i].cobertura.set(j, true);
                }
                nr += candidatos_posto[d][0];
                nc += candidatos_posto[d][1];
            }
        }
    }
}

bool encontraParedeOutpostCandidatosLinha(int col1, int col2, int linha){
    for (int cc = col1 + 1; cc < col2; cc++){
        if (tabuleiro[linha][cc] == '#' || isdigit(tabuleiro[linha][cc])){ 
            return true;    
        }
    }
    return false;
}

bool encontraParedeOutpostCandidatosColuna(int row1, int row2, int coluna){
    for (int rr = row1 + 1; rr < row2; rr++) {
        if (tabuleiro[rr][coluna] == '#' || isdigit(tabuleiro[rr][coluna])){ 
            return true;
        }
    }
    return false;
}

void calculaConflitosCandidatos() {
    for (int i = 0; i < N; i++) {
        for (int j = i + 1; j < N; j++) {
            if (candidatos[i].r == candidatos[j].r) {
                int linha = candidatos[i].r;
                int col1 = min(candidatos[i].c, candidatos[j].c);
                int col2 = max(candidatos[i].c, candidatos[j].c);
                bool bloqueado = encontraParedeOutpostCandidatosLinha(col1, col2, linha);
                if (!bloqueado) {
                    candidatos[i].conflito.set(j, true);
                    candidatos[j].conflito.set(i, true);
                }
            }
            if (candidatos[i].c == candidatos[j].c) {
                int coluna = candidatos[i].c;
                int row1 = min(candidatos[i].r, candidatos[j].r);
                int row2 = max(candidatos[i].r, candidatos[j].r);
                bool bloqueado = encontraParedeOutpostCandidatosColuna(row1, row2, coluna);
                if (!bloqueado) {
                    candidatos[i].conflito.set(j, true);
                    candidatos[j].conflito.set(i, true);    
                }
            }
        }
    }
}

bool verificaPonto(int r, int c){
    if (dentroTabuleiro(r,c) && tabuleiro[r][c] == '.'){
        return true;
    }else{
        return false;
    }
}

void constroiPostos() {
    for (int r = 0; r < R; r++) {
        for (int c = 0; c < C; c++) {
            if (isdigit(tabuleiro[r][c])) {
                Posto p;
                p.r = r;
                p.c = c;
                p.requerido = tabuleiro[r][c] - '0';
                p.indicesCandidatos.clear();
                for (int d = 0; d < 4; d++) {
                    int nr = r + candidatos_posto[d][0];
                    int nc = c + candidatos_posto[d][1];
                    if (verificaPonto(nr,nc)) {
                        int idx = indiceCandidato[nr][nc];
                        if (idx != -1)
                            p.indicesCandidatos.push_back(idx);
                    }
                }
                if (p.requerido > int(p.indicesCandidatos.size()))
                    throw string("invalid");
                postos.push_back(p);
            }
        }
    }
}

void clear(){
    tabuleiro.clear();
    candidatos.clear();
    postos.clear();
    coberturaCompleta.reset();
}

void processaCasoTeste() {
    cin >> R >> C;
    clear();
    tabuleiro = vector<string>(R);
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
    vector<int> contagemPostos(int(postos.size()), 0);
    buscaRecursiva(coberta, disponivel, contagemPostos, 0);
    if (melhor == INT_MAX){
        cout << "noxus will rise!" << "\n";
    }
    else{
        cout << melhor << "\n";
    }
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