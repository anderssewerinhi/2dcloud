package com.humaninference.tagcloud.worldofwords;

import java.util.Set;

/**
 * 
 * @author andersprivat
 * 
 */
public interface Configuration {
	
	interface Position {
		double x();
		double y();
		double z();
	}
	
	interface Line {
		int fromWord();
		int toWord();
	}
	
	
	int getWordCount();
	
	Set<Integer> getRelatedWords(int word);

	Position getPosition(final int word); 
	
	int getLineCount();
	
	Line getLine(final int line);
	
	

}
