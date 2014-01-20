package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.humaninference.tagcloud.worldofwords.Configuration;

public class ConcreteConfiguration implements Configuration {

	private final Map<String, Integer> wordToId;
	
	private final List<String> idToWord;
	
	private final Map<Integer, Set<Integer>> connections;
	
	private List<Configuration.Position> wordIdToPosition;
	
	private  List<Configuration.Position> logoIdToPosition;

	private final Set<Set<Integer>> lines;
	
	final List<Configuration.Line> linesAsList;
	
	public ConcreteConfiguration(final Map<String, Integer> wordToId,
			final List<String> idToWord,
			final Map<Integer, Set<Integer>> connections,
			final List<Configuration.Position> wordIdToPosition,
			final Set<Set<Integer>> lines,
			final List<Configuration.Line> linesAsList,
			final List<Configuration.Position> logoIdToPosition
			) {
		this.wordToId = wordToId;
		this.idToWord = idToWord;
		this.connections = connections;
		this.wordIdToPosition = wordIdToPosition;
		this.lines = lines;
		this.linesAsList = linesAsList;
		this.logoIdToPosition = logoIdToPosition;
		
	}

	@Override
	public int getWordCount() {
		return idToWord.size();
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

	public void changePositions(final List<Position> newPositions, final List<Position> newLogoPositions) {
		wordIdToPosition = newPositions;
		logoIdToPosition = newLogoPositions;
	}

	@Override
	public int getImageCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Position getImagePosition(int image) {
		// TODO Auto-generated method stub
		return null;
	}
	
}


