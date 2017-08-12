package Autocomplete;

import java.util.*;

public class Node extends Tree {
	
//	public Node parent = new Node();
	public ArrayList<Node> children = new ArrayList<Node>();
	public int probability;
	protected String content;
	public boolean isWord;

	
	public Node(String s) {
		
	}
}