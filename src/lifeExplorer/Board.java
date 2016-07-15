package lifeExplorer;

import java.awt.Color;
import java.awt.Polygon;

public class Board {
	private int board[][];
	private double temps[][];
	private int press[][];
	private int wide, height;
	
	public Board(int wide_x, int height_y, double tempTheta, int cluTempSize, int clusters){
		wide = wide_x;
		height = height_y;
		board = new int[wide][height];
		temps = GaussianKernel.heatmap(wide, height, tempTheta, clusters, cluTempSize);
		press = new int[wide][height];
		generateBoard();
	}
	
	public int update(int cellID, int x, int y, int prevx, int prevy){
		if(x >= 0 && y >= 0 && x < wide && y < height && board[x][y] == 0){
			board[x][y] = cellID;
			board[prevx][prevy] = 0;
			return 1;
		}
		return 0;
	}
	
	public int updateEnv(double temperature, int pressure, int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height){
			temps[x][y] = temperature;
			press[x][y] = pressure;
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
		if(x >= 0 && y >= 0 && x < wide && y < height) return board[x][y];
		return -1;
	}
	
	public double getTempOfCell(int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height) return temps[x][y];
		return 0;
	}
	
	public double[][] getTempMap(){
		return temps;
	}
	
	public Polygon idToPolygon(int id, int width, int height){
		Polygon p = new Polygon();
		switch(id){
		case 0: return null;
		case 1: {
			//a triangle
			p.addPoint(1,height-1);
			p.addPoint(width/2,0);
			p.addPoint(width-1,height-1);
			return p;
			
			}
		case 2: {
			//a square
			p.addPoint(1, 1);
			p.addPoint(width-1, 1);
			p.addPoint(width-1, height-1);
			p.addPoint(1, height-1);
			
			return p;
			}
		default: return null;
		}
		
	}
	
	public double getMaxTemp(){
		double max = temps[0][0];
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(max < temps[i][j]) max = temps[i][j];
			}
		}
		return max;
	}
	
	public double getMinTemp(){
		double min = temps[0][0];
		for(int i=0;i<wide;i++){
			for(int j=0;j<height;j++){
				if(min > temps[i][j]) min = temps[i][j];
			}
		}
		return min;
	}
	
	public Color tempColorEquivalence(double t, double max_t, double min_t){

		if(t > 0){
			//Hot
			//if(t/max_t < 0.00001) return Common.getBgColor();
			return new Color(1.0f, 0.0f, 0.0f, (float)(t/max_t));
	
		}else if(t < 0){
			//Cold
			//if(t/min_t < 0.00001) return Common.getBgColor();
			return new Color(0.0f, 0.0f, 1.0f, (float)(t/min_t));
			
		}else{
			return Common.getBgColor();
		}
	}
	
	public Color lineColorIdEquivalence(int id){
    	switch(id){
    	case 0: return Color.black;
    	case 1: return Color.MAGENTA;
    	case 2: return Color.green;
    	}
    	return Common.getBgColor();
    }
	
	public Color backgroundColorIdEquivalence(int id){
    	switch(id){
    	case 0: return Color.white;
    	case 1: return Color.black;
    	case 2: return Color.green;
    	}
    	return Color.magenta;
    }
	
	private void generateBoard(){
		for(int i=0; i<wide; i++){
			for(int j=0; j<height; j++){
				board[i][j] = 0;
			}
		}
	}
}