#include<stdio.h>
 int main(){
    int n;
    printf ("enter the year :");
    scanf("%d",&n);
    if(n%4==0){
        printf("Abhishek leap year hai");
    }
    else{
        printf("Abhishek is not leap year");
    }
    return 0;
 }