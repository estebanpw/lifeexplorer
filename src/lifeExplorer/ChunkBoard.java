package lifeExplorer;

public class ChunkBoard {
	private Chunk[][] board;
	private int wide, height;
	
	public ChunkBoard(int wide, int height){
		this.wide = wide;
		this.height = height;
		board = new Chunk[wide][height];
		generateChunkBoard();
	}
	
	private void generateChunkBoard(){
		for(int i=0; i<wide; i++){
			for(int j=0; j<height; j++){
				board[i][j] = new Chunk(i,j);
			}
		}
	}
	
	private void generateTemperature(int theta, int seeds, int clusterSize){
		double[][] tempMap = GaussianKernel.heatmap(wide, height, theta, seeds, clusterSize);
		for(int i=0; i<wide; i++){
			for(int j=0; j<height;j++){
				board[i][j].setTemperature(tempMap[i][j]);
			}
		}
	}
}
