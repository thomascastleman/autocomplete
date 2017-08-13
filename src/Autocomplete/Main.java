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
	public static String rawTrainingData;						// training data as single string

	// input and file io stuff
	public Scanner input = new Scanner(System.in);
	String file = "null";
	public static Tree wordTree = null;
	public static Tree charTree = null;

	//FileReader fileReader;
	//BufferedReader bufferedReader =  new BufferedReader(fileReader);


	public static void main(String[] args) {
		//rawTrainingData = readInRawData("beeMovie.txt");
		
		wordTree = new Tree(TreeType.WORDTREE);
		charTree = new Tree(TreeType.CHARTREE);
		
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
//	public static String readInRawData(String file) {
//		String line = null;
//		String total = "";
//
//		try {
//			FileReader fileReader = new FileReader(file);
//			BufferedReader bufferedReader =  new BufferedReader(fileReader);
//			
//			while((line = bufferedReader.readLine()) != null) { 
//				total = total + " " + line;
//			}
//
//			total = total.replaceAll("\\s\\s", " ");
//			total = total.replaceAll("\\s\\s\\s", " "); //you probably know some cool regEx to do this faster
//
//			bufferedReader.close();
//		}
//		
//		catch(FileNotFoundException ex) {
//			System.out.println("Unable to open file '" + file + "'");
//		}
//		catch(IOException ex) {
//			System.out.println("Error reading file '" + file + "'");
//		}
//	}	
}