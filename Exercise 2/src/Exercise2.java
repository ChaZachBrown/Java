import java.awt.Point;

public class Exercise2 {

    public static void main (String args[]) {
       Point p1 = new Point(1,2);
       Point p2 = new Point(3,4);
        
        swap(p1,p2);

        System.out.println(p1);
        System.out.println(p2);
    }

	private static void swap(Point p1, Point p2) {
		Point temp = new Point(0,0);
		temp.x = p1.x;
		temp.y = p1.y;
		p1.x = p2.x;
		p1.y=p2.y;
		p2.x = temp.x;
		p2.y = temp.y;
		
	
	}

}

