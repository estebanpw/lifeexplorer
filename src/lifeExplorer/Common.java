package lifeExplorer;

import java.awt.Color;
import java.awt.Polygon;

public class Common {
	
	
	public static int randomWithRange(int min, int max){
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
	
	
	public static void printTempMap(double [][] map){
		for(int i=0;i<map.length;i++){
			System.out.print("==");
		}
		System.out.println();
		for(int i=0;i<map.length;i++){
			for(int j=0;j<map.length;j++){
				System.out.printf("%.2f", map[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static Color getBgColor(){
		return new Color(0.9f, 0.9f, 0.9f);
	}
	

	public static Polygon idToPolygon(int id, int width, int height){
		Polygon p = new Polygon();
		//REMOVE THIS IN THE FUTURE
		if(Common.int2creatures(id).equals(Creatures.RABBIT)){
			id = 2;
		}
		switch(id){
		case 0: {
			//A background square
			p.addPoint(0, 0);
			p.addPoint(width, 0);
			p.addPoint(width, height);
			p.addPoint(0, height);
			}
		case 1: {
			//a triangle
			p.addPoint(1,height-1);
			p.addPoint(width/2,0);
			p.addPoint(width-1,height-1);
			return p;
			
			}
		case 2: {
			//a square
			p.addPoint(1, 1);
			p.addPoint(width-1, 1);
			p.addPoint(width-1, height-1);
			p.addPoint(1, height-1);
			
			return p;
			}
		default: return null;
		}
		
	}

	public static Color tempColorEquivalence(double t, double max_t, double min_t){

		if(t > 0){
			//Hot
			//if(t/max_t < 0.00001) return Common.getBgColor();
			return new Color(1.0f, 0.0f, 0.0f, (float)(t/max_t));
	
		}else if(t < 0){
			//Cold
			//if(t/min_t < 0.00001) return Common.getBgColor();
			return new Color(0.0f, 0.0f, 1.0f, (float)(t/min_t));
			
		}else{
			return Common.getBgColor();
		}
	}
	

	public static Color lineColorIdEquivalence(int id){
    	switch(id){
    	case 0: return Color.black;
    	case 1: return Color.MAGENTA;
    	case 2: return Color.green;
    	case 3: return Color.cyan;
    	case 4: return Color.black;
    	}
    	return Common.getBgColor();
    }
	
	public static Color backgroundColorIdEquivalence(int id){
    	switch(id){
    	case 0: return Color.white;
    	case 1: return Color.black;
    	case 2: return Color.green;
    	}
    	return Color.magenta;
    }
	
	public static Creatures int2creatures(int id){
		switch(id){
		case 0: return Creatures.BACKGROUND;
		case 1: return Creatures.VIRUS;
		case 2: return Creatures.CELL;
		case 3: return Creatures.BACTERIA;
		case 4: return Creatures.RABBIT;
		}
		return Creatures.ZERO;
	}
	
}
