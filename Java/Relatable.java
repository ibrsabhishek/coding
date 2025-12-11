public interface Relatable {
    public int isLargerThan(Relatable other);
    
}

public class RectanglePlus implements Relatable{
    public int width = 0;
    public int height = 0;
    public point origin;
}

public RectanglePlus(){
    origin =  new point(0,0);
}

public RectanglePlus(point P){
    origin = P;
}

public RectanglePlus(int w,int h){
    origin = new point(0,0);
    width = w;
    height = h;
}

public Rectangle(point p,int w,int h){
    origin = p;
    width = w;
    height = h;
}
public void move(int x,int y){
    origin x = x;
    origin y = y;


public int getArea(){
    return width*height;
}

public int isLargeerThan(Readable other){
    RectanglePlus otherRect = (Rectangle plus) other;
    if(this.getArea()<otherRect.getArea())
    return -1;
    else if(this.getArea()>otherRect.getArea())
    return 1;
    else return 0;
}
}
