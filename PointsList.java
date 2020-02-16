import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
   ArrayList-based implementation of a Point2DCollection, a collection
   of points in two dimensions.

   @author Jim Teresco
   @version Spring 2020
*/
public class PointsList<E extends Point2D.Double> implements Point2DCollection<E> {

    protected List<E> points;

    /**
       Construct a new, empty PointsList.
    */
    public PointsList() {

	points = new ArrayList<E>();
    }

    /**
       Add a new object to the collection.

       @param point the new object to be added
    */
    @Override
    public void add(E point) {

	points.add(point);
    }

    /**
       Get the object at the same coordinates at the given point.

       @param point a point with the coordinates being sought
       @return an object at the same coordinates as point, null if no
       such object is in the collection
    */
    @Override
    public E get(Point2D.Double point) {

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

