import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
   Read in the points from one or more METAL .tmg graph files, 
   reporting if any duplicate points.

   @author Jim Teresco
   @version Spring 2020
*/
public class WaypointTesting {

    /**
       Read in the points from one or more METAL .tmg graph files, 
       reporting if any duplicate points.

       @param args contains the list of filenames to read
    */
    public static void main(String args[]) {

	// first, make sure there's at least one file
	if (args.length == 0) {
	    System.err.println("At least one file must be specified on the command line");
	    System.exit(1);
	}

	Point2DCollection<Waypoint> pointsInUse = new PointsList<Waypoint>();
	//Point2DCollection<Waypoint> pointsInUse =
        //    new Quadtree<Waypoint>(-180, 180, -90, 90, 20);
	try {
	    for (String filename : args) {
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
		    Waypoint existing = pointsInUse.get(w);
		    if (existing != null) {
			System.out.println(w + " at the same point as existing Waypoint " + existing);
		    }
		    else {
			pointsInUse.add(w);
		    }
		}
		in.close();

		// report new stats
		System.out.println("Now have " + pointsInUse.size() + " point locations.");
	    }
	}
	catch (IOException e) {
	    System.err.println(e);
	    System.exit(1);
	}
	
    }
}
