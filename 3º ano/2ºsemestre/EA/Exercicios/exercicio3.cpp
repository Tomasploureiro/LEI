#include <iostream>
#include <vector>
#include <algorithm>


int main(){

    std::vector<int> arr;
    int n;

    while (std::cin >> n){
        arr.push_back(n);
    }

    std::sort(arr.begin(), arr.end());

    for (size_t i = 0; i < arr.size(); i++) {
        std::cout << arr[i] << "\n";
    }

    return 0;
}