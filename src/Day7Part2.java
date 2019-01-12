import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Day7Part2 {

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
		
		
		//have 5 worker ints, one counter int
		//go till prereqlesslist is empty
		//if any of the worker ints = 0 && there are nodes to process
		//remove that step from prereqless and add its children, and sort prereqlesslist
		//start that worker int with new step
		//subtract one from all worker ints, increase counter int
		int workerA, workerB, workerC, workerD, workerE, counter;
		workerA = workerB = workerC = workerD = workerE = 0;
		counter = -1;
		StepNode stepA, stepB, stepC, stepD, stepE;
		stepA = stepB = stepC = stepD = stepE = null;
		
		System.out.println("Counter WorkerA WorkerB WorkerC WorkerD WorkerE");
		while(prereqless.size() > 0 || workerA >= 0 || workerB >= 0 || workerC >= 0 || workerD >= 0 || workerE >= 0) {
			System.out.println(counter + "    " + stepA + "     " + stepB + "     " + stepC + "     " + stepD + "     " + stepE);
			if(workerA <= 0) {
				stepA = removeStep(stepA);
				if(prereqless.size() > 0) {
					stepA = prereqless.get(0);
					prereqless.remove(stepA);
					workerA = stepA.label - 4;		
				}
			}
			if(workerB <= 0) {
				stepB = removeStep(stepB);
				if(prereqless.size() > 0) {
					stepB = prereqless.get(0);
					prereqless.remove(stepB);
					workerB = stepB.label - 4;		
				}
			}
			if(workerC <= 0) {
				stepC = removeStep(stepC);
				if(prereqless.size() > 0) {
					stepC = prereqless.get(0);
					prereqless.remove(stepC);
					workerC = stepC.label - 4;		
				}
			}
			if(workerD <= 0) {
				stepD = removeStep(stepD);
				if(prereqless.size() > 0) {
					stepD = prereqless.get(0);
					prereqless.remove(stepD);
					workerD = stepD.label - 4;		
				}
			}
			if(workerE <= 0) {
				stepE = removeStep(stepE);
				if(prereqless.size() > 0) {
					stepE = prereqless.get(0);
					prereqless.remove(stepE);
					workerE = stepE.label - 4;		
				}
			}
			
			
			workerA--;
			workerB--;
			workerC--;
			workerD--;
			workerE--;
			counter++;
				
		}
		System.out.println(counter);
	}
	
	public static StepNode removeStep(StepNode step) {
		if(step != null) {
			String str = step.display();
			prereqless.addAll(step.prereqlessChildren());
			Collections.sort(prereqless);
		}
		return null;
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


