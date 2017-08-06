package Autocomplete;

import java.util.*;

public class Node extends Tree{
	public ArrayList<ArrayList<Node>> parents = new ArrayList<ArrayList<Node>>();
	public ArrayList<ArrayList<Node>> children = new ArrayList<ArrayList<Node>>();
	public String content;
	public Boolean isWord;
	
	
}
