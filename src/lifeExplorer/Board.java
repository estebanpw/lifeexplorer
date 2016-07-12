package lifeExplorer;

import java.awt.Color;
import java.awt.Polygon;

public class Board {
	private int board[][];
	private double temps[][];
	private int press[][];
	private int wide, height;
	
	public Board(int wide_x, int height_y, double tempTheta){
		wide = wide_x;
		height = height_y;
		board = new int[wide][height];
		temps = GaussianKernel.heatmap(wide, height, tempTheta, Common.randomWithRange(3, 7), Common.randomWithRange(4, 12));
		press = new int[wide][height];
		generateBoard();
	}
	
	public int update(int cellID, int x, int y, int prevx, int prevy){
		if(x >= 0 && y >= 0 && x < wide && y < height && x >= 0 && y >= 0 && board[x][y] == 0){
			board[x][y] = cellID;
			board[prevx][prevy] = 0;
			return 1;
		}
		return 0;
	}
	
	public int updateEnv(int temperature, int pressure, int x, int y){
		if(x >= 0 && y >= 0 && x < wide && y < height && x >= 0 && y >= 0){
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
	
	public Color lineColorIdEquivalence(int id){
    	switch(id){
    	case 0: return Color.red;
    	case 1: return Color.blue;
    	case 2: return Color.green;
    	}
    	return Color.magenta;
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