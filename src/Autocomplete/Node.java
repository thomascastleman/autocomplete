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

	public Node(int id_, int probability_, String content_, boolean isWord_) {
		this.id = id_;
		this.probability = probability_;
		this.content = content_;
		this.isWord = isWord_;
	}
}