package Autocomplete;

import java.util.*;

public abstract class Node extends Tree {
	
	public ArrayList<Node> parents = new ArrayList<Node>();
	public ArrayList<Node> children = new ArrayList<Node>();
	public int probability;
	public boolean isChar;
	
	public Node(boolean isChar_) {
		this.isChar = isChar_;
		
	}
}