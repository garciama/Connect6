public class Rectangle {

    private int x;
    private int y;

    public Rectangle(int inX, int inY){
        x = inX;
        y = inY;
    }
    public int calculateArea(){
        return x * y;
    }

    public int returnX(){
        return x;
    }

}
