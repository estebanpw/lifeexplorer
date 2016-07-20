package lifeExplorer;

import java.awt.Point;

public class Virus extends Individuals{
	
	
	
	public Virus(int lCycles, int mCycles, Point pos, double repPace, double repMax, EnvironmentSettings envS, Board board) {		
		super(lCycles, mCycles, pos, Creatures.VIRUS, repPace, repMax, envS, board);
		this.objective = new Point(Common.randomWithRange(0,board.getWide()), Common.randomWithRange(0,board.getHeight()));
	}

	@Override
	public OrganismActions lifeStep(){
		Point p  = this.headToHot(Common.randomWithRange(5, 15));
		oa.nx = p.x;
		oa.ny = p.y;
		oa.timeToReplicate = this.addReplicationStep();
		return oa;
	}

	@Override
	public Individuals copyObject() {
		// TODO Auto-generated method stub
		Virus c = new Virus(this.lifeCycles, this.maxCycles, this.position, this.replicationPace, this.repMax, this.eS, this.board);
		return c;
	}

	@Override
	public boolean addReplicationStep(){
		this.replicationAcum += this.replicationPace;
		if(replicationAcum > repMax){
			return true;
		}
		return false;
	}
}
