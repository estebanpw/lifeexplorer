package lifeExplorer;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public class Virus extends Individuals{
	
	List<Creatures> enemies;
	
	public Virus(int lCycles, int mCycles, Point pos, double repPace, double repMax, EnvironmentSettings envS, Board board) {		
		super(lCycles, mCycles, pos, Creatures.VIRUS, repPace, repMax, envS, board);
		this.objective = new Point(Common.randomWithRange(0,board.getWide()), Common.randomWithRange(0,board.getHeight()));
		enemies = new LinkedList<Creatures>();
		enemies.add(Creatures.CELL);
		enemies.add(Creatures.RABBIT);
	}

	@Override
	public OrganismActions lifeStep(){
		
		oa.enemyToKill = this.findEnemyToKill(enemies);
		if(oa.enemyToKill != null){
			oa.nx = oa.enemyToKill.x;
			oa.ny = oa.enemyToKill.y;
		}else{
			Point p  = this.headToHot(Common.randomWithRange(5, 15));
			oa.nx = p.x;
			oa.ny = p.y;
		}
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
