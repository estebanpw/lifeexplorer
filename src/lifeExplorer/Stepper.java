package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Stepper extends Thread{
	private Board b;
	private Frame f;
	private List<Individuals> indv;
	int nCycles = 1000;
	long delay;
	
	public Stepper(Board board, Frame frame, List <Individuals> individuals, long ms){
		b = board;
		f = frame;
		indv = individuals;
		delay = ms;
	}
	
	public void run(){
		OrganismActions oa;
		List<Individuals> indiToAdd = new LinkedList<Individuals>();
		while(nCycles > 0){
			indiToAdd.clear();
			//Update each individual
			for(Individuals i : indv){
				oa = i.lifeStep();
				if(b.update(Common.creatures2int(i.getType()), oa.nx, oa.ny, i.position.x, i.position.y) == 1){
					i.position.x = oa.nx;
					i.position.y = oa.ny;
				}
				//If one has accumulated enough rep power, save it to the add list
				if(oa.timeToReplicate == true){
					Point newSpawn = b.canBeSpawnedAround(new Point(oa.nx, oa.ny));
					if( newSpawn != null){
						i.replicationAcum = 0.0;
						//Copy individual
						//Individuals copyInd = i;
						Cell c = new Cell(100, 200, newSpawn,
								new EnvironmentSettings(220,250,1000,1200,10000), b);
						indiToAdd.add(c);
					}
				}
			}
			//Once we have finished checking for replications, just include them in the list and update will do the rest
			for(Individuals i : indiToAdd){
				indv.add(i);
			}
			f.update();
			nCycles--;
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
