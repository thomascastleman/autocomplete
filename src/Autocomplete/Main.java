package Autocomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Main{

	public int ngram = 3;						// length of ngram
	public int numCompletions = 3;				// number of completions returned by findCompletion()
	public enum TreeType{WORDTREE,CHARTREE};	// enum to determine tree type

	String rawTrainingData;						// training data as single string
	ArrayList<ArrayList<String>> dictionary; 				// dictionary, organized by word length and alphabetized

	// input and file io stuff
	public Scanner input = new Scanner(System.in);
	String file = "null";
	public static Tree wordTree = new Tree(TreeType.WORDTREE);
	public static Tree charTree = new Tree(TreeType.CHARTREE);

	//FileReader fileReader;
	//BufferedReader bufferedReader =  new BufferedReader(fileReader);

	// for alphabetization of dictionary
	public ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

	public static void main(String[] args) {
		// dictionary = readInDictionary("dictionary.txt");
		// rawTrainingData = readInRawData("beeMovie.txt");
		Database.constructTree(TreeType.CHARTREE);
		System.out.println(charTree.origin.content);

		System.out.println(charTree.origin.children.get(0).content);
		System.out.println(charTree.origin.children.get(0).children.get(0).content);
		System.out.println(charTree.origin.children.get(1).content);
		System.out.println(charTree.origin.children.get(2).content);
		
		
		Database.constructTree(TreeType.WORDTREE);
		System.out.println(wordTree.origin.content);
		System.out.println(wordTree.origin.children.get(0).content);
		System.out.println(wordTree.origin.children.get(1).content);
		System.out.println(wordTree.origin.children.get(0).children.get(0).content);
		System.out.println(wordTree.origin.children.get(1).children.get(0).content);
		
		
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

	// // read in training data as single string
	// public static String readInRawData(String file) {
	// 	String line = null;
	// 	String total = "";

	// 	try {
	// 		FileReader fileReader = new FileReader(file);
	// 		BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
	// 		while ((line = bufferedReader.readLine()) != null) { 
	// 			total = total + " " + line;
	// 		}

	// 		total = total.replaceAll("\\s\\s", " ");
	// 		total = total.replaceAll("\\s\\s\\s", " "); //you probably know some cool regEx to do this faster

	// 		bufferedReader.close();
	// 	}
		
	// 	catch(FileNotFoundException ex) {
	// 		System.out.println("Unable to open file '" + file + "'");
	// 	}
	// 	catch(IOException ex) {
	// 		System.out.println("Error reading file '" + file + "'");
	// 	}
	// }

	public ArrayList<ArrayList<String>> readInDictionary(String file) {
		String line = null;
		ArrayList<String> words = new ArrayList<String>();
		ArrayList<ArrayList<String>> d = new ArrayList<ArrayList<String>>();

		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			while ((line = bufferedReader.readLine()) != null) { 
				words.add(line);
			}

			bufferedReader.close();

			// get max word length in dictionary
			int max = words.get(0).length();
			for (int i = 0; i < words.size(); i++) {
				if (words.get(i).length() > max) {
					max = words.get(i).length();
				}
			}

			// initialize as empty arraylists
			for (int i = 0; i <= max; i++) {
				d.add(new ArrayList<String>());
			}

			// add words to d by length
			for (int i = 0; i < words.size(); i++) {
				d.get(words.get(i).length()).add(words.get(i));
			}

			// alphabetize each arraylist
			for (int i = 0; i < d.size(); i++) {
				d.get(i).clear();
				d.get(i).addAll(alphabetize(d.get(i)));
			}
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + file + "'");
		}

		return d;
	}

	// alphabetize an arraylist of strings
	public ArrayList<String> alphabetize(ArrayList<String> a) {
		ArrayList<String> ordered = new ArrayList<String>();

		while (a.size() > 0) {
			String first = a.get(0);
			for (int i = 1; i < a.size(); i++) {
				if (!alpha(first, a.get(i))) {
					first = a.get(i);
				}
			}

			a.remove(a.indexOf(first));
			ordered.add(first);
		}

		return ordered;
	}

	// true if s1 before s2, false otherwise
	public boolean alpha(String s1, String s2) {
		int i = 0;

		// find while characters are same from both words
		while (alphabet.indexOf(s1.charAt(i)) == alphabet.indexOf(s2.charAt(i))) {
			i++;

			// if s1 shorter than s2
			if (i >= s1.length()) {
				return true;
			// s2 shorter than s1
			} else if (i >= s2.length()) {
				return false;
			}
		}

		// check which of different chars comes first
		if (alphabet.indexOf(s1.charAt(i)) < alphabet.indexOf(s2.charAt(i))) {
			return true;
		} else {
			return false;
		}
	}
}