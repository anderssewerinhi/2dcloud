package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.Random;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
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
		
	private static Animation DO_NOTHING = new Animation() {
		
		private static final int TAG = -42;

		@Override
		public void perform(World target, Observer obs) {
			obs.onAnimationFinished(TAG);
			
		}

		@Override
		public int tag() {
			return 42;
		}
		
	};
	
	private static Configuration zoomWordAndRelatedWords(final Configuration initial) {
		// Make a new configuration that reflects this state - find the
		// word and zoom it. Find the related words and zoom them too.
		return initial;
	}
	

}
