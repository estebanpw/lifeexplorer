package lifeExplorer;

import java.awt.Point;
import java.util.Random;

public class Cell extends Individuals{
	
	
	public Cell(int lCycles, int mCycles, Point pos, double repMax, EnvironmentSettings envS, Board board) {
		super(lCycles, mCycles, pos, Creatures.CELL, Math.abs(new Random().nextGaussian()), repMax, envS, board);
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

	@Override
	public boolean addReplicationStep(){
		this.replicationAcum += this.replicationPace;
		if(replicationAcum > repMax){
			return true;
		}
		return false;
	}

	@Override
	public Individuals copyObject() {
		// TODO Auto-generated method stub
		Cell c = new Cell(this.lifeCycles, this.maxCycles, this.position, this.repMax, this.eS, this.board);
		return c;
	}
	
}
