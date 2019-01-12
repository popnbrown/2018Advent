
public class Day11Part1 {

	public static int serialNumber = 9995;
	public static int[][] grid = new int[300][300];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		for(int i=0;i < 300; i++)
			for(int j=0; j < 300; j++)
				grid[i][j] = getPowerLevel(i,j);
		//calculate every grid's value
		
		int maxPower = 0;
		int currPower = 0;
		int maxX = 0;
		int maxY = 0;
		int gridsize = 1;
		for(int i=0;i<297;i++)
			for(int j=0; j<297;j++) 
				for(int k=1;k<=300-i && k <=300-j;k++) {
					currPower = getSquarePower(i,j,k);

					if(maxPower < currPower) {
						maxPower = currPower;
						maxX = i;
						maxY = j;		
						gridsize = k;
					}
			}
		System.out.println(maxX + "," + maxY + "," + gridsize);
		
	
	}
	
	public static int getPowerLevel(int X, int Y) {
		int rackID = X + 10;
		int powerLevel = rackID * Y;
		powerLevel += Day11Part1.serialNumber;
		powerLevel *= rackID;
		powerLevel = powerLevel % 1000 / 100;
		return powerLevel - 5;
	}
	
	public static int getSquarePower(int x, int y, int gridsize) {
		int res = 0;
		for(int i=0;i < gridsize;i++)
			res += getColumnPower(x+i,y,gridsize);
		return res;
	}
	
	public static int getColumnPower(int x, int y, int gridsize) {
		if(gridsize == 1)
			return grid[x][y];
		return grid[x][y] + getColumnPower(x,y+1,gridsize-1);
	}

}
