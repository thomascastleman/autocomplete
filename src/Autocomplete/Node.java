package Autocomplete;

import java.util.*;

public class Node extends Tree {
	
	public int id;
	protected String content;
	public int probability;
	public Node parent;
	public ArrayList<Node> children = new ArrayList<Node>();
	public boolean isWord;


	public Node(int id, String content, int probability, boolean isWord) {
		this.id = id;
		this.probability = probability;
		this.content = content;
		this.isWord = isWord;
}
	public Node(String content_) {
		this.content = content_;
	}

}