package lifeExplorer;

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
			for(Individuals i : indv){
				oa = i.lifeStep();
				if(b.update(Common.creatures2int(i.getType()), oa.nx, oa.ny, i.position.x, i.position.y) == 1){
					i.position.x = oa.nx;
					i.position.y = oa.ny;
				}
				
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
