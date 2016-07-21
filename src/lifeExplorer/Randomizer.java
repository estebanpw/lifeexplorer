package lifeExplorer;

public class Randomizer {
	private Poisson eventGenerator;
	private int sumEvents;
	private int iteras;
	public Randomizer(double averageEventsPerMaxCycles, int maxCycles){
		
		iteras = 0;
		sumEvents = 0;
		eventGenerator = new Poisson(maxCycles/averageEventsPerMaxCycles);
	}
	
	public Event didSomethingHappen(){
		iteras++;
		if(eventGenerator.isRandomEventTime() == 1){
			sumEvents++;
			System.out.println("Iteras: "+iteras+" and events: "+sumEvents);
			switch(Common.randomWithRange(0, 3)){
				case 0: return Event.EARTHQUAKE;
				case 1: return Event.METEOR;
				case 2: return Event.HEATWAVE;
			}
		}
		return Event.NOTHING;
	}
	
	public double[][] generateMeteorite(double maxTemp){
		return GaussianKernel.kernel2scale(Common.randomWithRange(5, 15), (int)(2*maxTemp), 2, 1);
	}
}
