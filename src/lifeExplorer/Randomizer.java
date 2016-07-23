package lifeExplorer;

public class Randomizer {
	private Poisson eventGenerator;
	private int remainingCyclesLastEvent, cyclesToRestore, durationCycles;
	private Event lastEvent;
	private double hwheat, lastMinTemp;
	private Board b;
	
	public Randomizer(double averageEventsPerMaxCycles, int maxCycles, Board board){
		remainingCyclesLastEvent = 0;
		cyclesToRestore = 0;
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
			if(lastEvent.equals(Event.EARTHQUAKE)){
				lastEvent = Event.NOTHING;
			}
			else if(lastEvent.equals(Event.HEATWAVE)){
				if(remainingCyclesLastEvent > 0){
					remainingCyclesLastEvent--;
					this.generateWave(true);
				}else if(durationCycles > 0){
					durationCycles--;
				}else if(durationCycles == 0 && cyclesToRestore > 0){
					cyclesToRestore--;
					this.revertWave(true);
				}else{
					lastEvent = Event.NOTHING;
				}
			}
			else if(lastEvent.equals(Event.COLDWAVE)){
				if(remainingCyclesLastEvent > 0){
					remainingCyclesLastEvent--;
					this.generateWave(false);
				}else if(durationCycles > 0){
					durationCycles--;
				}else if(durationCycles == 0 && cyclesToRestore > 0){
					cyclesToRestore--;
					this.revertWave(false);
				}else{
					lastEvent = Event.NOTHING;
				}
			}
			
		}else{
			if(eventGenerator.isRandomEventTime() == 1){
				switch(Common.randomWithRange(0, 3)){
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
						hwheat = Common.uniRandomWithRange(0.1, 0.12); //Temperature that will be raised per cycle
						durationCycles = Common.randomWithRange(10, 20);
						remainingCyclesLastEvent = 4;
						lastMinTemp = -b.getMaxTemp();
						cyclesToRestore = remainingCyclesLastEvent;
						return Event.HEATWAVE;
					}
					case 3: {
						lastEvent = Event.COLDWAVE;
						hwheat = Common.uniRandomWithRange(0.1, 0.12); //Temperature that will be reduced per cycle
						durationCycles = Common.randomWithRange(10, 20);
						remainingCyclesLastEvent = 4;
						lastMinTemp = -b.getMaxTemp();
						cyclesToRestore = remainingCyclesLastEvent;
						return Event.COLDWAVE;
						
					}
				}
			}
		}
		
		return Event.NOTHING;
	}
	
	/*
	 *  Generates a small kernel of doubles representing the meteorite region impacted
	 */
	public void generateMeteorite(){
		b.insertEventOnTempMap(GaussianKernel.kernel2scale(Common.randomWithRange(10, 20), (int)(1.5*b.getMaxTemp()), 2, 1), 
				Common.randomWithRange(0, b.getWide()), Common.randomWithRange(0, b.getHeight()));
		b.recalculateTemps();
	}
	
	public void generateWave(boolean hotOrCold){
		
		for(int i=0;i<b.getWide();i++){
			for(int j=0;j<b.getHeight();j++){
				if(hotOrCold){
					b.updateTemp(b.getTempOfCell(i, j)+((Math.abs(b.getMaxTemp()) - b.getTempOfCell(i, j))*hwheat), i, j);
				}else{
					b.updateTemp(b.getTempOfCell(i, j)+((Math.abs(b.getMaxTemp()) - b.getTempOfCell(i, j))*(-1)*hwheat), i, j);
				}
			}
		}
		b.recalculateTemps();
	}
	
	public void revertWave(boolean hotOrCold){
		
		for(int i=0;i<b.getWide();i++){
			for(int j=0;j<b.getHeight();j++){
				if(hotOrCold){
					b.updateTemp(b.getTempOfCell(i, j)-((lastMinTemp + b.getTempOfCell(i, j)/b.getMaxTemp())*(-1)*hwheat), i, j);
				}else{
					b.updateTemp(b.getTempOfCell(i, j)-((lastMinTemp + b.getTempOfCell(i, j)/b.getMaxTemp())*hwheat), i, j);
				}
			}
		}
		b.recalculateTemps();
		
	}
}
