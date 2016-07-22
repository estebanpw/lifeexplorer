package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import lifeExplorer.EnvironmentSettings;


public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		//Configuration variables
		int x = 80, y = 80;
		int npcs1 = 20, npcs2 = 0, npcs3 = 0;
		int clusterSize = 20;
		int clusters = 4;
		int pixelsPerCell = 10;
		double averageEventsPerMaxCycles = 10;
		int maxCycles = 500;
		
		//System objects to model the board
		Board b = new Board(x, y, Math.sqrt(clusterSize), clusterSize, clusters);
		Frame f = new Frame();
		Randomizer revent = new Randomizer(averageEventsPerMaxCycles, maxCycles, b);
		
		
		//Addition of life
		List<Individuals> i = new LinkedList<Individuals>();
		
		for(int k=0;k<npcs1;k++){
			Virus v = new Virus(100, 200, new Point(x-1-k, y-1-k), 0.5, 35.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(v);
		}
		for(int k=0;k<npcs2;k++){
			Cell c = new Cell(100, 200, new Point(1+k, 1+k), 50.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(c);
		}
		for(int k=0;k<npcs3;k++){
			Rabbit r = new Rabbit(100, 200, new Point(x/2+k,y/2+k), 0.0, 5.0, new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(r);
		}
		
		//Initialize frame and board
		f.start(b, pixelsPerCell);
		
		//Start thread to run the system through iterations
		Stepper stp = new Stepper(b, f, i, maxCycles, revent);
		stp.run();
		
	}

}
