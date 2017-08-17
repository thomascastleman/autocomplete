package Autocomplete;

import java.io.*;
import java.util.*;

public class Main {
 
	public static int ngram = 4;													// length of highest ngram
	public static int numCompletions = 7;											// number of completions returned by findCompletion()
	public enum TreeType{WORDTREE,CHARTREE};										// enum to determine tree type

	public static String rawTrainingData;											// training data as single string
	public static Dictionary dictionary = new Dictionary("textFiles/dictionary.txt"); 		// dictionary, organized by word length and alphabetized
	
	// TREES
	public static Tree wordTree = new Tree(TreeType.WORDTREE);
	public static Tree charTree = new Tree(TreeType.CHARTREE);

	public static void main(String[] args) {
		rawTrainingData = readInRawData("textFiles/training/beeMovie.txt");
		
		charTree.train(charTree.formatData(rawTrainingData));
		wordTree.train(wordTree.formatData(rawTrainingData));
		
		String[] phi = {"You", "like", "ja"};
		ArrayList<Node> f = findCompletions(phi);
		System.out.println("\nTop " + numCompletions + " Completions: ");
		for (int i = 0; i < f.size(); i++) {
			logNode(f.get(i));
		}
		
		
		

		// DATABASE DEBUGING:
		
		
		//	System.out.println(charTree.origin.children.get(1).content);
//		System.out.println(charTree.type);
//		Database.constructTree(TreeType.CHARTREE);
//		System.out.println(charTree.treeIncrement);
//		
//		System.out.println(wordTree.type);
//		Database.constructTree(TreeType.WORDTREE);
//		System.out.println(wordTree.treeIncrement);
//		System.out.println(wordTree.type);

		//wordTree.treeIncrement++;
		//Database.constructTree(TreeType.WORDTREE);
//		System.out.println(wordTree.treeIncrement);
//		wordTree.train(wordTree.formatData(rawTrainingData));
//		
//		System.out.println(wordTree.origin.id);
		//System.out.println(wordTree.treeIncrement);
//		wordTree.train(wordTree.formatData(rawTrainingData));
		//System.out.println(wordTree.origin.id);
		
		//charTree.train(charTree.formatData(rawTrainingData));
//		wordTree.train(wordTree.formatData(rawTrainingData));
//		System.out.println(charTree.treeIncrement);
//		System.out.println(wordTree.treeIncrement);
//		
//		System.out.println(charTree.treeIncrement);
//		System.out.println(wordTree.treeIncrement);
	//	Database.uploadTreeToDatabase(TreeType.CHARTREE);
		//Database.uploadTreeToDatabase(TreeType.WORDTREE);
		// Database.uploadTreeToDatabase(TreeType.WORDTREE);

		//Database.constructTree(TreeType.WORDTREE);
		//Database.constructTree(TreeType.CHARTREE);
		
		// System.out.println(charTree.origin.children);
		
//		 Database.constructTree(TreeType.CHARTREE);
//		 System.out.println(charTree.origin.content);
//
//		System.out.println(charTree.origin.children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(1).content);
		// Database.constructTree(TreeType.WORDTREE);
		// System.out.println(wordTree.origin.children.get(0).content);
		// Database.uploadTreeToDatabase(TreeType.CHARTREE);
		// Database.uploadTreeToDatabase(TreeType.WORDTREE);

	//	System.out.println(wordTree.origin.content);
		//System.out.println(charTree.origin.children.get(0).children.get(0).content);
	//	System.out.println(charTree.origin.children.get(1).content);
//		System.out.println(charTree.origin.children.get(2).content);
//		
//		.children.get(0)
//		System.out.println(charTree.origin.content);
//		System.out.println(charTree.origin.children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).children.get(0).content);

		
//		System.out.println(wordTree.origin.children.get(0).children.get(0).content);
//		System.out.println(wordTree.origin.children.get(1).children.get(0).content);
		
		

	}
	public static int getTreeIncrement(TreeType t){
		if(t == TreeType.CHARTREE){
			return charTree.treeIncrement;
		}
		if(t == TreeType.WORDTREE){
			return wordTree.treeIncrement;
		}
		return 0;
	}
	
	public static void setTreeIncrement(TreeType t, int i){
		if(t == TreeType.CHARTREE){
			charTree.treeIncrement = i;
		}
		if(t == TreeType.WORDTREE){
			wordTree.treeIncrement = i;
		}
		
	}
	
	// dfs to display all nodes in tree debugging
	public static void dfs(Node n) {
		logNode(n);
		for (int i = 0; i < n.children.size(); i++) {
			dfs(n.children.get(i));
		}
	}
	
	// log all node info to console
	public static void logNode(Node n) {
		System.out.println("\n\n" + n.content + " " + n.probability + " " + n.isWord);
		if (n.parent != null) {
			System.out.print("parent: \"" + n.parent.content + "\" ");
		}
		System.out.print("children: (" + n.children.size() + ")");
		for (int i = 0; i < n.children.size(); i++) {
			System.out.print(" " + n.children.get(i).content);
		}
	}

	public static ArrayList<Node> findCompletions(String[] error) {
		
		// remove capitalization
		for (int i = 0; i < error.length; i++) {
			error[i] = error[i].toLowerCase();
		}
		
		String lastWord = error[error.length - 1];
		
		// if word exists in dictionary
		if (false) { // dictionary.search(lastWord)) {
			// make no completion
			return new ArrayList<Node>();
		} else {
			// error split by characters
			ArrayList<String> formattedForChar = new ArrayList<String>(Arrays.asList(lastWord.split("")));
			
			// last (ngram - 1) words of previous phrase
			ArrayList<String> formattedForWord;
			
			// if > 1 word in input
			if (error.length > 1) {
				ArrayList<String> asArrayList = new ArrayList<String>(Arrays.asList(error));
				
				// if phrase size larger than ngram
				if (asArrayList.size() >= ngram) {
					formattedForWord = new ArrayList<String>(asArrayList.subList(asArrayList.size() - ngram, asArrayList.size()));
				} else {
					// get whole phrase
					formattedForWord = new ArrayList<String>(asArrayList);
				}
				// remove error itself (last word)
				formattedForWord.remove(formattedForWord.size() - 1);
			} else {
				// if no other words in input, use empty array
				formattedForWord = new ArrayList<String>();
			}
			
			// DEBUG
			System.out.println("Word format: ");
			System.out.println(formattedForWord);
			System.out.println("Char format: ");
			System.out.println(formattedForChar);
			System.out.print("\n");
			
			ArrayList<Node> charCompletions = charTree.search(formattedForChar);
			ArrayList<Node> wordCompletions;
			
			if (formattedForWord.size() == 0) {
				
				// sort by probability
				Collections.sort(charCompletions, new ProbabilityComparator());
				
				if (numCompletions < charCompletions.size()) {
					return new ArrayList<Node>(charCompletions.subList(0, numCompletions));
				} else {
					return charCompletions;
				}
			} else {
				wordCompletions = wordTree.search(formattedForWord);
			}
			
			// DEBUG
			System.out.print("\n");
			System.out.println(charCompletions.size() + " character completions found");
			System.out.println(wordCompletions.size() + " word completions found");
			
			ArrayList<Node> intersection = intersect(charCompletions, wordCompletions);
			
			// DEBUG
			System.out.println(intersection.size() + " completions after intersection");
			
			ArrayList<Node> result = onion(intersection, relativeComplement(charCompletions, intersection));
			
			// DEBUG
			System.out.println(result.size() + " completions after union and relative complement");
			
			Collections.sort(result, new ProbabilityComparator());
			
			if (numCompletions < result.size()) {
				return new ArrayList<Node>(result.subList(0, numCompletions));
			} else {
				return result;
			}
		}
	}

	// SET THEORY OPERATIONS: 
	
	public static ArrayList<Node> intersect(ArrayList<Node> a, ArrayList<Node> b){
		// array of all nodes common to both a and b
		ArrayList<Node> both = new ArrayList<Node>();
		
		for (int aNode = 0; aNode < a.size(); aNode++) {
			for (int bNode = 0; bNode < b.size(); bNode++) {
				// if same content
				if (a.get(aNode).content.equals(b.get(bNode).content)) {
					// initialize new node with same content
					Node combined = new Node(a.get(aNode).content);
					
					// multiply probabilities
					combined.probability = a.get(aNode).probability * b.get(bNode).probability;
					
					both.add(combined);
				}
			}
		}
		
		return both;
	}
	
	public static ArrayList<Node> relativeComplement(ArrayList<Node> a, ArrayList<Node> b){
		ArrayList<String> bContent = new ArrayList<String>();
		for (int i = 0; i < b.size(); i++) {
			bContent.add(b.get(i).content);
		}
		
		ArrayList<Node> relComp = new ArrayList<Node>();
		
		for (int n = 0; n < a.size(); n++) {
			if (!bContent.contains(a.get(n).content)) {
				relComp.add(a.get(n));
			}
		}
		
		return relComp;
	}
	
	public static ArrayList<Node> onion(ArrayList<Node> a, ArrayList<Node> b){
		//union
		a.addAll(b);
		return a;
	}

	// read in training data as single string
	public static String readInRawData(String file) {
		String line = null;
		String total = "";

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) { 
				total = total + " " + line;
			}

			total = total.replaceAll("\\s{2,}", " ");

			bufferedReader.close();
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + file + "'");
		}
		
		return total;
	}
}

// used to sort by probability with Collections.sort()
class ProbabilityComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {
		if (n1.probability > n2.probability) {
			return -1;
		} else if (n2.probability > n1.probability) {
			return 1;
		} else {
			return 0;
		}
	}
}






















