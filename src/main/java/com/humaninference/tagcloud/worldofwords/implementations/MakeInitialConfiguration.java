package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.ConfigurationFactory;

public class MakeInitialConfiguration implements ConfigurationFactory {
	
	public MakeInitialConfiguration(final PositionFactory pf) {
		this.pf = pf;
	}
	
	private final PositionFactory pf;
	
	private int wordCount = 0;
	
	private int logoCount = 0;
	
	private final Map<String, Integer> wordToId = new HashMap<String, Integer>();
	
	private final List<String> idToWord = new LinkedList<String>();
	
	private final Map<Integer, Set<Integer>> connections = new HashMap<Integer, Set<Integer>>();
	
	private List<Configuration.Position> wordIdToPosition= 
			new LinkedList<Configuration.Position>();
	
	private List<Configuration.Position> logoIdToPosition= 
			new LinkedList<Configuration.Position>();
	
	private final Set<Set<Integer>> lines = new HashSet<Set<Integer>>();
	
	final List<Configuration.Line> linesAsList = new LinkedList<Configuration.Line>();

	public void addWord(final String word) {
		if (wordToId.containsKey(word)) {
			return; // We shall have no duplicate words
		}
		wordToId.put(word, wordCount);
		idToWord.add(word);
		// All words will have at least one connection... we expect
		// TODO: Assert no empty set for this id!!
		
		connections.put(wordCount, new HashSet<Integer>());
		wordIdToPosition.add(pf.newPosition());
		++wordCount;
    }
	
	public void addLogo() {
		logoIdToPosition.add(pf.newPosition());
		++logoCount;
	}
    
	public void addConnection(final String fromWord, final String toWord) {
		final int idxOfFrom = wordToId.get(fromWord);
		final int idxOfTo = wordToId.get(toWord);
		if (idxOfFrom == idxOfTo) {
			return;
		}
		
		// No need to check if connection already exists - we are using a set to
		// store them
		
		connections.get(idxOfFrom).add(idxOfTo);
		connections.get(idxOfTo).add(idxOfFrom);
		
		// Same goes for the lines really
		final Set<Integer> thisLine = new HashSet<Integer>();
		thisLine.add(idxOfFrom);
		thisLine.add(idxOfTo);
		if (!lines.contains(thisLine)) { 
			lines.add(thisLine); 
			linesAsList.add(
					new Configuration.Line() {
						
						@Override
						public int toWord() {
							return idxOfTo;
						}
						
						@Override
						public int fromWord() {
							return idxOfFrom;
						}
					}
					);
		}
    }


	@Override
	public Configuration makeConfiguration() {
				
		return new Configuration() {

			@Override
			public int getWordCount() {
				return wordCount;
			}

			@Override
			public Set<Integer> getRelatedWords(int word) {
				return connections.get(word);
			}

			@Override
			public int getLineCount() {
				return lines.size();
			}

			@Override
			public Position getPosition(int word) {
				return wordIdToPosition.get(word);
			}

			@Override
			public Line getLine(int relation) {
				return linesAsList.get(relation);
			}

			@Override
			public String getWord(int word) {
				return idToWord.get(word);
			}

			@Override
			public void changePositions(List<Position> newPositions, final List<Position> newLogoPositions) {
				wordIdToPosition = newPositions;
				logoIdToPosition = newLogoPositions;
				
			}

			@Override
			public int getImageCount() {
				return logoCount;
			}

			@Override
			public Position getImagePosition(int image) {
				return logoIdToPosition.get(image);
			}
			
		};
		
	}

}
