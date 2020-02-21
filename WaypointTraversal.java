import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
   Read in the points from one or more METAL .tmg graph files, 
   ignoring duplicate points, and writing a traversal to a .wpt
   file that can be displayed in HDX.

   @author Jim Teresco
   @version Spring 2020
*/
public class WaypointTraversal {

    /**
       Read in the points from one or more METAL .tmg graph files, 
       reporting if any duplicate points.

       @param args[0] the structure to use (PointsList or Quadtree)
       @param args[1] the .wpt file to write
       @param args[2-] contains the list of filenames to read
    */
    public static void main(String args[]) {

	// first, make sure there's at least one file after the two required
	// parameters
	if (args.length < 3) {
	    System.err.println("Usage: java WaypointTraversal [PointsList|Quadtree|ByX|ByY] outfile infiles");
	    System.exit(1);
	}
	Point2DCollection<Waypoint> points = null;
	if (args[0].equalsIgnoreCase("PointsList")) {
	    points = new PointsList<Waypoint>();
	}
	else if (args[0].equalsIgnoreCase("Quadtree")) {
	    points = new Quadtree<Waypoint>(-180, 180, -90, 90, 5);
	}
	else if (args[0].equalsIgnoreCase("ByX")) {
	    points = new PointsListSorted<Waypoint>(PointsListSorted.ORDER_BY_X);
	}
	else if (args[0].equalsIgnoreCase("ByY")) {
	    points = new PointsListSorted<Waypoint>(PointsListSorted.ORDER_BY_Y);
	}
	else {
	    System.err.println("Usage: java WaypointTraversal [PointsList|Quadtree|ByX|ByY] outfile infiles");
	    System.exit(1);
	}

	String outfileName = args[1];
	
	try {
	    for (int f = 2; f < args.length; f++) {
		String filename = args[f];
		System.out.println("Reading file: " + filename);
		Scanner in = new Scanner(new File(filename));

		// skip version line
		in.nextLine();
		// number of graph vertices, which will be our Waypoints
		int numV = in.nextInt();
		// skip remainder of line
		in.nextLine();

		// read numV lines
		for (int i = 0; i < numV; i++) {
		    Waypoint w = new Waypoint(in.next(),
					      in.nextDouble(),
					      in.nextDouble());
		    Waypoint existing = points.get(w);
		    if (existing == null) {
			points.add(w);
		    }
		}
		in.close();

		// report new stats
		System.out.println("Now have " + points.size() + " point locations.");
	    }

	    // now we write out the wpt file
	    PrintWriter pw = new PrintWriter(new File(outfileName));
	    for (Waypoint w : points) {
		pw.println(w.wptString());
	    }
	    pw.close();
	}
	catch (IOException e) {
	    System.err.println(e);
	    System.exit(1);
	}
	
    }
}
