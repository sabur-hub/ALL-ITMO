/*#define _CRT_SECURE_NO_WARNINGS

#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>
#include <stdio.h>

using namespace std;

int main()
{
    setlocale(LC_ALL, "Russian");
   
    int n, k, min, min_i;
    cin >> n;
    cin >> k;
    int* arr = new int[n];

    for (int i = 0; i < n; i++)
    {
        cin >> arr[i];
    }
    min = arr[0];

    if (k == 1)
    {
        for (int i = 0; i < n; i++)
        {
            cout << arr[i] << " ";
        }
    }
    else
    {
        for (int i = 0; i < k; i++)
        {
            if (arr[i] <= min)
            {
                min = arr[i];
                min_i = i;
            }
        }
        cout << min << " ";
        //cout << "������ ������� � �������� - " << min_i << endl;
        //cout << "�������� ���� � �������� k - " << k << endl;

        for (int i = k; i < n; i++)
        {
            //cout << "����� ����� �������� � �������� - " << i << endl;
            if (arr[i] <= min)
            {
                min = arr[i];
                min_i = i;                
            }
            else
            {                
                if(i - min_i >= k)
                {
                    //cout << "������� ������� �� ������� � ����, ������������� � " << (i-k+1) << " �� " << i << endl;
                    min = arr[i];
                    for (int j = i - k + 1; j <= i; j++)
                    {

                        if (arr[j] <= min)
                        {
                            min = arr[j];
                            min_i = j;
                        }
                    }
                    //cout << "������ ����� ������� - " << min << endl;
                }                
            }
            cout << min << " ";
        }
    }
    return 0;
}*/