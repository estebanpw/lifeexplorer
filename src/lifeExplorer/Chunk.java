package lifeExplorer;

public class Chunk {
	private int pressure, radiation, x, y;
	private double temperature;
	private boolean occupied;
	private int ID;
	
	public Chunk(double temperature, int pressure, int radiation, int x, int y){
		this.temperature = temperature;
		this.pressure = pressure;
		this.radiation = radiation;
		this.x = x;
		this.y = y;
		occupied = false;
		setID(0);
	}
	
	public Chunk(int x, int y){
		this(300,760,0,x,y);
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getRadiation() {
		return radiation;
	}

	public void setRadiation(int radiation) {
		this.radiation = radiation;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void occupy(){
		occupied = true;
	}
	
	public void leave(){
		occupied = false;
	}
	
	public boolean getOccupied(){
		return occupied;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}
	
}
