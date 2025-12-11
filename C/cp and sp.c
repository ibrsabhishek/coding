#include <stdio.h>
int main(){
    int cp;
    printf("enter the cost price :");
    scanf("%d",&cp);
    int sp;
    printf("enter the selling price :");
    scanf("%d",&sp);
    if(sp>cp){
        printf("profit");
    }
    if(cp>sp){
        printf("loss");
    }
    return 0;
}
