package Autocomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;



public class Main{
	public int n = 3;

	// all possible completions found from searching word and char trees
	public ArrayList<Node> completionsFromWordSearch = new ArrayList<Node>();
	public ArrayList<Node> completionsFromCharSearch = new ArrayList<Node>();
	public Scanner input = new Scanner(System.in);
	public enum TreeType{WORDTREE,CHARTREE};
	String trainingData;
	String file = "null";
	FileReader fileReader;
	BufferedReader bufferedReader =  new BufferedReader(fileReader);


	public static void main(String[] args) {
		
		Tree wordTree = new Tree(TreeType.WORDTREE);
		Tree charTree = new Tree(TreeType.CHARTREE);
		
		//wordTree.construct(false);
		
		//System.out.print(wordTree.trainingData);
		
	}

//	public static String findCompletion(String[] error) {
//
//	}

	public static void save() {
		
	}
	
	public static ArrayList<String> loadData(String file){
		String line = null;
		String total = "";
		
		ArrayList<String> d = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);
			
			while((line = bufferedReader.readLine()) != null) { 
				total = total+" "+line;
			}
				
				total = total.replaceAll("\\s\\s", " ");
				total = total.replaceAll("\\s\\s\\s", " "); //you probably know some cool regEx to do this faster
				String[] sentences = total.split(".");
				System.out.println(total);
				bufferedReader.close();
				return d;
			}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + file + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + file + "'");
		
		}
		return d;
	}
	
	
}
	

