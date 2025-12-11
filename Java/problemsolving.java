import java.util.Scanner;

public class problemsolving {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to our calculator");
        System.out.println("Enter your first number");
        int firstNum =input.nextInt();
        System.out.println("now, please enter in your second number:");
        int secondNum = input.nextInt();
        int sum = firstNum + secondNum;
        System.out.println("sum of your number is:" + sum);
        // String name = input.nextLine();
        // System.out.println("Welcome  "  + name  +"   KG codeing");
    }
}
