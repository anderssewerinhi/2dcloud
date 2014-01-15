package com.humaninference.tagcloud.implementations.worldofwords;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.WorldFactory;

public class WorldOfWords implements WorldFactory {

    @SuppressWarnings("unused")
	public void addWord(final String word) {
		throw new RuntimeException("Not implemented");
    }
    
    @SuppressWarnings("unused")
	public void addConnection(final String fromWord, final String toWord) {
		throw new RuntimeException("Not implemented");
    }
    
    @SuppressWarnings("unused")
	public Animation popWord(final String word) {
		throw new RuntimeException("Not implemented");
    }
    
	@Override
	public World makeWorld() {
		throw new RuntimeException("Not implemented");
	}

}
