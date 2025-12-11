import java.util.Scanner;

public class challange {
    public static void main(String[] args) {
        System.out.println("Enter your user name");
        Scanner input= new Scanner(System.in);
        String name= input.nextLine();
        System.out.println(" Welcome to " + "Quantum University ");
       Scanner abhi = new Scanner(System.in);
        System.out.println("Welcome to our calculator");
       System.out.println("Please enter your first number");

        int firstNum= abhi.nextInt();
       System.out.println("Please enter your second number");
       int secondNum= input.nextInt();
       int sum = firstNum + secondNum;
       System.out.println("sum of your number is :" + sum);

    }
}
