package com.humaninference.tagcloud.worldofwords;

import java.util.List;
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
	
	void changePositions(final List<Position> newPositions, final List<Position> newLogoPositions);

	int getWordCount();
	
	Set<Integer> getRelatedWords(int word);

	Position getPosition(final int word);
	
	String getWord(final int word);
	
	int getImageCount();
	
	Position getImagePosition(final int image);

}
