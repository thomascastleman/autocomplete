package Autocomplete;

import java.util.*;
import java.io.*;

public class Tree extends Main {
	Node origin = null;
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
			write tree to file

		*/
	}
	
	public void constructTreeFromDatabase(){
		Database.constructTree(this.type);
	}

	// // search tree for s
	// public ArrayList<Node> search(String[] s) {

	// }

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
				for (int ng = 0; ng < clause.size() - super.ngram; ng++) {
					// get ngram
					ArrayList<String> ngram = new ArrayList<String>(clause.subList(ng, ng + super.ngram));
					
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
			
			if (!lowestExistingFound) {
				// for every child of n + extra index
				for (int ch = 0; ch <= n.children.size(); ch++) {
					// if all children searched and no matches found
					if (ch == n.children.size()) {
						lowestExistingFound = true;
						break;
					}
					
					Node child = n.children.get(ch);
					// if match found
					if (child.content.equals(section.get(str))) {
						n = child;			// move to that child
						n.probability++;	// update probability
						break;
					}
				}
			} else {
				// start initializing new nodes to add to tree
				Node newBranch = new Node(section.get(str));
				
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






















