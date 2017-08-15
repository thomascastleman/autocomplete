package Autocomplete;

import java.io.*;
import java.util.*;

public class Dictionary extends Main {

	// for alphabetization of dictionary
	public ArrayList<Character> alphabet = new ArrayList<Character>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'));

	// actual dictionary this.content
	public ArrayList<ArrayList<String>> content = new ArrayList<ArrayList<String>>();

	public Dictionary(String filename) {

		String line = null;
		ArrayList<String> words = new ArrayList<String>();

		try {
			FileReader fileReader = new FileReader(filename);
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
				this.content.add(new ArrayList<String>());
			}

			// add words to content by length
			for (int i = 0; i < words.size(); i++) {
				this.content.get(words.get(i).length()).add(words.get(i));
			}

			// alphabetize each arraylist
			for (int i = 0; i < this.content.size(); i++) {
				// if array not empty
				if (this.content.get(i).size() > 0) {
					// alphabetize
					radixAlphabetize(this.content.get(i));
				}
			}
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
	}
	
	// alphabetize an array of strings using radix MSD
	public void radixAlphabetize(ArrayList<String> a) {

		// initialize working as 2d array with 1st dimension size 27
		ArrayList<ArrayList<String>> working = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 27; i++) {
			working.add(new ArrayList<String>());
		}

		// get max word length
		int max = a.get(0).length();
		for (int w = 0; w < a.size(); w++) {
			if (a.get(w).length() > max) {
				max = a.get(w).length();
			}
		}

		// for every index in reverse from end of longest word
		for (int index = max - 1; index >= 0; index--) {

			// for every word in array
			for (int w = 0; w < a.size(); w++) {
				String word = a.get(w);

				// if word long enough
				if (index < word.length()) {
					// add to proper bucket
					working.get(alphabet.indexOf(word.charAt(index)) + 1).add(word);
				} else {
					// add to 0th bucket
					working.get(0).add(word);
				}
			}

			// clear array
			a.clear();

			// push in new order to a, and clear working
			for (int i = 0; i < working.size(); i++) {
				a.addAll(working.get(i));
				working.get(i).clear();
			}
		}
	}

	// true if s1 before s2, false otherwise
	public boolean alpha(String s1, String s2) {
		int i = 0;

		// find while characters are same from both words
		while (this.alphabet.indexOf(s1.charAt(i)) == this.alphabet.indexOf(s2.charAt(i))) {
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
		if (this.alphabet.indexOf(s1.charAt(i)) < this.alphabet.indexOf(s2.charAt(i))) {
			return true;
		} else {
			return false;
		}
	}

	// binary search dictionary for string s, return true if found, false otherwise
	public boolean search(String s) {

		
		if (s.length() > this.content.size()) {
			return false;
		}
		// get words of same length
		ArrayList<String> sameLength = this.content.get(s.length());

		int pivot;						// pivot index
		int min = 0;					// inclusive minimum
		int max = sameLength.size();	// exclusive maximum

		while (true) {
			// calculate pivot index
			pivot = (int) Math.floor((max - min) / 2) + min;

			// if string found
			if (s.equals(sameLength.get(pivot))) {
				// System.out.println(s + " == " + sameLength.get(pivot));

				return true;
			} else {
 
				// if s comes before pivot
				if (alpha(s, sameLength.get(pivot))) {
					// System.out.println(s + " < " + sameLength.get(pivot));
					
					max = pivot;

				// if s comes after pivot
				} else {
					// System.out.println(s + " > " + sameLength.get(pivot));

					min = pivot + 1;
				}

				// if s not found
				if (min >= max) {
					return false;
				}
			}
		}
	}
}












