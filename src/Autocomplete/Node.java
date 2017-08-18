package Autocomplete;

import java.util.*;

public class Node extends Tree {

	public int id;
	public String content;
	public int probability = 1;
	public Node parent; //public nodity 
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isWord;

	public Node(int id, String content, int probability, boolean isWord) {
	
		this.id = id;
		this.probability = probability;
		this.content = content;
		this.isWord = isWord;
	}
	
	public Node(String content) {
		this.content = content;
		this.id = 1;
	}
	

	
	public Node(String content, TreeType t) {
			this.id = getTreeIncrement(t);
			setTreeIncrement(t, this.id+1);
		
		
		
		this.content = content;
	}
}
