package Autocomplete;

import java.util.*;

public class Node extends Tree {
	
	public int id;
	protected String content;
	public int probability;
	public Node parent;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isWord;

	public Node(String content_) {
		this.content = content_;
	}

	public Node(String content_, int id_, int probability_, boolean isWord_) {
		this.content = content_;
		this.id = id_;
		this.probability = probability_;
		this.isWord = isWord_;
	}
}