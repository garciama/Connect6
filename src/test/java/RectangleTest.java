import org.junit.*;
import static junit.framework.TestCase.assertEquals;


public class RectangleTest{


    @Test
    public void CalculateAreaTest() {
        Rectangle r = new Rectangle(3, 5);
        assertEquals(15, r.calculateArea());
    }

    @Test
    public void returnXTest(){
        Rectangle r = new Rectangle(4, 9);
        assertEquals(7, r.returnX());
    }
}
