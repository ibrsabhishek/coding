public class Polygon {
    public void render(){
        System.out.println("rendering polygon...");
    }
    
}
class Circle extends Polygon{
    public void render(){
        System.out.println("rendering circle..");
    }
}
class Square extends Polygon{
    public void render(){
        System.out.println("rendering square..");

    }
}





class main{
    public static void main(String[] args) {
        Square P = new Square();
        P.render();
    }
}
