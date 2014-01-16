package com.humaninference.tagcloud.worldofwords;

import java.util.List;

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
		Position fromPosition();
		Position toPosition();
	}
	
	interface Relation {
		
	}
	
	List<Integer> getWords();
	
	List<Integer> getRelatedWords(int word);
	
	List<Relation> getRelations(); 
	
	Position getPosition(final int word); 
	
	Line getLine(final Relation relation);
	
	

}
