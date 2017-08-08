package Autocomplete;

import java.util.*;


public class Tree extends Main{
	Node origin = null;
	public ArrayList<ArrayList<Node>> layers = new ArrayList<ArrayList<Node>>();
	public ArrayList<String> trainingData = new ArrayList<String>();
	
	public  Tree(){

		
	}

//	public ArrayList<Node> charSearch(String s){
// 
//	}
//	
//	public ArrayList<Node> wordSearch(String s){
//		
//	}
//
//	public ArrayList<Node> intersect(HashMap<Node, Integer> a, HashMap<Node, Integer> b){
//		
//	}
//	
//	public ArrayList<Node> relativeComplement(HashMap<Node, Integer> a, HashMap<Node, Integer> b){
//		
//	}
//	
//	public ArrayList<Node> onion(HashMap<Node, Integer> a, HashMap<Node, Integer> b){
//		//union
//	}
	//@SuppressWarnings("static-access")
	public void construct(Boolean isChar){
		if (isChar){
			
		}
		if (!isChar){
			for (int word = 0; word<trainingData.size() - super.n; word++){
				for(int gram = 0; gram < super.n; gram++){
					System.out.print(trainingData.get(word + gram) + "-");
				}
				System.out.println("");
			}
		}
	}
}

