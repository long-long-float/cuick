#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<cmath>
int main(int argc, char **argv){
    int ary[] = {1, 2, 3, 4, 5};
    for(int counter0 = 0;counter0 < sizeof(ary) / sizeof(ary[0]);counter0++){
        int &i = ary[counter0];
        std::cout << i << std::endl;
    }

    for(int counter0 = 2;counter0 <= 4;counter0++){
        int &i = ary[counter0];
        std::cout << i << std::endl;
    }

    for(int counter0 = 0;counter0 < 3;counter0++){
        int &i = ary[counter0];
        std::cout << i << std::endl;
    }

    int *ptr = ary;
    for(int counter0 = 0;counter0 < 5;counter0++){
        int &i = ptr[counter0];
        std::cout << i << std::endl;
    }

    for(int counter0 = 0;counter0 < sizeof(ary) / sizeof(ary[0]);counter0++){
        int &i = ary[counter0];
        for(int counter1 = 0;counter1 < sizeof(ary) / sizeof(ary[0]);counter1++){
            int &j = ary[counter1];
            std::cout << i * j << " " << "";
        }

        std::cout << std::endl;
    }

    for(int i = 1;i <= 3;i++){
        std::cout << i << std::endl;
    }

    for(int i = 0;i < 10;i++){
        std::cout << i << std::endl;
    }

    std::vector<int> vec;
    vec.push_back(10);
    vec.push_back(20);
    vec.push_back(30);
    for(std::vector<int>::iterator itr0 = vec.begin();itr0 != vec.end();itr0++){
        int &itr = *itr0;
        std::cout << itr << std::endl;
    }

    return 0;
}

