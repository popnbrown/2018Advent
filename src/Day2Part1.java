import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2Part1 {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		ArrayList<String> inputs = new ArrayList<String>();
		String st;
		int sum = 0;
		while ((st = br.readLine()) != null) 
			inputs.add(st);
		
		int[] alphabet = new int[26];
		char[] currStr;
		int countTwo = 0;
		int countThree = 0;
		boolean hasTwo = false;
		boolean hasThree = false;
		for(int i=0;i<inputs.size();i++)
		{
			hasTwo = hasThree = false;
			currStr = inputs.get(i).toCharArray();
			for(int j=0;j<currStr.length;j++)
				alphabet[inputs.get(i).charAt(j)-97]++;
			
			for(int k=0;k<26;k++) {
				if(alphabet[k]==2)
					hasTwo = true;
				else if(alphabet[k]==3)
					hasThree = true;
				alphabet[k] = 0;				
			}
			countTwo = hasTwo ? countTwo + 1 : countTwo;
			countThree = hasThree ? countThree + 1 : countThree;
			
		}
		
		System.out.println(countTwo*countThree);
			
	}

}
