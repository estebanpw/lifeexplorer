package lifeExplorer;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.Border;
 
/* FrameDemo.java requires no other files. */
public class Frame {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	
	class PaintPanel extends JPanel{
		Polygon p[][];
		Color lc[][]; //line color
		int width, height;
		int cellSize;
		public PaintPanel(int width, int height, int cellSize){
			super();
			p = new Polygon[width][height];
			lc = new Color[width][height];
			this.width = width;
			this.height = height;
			this.cellSize = cellSize;
		}
		public void paintComponent(Graphics g){
			//this.setBackground(bc);
			super.paintComponent(g);
			
		
			//SPECIFY HERE THE COORDINATES OF THE POLYGON!!!
			for(int x=0; x<width; x++){
				for(int y=0; y<height; y++){
					if(p[x][y] != null){
						g.setColor(lc[x][y]);
						for(int len=0; len<p[x][y].npoints; len++){
							p[x][y].xpoints[len] += (int)x * (int)width/cellSize;
							p[x][y].ypoints[len] += (int)y * (int)height/cellSize;
							
						}
						g.fillPolygon(p[x][y]);
					}
				}
			}
				
		}
		
		public void updateColor(int x, int y, Color c){
			lc[x][y] = c;
		}
		
		public void updatePoly(int x, int y, int id){
			p[x][y] = Common.idToPolygon(id, cellSize, cellSize);
		}
	};
	
	private Board board;
	private JFrame frame = new JFrame("Life explorer");
	private PaintPanel panelHolder;
	private JPanel North, South;
	private JLabel temp, tempDisplay;
	private int cellSize;
	
    public void create() {
        //Create and set up the window
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridLayout gl = new GridLayout(board.getWide(), board.getHeight());
    	frame.setLayout(new BorderLayout());
    	
    	North = new JPanel();
    	South = new JPanel();
    	temp = new JLabel("Actual temperature (celsius): ");
    	tempDisplay = new JLabel("No world created yet.");
       
        North.setLayout(gl);
        South.setLayout(new FlowLayout());
        South.add(temp);
        South.add(tempDisplay);
        //frame.setLayout(gl);
    	loadImages();
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    private void loadImages(){
    	panelHolder = new PaintPanel(board.getWide(),board.getHeight(), this.cellSize);
    	
    	for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			panelHolder.updateColor(i, j, Color.white);
    			panelHolder.updatePoly(i, j, 0);
    		}
    	}
    	panelHolder.repaint();
    	
    	
    	North.add(panelHolder);
    	frame.add(North, BorderLayout.NORTH);
    	frame.add(South, BorderLayout.SOUTH);
    }
    
    
    public void updateTemp(int t){
    	tempDisplay.setText(Integer.toString(t));
    }
    
    
    public void update(){
    	
    	double min_t = board.getMinTemp();
    	double max_t = board.getMaxTemp();
    	
		for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			panelHolder.updateColor(i, j, Common.tempColorEquivalence(board.getTempOfCell(i, j), max_t, min_t));
    			panelHolder.updatePoly(i, j, board.getCell(i, j));
    			//panelHolder[i][j].setBackground(Common.tempColorEquivalence(board.getTempOfCell(i, j), max_t, min_t));
    			//panelHolder[i][j].lc = Common.lineColorIdEquivalence(board.getCell(i, j));
    			//panelHolder[i][j].p = Common.idToPolygon(board.getCell(i, j), panelHolder[i][j].getWidth(), panelHolder[i][j].getHeight());
        		
    		}
    	}
		panelHolder.repaint();
		frame.repaint();
    }
    
    
    public void start(Board b, int sizeOfCell) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	cellSize = sizeOfCell;
    	
    	board = b;
    	if(board.getHeight() != board.getWide() || board.getHeight() % sizeOfCell != 0){
    		throw new RuntimeException("Board dimensiones must be equal and multiples of the cell size");
    	}
        create();
    }
}