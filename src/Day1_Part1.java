import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Day1_Part1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("Day1Part1.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String st;
		int sum = 0;
		while ((st = br.readLine()) != null) 
			sum += Integer.parseInt(st);
		System.out.println(sum);
	}

}
