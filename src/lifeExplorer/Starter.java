package lifeExplorer;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lifeExplorer.EnvironmentSettings;


public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		//Configuration variables
		int x = 80, y = 60;
		int npcs1 = 20, npcs2 = 2, npcs3 = 5;
		int clusterSize = 20;
		int clusters = 8;
		int pixelsPerCell = 15;
		double averageEventsPerMaxCycles = 10;
		int maxCycles = 100000;
		
		//System objects to model the board
		Board b = new Board(x, y, Math.sqrt(clusterSize), clusterSize, clusters);
		Frame f = new Frame();
		Randomizer revent = new Randomizer(averageEventsPerMaxCycles, maxCycles, b);
		
		
		//Addition of life
		Map<Point, Individuals> i = new HashMap<Point, Individuals>();
		
		for(int k=0;k<npcs1;k++){
			Virus v = new Virus(100, 200, new Point(x-1-k, y-1-k), 0.1, 35.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.put(new Point(x-1-k, y-1-k), v);
		}
		for(int k=0;k<npcs2;k++){
			Cell c = new Cell(100, 200, new Point(1+k, 1+k), 5.0,100.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.put(new Point(1+k, 1+k), c);
		}
		for(int k=0;k<npcs3;k++){
			Rabbit r = new Rabbit(100, 200, new Point(x/2+k,y/2+k), 0.1, 5.0, new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.put(new Point(x/2+k,y/2+k), r);
		}
		
		//Initialize frame and board
		f.start(b, pixelsPerCell);
		
		//Start thread to run the system through iterations
		Stepper stp = new Stepper(b, f, i, maxCycles, revent);
		stp.run();
		
	}

}
