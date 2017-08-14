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
			String[] clauses = trainingData.split("\\.|\\,|\\?|\\!");

			for (int c = 0; c < clauses.length; c++) {
				
				clauses[c] = (clauses[c].replaceAll("\\s{2,}", " ")).replaceAll("[^\\w\\s\\']", "");
				System.out.println(clauses[c]);
				
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

	}

	// add a String[] section to the tree, updating what already exists and creating a new branch when necessary
	public void addSection(String[] section) {

	}
}

