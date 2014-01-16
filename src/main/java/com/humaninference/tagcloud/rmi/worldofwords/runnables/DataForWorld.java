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
		"was"
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
	
	public static WorldOfWords makeRepoducablyRandomWorld() {
		final WorldOfWords res = new WorldOfWords(new RandomPositionFactory(42L), 600.0, 300.0);
		
		for (final String word : WORDS) {
			res.addWord(word);
		}
		
		for (int i = 0; i < CONNECTIONS_FROM_TO.length; i += 2) {
			final String from = CONNECTIONS_FROM_TO[i];
			final String to = CONNECTIONS_FROM_TO[i+1];
			res.addConnection(from, to);
		}
		
		return res;
	}
}
