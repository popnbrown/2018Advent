import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Day13Part1 {

	public static char[][] input;
	public static ArrayList<Vehicle> vehicles;
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stubFile file = new File("input.txt"); 
		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		String str;	
		int row=0;
		int column=0;
		while ((str = br.readLine()) != null) {
			row++;
			if(str.length() > column)
				column = str.length();
		}
		
		input = new char[row][column];
		char[] temprow;
		vehicles = new ArrayList<Vehicle>();
		
		br.close();
		br = new BufferedReader(new FileReader(file)); 
		int id=0;
		row = 0;
		while((str = br.readLine()) != null) {
			temprow = str.toCharArray();
			for(int i=0;i<temprow.length;i++)
				if(temprow[i] == '<' || temprow[i] == '>') {
					vehicles.add(new Vehicle(row,i,temprow[i],id));
					temprow[i] = '-';		
					id++;
				} else if(temprow[i] == '^' || temprow[i] == 'v') {
					vehicles.add(new Vehicle(row,i,temprow[i],id));
					temprow[i] = '|';
					id++;
				}
			input[row] = str.toCharArray();
			row++;
		}

		//ifvehicle then replace with straight, and create a new vehicle class add to arraylist of vehicles
		
		ArrayList<Vehicle> movedVehicles = new ArrayList<Vehicle>();
		int ticks = 0;
		boolean crash = false;

		
		while(!crash) {
			Collections.sort(vehicles);
			for(int i=0;i<vehicles.size();i++)
				System.out.println(vehicles.get(i));
			movedVehicles.clear();
			
			//sort list of vehicles
			for(int i=0;i<vehicles.size() && !crash;i++) {
				vehicles.get(i).move();
				crash = vehicles.indexOf(vehicles.get(i)) != vehicles.lastIndexOf(vehicles.get(i));
				row = vehicles.get(i).row;
				column = vehicles.get(i).column;
			}
			//movedvehicles.contains(currVehicle) then crash
			ticks++;
		}
		System.out.println("crash at " + row + "," + column);
		
		
	}
	
	//vehicle - current direction, row, column location, last intersection direction
	//turn function that changes based on intersection direction
	//compare sort by row then column
	//override equals to compare row and column
	//move function - based on direction get next cell of input, 
	//if / if < then v if ^ then <  
	// elif \ if > then v if ^ then <
	//if + turn function
	//change row column to the cell
	
	static class Vehicle implements Comparable<Vehicle> {
		public int direction;//0-left,1-up,2-right,3-down
		public int row;
		public int column;
		public int directionChange;//0-left,1-straight,2-right
		public int id;
		
		public Vehicle(int r, int c, char vehicle, int id) {
			this.row = r;
			this.column = c;
			directionChange = 0;
			this.id = id;
			
			switch(vehicle) {
				case '<': direction = 0; break;
				case '^': direction = 1; break;
				case '>': direction = 2; break;
				case 'v': direction = 3;
			}
		}
		
		public void turn() {
			if(directionChange == 0)
				switch(direction) {
					case 0: direction = 3; break;
					case 1: direction = 0; break;
					case 2: direction = 1; break;
					case 3: direction = 2;
				}
			

			if(directionChange == 2)
				switch(direction) {
					case 0: direction = 1; break;
					case 1: direction = 2; break;
					case 2: direction = 3; break;
					case 3: direction = 0;
				}
			
			directionChange++;
			if(directionChange == 3)
				directionChange = 0;
		}
		
		public void move() {
			switch(direction) {
				case 0: this.column--; break;
				case 1: this.row--; break;
				case 2: this.column++; break;
				case 3: this.row++;
			}
			
			char next = input[this.row][this.column];

			//if / if 0 then 2 if 1 then 3  
			// elif \ if > then v if ^ then <
			//if + turn function
			if(next == '+')
				turn();
			else if(next == '/')
				switch(direction) {
					case 0: direction = 3; break;
					case 1: direction = 2; break;
					case 2: direction = 1; break;
					case 3: direction = 0;
				}
			else if(next == '\\')
				switch(direction) {
					case 0: direction = 1; break;
					case 1: direction = 0; break;
					case 2: direction = 3; break;
					case 3: direction = 2;
				}
		}
		
		@Override 
		public boolean equals(Object o) {
			if(o == this)
				return true;
			
			if(!(o instanceof Vehicle)) 
				return false;
			
			Vehicle v = (Vehicle)o;
			
			return v.row == this.row && v.column == this.column;
		}
		
		@Override
		public int compareTo(Vehicle arg0) {
			// TODO Auto-generated method stub
			int rowDiff = this.row - arg0.row;
			return rowDiff == 0 ? this.column - arg0.column : rowDiff;
		}
		
		public String toString() {
			return id + " at " + row + ", " + column + " going in " + direction + " next change " + directionChange;
		}
	}

}
