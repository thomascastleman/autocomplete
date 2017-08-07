package Autocomplete;

import java.util.*;

public class WordNode extends Node {
	protected String content;
	
	public WordNode() {
		super(false);
	}
	
	public void content(String content){
		this.content = content;
	}
	

}
