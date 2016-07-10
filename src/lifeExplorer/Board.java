package lifeExplorer;

import java.awt.Color;

public class Board {
	private int board[][];
	private int wide, height;
	
	public Board(int wide_x, int height_y){
		wide = wide_x;
		height = height_y;
		board = new int[wide][height];
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
	
	public Color colorIdEquivalence(int id){
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