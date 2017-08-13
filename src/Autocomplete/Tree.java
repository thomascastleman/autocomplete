package Autocomplete;

import java.util.*;
import java.io.*;

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
			String[] clauses = trainingData.split("\\.|\\,");

			for (int c = 0; c < clauses.length; c++) {
				formatted.add(new ArrayList<String>());
				formatted.get(c).addAll(Arrays.asList(clauses[c].split(" ")));
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

