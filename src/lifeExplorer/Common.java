package lifeExplorer;

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
}
