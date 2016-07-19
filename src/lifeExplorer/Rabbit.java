package lifeExplorer;

import java.awt.Point;

public class Rabbit extends Individuals{

	public Rabbit(int lCycles, int mCycles, Point pos, double repPace, EnvironmentSettings envS, Board board) {		
		super(lCycles, mCycles, pos, Creatures.RABBIT, repPace, envS, board);
		this.objective = new Point(Common.randomWithRange(0,board.getWide()), Common.randomWithRange(0,board.getHeight()));
	}

	@Override
	public OrganismActions lifeStep() {
		Point p  = this.avoidParticularEnemy(Creatures.VIRUS);
		oa.nx = p.x;
		oa.ny = p.y;
		oa.timeToReplicate = this.addReplicationStep();
		return oa;
	}

	@Override
	public boolean addReplicationStep(){
		this.replicationAcum += this.replicationPace;
		if(replicationAcum > 1.0) return true;
		return false;
	}

}
