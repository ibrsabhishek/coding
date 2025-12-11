import java.util.Scanner;

public class Challange2 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to our calculator");
        System.out.println("Enter your first number: ");
        int firstNum = input.nextInt();
        System.out.println("Enter the second number: ");
        int secondNum = input.nextInt();
         int sum = firstNum + secondNum;
         System.out.println("sum of your number is: " + sum);

    }
}
