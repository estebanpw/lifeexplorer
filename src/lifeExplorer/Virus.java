package lifeExplorer;

import java.awt.Point;

public class Virus extends Individuals{
	
	public Virus(int ID, int lCycles, int mCycles, Point pos, double repPace, EnvironmentSettings envS, Board board) {
		
		super(ID, lCycles, mCycles, pos, Creatures.VIRUS, repPace, envS, board);
	}

	@Override
	public OrganismActions lifeStep() {
		Point p  = this.headToPoint(new Point(this.board.getWide(),0));
		
		oa.nx = p.x;
		oa.ny = p.y;
		return oa;
		// TODO Auto-generated method stub
		
	}
}
