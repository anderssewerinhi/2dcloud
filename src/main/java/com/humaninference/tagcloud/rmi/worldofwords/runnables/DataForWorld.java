package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import com.humaninference.tagcloud.worldofwords.WorldOfWords;
import com.humaninference.tagcloud.worldofwords.implementations.RandomPositionFactory;

public class DataForWorld {

	
	// Note: Duplicates are OK - the builder classes will cope
	public static final String WORDS[] = {
		"this",
		"is",
		"a",
		"small",
		"exciting",
		"test",
		"was",
		
		"As", "long", "as", "my", "repo", "had", "nothing", "to", "do", "with", "the", "public", "version,", "this", "was", "all", "fine,", "but", "since", "now", "I'd", "want", "the", "ability", "to", "collorate", "on", "WIP", "with", "other", "team", "members", "and", "outside", "contributors,", "I", "want", "to", "make", "sure", "that", "my", "public", "branches", "are", "reliable", "for", "others", "to", "branch", "off", "and", "pull", "from,", "i.e.", "no", "more", "rebase", "and", "reset", "on", "things", "I've", "pushed", "to", "the", "remote", "backup,", "since", "it's", "now", "on", "GitHub", "and", "public."
	};
	
	// Note: Duplicates are OK - the builder classes will cope
	// However, a connection to a missing word may NOT work (at the time of writing)
	public static final String CONNECTIONS_FROM_TO[] = {
		// From "this"
		"this", "is",
		"this", "was",
		"this", "small",
		"this", "exciting",

		// From "is"
		"is", "this",
		"is", "small",
		"is", "exciting",
		"is", "a",
		
		// From "a"
		"a", "small",
		"a", "test",
		
		// From "exciting"
		
		"exciting", "test"
		
	};
	
	private static final int NUM_LOGOS = 5;
	
	private static final double BEAMER_HEIGHT_PIXELS = 1080.0;
	
	private static final double BEAMER_WIDTH_PIXELS = 1920.0;
	
	private static final double SPACE_BETWEEN_BEAMERS_IN_RATIO_OF_BEAMER_WIDTH = 0.1;
	
	private static final double BEAMER_SPACING_PIXELS = SPACE_BETWEEN_BEAMERS_IN_RATIO_OF_BEAMER_WIDTH * BEAMER_WIDTH_PIXELS;
	
    private static final double EXPECTED_NUMBER_OF_CLIENTS = 4.0; 
    
	public static WorldOfWords makeRepoducablyRandomWorld() {
		final double actualWidth = EXPECTED_NUMBER_OF_CLIENTS * BEAMER_WIDTH_PIXELS + (EXPECTED_NUMBER_OF_CLIENTS - 1) * BEAMER_SPACING_PIXELS;
		final WorldOfWords res = new WorldOfWords(new RandomPositionFactory(42L), actualWidth, BEAMER_HEIGHT_PIXELS);
		
		for (final String word : WORDS) {
			res.addWord(word);
		}
		
		
		for (int i = 0; i < CONNECTIONS_FROM_TO.length; i += 2) {
			final String from = CONNECTIONS_FROM_TO[i];
			final String to = CONNECTIONS_FROM_TO[i+1];
			res.addConnection(from, to);
		}
		
		
		for (int i = 0; i < NUM_LOGOS; ++i) {
			res.addLogo();
		}
		return res;
	}
}
