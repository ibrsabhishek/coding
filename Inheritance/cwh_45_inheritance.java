public Class Abhi{
    public static void main(String[] args) {
        Base b = new Base();
        b.setX(4);
        System.out.println(b.getX());
        Derived d = new Derived();
        d.setY(43);
        System.out.println(d.getY());
        
    }
}
class Base{
    int x;
    public int getX(){
        return x;

    }
    public void setX(int x){
        System.out.println("I am a setting x now");
        this.x = x;

    }
    public void printMe(){
        System.out.println("I am a constructor");

    }

}
class Derived extends Base{
 int y;
 public int getY(){
    return 0;
 }
}
public class cwh_45_inheritance{
    public static void  main(String[] args) {
        
    }
}