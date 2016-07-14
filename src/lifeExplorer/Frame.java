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
		Polygon[][] p;
		Color[][] lc; //line color
		int pivotx, pivoty;
		public PaintPanel(int width, int height){
			super();
			p = new Polygon[width][height];
			lc = new Color[width][height];
					
		}
		public void paintComponent(Graphics g){
			//this.setBackground(bc);
			super.paintComponent(g);
			g.setColor(lc[pivotx][pivoty]);
			if(p[pivotx][pivoty] != null){
				//SPECIFY HERE THE COORDINATES OF THE POLYGON!!!
				g.fillPolygon(p[pivotx][pivoty]);
			}
		}
		
		public void setColor(Color c){
			lc[pivotx][pivoty] = c;
		}
		
		public void update(int x, int y){
			pivotx = x;
			pivoty = y;
		}
	};
	
	private Board board;
	private JFrame frame = new JFrame("Life explorer");
	private PaintPanel panelHolder;
	private JPanel North, South;
	private JLabel temp, tempDisplay;
	
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
    	panelHolder = new PaintPanel(board.getWide(),board.getHeight());
    	
    	for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			
    			
    			panelHolder[i][j].setBackground(Common.getBgColor());
    			
    		}
    	}
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
    			panelHolder[i][j].setBackground(board.tempColorEquivalence(board.getTempOfCell(i, j), max_t, min_t));
    			panelHolder[i][j].lc = board.lineColorIdEquivalence(board.getCell(i, j));
    			panelHolder[i][j].p = board.idToPolygon(board.getCell(i, j), panelHolder[i][j].getWidth(), panelHolder[i][j].getHeight());
        		panelHolder[i][j].repaint();
    		}
    	}
    	
		frame.repaint();
    }
    
    public void paintAPanel(int i, int j){
    	panelHolder.update(i, j);
    	panelHolder.setColor(Color.white);
    	
    	panelHolder.repaint();
    }
    
    public void start(Board b) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	board = b;
        create();
    }
}