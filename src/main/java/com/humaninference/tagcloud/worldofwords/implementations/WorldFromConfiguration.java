package com.humaninference.tagcloud.worldofwords.implementations;

import java.awt.Color;
import java.util.Random;

import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

import demo.simpleapplication.WordsWorld;

public class WorldFromConfiguration {

	private static final double MIN_SIZE_LOGO = 0.1;
	private static final double SCALE_FACTOR_LOGO = 0.3;
	
	private static Random myPredictableRandom = new Random(667);
		
	public static World makeWorld(final double width, final double height, final Configuration configuration) {
				
	    final WordsWorld textWorld =  new WordsWorld(); 
		final double halfHeight = height/2.0;
		final double halfWidth = width/2.0;
	    
	    for (int i = 0; i < configuration.getWordCount(); ++i) {
			 final Position pos = configuration.getPosition(i);  
			 double scale = 1.0 +pos.z();
			 textWorld.addLabel(configuration.getWord(i), pos.x()* halfWidth+halfWidth, pos.y()*halfHeight +halfHeight, scale, Color.black);
	    }
	    for (int i = 0; i < configuration.getImageCount(); ++i) {
			 final Position pos = configuration.getImagePosition(i);  
			 double scale = myPredictableRandom.nextDouble() * SCALE_FACTOR_LOGO + MIN_SIZE_LOGO;
			 textWorld.addLogo(pos.x()* halfWidth+halfWidth, pos.y()*halfHeight +halfHeight, scale);
	    }
		return textWorld;
	}
}
