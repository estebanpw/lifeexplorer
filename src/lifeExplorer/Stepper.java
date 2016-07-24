package lifeExplorer;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Stepper extends Thread{
	private Board b;
	private Frame f;
	private Map<Point,Individuals> indv;
	private Randomizer r;
	private int nCycles, cCycles;
	
	public Stepper(Board board, Frame frame, Map<Point,Individuals> individuals, int maxCycles, Randomizer r){
		b = board;
		f = frame;
		indv = individuals;
		nCycles = maxCycles;
		cCycles = 0;
		this.r = r;
	}
	
	public void run(){
		OrganismActions oa;
		Map<Point,Individuals> indiToAdd = new HashMap<Point,Individuals>();
		Map<Point,Individuals> deadIndividuals = new HashMap<Point,Individuals>();
		Event event;
		while(cCycles < nCycles){
			
			//Clear individuals to add due to replication
			indiToAdd.clear();
			//Clear the list of deleted individuals
			deadIndividuals.clear();
			
			
			//Check if an event happened
			event = r.didSomethingHappen();
			this.handleEvent(event);
			
			
			//Update each individual
			Individuals i;
			for(Point p : indv.keySet()){
				//Get current individual
				i = indv.get(p);
				
				if(!deadIndividuals.containsKey(i.position)){
					//Get its actions
					oa = i.lifeStep();
					//Check if someone has to die
					if(oa.enemyToKill != null){
						//Remove if it existed in the "to add" list
						indiToAdd.remove(oa.enemyToKill);
						//Update the board so that others can move in the position of the dead
						b.clearPosition(oa.enemyToKill.x, oa.enemyToKill.y);
						//Add it to the list of dead so we do not allow it to interactuate
						deadIndividuals.put(oa.enemyToKill, indv.get(oa.enemyToKill));
					}
					
					//Update our moving position
					if(b.update(Common.creatures2int(i.getType()), oa.nx, oa.ny, i.position.x, i.position.y) == 1){
						i.position.x = oa.nx;
						i.position.y = oa.ny;
					}
					//Put modified in the new list. This will not interfere with other individuals since the positions are updated in the board
					indiToAdd.put(new Point(i.position.x, i.position.y), i);
					
					//If one has accumulated enough rep power, save it to the add list
					if(oa.timeToReplicate == true){
						Point newSpawn = b.canBeSpawnedAround(new Point(i.position.x, i.position.y));
						if( newSpawn != null){
							i.replicationAcum = 0.0;
							//Copy individual
							Individuals copyInd = i.copyObject();
							copyInd.position = newSpawn;
							//Add the new individual to the map
							indiToAdd.put(newSpawn, copyInd);
							//Update the board
							b.update(Common.creatures2int(copyInd.getType()), newSpawn.x, newSpawn.y, -1, -1);
						}
					}
				}
			}
			//Once all individuals have had their step ran, we need to readd them
			indv.clear();
			indv.putAll(indiToAdd);
			
			
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
			if(event.equals(Event.METEOR)) r.generateMeteorite();
			if(event.equals(Event.HEATWAVE)) r.generateWave(true);
			if(event.equals(Event.COLDWAVE)) r.generateWave(false);
		}
		
	}
}
