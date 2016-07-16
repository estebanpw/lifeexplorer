package lifeExplorer;

import java.awt.Point;

public class Board {
	private Chunk[][] board;
	private int wide, height;
	
	
	public Board(int wide, int height, double tempTheta, int cluTempSize, int clusters){
		this.wide = wide;
		this.height = height;
		board = new Chunk[wide][height];
		generateChunkBoard();
		generateTemperature(tempTheta, clusters, cluTempSize);
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
	
	public double getMaxTemp(){
		double max = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(max < board[i][j].getTemperature()) max = board[i][j].getTemperature();
			}
		}
		return max;
	}
	

	public Point getMaxTemPos(){
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
	
	public double getMinTemp(){
		double min = board[0][0].getTemperature();
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(min > board[i][j].getTemperature()) min = board[i][j].getTemperature();
			}
		}
		return min;
	}
	
	private void generateTemperature(double theta, int seeds, int clusterSize){
		double[][] tempMap = GaussianKernel.heatmap(wide, height, theta, seeds, clusterSize);
		for(int i=0; i<wide; i++){
			for(int j=0; j<height;j++){
				board[i][j].setTemperature(tempMap[i][j]);
			}
		}
	}
}
