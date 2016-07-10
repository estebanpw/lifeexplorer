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
		Polygon p;
		Color bc; //background color
		Color lc; //line color
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			this.setBackground(bc);
			g.setColor(lc);
			if(p != null) g.fillPolygon(p);
		}
	};
	
	private Board board;
	private Border separator;
	private JFrame frame = new JFrame("Life explorer");
	private PaintPanel[][] panelHolder;
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
    	
    	separator = BorderFactory.createLineBorder(Color.black, 1);
    	panelHolder = new PaintPanel[board.getWide()][board.getHeight()];
    	
    	for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			
    			panelHolder[i][j] = new PaintPanel();
    			//panelHolder[i][j].setBorder(separator);
    			//panelHolder[i][j].setBackground(board.colorIdEquivalence(board.getCell(i, j)));
    			panelHolder[i][j].bc = Color.white;
    			//frame.add(panelHolder[i][j]);
    			North.add(panelHolder[i][j]);
    		}
    	}
    	frame.add(North, BorderLayout.NORTH);
    	frame.add(South, BorderLayout.SOUTH);
    }
    
    
    public void updateTemp(int t){
    	tempDisplay.setText(Integer.toString(t));
    }
    public void update(){
    	
    	
		for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			panelHolder[i][j].bc = board.backgroundColorIdEquivalence(0);
    			panelHolder[i][j].lc = board.lineColorIdEquivalence(board.getCell(i, j));
    			panelHolder[i][j].p = board.idToPolygon(board.getCell(i, j), panelHolder[i][j].getWidth(), panelHolder[i][j].getHeight());
        		panelHolder[i][j].repaint();
    		}
    	}
    	
		frame.repaint();
    }
    public void start(Board b) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
    	board = b;
        create();
    }
}