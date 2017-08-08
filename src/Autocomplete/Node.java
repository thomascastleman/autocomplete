package Autocomplete;

import java.util.*;

public abstract class Node extends Tree {
	
	public ArrayList<ArrayList<Node>> parents = new ArrayList<ArrayList<Node>>();
	public ArrayList<ArrayList<Node>> children = new ArrayList<ArrayList<Node>>();
	public int probability;
	public boolean isChar;
	
	public Node(boolean isChar_) {
		this.isChar = isChar_;
		
	}
	
	public Node() {
		
	}
}

//