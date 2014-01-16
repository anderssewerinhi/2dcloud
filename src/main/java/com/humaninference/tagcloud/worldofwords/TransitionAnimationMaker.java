package com.humaninference.tagcloud.worldofwords;

import com.humaninference.tagcloud.Animation;

public interface TransitionAnimationMaker {

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
	Animation animateTransition(
			final double width,
			final double height,
			final Configuration from, final Configuration to);
	
}
