package com.humaninference.tagcloud.worldofwords.implementations;

import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.implementations.SequentialAnimationComposite;
import com.humaninference.tagcloud.implementations.TaggedAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;

public class PopAndUnpopWordAnimationMaker {

	private final static int POP_ANIMATION_TAG = 667;
	
	@SuppressWarnings("unused")
	public static Animation makeAnimation(final String word, 
			final Configuration initialConfiguration, final double width, final double height) {
		
		// TODO: Do NOT fake the animation
		// return makePopAnimation(word, <...>);
		
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
		
		final Configuration wordIsPopped = zoomWordAndRelatedWords(word, initialConfiguration) ;
		
		final Animation popAnimaiton = TransitionAnimationMaker.animateTransition(width, height, initialConfiguration, wordIsPopped);
		
		final Animation unpopAnimaiton = TransitionAnimationMaker.animateTransition(width, height, wordIsPopped, initialConfiguration);
		
		res.addAnimation(popAnimaiton);
		res.addAnimation(pauseForInterval(1000));
		res.addAnimation(unpopAnimaiton);
		return res;
	}
	
	private static Configuration zoomWordAndRelatedWords(final String word, final Configuration initial) {
		// Make a new configuration that reflects this state - find the
		// word and zoom it. Find the related words and zoom them too.
		
		final int wordIdx = /*initial.getIndexOfWord(word); */ 0;
		
		// For now just move that word front, and zoom to double size
		final Configuration.Position centerAndZoomed = centerAndZoomedPosition();
		
		final Configuration res = overridePositionForWord(initial, wordIdx,
				centerAndZoomed);
		return res;
	}

	private static Configuration.Position centerAndZoomedPosition() {
		final Configuration.Position centerAndZoomed = new Configuration.Position() {

			@Override
			public double x() {
				return 0;
			}

			@Override
			public double y() {
				return 0;
			}

			@Override
			public double z() {
				return 2.0;
			}
			
		};
		return centerAndZoomed;
	}

	private static Configuration overridePositionForWord(
			final Configuration initial, final int wordIdx,
			final Configuration.Position centerAndZoomed) {
		final Configuration res = new Configuration() {

			@Override
			public int getWordCount() {
				return initial.getWordCount();
			}

			@Override
			public Set<Integer> getRelatedWords(int word) {
				return initial.getRelatedWords(wordIdx);
			}

			@Override
			public Position getPosition(int word) {
				if (word == wordIdx ) {
					return centerAndZoomed;
				}
				return initial.getPosition(wordIdx);
			}

			@Override
			public String getWord(int word) {
				return initial.getWord(wordIdx);
			}

			@Override
			public int getLineCount() {
				return initial.getLineCount();
			}

			@Override
			public Line getLine(int line) {
				return initial.getLine(line);
			}
			
		};
		return res;
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
