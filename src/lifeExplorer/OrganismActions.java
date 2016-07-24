package lifeExplorer;

import java.awt.Color;
import java.awt.Point;

public class OrganismActions {
	int nx, ny; //Next x and y
	Color ncolor;
	boolean timeToReplicate;
	Point enemyToKill;
	
	public OrganismActions(int nextx, int nexty, Color c){
		nx = nextx;
		ny = nexty;
		ncolor = c;
		timeToReplicate = false;
		enemyToKill = null;
	}
}
