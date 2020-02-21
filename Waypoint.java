import java.awt.geom.Point2D;

/**
   A class to represent the Waypoints from METAL data.

   These have a String label, and a latitude and longitude
   encoded in the Point2D.Double.

   @author Jim Teresco
   @version Spring 2020
*/
public class Waypoint extends Point2D.Double {

    protected String label;

    /**
       Construct a new Waypoint.
       
       @param label point label
       @param lat latitude
       @param lng longitude
    */
    public Waypoint(String label, double lat, double lng) {

	super(lng, lat);
	this.label = label;
    }

    /**
       Return a String representation of the Waypoint

       @return a String representation of the Waypoint
    */
    public String toString() {

	return label + " (" + y + "," + x + ")";
    }

    /**
       Return a METAL .wpt-format line for this Waypoint 

       @return a METAL .wpt-format line for this Waypoint
    */
    public String wptString() {

	return label + "  http://www.openstreetmap.org/?lat=" + y + "&lon=" + x;
    }

}
