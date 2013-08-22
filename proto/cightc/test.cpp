#include<iostream>
#include<vector>
#include<map>
#include<algorithm>
#include<cmath>
void puts(int i)
{
    std::cout << i << std::endl;
}


int main(int argc, int** argv)
{
    int ary[] = {1, 2, 3, 4, 5};
    int counter = 10;
    for(int counter0 = 0;counter0 < sizeof(ary) / sizeof(ary[0]);counter0++)    {
        int& i = ary[counter0];
        for(int counter0 = 0;counter0 < sizeof(ary) / sizeof(ary[0]);counter0++)        {
            int& j = ary[counter0];
            puts(counter);
        }

    }

}


