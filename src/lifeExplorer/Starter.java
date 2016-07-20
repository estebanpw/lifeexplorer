package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import lifeExplorer.EnvironmentSettings;


public class Starter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/*
		GaussianKernel k;
		int size = 5;
		int [][] kernel = GaussianKernel.kernel2integer(size, 10, 1, 1.5);
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				System.out.print(kernel[i][j]+ "  ");
			}
			System.out.println();
		}
		*/
		
		//Common.printTempMap(GaussianKernel.heatmap(50, 50, 1.5, 6, 30));
		
		
		int x = 80, y = 80;
		int npcs1 = 20, npcs2 = 1, npcs3 = 5;
		int clusterSize = 20;
		int clusters = 4;
		int pixelsPerCell = 10;
		
		Board b = new Board(x, y, Math.sqrt(clusterSize), clusterSize, clusters);
		Frame f = new Frame();
		
		
		List<Individuals> i = new LinkedList<Individuals>();
		
		for(int k=0;k<npcs1;k++){
			Virus v = new Virus(100, 200, new Point(x-1-k, y-1-k), 0.0, 4.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(v);
		}
		for(int k=0;k<npcs2;k++){
			Cell c = new Cell(100, 200, new Point(1+k, 1+k), 20.0,
					new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(c);
		}
		for(int k=0;k<npcs3;k++){
			Rabbit r = new Rabbit(100, 200, new Point(x/2+k,y/2+k), 0.0, 5.0, new EnvironmentSettings(220,250,1000,1200,10000), b);
			i.add(r);
		}
		
		
		f.start(b, pixelsPerCell);
		
		Stepper stp = new Stepper(b, f, i);
		stp.run();
		
	}

}
