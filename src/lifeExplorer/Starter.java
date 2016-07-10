package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import lifeExplorer.EnvironmentSettings;


public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 60, y = 60;
		
		Board b = new Board(x, y);
		Frame f = new Frame();
		List<Individuals> i = new LinkedList<Individuals>();
		
		Virus v = new Virus(1, 100, 200, new Point(x/2, y/2), 0.5,
				new EnvironmentSettings(220,250,1000,1200,10000), b);
		
		Cell c = new Cell(2, 100, 200, new Point(x/4, y/4),
				new EnvironmentSettings(220,250,1000,1200,10000), b);
		
		i.add(v);
		i.add(c);
		f.start(b);
		
		Stepper stp = new Stepper(b, f, i, 100);
		stp.start();
	}

}
