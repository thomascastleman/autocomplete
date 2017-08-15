
package Autocomplete;

import java.util.*;
import java.io.*;

public class Tree extends Main {
	Node origin = null;
	public static int treeIncrement;
	TreeType type;
	public Tree(){
		
	}
	public Tree(TreeType t){
		this.type = t;
		origin = new Node(null);	// initialize origin as null

		/*

		check for existing files of same TreeType

		if (existing)
			construct from file
		else
			format rawTrainingData
			construct from training

		*/
	}
	
	public void constructTreeFromDatabase(){
		Database.constructTree(this.type);
	}

	// search tree for s
	public ArrayList<Node> search(ArrayList<String> s) {
		Node current = this.origin;
		boolean lowestNodeFound = false;
		
		// for string in array
		for (int str = 0; str < s.size(); str++) {
			
			
			for (int ch = 0; ch <= current.children.size(); ch++) {
				// if all children search and no match
				if (ch == current.children.size()) {
					// if wordtree, return empty array
					if (this.type == TreeType.WORDTREE) {
						return new ArrayList<Node>();
						
					// if chartree, found lowest node
					} else if (this.type == TreeType.CHARTREE) {
						lowestNodeFound = true;
						break;
					}
				}
				
				Node child = current.children.get(ch);
				// if child matches string
				if (child.content.equals(s.get(str))) {
					
					// DEBUG
					System.out.println("Found " + s.get(str) + " in tree");
					
					// move forward to child
					current = child;
					break;
				}
			}
			
			if (lowestNodeFound) {
				break;
			}
		}
		
		System.out.println("Lowest node \"" + current.content + "\" found");
		
		// if wordtree, return immediate children
		if (this.type == TreeType.WORDTREE) {
			return current.children;
			
		// chartree requires additional searching to find possible complete word completions of current node
		} else if (this.type == TreeType.CHARTREE) {
			ArrayList<Node> isWordNodes = this.getAllCompletedChildren(current);
			ArrayList<Node> completedNodes = new ArrayList<Node>();
			
			for (int n = 0; n < isWordNodes.size(); n++) {
				// update node content to reflect whole word
				
				Node node = new Node(this.getWholeWord(isWordNodes.get(n)));
				node.probability = isWordNodes.get(n).probability;
				completedNodes.add(node);
				
				
			}
			
			return completedNodes;
		}
		
		return null;
	}
	
	// non-recursive DFS to find all completed-word children
	public ArrayList<Node> getAllCompletedChildren(Node n) {
		// array of completed children
		ArrayList<Node> completed = new ArrayList<Node>();
		
		// queue for dfs
		ArrayList<Node> q = new ArrayList<Node>();
		
		q.add(n);
		Node v;
		
		// while queue size > 0
		while (q.size() > 0) {
			// pop from queue
			v = q.remove(q.size() - 1);
			
			// add to word nodes if word
			if (v.isWord) {
				completed.add(v);
			}
			
			// for all children
			for (int ch = 0; ch < v.children.size(); ch++) {
				// add to queue
				q.add(v.children.get(ch));
			}
		}
		
		return completed;
		
		
	}
	
	public String getWholeWord(Node charNode) {
		String reverse = "";
		int prob = charNode.probability;		// keep track of isWord node's probability
		
		// while origin not yet reached
		while (charNode.content != null) {
			// move back up tree and add content in reverse to string
			reverse += charNode.content;
			charNode = charNode.parent;
		}
		
		// reverse string
		String word = "";
		for (int l = reverse.length() - 1; l >= 0; l--) {
			word += reverse.charAt(l);
		}
		
		return word;
	}

	// format raw training data into 2 dimensional string array (words for chartree, clauses and words for wordtree)
	public ArrayList<ArrayList<String>> formatData(String trainingData) {

		ArrayList<ArrayList<String>> formatted = new ArrayList<ArrayList<String>>();

		if (this.type == TreeType.CHARTREE) {
			String[] temp = trainingData.split(" ");

			for (int i = 0; i < temp.length; i++) {
				formatted.add(new ArrayList<String>());
			}

			for (int i = 0; i < temp.length; i++) {
				formatted.get(i).add(temp[i].replaceAll("\\W", ""));
			}
			
			// remove excess empty string
			if (formatted.size() > 0) {
				formatted.remove(0);
			}

			return formatted;

		} else if (this.type == TreeType.WORDTREE) {
			
			// NEED TO CLEAN DATA MORE !!
			
			String[] clauses = trainingData.split("\\.|\\,|\\?|\\!");

			for (int c = 0; c < clauses.length; c++) {
				
				clauses[c] = (clauses[c].replaceAll("\\s{2,}", " ")).replaceAll("[^\\w\\s\\']", "");
				
				ArrayList<String> words = new ArrayList<String>(Arrays.asList(clauses[c].split(" ")));
				
				if (words.size() != 0) {
					words.remove(0);
				}
				
				if (words.size() != 0) {
					formatted.add(new ArrayList<String>());
					formatted.get(formatted.size() - 1).addAll(words);
				}
			}
		}
		
		return formatted;
	}

	// train tree from formatted training data
	public void train(ArrayList<ArrayList<String>> formattedTraining) {
		
		if (this.type == TreeType.CHARTREE) {
			for (int i = 0; i < formattedTraining.size(); i++) {
				// get word split by its characters
				ArrayList<String> characters = new ArrayList<String>(Arrays.asList(formattedTraining.get(i).get(0).split("")));
				
				// add section to tree
				this.addSection(characters);
			}
		} else if (this.type == TreeType.WORDTREE) {
			// for every clause
			for (int cl = 0; cl < formattedTraining.size(); cl++) {
				ArrayList<String> clause = formattedTraining.get(cl);
				
				// for every ngram
				for (int ng = 0; ng < clause.size() - 1; ng++) {
					
					// get ngram phrase
					ArrayList<String> ngram;
					
					// if no super.ngrams left in clause, use smaller ngrams
					if (ng > clause.size() - super.ngram) {
						ngram = new ArrayList<String>(clause.subList(ng, ng + (clause.size() - ng)));
					} else {
						ngram = new ArrayList<String>(clause.subList(ng, ng + super.ngram));
					}
					
					// add ngram to tree
					this.addSection(ngram);
				}
			}
		}
	}

	// add a String[] section to the tree, updating what already exists and creating a new branch when necessary
	public void addSection(ArrayList<String> section) {
		
		// pointer to origin
		Node n = this.origin;
		// whether or not the lowest existing node that matches string content addition has been found
		boolean lowestExistingFound = false;
		
		for (int str = 0; str < section.size(); str++) {
			String s = section.get(str);
			
			if (!lowestExistingFound) {
				// for every child of n + extra index
				for (int ch = 0; ch <= n.children.size(); ch++) {
					// if all children searched and no matches found
					if (ch == n.children.size()) {
						lowestExistingFound = true;
						str--;							// decrement index so as not to skip over a string
						break;
					}
					
					Node child = n.children.get(ch);
					
					// if match found
					if (child.content.equals(s)) {
						n = child;			// move to that child
						n.probability++;	// update probability
						break;
					}
				}
			} else {				

				// start initializing new nodes to add to tree
				Node newBranch = new Node(s);
				
				// update parent / children info
				newBranch.parent = n;
				n.children.add(newBranch);
				
				// if in chartree and at end of string, node is word
				if (this.type == TreeType.CHARTREE && str == section.size() - 1) {
					newBranch.isWord = true;
				}
				
				// move to newBranch
				n = newBranch;
			}
		
		}
	}
}




























