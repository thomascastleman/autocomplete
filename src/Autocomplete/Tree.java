package Autocomplete;

import java.util.*;


public class Tree extends Main{
	Node origin = null;
	public ArrayList<ArrayList<Node>> layers = new ArrayList<ArrayList<Node>>();
	public ArrayList<String> trainingData = new ArrayList<String>();

	// all possible completions found from searching word and char trees
	public ArrayList<Node> completionsFromWordSearch = new ArrayList<Node>();
	public ArrayList<Node> completionsFromCharSearch = new ArrayList<Node>();
	
	public  Tree(){

		
	}

	// search character tree for possible completions of a given string
	public void charSearch(String s){
		Node n = origin;
		boolean lowestNodeFound = false;

		// for every character in string
		for (int c = 0; c < s.length(); c++) {
			// for every child of corresponding node for current character
			for (int child = 0; child <= n.children.size(); child++) {

				// if no matching children found, break
				if (child = n.children.size()) {
					lowestNodeFound = true;
					break;

				// if child found, move to that node	
				} else if (n.children.get(child).content == s.charAt(c)) {	
					n = n.children.get(child);
					break;
				}
			}

			if (lowestNodeFound) {
				break;
			}
		}

		// search for all completed children of lowest found node
		searchCompletedChildren(n);
	}

	// depth first search from given node to find every completed-word child beneath it
	public void searchCompletedChildren(Node n) {
		for (int i = 0; i < n.children.size(); i++) {
			if (((CharNode) n.children.get(i)).isWord) {
				completionsFromCharSearch.add(n.children.get(i));
			}
			searchCompletedChildren(n.children.get(i));
		}
	}

//	
//	public ArrayList<Node> wordSearch(String s){
//		
//	}
//
//	public ArrayList<Node> intersect(ArrayList<Node> a, ArrayList<Node> b){
//		
//	}
//	
//	public ArrayList<Node> relativeComplement(ArrayList<Node> a, ArrayList<Node> b){
//		
//	}
//	
//	public ArrayList<Node> onion(ArrayList<Node> a, ArrayList<Node> b){
//		//union
//	}
	//@SuppressWarnings("static-access")
	public void construct(Boolean isChar){
		if (isChar){
			
		}
		if (!isChar){
			for (int word = 0; word < trainingData.size() - super.n; word++){
				for(int gram = 0; gram < super.n; gram++){
					System.out.print(trainingData.get(word + gram) + "-");
				}
				System.out.println("");
			}
		}
	}
}

