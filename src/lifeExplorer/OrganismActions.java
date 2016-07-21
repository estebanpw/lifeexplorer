package lifeExplorer;

import java.awt.Color;

public class OrganismActions {
	int nx, ny; //Next x and y
	Color ncolor;
	boolean timeToReplicate;
	
	public OrganismActions(int nextx, int nexty, Color c){
		nx = nextx;
		ny = nexty;
		ncolor = c;
		timeToReplicate = false;
	}
}
