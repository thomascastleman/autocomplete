package Autocomplete;

import java.util.*;

public class Node extends Tree {
	
	public ArrayList<Node> parents = new ArrayList<Node>();
	public ArrayList<Node> children = new ArrayList<Node>();
	public int probability;
	protected String content;
	
	public Node() {
		
	}
}