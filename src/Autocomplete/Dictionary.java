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
				this.content.get(i).clear();
				this.content.get(i).addAll(alphabetize(this.content.get(i)));
			}
		}
		
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file '" + filename + "'");
		}
		catch(IOException ex) {
			System.out.println("Error reading file '" + filename + "'");
		}
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