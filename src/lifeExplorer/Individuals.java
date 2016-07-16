package lifeExplorer;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public abstract class Individuals {
	//bacteria, hostCell and virus. Bacteria reproduces by fission; same with hostCell; virus moves.
	protected Board board;
	protected Creatures creature;
	protected int lifeCycles, maxCycles;
	protected Point position;
	protected double replicationPace;
	protected EnvironmentSettings eS;
	protected OrganismActions oa; 
	protected Point objective;
	
	public Individuals(int lCycles, int mCycles, Point pos, Creatures crea, double repPace, EnvironmentSettings envS, Board b){
		lifeCycles = lCycles;
		maxCycles = mCycles;
		position = pos;
		creature = crea;
		replicationPace = repPace;
		eS = envS;
		board = b;
		oa = new OrganismActions(0,0, Common.backgroundColorIdEquivalence(creature.ordinal()));
	}
	public List<Point> isTouching(){
		//check if any other system is near it, if so, return position, then ask board for type of system.
		/*    1 2 3
		 *    4 x 5
		 *    6 7 8
		 */
		List<Point> lTouching = new LinkedList<Point>();
		if(position.x -1 >= 0 && position.y-1 >= 0){ // Position 1
			if(board.getCell(position.x-1, position.y-1) != 0) lTouching.add(new Point(position.x-1, position.y-1));
		}
		if(position.x -1 >= 0){ //Position 2 
			if(board.getCell(position.x-1, position.y) != 0) lTouching.add(new Point(position.x-1, position.y));
		}
		if(position.x -1 >= 0 && position.y +1 < board.getWide()){ //Position 3
			if(board.getCell(position.x -1, position.y +1) != 0) lTouching.add(new Point(position.x-1, position.y+1));
		}
		if(position.y-1 >= 0){ //Position 4
			if(board.getCell(position.x, position.y-1) != 0) lTouching.add(new Point(position.x, position.y-1));
		}
		if(position.y +1 < board.getWide()){ //Position 5
			if(board.getCell(position.x, position.y+1) != 0) lTouching.add(new Point(position.x,position.y+1));
		}
		if(position.x +1 < board.getHeight() && position.y -1 >= 0){ //Position 6
			if(board.getCell(position.x+1, position.y-1) != 0) lTouching.add(new Point(position.x+1, position.y-1));
		}
		if(position.x +1 < board.getHeight()){ //Position 7
			if(board.getCell(position.x+1, position.y) != 0) lTouching.add(new Point(position.x+1, position.y));
		}
		if(position.x+1 < board.getHeight() && position.y+1 < board.getWide()){ //Position 8
			if(board.getCell(position.x+1, position.y+1) != 0) lTouching.add(new Point(position.x+1, position.y+1));
		}
		return lTouching;
	}
	
	public Point goSouth(){
		return new Point(position.x+1, position.y);
	}
	
	public Point headToPoint(Point p){
		Point rp = new Point(position);
		if(p.x > position.x) rp.x++;
		if(p.x < position.x) rp.x--;
		if(p.y > position.y) rp.y++;
		if(p.y < position.y) rp.y--;
		return rp;
	}
	
	public Point headToHot(){
		Point target = board.getMaxTemPos();
		return headToPoint(target);
	}
	
	public Point goAnywhere(){
		return new Point(position.x+ ((-0.5 + Math.random() >= 0) ? 1 : -1), position.y+((-0.5 + Math.random() >= 0) ? 1 : -1));		
	}
	
	public abstract OrganismActions lifeStep();
	
}
