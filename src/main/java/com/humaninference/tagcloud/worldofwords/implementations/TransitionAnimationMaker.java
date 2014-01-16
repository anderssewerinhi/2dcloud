package com.humaninference.tagcloud.worldofwords.implementations;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.implementations.LineAnimation;
import com.humaninference.tagcloud.implementations.ParallelAnimationComposite;
import com.humaninference.tagcloud.implementations.TextAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Line;
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
	 * @return
	 */
	 public static Animation animateTransition(
			final double width,
			final double height,
			final Configuration from, 
			final Configuration to) {
		 final ParallelAnimationComposite pac = new ParallelAnimationComposite(42);
		 final double xOffset = width / 2.0;
		 final double yOffset = height / 2.0;
		 
		 // First animate moving the words
		 for (int i = 0; i < to.getWordCount(); ++i) {
			 final Position p = to.getPosition(i); 
			 final double targetX = p.x() * width + xOffset;
			 final double targetY = p.y() * height + yOffset;
			 final double targetZoom = p.z() + 0.5; // No text too small to read
			 final TextAnimation ta = 
					 new TextAnimation(i, targetX, targetY, targetZoom, 1000, 42);
			 pac.addAnimation(ta);
		 }
		 
		 // Then animate the lines moving
		 for (int i = 0; i < to.getLineCount(); ++i) {
			 final Line l =  to.getLine(i);
			 final Position leftP = to.getPosition(l.fromWord()); 
			 final double toLeftX = leftP.x() * width + xOffset;
			 final double toLeftY = leftP.y() * height + yOffset;
			 final Position rightP = to.getPosition(l.fromWord()); 
			 final double toRightX = rightP.x() * width + xOffset;
			 final double toRightY = rightP.y() * height + yOffset;

			 final LineAnimation la = 
					 new LineAnimation(42, toLeftX, toLeftY, toRightX, toRightY);
			 pac.addAnimation(la);
		 }
		 
		return pac;
	}
	
}
