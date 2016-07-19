package lifeExplorer;

import java.awt.Point;
import java.util.Random;

public class Cell extends Individuals{
	
	private double REP_POWER;
	
	public Cell(int lCycles, int mCycles, Point pos, EnvironmentSettings envS, Board board) {
		super(lCycles, mCycles, pos, Creatures.CELL, Math.abs(new Random().nextGaussian()), envS, board);
		REP_POWER = 10;
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
		if(replicationAcum > REP_POWER){
			REP_POWER = 10000000;
			return true;
		}
		return false;
	}
	
}
