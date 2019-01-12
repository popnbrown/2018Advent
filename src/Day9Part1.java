import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Day9Part1 {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String st = br.readLine();

		int marbleCount = Integer.parseInt(st.substring(st.indexOf("worth")+6,st.indexOf("points")-1));
		int currentMarbleID = 1;
		
		int playerCount = Integer.parseInt(st.substring(0, st.indexOf(' ')));		
		long[] playerScores = new long[playerCount];
		int playerIndex = 0;
		
		Marble currMarble = new Marble(0, null, null);
		currMarble.previous = currMarble;
		currMarble.next = currMarble;
		Marble firstNext, secondNext;
		
		while(currentMarbleID <= marbleCount) {
			if(currentMarbleID % 23 == 0) {
				playerScores[playerIndex] += currentMarbleID;
				
				currMarble = currMarble.getXPrevious(7);
				playerScores[playerIndex] += currMarble.id;
				
				currMarble.next.previous = currMarble.previous;
				currMarble.previous.next = currMarble.next;
				
				currMarble = currMarble.next;
			} else {
				firstNext = currMarble.next;
				secondNext = currMarble.next.next;
				currMarble = new Marble(currentMarbleID, firstNext, secondNext);				
			}
			
			currentMarbleID++;
			playerIndex++;
			if(playerIndex >= playerCount)
				playerIndex = 0;
		}
		
		//create an array of playerScores off playercount
		//currmarble = 0,next is currmarble, prev is currmarble
		//new marble
		//playerIndex
		//loop from 1 to marblecount
		//if mod 23 == 0
		//playerScores[playerIndex] += marblecount
		//newmarble = currmarble.getprevx(7)
		//playerScores += newmarble.id
		//newmarble.next.prev = newmarble.prev
		//newmarble.prev.next = newmarble.next
		//currmarble = newmarble
		//else
		//create newmarble
		//off currmarble find 1st next, and 2nd next
		//1st next next is newmarble
		//newmarbles prev is 1st next
		//2nd next prev is newmarble
		//newmarble next is 2nd next
		//curmarble is newmarble
		//end else
		//increment marblecount
		//increment playerIndex
		
		//do a max search through playerscores
		long max = playerScores[0];
		for(int i=1;i<playerScores.length;i++)
			if(playerScores[i]>max)
				max = playerScores[i];
		
		System.out.println(max);
	}
	
	//marble class with prev, next, and marble id
	//static function returns 7 before
	static class Marble {
		public Marble previous;
		public Marble next;
		public int id;
		
		public Marble(int marbleID, Marble p, Marble n) {
			id = marbleID;
			
			previous = p;
			if(p != null)
				p.next = this;
			
			next = n;
			if(n != null)
				n.previous = this;
		}
		
		public Marble getXPrevious(int x) {
			if(x==0)
				return this;
			return previous.getXPrevious(x-1);
		}
	}
}
