package com.humaninference.tagcloud.worldofwords.implementations;

import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

import demo.simpleapplication.ImageWorld;
import demo.simpleapplication.WordsWorld;

public class WorldFromConfiguration {

	public static World makeWorld(final double width, final double height, final Configuration configuration) {
		
		//return new ImageWorld(); // Just for testing purposes
		
	    WordsWorld textWorld =  new WordsWorld(); 
	    
	    for (int i = 0; i < configuration.getWordCount(); ++i) {
			 final Position p = configuration.getPosition(i);
			 Position pos = configuration.getPosition(i);  
			 textWorld.addLabel(configuration.getWord(i), pos.x(), pos.y());
			 
	    }
		return textWorld;
//		throw new RuntimeException("Not implemented");
	}
}
