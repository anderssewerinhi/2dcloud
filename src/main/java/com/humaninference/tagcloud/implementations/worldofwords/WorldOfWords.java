package com.humaninference.tagcloud.implementations.worldofwords;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.WorldFactory;

public class WorldOfWords implements WorldFactory {

    public void addWord(final String word) {
		throw new RuntimeException("Not implemented");
    }
    
    public void addConnection(final String fromWord, final String toWord) {
		throw new RuntimeException("Not implemented");
    }
    
    public Animation popWord(final String word) {
		throw new RuntimeException("Not implemented");
    }
    
	@Override
	public World makeWorld() {
		throw new RuntimeException("Not implemented");
	}

}
