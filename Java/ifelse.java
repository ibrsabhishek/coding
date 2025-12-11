import java.util.Scanner;

public class ifelse {
    public static void main(String[]args){
        Scanner sc = new Scanner(System.in);
        int age = sc .nextInt();
        if (age>19) {
            System.out.println("Adult");
        }
        else{
            System.out.println("NotAdult");
        }
    }
}
