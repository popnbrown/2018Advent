import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day2Part2 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		ArrayList<String> inputs = new ArrayList<String>();
		String st;
		int sum = 0;
		while ((st = br.readLine()) != null) 
			inputs.add(st);
		
		//length of id = 26
		String firstStr = "";
		String secondStr;
		int difference = 0;
		int differentIndex = 0;
		for(int i=0;i<inputs.size();i++) {
			firstStr = inputs.get(i);
			for(int j=i+1;j<inputs.size();j++) {
				secondStr = inputs.get(j);
				for(int k=0;k<26;k++)
					if(firstStr.charAt(k)!=secondStr.charAt(k)) {
						difference++;
						differentIndex = k;
					}
				
				if(difference == 1) {
					System.out.println(firstStr + " " + secondStr);
					j = i = inputs.size()+1;
				}
				difference = 0;
			}
		}
		
		System.out.println(firstStr.substring(0, differentIndex) + ""+firstStr.substring(differentIndex+1));
		System.out.println(differentIndex);
		
	}

}
