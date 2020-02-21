import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

/**
   Search sorted list-based implementation of a Point2DCollection, a
   collection of points in two dimensions.  Can be sorted by x or y
   coordinate.

   @author Jim Teresco
   @version Spring 2020
*/
public class PointsListSorted<E extends Point2D.Double> implements Point2DCollection<E> {

    public static final int ORDER_BY_X = 0;
    public static final int ORDER_BY_Y = 1;

    protected ArrayList<E> points;
    protected Comparator<E> c;

    /**
       Construct a new, empty PointsListSorted.

       @param order Either ORDER_BY_X or ORDER_BY_Y to determine which
       coordinate is to be used for the ordering
    */
    public PointsListSorted(int order) {

	points = new ArrayList<E>();
	if (order == ORDER_BY_X) {
	    c = new Comparator<E>() {
		    public int compare(E a, E b) {
			if (a.getX() < b.getX()) return -1;
			if (a.getX() == b.getX()) return 0;
			return 1;
		    }
		};
	}
	else {
	    c = new Comparator<E>() {
		    public int compare(E a, E b) {
			if (a.getY() < b.getY()) return -1;
			if (a.getY() == b.getY()) return 0;
			return 1;
		    }
		};
	}
	// should really do an error check to make sure it was one
	// of the above
	    
    }

    /**
       Add a new object to the collection.

       @param point the new object to be added
    */
    @Override
    public void add(E point) {

	int addPos = 0;
	while (addPos < points.size() &&
	       c.compare(points.get(addPos), point) < 0) {
	    addPos++;
	}
	points.add(addPos, point);
    }

    /**
       Get the object at the same coordinates at the given point.

       @param point a point with the coordinates being sought
       @return an object at the same coordinates as point, null if no
       such object is in the collection
    */
    @Override
    public E get(Point2D.Double point) {

	// to be replaced by a binary search
	for (E item : points) {
	    if (item.getX() == point.getX() &&
		item.getY() == point.getY()) {
		return item;
	    }
	}
	return null;
    }
    
    /**
       Remove an object at the same coordinates at the given point.

       @param point a point with the coordinates at which an item to be removed
       is located
       @return an the object removed, null if no object at the coordinates
       of point is in the collection
    */
    @Override
    public E remove(Point2D.Double point) {

	// to be replaced by a binary search
	for (E item : points) {
	    if (item.getX() == point.getX() &&
		item.getY() == point.getY()) {
		points.remove(item);
		return item;
	    }
	}
	return null;
    }

    /**
       Return the number of points in the collection.

       @return the number of points in the collection
    */
    @Override
    public int size() {

	return points.size();
    }

    /**
       Return an iterator over the points in the collection.

       @return an iterator over the points in the collection
    */
    @Override
    public Iterator<E> iterator() {

	return points.iterator();
    }
	   
}

