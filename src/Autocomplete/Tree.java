package Autocomplete;

import java.util.*;



public class Tree extends Main{
	Node origin = null;
	public ArrayList<ArrayList<Node>> layers = new ArrayList<ArrayList<Node>>();
	public ArrayList<String> trainingData = new ArrayList<String>();
	TreeType type;
	
	public Tree(TreeType t){
		this.type = t;
		
	}
	public Tree(){
		
		
	}
/*
	// search character tree for possible completions of a given string
	public void charSearch(String s){
	
		Node n = origin;
		boolean lowestNodeFound = false;

		// for every character in string
		for (int c = 0; c < s.length(); c++) {
			// for every child of corresponding node for current character
			for (int child = 0; child <= n.children.size(); child++) {

				// if no matching children found, break
				if (child == n.children.size()) {
					lowestNodeFound = true;
					break;

				// if child found, move to that node	
//				} else if (((CharNode) n.children.get(child)).equals(s.charAt(c))) {	
//					n = n.children.get(child);
//					//break;
//				}
			}

			if (lowestNodeFound) {
				break;
			}
		}

*/
	// search for all completed children of lowest found node
		//searchCompletedChildren(n);
	

	// depth first search from given node to find every completed-word child beneath it
	public void searchCompletedChildren(Node n) {
		for (int i = 0; i < n.children.size(); i++) {
//			if (((CharNode) n.children.get(i)).isWord) {
//				super.completionsFromCharSearch.add(n.children.get(i));
//			}
			searchCompletedChildren(n.children.get(i));
		}
	}

	// search word tree for possible completions of a given ngram
	/*
	public ArrayList<Node> wordSearch(String[] ngram){
		Node n = origin;
		boolean lowestNodeFound = false;

		// for every word in ngram
		for (int word = 0; word < ngram.length; word++) {
			// for every child of corresponding node for current character
			for (int child = 0; child <= n.children.size(); child++) {

				// if no matching children found, break
				if (child == n.children.size()) {
					lowestNodeFound = true;
					break;

				// if child found, move to that node	
				} else if (((WordNode) n.children.get(child)).equals(ngram[child])) {						
					n = n.children.get(child);
					break;
				}
			}

			if (lowestNodeFound) {
				break;
			}
		}

		// return all children of ngram
		return n.children;
	}
*/

	//@SuppressWarnings("static-access")
	public void construct(Boolean isChar){
		// check if tree files exist
		// if yes, reconstruct from those
		// else:

		// character tree
		if (isChar){
		
		// word tree
		} else {
			for (int word = 0; word < trainingData.size() - super.n; word++){
				for(int gram = 0; gram < super.n; gram++){
					System.out.print(trainingData.get(word + gram) + "-");
				}
				System.out.println("");
			}
		}
	}

	// write entire tree structure to txt file for later reconstruction
	public void writeToFile() {
		// open file here

		this.dfs(this.origin);

		// close file
	}

	public void dfs(Node n) {
		
		for (int i = 0; i < n.children.size(); i++) {
			
			// Write (n.children.get(i).content + " " + n.children.get(i).probability + "\n") to file
			
			this.dfs(n.children.get(i));
			
			// Write "u" to file
		}
	}
}

