class multi extends Thread{
    public void run(){
        try{
            for(int i=1;i<=5;i++){
                System.out.println("Abhishek kumar");
                Thread.sleep(1000);
            }
        }
        catch(InterruptedException i){

        }
    }
}
public class thread1{
    public static void main(String[] args)throws InterruptedException {
        multi m1 = new multi();
        m1.start();
       for(int i=1;i<=5;i++){
        System.out.println("singh");
        Thread .sleep(1000);
       }
    }
}