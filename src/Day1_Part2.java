import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day1_Part2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Day1Part1.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		ArrayList<Integer> sums = new ArrayList<Integer>();
		ArrayList<Integer> inputs = new ArrayList<Integer>();
		
		String st;
		while ((st = br.readLine()) != null) 
			inputs.add(new Integer(st));
		
		boolean sumFound = false;
		int i = -1;
		int currSum = 0;
		sums.add(new Integer(currSum));
		
		while(!sumFound) {

			i = i==inputs.size()-1 ? 0 : i + 1;
			currSum += inputs.get(i);	
			
			for(int j=0;j<sums.size();j++)
				if(currSum == sums.get(j).intValue())
					sumFound = true;

			sums.add(new Integer(currSum));
		}
		
		System.out.println(currSum);	
		
	}

}
