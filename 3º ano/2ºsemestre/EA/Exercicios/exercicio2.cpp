#include <iostream>
#include <array>

int main() {
    const int SIZE = 1000;
    int n;
    std::cin >> n;
    
    std::array<int, SIZE> arr;

    if(n<SIZE){
        for (int i = 0; i < n; i++) {
            std::cin >> arr[i];
        }

        for (int i = n - 1; i >= 0; i--) {
            if(i != 0){
                std::cout << arr[i] << " ";
            }else{
                std::cout << arr[i] << "\n";
            }
        }

    }
    return 0;
}