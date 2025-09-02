#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <map>

using namespace std;

int turret_places[][2] = {{-1, 0}, {0, 1}, {1, 0},{0, -1}};

map<pair<int,int>,char> grid;
map<pair<int,int>,int> outposts;
map<pair<int, int>, map<pair<int, int>, int>> turrets;
map<pair<int,int>,int> walls;

int T,R,C,n_turrets,c_cells;
void print_grid() {
    for (int i = 0; i < R; i++) {
        for (int j = 0; j < C; j++) {
            cout << grid[{i, j}];
        }
        cout << endl;
    }
    cout << endl;
}
void clear(){
    grid.clear();
    outposts.clear();
    turrets.clear();
    walls.clear();
    n_turrets = 0;
    c_cells = 0;
}

int check_grid(int x,int y){
    if(x >= 0 && x < R && y >= 0 && y < C){
        return 1;
    }
    return 0;
}

int count_covered_cells(int new_x, int new_y) {
    int covered_cells = 0;
    bool tower_count = true;

    int x = new_x - 1;
    while (x >= 0) {
        if (grid[{x, new_y}] == 'x') {
            if (tower_count == true) {
                return 0;
            }
            tower_count = true;
        } else if (grid[{x,new_y}] == '.') {
            covered_cells++;
        }else if(grid[{x,new_y}] == '#' || isdigit(grid[{x,new_y}])){
            break;
        }
        x--; 
    }
    tower_count = true;
    int y = new_y + 1;
    while (y < C) {
        if (grid[{new_x,y}] == 'x') {
            if (tower_count == true) {
                return 0;
            }
            tower_count = true;
        } else if (grid[{new_x,y}] == '.') {
            covered_cells++;
        }else if(grid[{new_x,y}] == '#' || isdigit(grid[{new_x,y}])){
            break;
        }
        y++; 
    }
    tower_count = true;
    x = new_x + 1;
    while (x < R) {
        if (grid[{x,new_y}] == 'x') {
            if (tower_count == true) {
                return 0;
            }
            tower_count = true;
        } else if (grid[{x,new_y}] == '.') {
            covered_cells++;
        }else if(grid[{x,new_y}] == '#' || isdigit(grid[{x,new_y}])){
            break;
        }
        x++; 
    }
    tower_count = true;
    y = new_y - 1;
    while (y >= 0) {
        if (grid[{new_x,y}] == 'x') {
            if (tower_count == true) {
                return 0;
            }
            tower_count = true;
        } else if (grid[{new_x,y}] == '.') {
            covered_cells++;
        }else if(grid[{new_x,y}] == '#' || isdigit(grid[{new_x,y}])){
            break;
        }
        y--;  
    }

    return covered_cells;
}

set<pair<int,pair<int,int>>> check_places(int r, int c){
    set<pair<int,pair<int,int>>> available_places;
    for(int place = 0; place < 4 ; place++){
        int new_x = r + turret_places[place][0];
        int new_y = c + turret_places[place][1];
        int n_cells;
        if(check_grid(new_x,new_y) && grid[{new_x,new_y}] == '.'){
            n_cells = count_covered_cells(new_x,new_y);
            if (n_cells != 0) {
                available_places.insert({n_cells,{new_x,new_y}});
            }
        }
    }
    return available_places;
}

int put_turrets(int r, int c, int number_turrets){
    if(number_turrets == 0){
        return 1;
    }
    set<pair<int,pair<int,int>>> places = check_places(r,c);
    if(int(places.size()) >= number_turrets){
        auto pl = places.begin();
        for(int i = 0; i < number_turrets; i++){
            turrets[{r,c}][pl->second] = 1;
            grid[pl->second] = 'x';
            pl++;
            n_turrets++;
            c_cells++;
        }
    }else{
        return 0;
    }

    return 1;
}

int count_turrets_around_outpost(int r,int c){
    int turrets_placed = 0;
    for(int i = 0; i < 4; i++){
        int new_x = r + turret_places[i][0];
        int new_y = c + turret_places[i][1];
        if(check_grid(new_x,new_y)){
            if(grid[{new_x,new_y}] == 'x'){
                turrets_placed++;
            }
        }
    }

    return turrets_placed;
}

/* int fatorial(int n){

    int res = 1;
    for(int i = 1; i <= n; i++){
        res *= i;
    }
    return res;
}

int calc_n_combinations(){
    int n_combinations = 1;
    int n_places;
    int n_turrets;
    for(auto turret : turrets){
        n_places = turret.second.size();
        n_turrets = 0;
        for(auto place : turret.second){
            if(place.second == 1){
                n_turrets++;
            }
        }
        n_combinations *= fatorial(n_places)/(fatorial(n_turrets)*fatorial(n_places - n_turrets));
    }
    cout << n_combinations << endl;
    return n_combinations;
}*/

bool check_same_row(int r, int c, bool turret_found) {
    if (c >= C) {
        return false;
    }
    if (grid[{r, c}] == 'x') {
        if (turret_found) {
            return true; 
        }
        turret_found = true;
    } else if (grid[{r, c}] == '#' || isdigit(grid[{r, c}])) {
        turret_found = false;  
    }
    return check_same_row(r, c + 1, turret_found); 
}

bool check_same_column(int c, int r, bool turret_found) {
    if (r >= R) {
        return false; 
    }
    if (grid[{r, c}] == 'x') {
        if (turret_found) {
            return true; 
        }
        turret_found = true;
    } else if (grid[{r, c}] == '#' || isdigit(grid[{r, c}])) {
        turret_found = false; 
    }
    return check_same_column(c, r + 1, turret_found);  
}

bool check_turret_same_column_or_line(){

    for (int r = 0; r < R; r++) {
        if (check_same_row(r, 0, false)) {
            return true; 
        }
    }
    for (int c = 0; c < C; c++) {
        if (check_same_column(c, 0, false)) {
            return true; 
        }
    }
    return false;
}

void check_turret_vision(){
    for(auto turret : turrets){
        for(auto place : turret.second){
            int x = place.first.first;
            int y = place.first.second;
            int new_x = x - 1;
            while (new_x >= 0 && grid[{new_x, y}] != '#' && !isdigit(grid[{new_x, y}])) {
                if(grid[{new_x, y}] == '.'){
                    grid[{new_x, y}] = 'D';c_cells++;
                }
                new_x--;
            }
            int new_y = y + 1;
            while (new_y < C && grid[{x, new_y}] != '#' && !isdigit(grid[{x, new_y}])) {
                if (grid[{x, new_y}] == '.' ) {
                    grid[{x, new_y}] = 'D';c_cells++;
                }
                new_y++;
            }
            new_x = x + 1;
            while (new_x < R && grid[{new_x, y}] != '#' && !isdigit(grid[{new_x, y}])) {
                if (grid[{new_x, y}] == '.' ) {
                    grid[{new_x, y}] = 'D';c_cells++;
                }
                new_x++;
            }
            new_y = y - 1;
            while (new_y >= 0 && grid[{x, new_y}] != '#' && !isdigit(grid[{x, new_y}])) {
                if (grid[{x, new_y}] == '.' ) {
                    grid[{x, new_y}] = 'D';c_cells++;
                }
                new_y--;
            }
        }
    }
}

void fill_turrets(){
    set<pair<int,pair<int,int>>> new_places;
    int p=0;
    for(int r = 0; r < R; r++){
        for(int c = 0; c < C; c++){
            if(grid[{r,c}] == '.'){
                if(p==0){
                    p=1;
                    grid[{r,c}] = 'x';
                    turrets[{r,c}][{r,c}] = 1;
                    c_cells++;
                    n_turrets++;
                }else{
                    int cells = count_covered_cells(r,c);
                    if (cells> 0){
                        new_places.insert({cells,{r,c}});
                    }
                }
                
            }
        }
    }
    auto place = new_places.rbegin();
    grid[place->second] = 'x';
    turrets[place->second][place->second] = 1;
    n_turrets++;
    c_cells++;
    check_turret_vision();
}


void work_outposts(){
    for(auto outpost : outposts){
        int r = outpost.first.first;
        int c = outpost.first.second;
        int number_turrets = outpost.second;
        int remaining_turrets_to_place;
        number_turrets == 0 ? remaining_turrets_to_place = 0 : remaining_turrets_to_place = number_turrets-count_turrets_around_outpost(r,c);
        if(!put_turrets(r,c,remaining_turrets_to_place)){
            cout << "noxus will rise!" << '\n';
            return;
        }
    }
    check_turret_vision();
    fill_turrets();
    cout << n_turrets << '\n';
}

void print_turrets(){
    for(auto turret : turrets){
        for(auto place : turret.second){
            cout <<"Outpost: x:" << turret.first.first << " y:" << turret.first.second << " Torre x:" << place.first.first << " y:" << place.first.second << " Valor " << place.second << endl;
        }
    }
}

void heimerdinger(){
    cin >> T;
    while(T--){
        cin >> R >> C;
        clear();
        for(int r = 0; r < R; r++){
            for(int c = 0; c < C; c++){
                char aux;
                cin >> aux;
                grid[{r,c}] = aux;
                if(isdigit(aux)){
                    outposts[{r,c}] = aux - '0';
                    c_cells++;
                }else if(aux == '#'){
                    walls[{r,c}] = 1;
                    c_cells++;
                }
            }
        }
        work_outposts();
    }
}


int main() {
    heimerdinger();
    return 0;
}