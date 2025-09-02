#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
using namespace std;

struct UFA {
    vector<int> parent;
    UFA(int n) : parent(n) {
        for (int i = 0; i < n; i++){
            parent[i] = i;
        } 
    }
    int find(int x) {
        if (parent[x] != x){
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }
    void unite(int x, int y) {
        parent[find(x)] = find(y);
    }
};

struct Edge {
    int u, v, cost;
};

bool can_connect(int maxCost, const vector<Edge>& edges, const vector<int>& mandatory, int n_towers) {
    UFA ufa(n_towers);
    for (const Edge& edge: edges) {
        if (edge.cost <= maxCost) {
            ufa.unite(edge.u, edge.v);
        }
    }
    for (int i = 1; i < (int) mandatory.size(); i++) {
        if (ufa.find(mandatory[i]) != ufa.find(mandatory[0])) {
            return false;
        }
    }
    return true;
}

int binary_search(int n_towers, const vector<Edge>& edges, const vector<int>& mandatory) {
    int low = 0, high = 1000000000, ans = 0;
    while (low <= high) {
        int mid = (low + high) / 2;
        if (can_connect(mid, edges, mandatory, n_towers)) {
            ans = mid;
            high = mid - 1;
        } else {
            low = mid + 1;
        }
    }
    return ans;
}

void read_edges_input(vector<Edge>& edges, int n_edges) {
    for (int i = 0; i < n_edges; i++) {
        cin >> edges[i].u >> edges[i].v >> edges[i].cost;
    }
}

void read_mandatory_input(vector<int>& mandatory, int m_towers){
    for (int i = 0; i < m_towers; i++) {
        cin >> mandatory[i];
    }
}

void pilt_towers(){
    int n_towers, n_edges, m_towers;
    while (cin >> n_towers >> n_edges) {
        vector<Edge> edges(n_edges);
        read_edges_input(edges, n_edges);

        cin >> m_towers;
        vector<int> mandatory(m_towers);
        read_mandatory_input(mandatory, m_towers);

        int res = binary_search(n_towers, edges, mandatory);
        if (res > 0) {
            cout << res << endl;
        } else {
            cout << "Impossible to connect!" << endl;
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    pilt_towers();
    return 0;
}