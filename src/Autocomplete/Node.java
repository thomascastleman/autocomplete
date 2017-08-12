package Autocomplete;

import java.util.*;

public class Node extends Tree {
	
//	public Node parent = new Node();
	public int id;
	public ArrayList<Node> children = new ArrayList<Node>();
	public int probability;
	protected String content;
	public boolean isWord;

	
	public Node(int id, int probability, String content, boolean isWord) {
		this.id = id;
		this.probability = probability;
		this.content = content;
		this.isWord = isWord;
	}
}