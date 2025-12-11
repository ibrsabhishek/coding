// #include<stdio.h>
// int main(){
//     // int n ;
//     printf("Enter the no of stars: \n");
//     // scanf("%d",&n);
//     // int m;
//     // printf ("Enter the no of columns: ");
//     // scanf("%d",&n);
//     for(int i=1 ; i<=4;i++){
//         for (int i=1; i<=4; i++){
//             printf(" * ");
//         }
//         printf("\n");
//     }
//     return 0;
// }
// #include<stdio.h>
//   int main(){
//     int n;
//     printf ("Enter the no of rows: ");
//     scanf("%d",&n);
//     // for(int i=1; i<=n;i++){
//     //     for (int i=1; i<=n;i++){
//     //         printf("1");
//     //     }
//     //     printf("\n");
//     for(int i=1;i<=n; i++){
//         printf("%d",i);
//     }
//     return 0;
//     }
  #include <stdio.h>
  int main(){
    int n ;
    printf("Enter the number of rows : ");
    scanf("%d", &n);
    for(int i=1; i<=n; i++){
      for (int i=1; i<=n; i++)
      {
        printf("%d ", i );
      }
      printf("\n");
    }
    return  0;
  }