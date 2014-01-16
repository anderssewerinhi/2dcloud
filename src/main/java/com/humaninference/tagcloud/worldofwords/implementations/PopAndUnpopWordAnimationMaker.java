package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.implementations.SequentialAnimationComposite;
import com.humaninference.tagcloud.implementations.TaggedAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.TransitionAnimationMaker;

public class PopAndUnpopWordAnimationMaker {

	private final static int POP_ANIMATION_TAG = 667;
	
	private final static TransitionAnimationMaker transitionMaker = null; 
	
	@SuppressWarnings("unused")
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
	
	@SuppressWarnings("unused")
	private static Animation makePopAnimation(final String word,
			final Configuration initialConfiguration, final double width, final double height) {
		final SequentialAnimationComposite res = new SequentialAnimationComposite(POP_ANIMATION_TAG);
		
		final Configuration wordIsPopped = zoomWordAndRelatedWords(initialConfiguration) ;
		
		final Animation popAnimaiton = transitionMaker.animateTransition(width, height, initialConfiguration, wordIsPopped);
		
		final Animation unpopAnimaiton = transitionMaker.animateTransition(width, height, wordIsPopped, initialConfiguration);
		
		res.addAnimation(popAnimaiton);
		res.addAnimation(pauseForInterval(1000));
		res.addAnimation(unpopAnimaiton);
		return res;
	}
	
	private static Configuration zoomWordAndRelatedWords(final Configuration initial) {
		// Make a new configuration that reflects this state - find the
		// word and zoom it. Find the related words and zoom them too.
		return initial;
	}
	
	private static Animation pauseForInterval(final long millis) {
		return new TaggedAnimation(42) {

			private static final long serialVersionUID = 1L;

			@Override
			public void perform(World target, final Observer obs) {
				final Timer t = new Timer();
				
				final TimerTask callObs = new TimerTask() {

					@Override
					public void run() {
						obs.onAnimationFinished(tag());
						
					}
					
				};
				
				t.schedule(callObs, millis);
			}
			
		};
	}
	

}
