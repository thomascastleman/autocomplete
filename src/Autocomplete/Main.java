package Autocomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main {

	public int ngram = 3;													// length of ngram
	public int numCompletions = 3;											// number of completions returned by findCompletion()
	public enum TreeType{WORDTREE,CHARTREE};								// enum to determine tree type

	public static String rawTrainingData;															// training data as single string
	public static Dictionary dictionary = new Dictionary("dictionary.txt"); 		// dictionary, organized by word length and alphabetized

	// input and file io stuff
	public Scanner input = new Scanner(System.in);
	public String file = "null";
	public static Tree wordTree = new Tree(TreeType.WORDTREE);
	public static Tree charTree = new Tree(TreeType.CHARTREE);

	//FileReader fileReader;
	//BufferedReader bufferedReader =  new BufferedReader(fileReader);
// for (int i = 0; i < 50; i++) {
//		 	System.out.print("\n\nClause " + i + ": [");
//		 	for (int w = 0; w < test.get(i).size(); w++) {
//		 		System.out.print("\"" + test.get(i).get(w) + "\" ");
//		 	}
//		 	System.out.print("]");
//		 }
	public static void main(String[] args) {

		rawTrainingData = readInRawData("beeMovie.txt");
		
		ArrayList<ArrayList<String>> test = wordTree.formatData(rawTrainingData);
		
		// CHARTREE FORMAT DEBUGGING:
//		for (int i = 0; i < 50; i++) {
//			System.out.println(test.get(i).get(0));
//		}

		// WORDTREE FORMAT DEBUGGING:
//		for (int i = 0; i < 100; i++) {
//		 	System.out.print("\nClause " + i + ": [");
//		 	for (int w = 0; w < test.get(i).size(); w++) {
//		 		System.out.print("\"" + test.get(i).get(w) + "\" ");
//		 	}
//		 	System.out.print("]");
//		 }
		
		
		// Database.constructTree(TreeType.CHARTREE);
		// System.out.println(charTree.origin.content);

//		System.out.println(charTree.origin.children.get(0).content);
//		System.out.println(charTree.origin.children.get(0).children.get(0).content);
//		System.out.println(charTree.origin.children.get(1).content);
//		System.out.println(charTree.origin.children.get(2).content);
//		
//		
//		Database.constructTree(TreeType.WORDTREE);
//		System.out.println(wordTree.origin.content);
//		System.out.println(wordTree.origin.children.get(0).content);
//		System.out.println(wordTree.origin.children.get(1).content);
//		System.out.println(wordTree.origin.children.get(0).children.get(0).content);
//		System.out.println(wordTree.origin.children.get(1).children.get(0).content);
	
		
		

	}

	// public static String findCompletion(String[] error) {

	// }

	// // SET THEORY OPERATIONS: 
	// public ArrayList<Node> intersect(ArrayList<Node> a, ArrayList<Node> b){
		
	// }
	
	// public ArrayList<Node> relativeComplement(ArrayList<Node> a, ArrayList<Node> b){
		
	// }
	
	// public ArrayList<Node> onion(ArrayList<Node> a, ArrayList<Node> b){
	// 	//union
	// }

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