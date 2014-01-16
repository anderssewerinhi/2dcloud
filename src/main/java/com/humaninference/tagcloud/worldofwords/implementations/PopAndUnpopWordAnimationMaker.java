package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.Random;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;

public class PopAndUnpopWordAnimationMaker {

	public static Animation makeAnimation(final String word,
			final Configuration initialConfiguration, final double width, final double height) {
		
		// TODO: Do NOT fake the animation
		
		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
		final Animation imgAnim = new ImageAnimation(0, newX, newY, duration, 0);
		return imgAnim;

	}

}
