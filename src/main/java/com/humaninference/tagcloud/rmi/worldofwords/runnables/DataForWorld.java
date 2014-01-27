package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import com.humaninference.tagcloud.worldofwords.WorldOfWords;
import com.humaninference.tagcloud.worldofwords.implementations.RandomPositionFactory;

public class DataForWorld {	
	
	private static final double BEAMER_HEIGHT_PIXELS = 1080.0;
	
	private static final double BEAMER_WIDTH_PIXELS = 1920.0;
	
	private static final double SPACE_BETWEEN_BEAMERS_IN_RATIO_OF_BEAMER_WIDTH = 0.1;
	
	private static final double BEAMER_SPACING_PIXELS = SPACE_BETWEEN_BEAMERS_IN_RATIO_OF_BEAMER_WIDTH * BEAMER_WIDTH_PIXELS;
	
    private static final double EXPECTED_NUMBER_OF_CLIENTS = 4.0; 
    
	public static WorldOfWords makeRepoducablyRandomWorld(String configFromJar) {
		final WordsAndConnections data = SettableWordsAndConnections.fromDefaultConfig(configFromJar);
		return makeRepoducablyRandomWorld(data);
	}
	
	public static WorldOfWords makeRepoducablyRandomWorld(final WordsAndConnections data) {
		final double actualWidth = EXPECTED_NUMBER_OF_CLIENTS * BEAMER_WIDTH_PIXELS + (EXPECTED_NUMBER_OF_CLIENTS - 1) * BEAMER_SPACING_PIXELS;
		final WorldOfWords res = new WorldOfWords(new RandomPositionFactory(42L), actualWidth, BEAMER_HEIGHT_PIXELS);
		
		for (final String word : data.getWords()) {
			res.addWord(word);
		}
		
		
		for (int i = 0; i < data.getConnectionsFromTo().size(); i += 2) {
			final String from = data.getConnectionsFromTo().get(i);
			final String to = data.getConnectionsFromTo().get(i + 1);
			res.addConnection(from, to);
		}
		
		
		for (int i = 0; i < data.getNumLogos(); ++i) {
			res.addLogo();
		}
		return res;
	}
	
	
}
