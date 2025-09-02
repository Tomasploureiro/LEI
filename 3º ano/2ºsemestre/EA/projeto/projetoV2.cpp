#include <iostream>
#include <vector>
#include <string>
#include <bitset>
#include <climits>
#include <cctype>
#include <algorithm>
using namespace std;

const int MAX_CANDIDATES = 256;

struct Candidate {
    int r, c;
    bitset<MAX_CANDIDATES> coverage;
    bitset<MAX_CANDIDATES> conflict;
};

struct Outpost {
    int r, c, required;
    vector<int> candIndices;
};

int R, C;
vector<string> grid;
vector<Candidate> candidates;
int N;
vector<vector<int>> candidateIndex;
vector<Outpost> outposts;

bitset<MAX_CANDIDATES> fullCoverage;

int best = INT_MAX;

void dfs(bitset<MAX_CANDIDATES> covered, bitset<MAX_CANDIDATES> available, vector<int>& outpostCount, int chosenCount) {
    if (chosenCount >= best) return;
    if (covered == fullCoverage) {
        for (size_t i = 0; i < outposts.size(); i++) {
            if (outpostCount[i] != outposts[i].required)
                return;
        }
        best = chosenCount;
        return;
    }
    bitset<MAX_CANDIDATES> uncovered = fullCoverage & ~covered;
    int uncoveredCount = uncovered.count();
    int maxCover = 0;
    for (int i = 0; i < N; i++) {
        if (available.test(i)) {
            bitset<MAX_CANDIDATES> add = candidates[i].coverage & ~covered;
            int addCount = add.count();
            maxCover = max(maxCover, addCount);
        }
    }
    if (maxCover == 0) return;
    int lowerBound = (uncoveredCount + maxCover - 1) / maxCover;
    if (chosenCount + lowerBound >= best) return;
    int u = -1;
    for (int i = 0; i < N; i++) {
        if (uncovered.test(i)) { u = i; break; }
    }
    if (u == -1) return;
    for (int i = 0; i < N; i++) {
        if (available.test(i) && candidates[i].coverage.test(u)) {
            bitset<MAX_CANDIDATES> newCovered = covered | candidates[i].coverage;
            bitset<MAX_CANDIDATES> newAvailable = available & ~(candidates[i].conflict);
            newAvailable.reset(i);
            vector<int> newOutpostCount = outpostCount;
            bool valid = true;
            for (size_t j = 0; j < outposts.size(); j++) {
                for (int cand : outposts[j].candIndices) {
                    if (cand == i) { newOutpostCount[j]++; break; }
                }
                if (newOutpostCount[j] > outposts[j].required) { valid = false; break; }
                int remain = 0;
                for (int cand : outposts[j].candIndices) {
                    if (newAvailable.test(cand)) remain++;
                }
                if (newOutpostCount[j] + remain < outposts[j].required) { valid = false; break; }
            }
            if (!valid) continue;
            dfs(newCovered, newAvailable, newOutpostCount, chosenCount + 1);
        }
    }
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int T;
    cin >> T;
    for (int t = 0; t < T; t++) {
        cin >> R >> C;
        grid.clear();
        grid.resize(R);
        for (int i = 0; i < R; i++) {
            cin >> grid[i];
        }
        candidateIndex.assign(R, vector<int>(C, -1));
        candidates.clear();
        N = 0;
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (grid[r][c] == '.') {
                    candidateIndex[r][c] = N;
                    Candidate cand;
                    cand.r = r; cand.c = c;
                    cand.coverage.reset();
                    cand.conflict.reset();
                    candidates.push_back(cand);
                    N++;
                }
            }
        }
        fullCoverage.reset();
        for (int i = 0; i < N; i++) {
            fullCoverage.set(i, true);
        }
        int dr[4] = {-1, 0, 1, 0};
        int dc[4] = {0, 1, 0, -1};
        for (int i = 0; i < N; i++) {
            int r = candidates[i].r;
            int c = candidates[i].c;
            candidates[i].coverage.set(i, true);
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d], nc = c + dc[d];
                while (nr >= 0 && nr < R && nc >= 0 && nc < C && grid[nr][nc] != '#' && !isdigit(grid[nr][nc])) {
                    if (grid[nr][nc] == '.') {
                        int j = candidateIndex[nr][nc];
                        if(j != -1)
                            candidates[i].coverage.set(j, true);
                    }
                    nr += dr[d];
                    nc += dc[d];
                }
            }
        }
        for (int i = 0; i < N; i++) {
            for (int j = i+1; j < N; j++) {
                if (candidates[i].r == candidates[j].r) {
                    int row = candidates[i].r;
                    int col1 = min(candidates[i].c, candidates[j].c);
                    int col2 = max(candidates[i].c, candidates[j].c);
                    bool blocked = false;
                    for (int cc = col1+1; cc < col2; cc++) {
                        if (grid[row][cc] == '#' || isdigit(grid[row][cc])) { blocked = true; break; }
                    }
                    if (!blocked) {
                        candidates[i].conflict.set(j, true);
                        candidates[j].conflict.set(i, true);
                    }
                }
                if (candidates[i].c == candidates[j].c) {
                    int col = candidates[i].c;
                    int row1 = min(candidates[i].r, candidates[j].r);
                    int row2 = max(candidates[i].r, candidates[j].r);
                    bool blocked = false;
                    for (int rr = row1+1; rr < row2; rr++) {
                        if (grid[rr][col] == '#' || isdigit(grid[rr][col])) { blocked = true; break; }
                    }
                    if (!blocked) {
                        candidates[i].conflict.set(j, true);
                        candidates[j].conflict.set(i, true);
                    }
                }
            }
        }
        outposts.clear();
        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (isdigit(grid[r][c])) {
                    Outpost op;
                    op.r = r; op.c = c;
                    op.required = grid[r][c] - '0';
                    op.candIndices.clear();
                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d], nc = c + dc[d];
                        if (nr >= 0 && nr < R && nc >= 0 && nc < C && grid[nr][nc] == '.') {
                            int idx = candidateIndex[nr][nc];
                            if (idx != -1)
                                op.candIndices.push_back(idx);
                        }
                    }
                    if (op.required > static_cast<int>(op.candIndices.size())) {
                        cout << "noxus will rise!" << "\n";
                        goto next_test;
                    }
                    outposts.push_back(op);
                }
            }
        }
        
        {
            best = INT_MAX;
            bitset<MAX_CANDIDATES> initialCovered; initialCovered.reset();
            bitset<MAX_CANDIDATES> initialAvailable; initialAvailable.reset();
            for (int i = 0; i < N; i++) {
                initialAvailable.set(i, true);
            }
            vector<int> initialOutpostCount(outposts.size(), 0);
            dfs(initialCovered, initialAvailable, initialOutpostCount, 0);
        }
        if (best == INT_MAX)
            cout << "noxus will rise!" << "\n";
        else
            cout << best << "\n";
        
        next_test: ;
    }
    return 0;
}
