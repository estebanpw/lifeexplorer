package lifeExplorer;

public class Poisson {
	private double interval, acum;
	
	public Poisson(double lambda){
		interval = Math.exp(-lambda);
		acum = 1;
	}
	
	public int isRandomEventTime(){
		acum = acum * Math.random();
		//If we spill over the interval, restart counter and return an indication that a random event should happen
		if(acum < interval){
			acum = 1;
			return 1;
		}
		//If we did not spill over then just return "No random m8"
		return -1;
	}
}
