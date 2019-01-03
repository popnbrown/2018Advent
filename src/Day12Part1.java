import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Day12Part1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		String initial = br.readLine();
		initial = initial.substring(initial.indexOf(":")+2, initial.length());
		initial = "...." + initial + ".....";
		char[] states = initial.toCharArray();
		
		Pot root = new Pot(-5,'.',null);
		Pot curr = root;
		Pot previous = root;
		Pot lastPot = root;
		
		for(int i=0;i<states.length;i++) {
			previous = curr;
			curr = new Pot(i-4,states[i],previous);
			previous.after = curr;
			lastPot = curr;
		}		
		ArrayList<String> patterns = new ArrayList<String>();
		ArrayList<String> pattern_result = new ArrayList<String>();
		while ((initial = br.readLine()) != null) 
			if(initial.length() > 0) {
				patterns.add(initial.substring(0,initial.indexOf("=")-1));
				pattern_result.add(initial.substring(initial.indexOf(">")+2,initial.length()));
			}
		//read every single line
		//parse save in pattern array
		//save in pattern result arraylist
		String currentFive = "";
		String currentGen = "";
		boolean updated = false;
		
		long generation = 0;
		int outter = 0;
		while(outter < 100) {
			generation = 0;
			while(generation < 500000000) {
				root = addFrontPots(root);
				lastPot = addBackPots(lastPot);
				//check if needMorePots at the end
				
				currentGen = "..";
				curr = root.after.after;
				while(curr.canBeCenter()) {
					updated = false;
					currentFive = curr.getFive();
					for(int i=0;i<patterns.size() && !updated;i++)
						if(currentFive.equals(patterns.get(i))) {
							currentGen += pattern_result.get(i);
							updated = true;
						}
					if(!updated)
						currentGen += curr.toString();
					curr = curr.after;
				}
				
				//update the whole generation
				states = currentGen.toCharArray();
				curr = root;
				for(int i=0;i<states.length;i++) {
					curr.updatePot(states[i]);
					curr = curr.after;
				}
				
				generation++;
			}
			outter++;
		}
		
		int sum = 0;
		curr = root;
		while(curr != null) {
			if(curr.pot)
				sum += curr.id;
			curr = curr.after;
		}
		System.out.println(sum);
		//add up all pots that have plant
	}
	
	public static String displayPots(Pot pot) {
		if(pot.after == null)
			return pot.toString();
		return pot.toString() + displayPots(pot.after);
	}
	
	//if any of the first 5 pots are potted, then add as many pots before as pots
	public static Pot addFrontPots(Pot root) {
		boolean noPlant = false;		
		Pot curr = root;
		
		for(int i=0;i<5 && !noPlant;i++) {
			noPlant = curr.pot;
			curr = curr.after;
		}
		if(!noPlant)//no plants found
			return root;
		curr = curr.before;//get the pot with the plant
		Pot next = root;
		Pot newroot = null;
		int end = curr.id-6;
		
		//look for the first plant
		//5-root.id-plant is how many pots we need to make
		//make empty pots
		for(int i=root.id-1;i>end;i--) {
			if(i == end + 1) {
				newroot = new Pot(i,'.',null);
				next.before = newroot;
				newroot.after = next;
			} else {
				curr = new Pot(i,'.',null);
				next.before = curr;
				curr.after = next;
				next = curr;
			}							
		}	
		return newroot;
	}
	
	public static Pot addBackPots(Pot lastPot) {
		boolean noPlant = false;		
		Pot curr = lastPot;
		
		for(int i=0;i<5 && !noPlant;i++) {
			noPlant = curr.pot;
			curr = curr.before;
		}
		if(!noPlant)//no plants found
			return lastPot;
		
		curr = curr.after;//get the pot with the plant
		Pot previous = lastPot;
		Pot newLast = null;
		int end = curr.id+6;
		
		//look for the first plant
		//5-root.id-plant is how many pots we need to make
		//make empty pots
		for(int i=lastPot.id+1;i<end;i++) {
			if(i == end - 1) {				
				newLast = new Pot(i,'.',previous);
				previous.after = newLast;
			} else {
				curr = new Pot(i,'.',previous);
				previous.after = curr;
				previous = curr;
			}							
		}	
		return newLast;
	}
	
	
	static class Pot{
		public boolean pot;
		public int id;
		public Pot before;
		public Pot after;
		
		public Pot(int id, char pot, Pot before) {
			this.pot = (pot == '#');
			this.id = id;
			this.before = before;			
		}
		
		public void updatePot(char plant) {
			this.pot = (plant == '#');
		}
		
		public String toString() {
			if(pot) return "#";
			return ".";
		}
		
		public String getFive() {
			return "" + this.before.before + this.before + this + this.after + this.after.after;
		}
		
		public boolean canBeCenter() {
			if(this.after == null)
				return false;
			if(this.after.after == null)
				return false;
			return true;
		}
	}

	
}
