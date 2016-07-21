package lifeExplorer;

import java.awt.Point;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Board {
	private Chunk[][] board;
	private int wide, height;
	private double maxTemp, minTemp;
	private Point maxTempPos, minTempPos;
	
	
	public Board(int wide, int height, double tempTheta, int cluTempSize, int clusters){
		this.wide = wide;
		this.height = height;
		board = new Chunk[wide][height];
		generateChunkBoard();
		generateTemperature(tempTheta, clusters, cluTempSize);
		maxTemp = calcMaxTemp();
		maxTempPos = calcMaxTemPos();
		minTemp = calcMinTemp();
		minTempPos = calcMinTemPos();
	}
	
	private void generateChunkBoard(){
		for(int i=0; i<wide; i++){
			for(int j=0; j<height; j++){
				board[i][j] = new Chunk(i,j);
			}
		}
	}
	
	public int update(int newID, int x, int y, int prevx, int prevy){
		if(x >= 0 && y >= 0 && x < wide && y < height && board[x][y].getID() == 0){
			board[x][y].setID(newID);
			board[prevx][prevy].setID(0);
			return 1;
		}
		return 0;
	}
	
	public int updateEnv(double temperature, int pressure, int radiation, int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height){
			board[x][y].setTemperature(temperature);
			board[x][y].setPressure(pressure);
			board[x][y].setRadiation(radiation);
			return 1;
		}
		return 0;
	}
	
	public void recalculateTemps(){
		maxTemp = calcMaxTemp();
		maxTempPos = calcMaxTemPos();
		minTemp = calcMinTemp();
		minTempPos = calcMinTemPos();
	}
	
	public int getWide(){
		return wide;
	}
	public int getHeight(){
		return height;
	}
	
	public int getCell(int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height) return board[x][y].getID();
		return -1;
	}
	
	public double getTempOfCell(int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height) return board[x][y].getTemperature();
		return 0;
	}
	
	private double calcMaxTemp(){
		double max = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(max < board[i][j].getTemperature()) max = board[i][j].getTemperature();
			}
		}
		return max;
	}
	
	public Point canBeSpawnedAround(Point position){
		//Check if a new unit can be spawned around
		/*    1 2 3
		 *    4 x 5
		 *    6 7 8
		 */
		List <Point> possiblePoints = new LinkedList<Point>();
		
		if(position.x -1 >= 0 && position.y-1 >= 0){ // Position 1
			if(this.getCell(position.x-1, position.y-1) == 0) possiblePoints.add(new Point(position.x-1, position.y-1));
		}
		if(position.x -1 >= 0){ //Position 2 
			if(this.getCell(position.x-1, position.y) == 0) possiblePoints.add(new Point(position.x-1, position.y));
		}
		if(position.x -1 >= 0 && position.y +1 < this.getWide()){ //Position 3
			if(this.getCell(position.x -1, position.y +1) == 0) possiblePoints.add(new Point(position.x-1, position.y+1));
		}
		if(position.y-1 >= 0){ //Position 4
			if(this.getCell(position.x, position.y-1) == 0) possiblePoints.add(new Point(position.x, position.y-1));
		}
		if(position.y +1 < this.getWide()){ //Position 5
			if(this.getCell(position.x, position.y+1) == 0) possiblePoints.add(new Point(position.x,position.y+1));
		}
		if(position.x +1 < this.getHeight() && position.y -1 >= 0){ //Position 6
			if(this.getCell(position.x+1, position.y-1) == 0) possiblePoints.add(new Point(position.x+1, position.y-1));
		}
		if(position.x +1 < this.getHeight()){ //Position 7
			if(this.getCell(position.x+1, position.y) == 0) possiblePoints.add(new Point(position.x+1, position.y));
		}
		if(position.x+1 < this.getHeight() && position.y+1 < this.getWide()){ //Position 8
			if(this.getCell(position.x+1, position.y+1) == 0) possiblePoints.add(new Point(position.x+1, position.y+1));
		}
		if(possiblePoints.size()>0){
			//Shuffle so we get a random position to spawn 
			Collections.shuffle(possiblePoints);
			return possiblePoints.get(0);
		}
		return null;
	}

	private Point calcMaxTemPos(){
		Point maxP = new Point(0,0);
		double max = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(max < board[i][j].getTemperature()){ 
					max = board[i][j].getTemperature();
					maxP.setLocation(i, j);
				}
			}
		}
		return maxP;
	}
	
	private double calcMinTemp(){
		double min = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(min > board[i][j].getTemperature()) min = board[i][j].getTemperature();
			}
		}
		return min;
	}
	

	private Point calcMinTemPos(){
		Point minP = new Point(0,0);
		double min = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(min > board[i][j].getTemperature()){ 
					min = board[i][j].getTemperature();
					minP.setLocation(i, j);
				}
			}
		}
		return minP;
	}
	
	
	private void generateTemperature(double theta, int seeds, int clusterSize){
		double[][] tempMap = GaussianKernel.heatmap(wide, height, theta, seeds, clusterSize);
		for(int i=0; i<wide; i++){
			for(int j=0; j<height;j++){
				board[i][j].setTemperature(tempMap[i][j]);
			}
		}
	}
	

	public double getMaxTemp() {
		return maxTemp;
	}

	public Point getMaxTempPos() {
		return maxTempPos;
	} 
	
	public double getMinTemp() {
		return minTemp;
	}

	public Point getMinTempPos() {
		return minTempPos;
	} 
}
