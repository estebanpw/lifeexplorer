package lifeExplorer;

import java.awt.Color;
import java.awt.Polygon;

public class Common {
	
	
	public static int randomWithRange(int min, int max){
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
	
	public static double uniRandomWithRange(double min, double max){
		   double range = (max - min) + 1;     
		   return (int)(Math.random() * range) + min;
	}
	
	public static int manhattanDistance(int x1, int y1, int x2, int y2){
		return (Math.abs(x1-x2) + Math.abs(y1-y2));
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

		if(Common.int2creatures(id).equals(Creatures.RABBIT)){
			id = 2;
		}
		switch(id){
		case 0: {
			//A background square
			p.addPoint(1, 1);
			p.addPoint(width-1, 1);
			p.addPoint(width-1, height-1);
			p.addPoint(1, height-1);
			return p;
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
		if((float)(t/max_t) > 1) System.out.println("MAX_T: "+max_t +" and division "+(t/max_t));
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
    	case 0: return Color.white;
    	case 1: return Color.magenta;
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
	
	public static int creatures2int(Creatures c){
		if(c.equals(Creatures.BACKGROUND)) return 0;
		if(c.equals(Creatures.VIRUS)) return 1;
		if(c.equals(Creatures.CELL)) return 2;
		if(c.equals(Creatures.BACTERIA)) return 3;
		if(c.equals(Creatures.RABBIT)) return 4;
		return 0;
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
	
	public static String event2message(Event e){
				
		if(e.equals(Event.EARTHQUAKE)) return "An earthquake makes the ground CRUMBLE";
		if(e.equals(Event.METEOR)) return "A METEORITE HAS LANDED DESTROYING AND CONSUMING EVERYTHING!!";
		if(e.equals(Event.HEATWAVE)) return "Damn son it is getting hot in here huh [HEATWAVE]";
		if(e.equals(Event.COLDWAVE)) return "Everything started to freeze...[COLDWAVE]";
		return "Nothing but a Gthang";
	}
	
}
