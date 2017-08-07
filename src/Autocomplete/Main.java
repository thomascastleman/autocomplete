package Autocomplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;




public class Main{
	public static void main(String[] args) {
		Tree wordTree = new Tree();
		Tree charTree = new Tree();
		loadData("beeMovie.txt", wordTree);
		System.out.print(wordTree.trainingData);
		
	}
	public static void save(){
		
	}
	
	public static ArrayList<String> loadData(String file, Tree tree){
		String line = null;
		ArrayList<String> d = new ArrayList<String>();
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader =  new BufferedReader(fileReader);

			while((line = bufferedReader.readLine()) != null) {
				String[] s = line.split(" ");
				
				for (int i = 0; i<s.length;i++){
					d.add(s[i]);
				}
			}
				bufferedReader.close();
				tree.trainingData = d;
				return d;
			}
		
		catch(FileNotFoundException ex) {
			System.out.println(
					"Unable to open file '" + 
							file + "'");
		}
		catch(IOException ex) {
			System.out.println(
					"Error reading file '" 
							+ file + "'");
		
		}
		return d;
	}
}
	

