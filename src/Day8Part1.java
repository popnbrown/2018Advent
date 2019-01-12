import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day8Part1 {
	
	public static char globalLabel = 'A';

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file = new File("input.txt"); 
		  
		Scanner scanner = new Scanner(file); 
		  
		ArrayList<Integer> inputs = new ArrayList<Integer>();
		String str = "";
		while (scanner.hasNext()) {
			inputs.add(scanner.nextInt());
		}
		
		MetadataNode parent, tempParent, currNode, root;
		parent = tempParent = currNode = root = null;
		int index = 0;
		while(index < inputs.size()) {// still integers to read

			System.out.println("parent" + parent);
			if(parent != null && parent.childCount == parent.children.size()) {
				System.out.println("metadata" + parent);
				parent.setMetadata(inputs.subList(index, index + parent.metadataCount));
				
				index += parent.metadataCount;
				tempParent = parent.parent;
				if(tempParent != null)
					tempParent.children.add(parent);
				parent = tempParent;
				
			} else {
				currNode = new MetadataNode(inputs.get(index), inputs.get(index + 1));
				System.out.println(currNode);
				index += 2;
				
				if(root == null) {
					root = currNode;
					parent = currNode;
				} else {
					currNode.parent = parent;
					
					if(currNode.childCount > 0){
						parent = currNode;
					} else {
						currNode.setMetadata(inputs.subList(index, index + currNode.metadataCount));
						index += currNode.metadataCount;
						
						tempParent = MetadataNode.findChildlessParent(currNode);
						parent.children.add(currNode);
						parent = tempParent;
					}
					
				}
			}
		}
		
		System.out.println(MetadataNode.getMetadataTotal(root));
		//if childcount == parent node child size and parent node not null
		// set metadata for parent node	
		// parent node = above whos children size is smaller than childcount
		//else all below
		//create node
		//first number is childcount
		//second number is metadata
		//if root is null, root = new node, parent = new node, skip below and iterate
		//if childcount > 0
		//set temp parent node to this node
		//else set metadata
		//then  set temp parent node above whose children size is smaller than childcount
		//add to parent node
		//node's parent is set to parent node
		//parent node = tempnode
		//start with root, iterate through all children adding metadata
	}
	
	public static class MetadataNode {
		public int[] metadata;
		public ArrayList<MetadataNode> children;
		public MetadataNode parent;
		public int metadataCount;
		public int childCount;
		public int metadataTotal;
		public char label;
		
		public MetadataNode(int countChild, int countMetadata) {
			metadataCount = countMetadata;
			childCount = countChild;
			metadata = new int[metadataCount];
			children = new ArrayList<MetadataNode>();
			parent = null;
			metadataTotal = 0;
			label = globalLabel;
			globalLabel++;
		}
		
		public void setMetadata(List<Integer> metadataList) {
			for(int i=0;i<metadataCount;i++) {
				metadata[i] = metadataList.get(i).intValue();
				metadataTotal += metadata[i];
			}
		}
		
		public static MetadataNode findChildlessParent(MetadataNode node) {
			System.out.println("find" + node);
			if(node == null || node.children.size() < node.childCount)
				return node;
			return findChildlessParent(node.parent);
		}
		
		public static int getMetadataTotal(MetadataNode node) {
			int total = node.metadataTotal;
			for(int i=0;i<node.children.size();i++)
				total += getMetadataTotal(node.children.get(i));
			return total;
		}
		
		@Override
		public String toString() {
			return "Node " + label + " has childCount " + this.childCount + " metadataCount " + this.metadataCount;
		}
		
	}

}
