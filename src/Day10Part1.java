import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Day10Part1 {

	public static int SECONDS = 10905;
	public static boolean DISPLAY = false;
	public static boolean XYYES = true;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		File file = new File("input.txt"); 
		
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		ArrayList<Point> points = new ArrayList<Point>();
		String st;
		while ((st = br.readLine()) != null) 
			points.add(new Point(st.substring(st.indexOf("="),st.indexOf('>')),st.substring(st.indexOf("velocity=")+9)));

		int filenum = 1;
		
		if(DISPLAY) {
			int lastPointX = 0;
			int lastPointY = 0;
			String rowString = "";
			int leftmost, rightmost;
				
			while(filenum <= SECONDS) {
				//run forever loop
				System.out.println("------------------");
		
				Collections.sort(points, new SortByX());
				leftmost = points.get(0).x;
				rightmost = points.get(points.size()-1).x;
		
				System.out.println("leftmost " + leftmost + "  rightmost " + rightmost );
		
				Collections.sort(points);
				
				for(int i=0;i<points.size();i++) {
					if(i==0)
						lastPointX = leftmost-1;
					if(points.get(i).y > lastPointY) {
						for(int j=lastPointX;j<rightmost;j++)
							rowString += ".";
						System.out.println(rowString);
						rowString = "";
						lastPointX = leftmost-1;
					} 
					
					for(int j=lastPointX+1;j<points.get(i).x;j++)
						rowString += ".";
					rowString += "#";
					
					lastPointX = points.get(i).x;
					lastPointY = points.get(i).y;
					points.get(i).move();
				//if lastpointY change meaning new row, resdt lastpointX, ssysprintln prev row, reset rowstring
				//rowstring += display point where it displays . between lastpointX and now
				//let velocity change xy
				}
				rowString = "";
				filenum++;
				Thread.sleep(1000);
			}
		} else if(XYYES){
			PrintWriter pwx = new PrintWriter("x.txt", "UTF-8");
			for(int i=0;i<points.size();i++)
				pwx.println(points.get(i).x);
			PrintWriter pwy = new PrintWriter("y.txt", "UTF-8");
			for(int i=0;i<points.size();i++)
				pwy.println(points.get(i).y);
			pwx.close();
			pwy.close();
		}else {
			while(filenum <= SECONDS) {
				for(int i=0;i<points.size();i++)
					points.get(i).move();
				filenum++;
			}
			Collections.sort(points);
			Point.printOut(points);
			
		}
			//threadsl eep 500 millisecond
			//end loop
	}
	
	//point class with x y velox veloy
	//class should be compare to with x difference or if they're same y difference
	static class Point implements Comparable<Point> {

		public int x;
		public int y;
		public int velX;
		public int velY;
		
		public Point(String point, String velocity) {
			x = Integer.parseInt(point.substring(2, point.indexOf(",")).trim());
			y = Integer.parseInt(point.substring(point.indexOf(',')+2,point.length()).trim());
			velX = Integer.parseInt(velocity.substring(1, velocity.indexOf(",")).trim());
			velY = Integer.parseInt(velocity.substring(velocity.indexOf(',')+2,velocity.length()-1).trim());
		}
		
		public void move() {
			x += velX;
			y += velY;
		}
		
		@Override
		public String toString() {
			return x + "," + y + "velocity - " + velX + "," + velY;
		}
		
		@Override
		public int compareTo(Point arg0) {
			// TODO Auto-generated method stub
			return this.y - arg0.y != 0 ? this.y - arg0.y : this.x - arg0.x;
		}
		
		public static void printOut(ArrayList<Point> points) throws FileNotFoundException, UnsupportedEncodingException {
			PrintWriter pw = new PrintWriter("output.txt", "UTF-8");
			for(int i=0;i<points.size();i++)
				pw.println("position=<"+ points.get(i).x + ", " + points.get(i).y + "> velocity=<" + points.get(i).velX + ", " + points.get(i).velY + ">");
			pw.close();
		}
		
	}
	
	static class SortByX implements Comparator<Point>{
		@Override
		public int compare(Point arg0, Point arg1) {
			return arg1.x - arg0.x == 0 ? arg0.y - arg1.y : arg0.x - arg1.x;
		}
	}
}
