import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
   Quadtree-based implementation of a Point2DCollection, a collection
   of points in two dimensions.

   @author Jim Teresco
   @version Spring 2020
*/
public class Quadtree<E extends Point2D.Double> implements Point2DCollection<E> {

    /** refinement threshold */
    protected int refinement;
    
    /** bounding box of this quadrant */
    protected double xMin, yMin, xMax, yMax;

    /** child quadrants for non-leaf quadrants */
    protected Quadtree<E> nw, ne, sw, se;

    /** point list for leaf quadrants */
    protected List<E> points;

    /**
       Construct a new, empty Quadtree.

       @param xMin smallest x-coordinate
       @param xMax largest x-coordinate
       @param yMin smallest y-coordinate
       @param yMax largest y-coordinate
       @param refinement the maximum number of points allowed in a
       quadrant before it needs to be refined
    */
    public Quadtree(double xMin, double xMax, double yMin, double yMax,
		    int refinement) {

	this.xMin = xMin;
	this.xMax = xMax;
	this.yMin = yMin;
	this.yMax = yMax;
	this.refinement = refinement;
	points = new ArrayList<E>();
    }

    /**
       Check if this quadrant is a leaf.

       @return true if this is a leaf quadrant
    */
    protected boolean isLeaf() {

	// in a leaf, all 4 children are null
	return se == null;
    }

    /**
       Check if this quadrant has overfilled and needs refinement.

    */
    protected void refineIfNeeded() {

	if (points.size() > refinement) {
	    double midX = (xMin + xMax) / 2.0;
	    double midY = (yMin + yMax) / 2.0;
	    nw = new Quadtree<E>(xMin, midX, midY, yMax, refinement);
	    ne = new Quadtree<E>(midX, xMax, midY, yMax, refinement);
	    sw = new Quadtree<E>(xMin, midX, yMin, midY, refinement);
	    se = new Quadtree<E>(midX, xMax, yMin, midY, refinement);
	    for (E p : points) {
		childThatContains(p).add(p);
	    }
	    points = null;
	}
    }

    /**
       Return the child quadrant that should contain the given point.

       @param point the point whose child quadrant is sought
       @return the child quadrant where point should be placed
    */
    protected Quadtree<E> childThatContains(Point2D.Double point) {

	double midX = (xMin + xMax) / 2.0;
	double midY = (yMin + yMax) / 2.0;
	if (point.x < midX) {
	    if (point.y < midY) {
		return sw;
	    }
	    else {
		return nw;
	    }
	}
	else {
	    if (point.y < midY) {
		return se;
	    }
	    else {
		return ne;
	    }
	}
    }

    /**
       Add a new object to the quadtree

       @param point the new object to be added
    */
    @Override
    public void add(E point) {

	// could do some error checking against the bounding box here

	// if this is not a leaf, pass on to the appropriate child
	if (!isLeaf()) {
	    childThatContains(point).add(point);
	}
	else {
	    // first, add
	    points.add(point);
	    
	    // check if we need refinement
	    refineIfNeeded();
	}
    }

    /**
       Get the object at the same coordinates at the given point.

       @param point a point with the coordinates being sought
       @return an object at the same coordinates as point, null if no
       such object is in the collection
    */
    @Override
    public E get(Point2D.Double point) {

	// for a leaf, search my list
	if (isLeaf()) {
	    for (E item : points) {
		if (item.getX() == point.getX() &&
		    item.getY() == point.getY()) {
		    return item;
		}
	    }
	    return null;
	}
	// not a leaf, check in the appropriate child
	return childThatContains(point).get(point);
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

	// for a leaf, try to remove from my own list
	if (isLeaf()) {
	    for (E item : points) {
		if (item.getX() == point.getX() &&
		    item.getY() == point.getY()) {
		    points.remove(item);
		    return item;
		}
	    }
	    return null;
	}
	// non-leaf, try to remove from the appropriate child
	return childThatContains(point).remove(point);
    }

    /**
       Return the number of points in the collection.

       @return the number of points in the collection
    */
    @Override
    public int size() {

	// for a leaf, it's just the size of my list
	if (isLeaf()) {
	    return points.size();
	}
	// otherwise, add up the sizes of the children
	return ne.size() + nw.size() + se.size() + sw.size();
    }

    /**
       Return an iterator over the points in the collection.

       @return an iterator over the points in the collection
    */
    @Override
    public Iterator<E> iterator() {

	if (isLeaf()) {
	    return points.iterator();
	}
	else {
	    return new QuadtreeIterator<E>(this);
	}
    }
	   
}

/**
   An iterator that visits all points in a non-leaf quadrant by building
   on the iterators of each of the child quadrants.
*/
class QuadtreeIterator<E extends Point2D.Double> implements Iterator<E> {

    // an array of the iterators over the children
    protected Iterator<E> childIterators[];

    // current iterator to use, the lowest numbered one that still
    // hasNext, when this becomes 4, the contents of all child
    // quadrants have been returned
    protected int current;

    public QuadtreeIterator(Quadtree<E> tree) {

	childIterators = new Iterator[4];
	childIterators[0] = tree.nw.iterator();
	childIterators[1] = tree.ne.iterator();
	childIterators[2] = tree.sw.iterator();
	childIterators[3] = tree.se.iterator();
	advanceOverEmptyIterators();
    }

    private void advanceOverEmptyIterators() {

	while (current < 4 && !childIterators[current].hasNext()) {
	    current++;
	}
    }

    @Override
    public boolean hasNext() {

	return current < 4;
    }

    @Override
    public E next() {

	E answer = childIterators[current].next();
	advanceOverEmptyIterators();
	return answer;
    }
	
}
