package lifeExplorer;

public class Randomizer {
	private Poisson eventGenerator;
	private int remainingCyclesLastEvent;
	private Event lastEvent;
	private double hwheat, maxRise;
	private Board b;
	
	public Randomizer(double averageEventsPerMaxCycles, int maxCycles, Board board){
		remainingCyclesLastEvent = 0;
		lastEvent = Event.NOTHING;
		eventGenerator = new Poisson(maxCycles/averageEventsPerMaxCycles);
		b = board;
	}
	
	/* This function calls the event generator (poisson distr.), adding a random arrival to the interval. 
	 * Once this interval gets spilled over, an event takes place. An event can be either of instant effect 
	 * e.g. a meteorite, or a cycles-lasting one (e.g. heatwave). If its the case for a long-lasting event,
	 * this function makes the recall
	 */
	public Event didSomethingHappen(){
		if(!lastEvent.equals(Event.NOTHING)){
			if(lastEvent.equals(Event.HEATWAVE) && remainingCyclesLastEvent > 0){
				remainingCyclesLastEvent--;
				this.generateHeatWave();
			}
		}
		if(eventGenerator.isRandomEventTime() == 1){
			//switch(Common.randomWithRange(0, 3)){
			switch(2){
				case 0: {
					lastEvent = Event.EARTHQUAKE;
					remainingCyclesLastEvent = 0;
					return Event.EARTHQUAKE;
				}
				case 1: {
					lastEvent = Event.METEOR;
					remainingCyclesLastEvent = 0;
					return Event.METEOR;
				}
				case 2: {
					lastEvent = Event.HEATWAVE;
					hwheat = Common.uniRandomWithRange(0.0, 0.2); //Temperature that will be raised per cycle
					maxRise = b.getMaxTemp()/Common.randomWithRange(2, 4);
					remainingCyclesLastEvent = Common.randomWithRange(2, 10);
					return Event.HEATWAVE;
				}
			}
		}
		return Event.NOTHING;
	}
	
	/*
	 *  Generates a small kernel of doubles representing the meteorite region impacted
	 */
	public void generateMeteorite(){
		b.insertEventOnTempMap(GaussianKernel.kernel2scale(Common.randomWithRange(10, 20), (int)(2*b.getMaxTemp()), 2, 1), 
				Common.randomWithRange(0, b.getWide()), Common.randomWithRange(0, b.getHeight()));
		b.recalculateTemps();
	}
	
	public void generateHeatWave(){
		
		for(int i=0;i<b.getWide();i++){
			for(int j=0;j<b.getHeight();j++){
				if(b.getTempOfCell(i, j) < maxRise){
					b.updateTemp(b.getTempOfCell(i, j)+b.getMaxTemp()*(hwheat), i, j);
				}
			}
		}
		b.recalculateTemps();
	}
}
