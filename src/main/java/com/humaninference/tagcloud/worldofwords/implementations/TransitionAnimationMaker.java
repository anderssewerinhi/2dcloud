package com.humaninference.tagcloud.worldofwords.implementations;

import java.awt.Color;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.implementations.ParallelAnimationComposite;
import com.humaninference.tagcloud.implementations.TextAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

public class TransitionAnimationMaker {

	/**
	 * The scaling factors width / height are required, as the positions in Configuration
	 * are normalized in range -1 .. 1
	 * 
	 * The "from" may not be needed - time shall tell.
	 * 
	 * The assumption is that the ID of a word in a Configuration is 1:1 mapped to
	 * the textlabels in the world, and that the same goes for the lines in configuraiton
	 * and paths in world.
	 * 
	 * @param width Width of area to render to
	 * @param height Height of area to render to
	 * @param from The state we start in (positions of the labels etc.)
	 * @param to The state we end in (positions of the lavels etc.)
	 * @param currentNode 
	 * @return
	 */
	 public static Animation animateTransition (
			final double width,
			final double height,
			final Color rotationColor,
			final int currentNode, 
			final Color popInColor,
			final Configuration from, 
			final Configuration to) {
		 final ParallelAnimationComposite pac = new ParallelAnimationComposite(42);
		 final double xOffset = width / 2.0;
		 final double yOffset = height / 2.0;
		 
		 // First animate moving the logos
		 for (int i = 0; i < to.getImageCount(); ++i) {
			 final Position p = to.getImagePosition(i);
			 if (from.getImagePosition(i).equals(p)) {
//				 continue;
			 }
			 final double targetX = p.x() * xOffset + xOffset;
			 final double targetY = p.y() * yOffset + yOffset;
			 final double targetZoom = WorldFromConfiguration.scaleImage(p); // No text too small to read
			 final ImageAnimation  
				 ta = 
					 new ImageAnimation(i, targetX, targetY, targetZoom, 1000, 42);
			 pac.addAnimation(ta);
		 }
		 
		 // Then animate moving the words
		 for (int i = 0; i < to.getWordCount(); ++i) {
			 final Position p = to.getPosition(i); 
			 if (from.getPosition(i).equals(p)) {
//				 continue;
			 }
			 final double targetX = p.x() * xOffset + xOffset;
			 final double targetY = p.y() * yOffset + yOffset;
			 final double targetZoom = WorldFromConfiguration.scaleText(p);
			 final TextAnimation ta ;
			 if (i != currentNode ){
		     
				 ta = 
					 new TextAnimation(i, targetX, targetY, targetZoom, 1000, 42, rotationColor);
			 }
			 else {
				 
				 ta =  new TextAnimation(i, targetX, targetY, targetZoom, 1000, 42, popInColor);
			 }
			 pac.addAnimation(ta);
		 }
		 
		return pac;
	}
	
}
