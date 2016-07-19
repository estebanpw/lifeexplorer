package lifeExplorer;

import java.awt.Point;
import java.util.Random;

public class Cell extends Individuals{
	
	public Cell(int lCycles, int mCycles, Point pos, EnvironmentSettings envS, Board board) {
		super(lCycles, mCycles, pos, Creatures.CELL, new Random().nextGaussian(), envS, board);
	}

	@Override
	public OrganismActions lifeStep() {
		Point p  = this.goAnywhere();
		oa.nx = p.x;
		oa.ny = p.y;
		oa.timeToReplicate = this.addReplicationStep();
		return oa;
		// TODO Auto-generated method stub
		
	}
	
}
