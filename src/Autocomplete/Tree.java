package Autocomplete;

import java.util.*;

public class Tree extends Main {
	Node origin = null;
	static TreeType type;
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
	
	public static void constructTreeFromDatabase(){
		Database.constructTree(type);
	}

	// // search tree for s
	// public ArrayList<Node> search(String[] s) {

	// }

	// format raw training data into 2 dimensional string array (words for chartree, clauses and words for wordtree)
	public String[][] formatData(String trainingData) {
		if (this.type == TreeType.CHARTREE) {
			String[] temp = trainingData.split(" ");
			String[][] formatted = new String[temp.length][1];

			for (int i = 0; i < temp.length; i++) {
				formatted[i][0] = temp[i].replaceAll("\W", "");
			}
			return formatted;

		} else if (this.type == TreeType.WORDTREE) {
			String[] clauses = trainingData.split("\.|\,");

			String[][] formatted;

			for (int c = 0; c < clauses.length; c++) {
				formatted[c] = clauses[c].split(" ");
			}

			return formatted;
		}
	}

	// train tree from formatted training data
	public void train(String[][] formattedTraining) {

	}

	// add a String[] section to the tree, updating what already exists and creating a new branch when necessary
	public void addSection(String[] section) {

	}
}

