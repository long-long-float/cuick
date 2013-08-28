#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<cmath>
int fact(int n){
    static std::vector<int> fact_memo0(1000, -1);
    if(fact_memo0[n] != -1)return fact_memo0[n];
    return n > 0 ? n * fact(n - 1) : 1;
}

int main(int argc, char** argv){
    std::cout << fact(10) << std::endl;
    return 0;
}

