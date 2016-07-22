package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Stepper extends Thread{
	private Board b;
	private Frame f;
	private List<Individuals> indv;
	private Randomizer r;
	private int nCycles, cCycles;
	
	public Stepper(Board board, Frame frame, List <Individuals> individuals, int maxCycles, Randomizer r){
		b = board;
		f = frame;
		indv = individuals;
		nCycles = maxCycles;
		cCycles = 0;
		this.r = r;
	}
	
	public void run(){
		OrganismActions oa;
		List<Individuals> indiToAdd = new LinkedList<Individuals>();
		Event event;
		while(cCycles < nCycles){
			//Check if an event happened
			event = r.didSomethingHappen();
			this.handleEvent(event);
			
			//Clear individuals to add due to replication
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
					Point newSpawn = b.canBeSpawnedAround(new Point(i.position.x, i.position.y));
					if( newSpawn != null){
						i.replicationAcum = 0.0;
						//Copy individual
						Individuals copyInd = i.copyObject();
						copyInd.position = newSpawn;
						indiToAdd.add(copyInd);
					}
				}
			}
			//Once we have finished checking for replications, just include them in the list and update will do the rest
			for(Individuals i : indiToAdd){
				indv.add(i);
			}
			
			f.setCycle(cCycles);
			f.update();
			cCycles++;
			try {
				Thread.sleep(f.getMSDelay());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void handleEvent(Event event){
		//Something happened
		if(!event.equals(Event.NOTHING)){
			f.setInfoLabel(Common.event2message(event), cCycles);
			if(event.equals(Event.METEOR)) b.insertEventOnTempMap(r.generateMeteorite(b.getMaxTemp()), Common.randomWithRange(0, b.getWide()), Common.randomWithRange(0, b.getHeight()));
			b.recalculateTemps();
		}
		
	}
}
