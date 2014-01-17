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
			 double halfHeight = height/2.0;
			double halfWidth = width/2.0;
		    double scale = 1.0 +pos.z();
		    
			textWorld.addLabel(configuration.getWord(i), pos.x()* halfWidth+halfWidth, pos.y()*halfHeight +halfHeight, scale);
			 
	    }
		return textWorld;
//		throw new RuntimeException("Not implemented");
	}
}
