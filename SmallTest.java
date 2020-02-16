import java.awt.geom.Point2D;

public class SmallTest {

    public static void main(String args[]) {

	//Point2DCollection<Point2D.Double> points = new PointsList<Point2D.Double>();
	Point2DCollection<Point2D.Double> points = new Quadtree<Point2D.Double>(-100, 100, -100, 100, 4);
	points.add(new Point2D.Double(4.2, 9.1));
	points.add(new Point2D.Double(-14.0, 39.1));
	points.add(new Point2D.Double(8.4, 82.4));
	points.add(new Point2D.Double(9.9, -9.4));
	points.add(new Point2D.Double(12.17, 12.12));
	points.add(new Point2D.Double(-1.0, -1.0));
	points.add(new Point2D.Double(43.2, 1.5));
	points.add(new Point2D.Double(92.6, 23.5));
	points.add(new Point2D.Double(0.04, 0.02));
	points.add(new Point2D.Double(-12.0, -3.1));

	System.out.println("size: " + points.size());
	
	for (Point2D.Double point : points) {
	    System.out.println(point);
	}

	System.out.println("get");
	System.out.println(points.get(new Point2D.Double(8.4, 82.4)));
	System.out.println(points.get(new Point2D.Double(82.4, 8.4)));

    	System.out.println("remove");
	System.out.println(points.remove(new Point2D.Double(8.4, 82.4)));
	System.out.println(points.remove(new Point2D.Double(82.4, 8.4)));

	System.out.println("size: " + points.size());

	for (Point2D.Double point : points) {
	    System.out.println(point);
	}
    }
}
