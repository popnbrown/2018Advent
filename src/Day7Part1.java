import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Day7Part1 {

	public static ArrayList<StepNode> prereqless;
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		File file = new File("input.txt"); 
		  
		BufferedReader br = new BufferedReader(new FileReader(file)); 
		  
		ArrayList<String> inputs = new ArrayList<String>();
		String st;
		while ((st = br.readLine()) != null) 
			inputs.add(st);
		//5 is first, 36 is second charAt

		prereqless = new ArrayList<StepNode>();
		StepNode first = null;
		StepNode second = null;
		for(int i=0;i<inputs.size();i++) {
			System.out.println(inputs.get(i).charAt(5) + "-" + inputs.get(i).charAt(36));
			first = findOrCreateStep(inputs.get(i).charAt(5));
			second = findOrCreateStep(inputs.get(i).charAt(36));
			second.prereqs.add(first);
			first.children.add(second);

			System.out.println(prereqless);
			if(prereqless.contains(second))
				prereqless.remove(second);
			
			if(first.prereqs.size()==0 && !prereqless.contains(first))
				prereqless.add(first);
				
		}
		//find node if it exists
		//create node if it doesn't exist
		//add first node to prereq list for second node
		//add second node to child list for first node
		//if second node is childless add to childless collection else remove from cihldless
		StringBuilder str = new StringBuilder();
		StepNode curr = null;
		while(prereqless.size() > 0) {
			
			Collections.sort(prereqless);
			curr = prereqless.get(0);
			System.out.println(curr + ": " + prereqless);
			str.append(curr.display());
			prereqless.remove(curr);
			prereqless.addAll(curr.prereqlessChildren()); //only add prereqless children
		}
		System.out.println(str);
		
		
		
		//use prereqless collection
		//start with first alpha
		//set displayed to true, remove from displayList
		//add children to displayList
		//sort displayList
	}
	
	public static StepNode findOrCreateStep(char findLabel) {
		StepNode res = null;
		for(int i=0;res==null && i<prereqless.size();i++)
			res = prereqless.get(i).find(findLabel);
		//traverse through all prereqless collection
		//go through all children and find matching
		if(res == null)
			res = new StepNode(findLabel);
		return res;
	}
	
	static class StepNode implements Comparable<StepNode> {
		public char label;
		public ArrayList<StepNode> prereqs;
		public ArrayList<StepNode> children;
		
		public StepNode(char label) {
			this.label = label;
			prereqs = new ArrayList<StepNode>();
			children = new ArrayList<StepNode>();
		}
		
		public StepNode find(char findLabel) {
			if(findLabel == this.label)
				return this;
			
			StepNode res = null;
			for(int i=0;res==null && i<children.size();i++)
				res = children.get(i).find(findLabel);
			return res;
		}
		
		public ArrayList<StepNode> prereqlessChildren() {
			ArrayList<StepNode> prereqlessChildList = new ArrayList<StepNode>();
			for(int i=0;i<children.size();i++)
				if(children.get(i).prereqs.size() == 0)
					prereqlessChildList.add(children.get(i));
			return prereqlessChildList;				
		}
		
		//remove from children's prereqlist
		public String display() {
			for(int i=0;i<children.size();i++)
				children.get(i).prereqs.remove(this);
			return this.toString();
		}
		
		@Override
		public String toString() {
			return this.label + "";
		}
		
		@Override
		public int compareTo(StepNode step) {
			// TODO Auto-generated method stub
			return this.label - step.label;
		}
	}
	
}


