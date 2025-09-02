#include <iostream>
#include <list>
#include <string>

int main(){
    std::list<int> list = {0};
    std::list<int>::iterator ptr = list.begin();
    std::string cmd;
    while(std::cin >> cmd){
        if (cmd == "MOVE"){

            std::string direction; 
            std:: cin >> direction;
            if( direction == "LEFT" && ptr != list.begin()){
                --ptr;
            }else if(direction == "RIGHT" && ptr != list.end()){
                ++ptr;
            }

        }else if(cmd == "INSERT"){
            std::string direction;
            int value;
            std::cin >> direction >> value;
            if(direction == "LEFT"){
                list.insert(ptr, value);
            }else if(direction == "RIGHT"){
                list.insert(std::next(ptr), value);
            }
        }
    }
     for (int val : list) {
        std::cout << val << "\n";
    }
    return 0;
}