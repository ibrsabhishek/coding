import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
 Scanner input = new Scanner(System.in);
  System.out.println("Welcome to our Calculator");
  System.out.println("Enter the first number");
  int myfirstNum =input.nextInt();
  System.out.println("Enter the second number");
  int secondNum = input.nextInt();
  int sum = myfirstNum + secondNum;
  System.out.println("Sum of total number is : " + sum);
 } 
}