#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<cmath>
int main(int argc, char** argv){
    int n;
    while(true){
        std::cout << "a" << std::endl;
        {
            std::cin >> n;
            if(!n) break;;
        }

        std::cout << std::pow(n, 2) << std::endl;
    }

    return 0;
}

