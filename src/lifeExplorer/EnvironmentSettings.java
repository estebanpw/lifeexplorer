package lifeExplorer;

public class EnvironmentSettings{
	//Units are Kelvin, Kelvin, mm Hg, mm Hg,  uSv/year
	private int minTemp, maxTemp, minPressure, maxPressure, maxRadiationExposure;
	
	public EnvironmentSettings(int minT,int maxT,int minP,int maxP,int maxR){
		minTemp = minT;
		maxTemp = maxT;
		minPressure = minP;
		maxPressure = maxP;
		maxRadiationExposure = maxR;
	}
	public int canSubsist(int temperature, int pressure, int radiation){
		int suitable = 0;
		suitable += (temperature > maxTemp || temperature < minTemp) ?  -1 : 1 ;
		suitable += (pressure > maxPressure || pressure < minPressure) ? -1 : 1;
		suitable += (radiation > maxRadiationExposure) ? -1 : 1;
		return suitable;
	}
}
