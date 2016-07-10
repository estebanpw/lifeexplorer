package lifeExplorer;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
 
/* FrameDemo.java requires no other files. */
public class Frame {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
	private Board board;
	private Border separator;
	private JFrame frame = new JFrame("Life explorer");
	private JPanel[][] panelHolder;
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
    	panelHolder = new JPanel[board.getWide()][board.getHeight()];
    	for(int i=0; i<board.getWide(); i++){
    		for(int j=0; j<board.getHeight(); j++){
    			panelHolder[i][j] = new JPanel();
    			//panelHolder[i][j].setBorder(separator);
    			panelHolder[i][j].setBackground(board.colorIdEquivalence(board.getCell(i, j)));
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
    			panelHolder[i][j].setBackground(board.colorIdEquivalence(board.getCell(i, j))); 
    			panelHolder[i][j].validate();
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