package com.humaninference.tagcloud.worldofwords.implementations;

import java.awt.Color;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.implementations.SequentialAnimationComposite;
import com.humaninference.tagcloud.implementations.TaggedAnimation;
import com.humaninference.tagcloud.implementations.TextAnimation;
import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

public class PopAndUnpopWordAnimationMaker {

	private final static int POP_ANIMATION_TAG = 667;
	
	@SuppressWarnings("unused")
	public static Animation makeAnimation(final int currentNode, 
			final Configuration initialConfiguration, final double width, final double height, Color popInColor, Color popOutColor) {
		
		
		final SequentialAnimationComposite res = 
				new SequentialAnimationComposite(POP_ANIMATION_TAG);
		
		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
		final Animation imgAnim = new ImageAnimation(0, newX, newY, 1.0, duration, 0);
		final double halfWidth = width /2.0; 
			
		final double halfHeight = height /2.0;
		 
		final Animation popTextAnim = new TextAnimation(currentNode,halfWidth, halfHeight, 2.0,1000, 0, popInColor); 
		 
		final Position pos = initialConfiguration.getPosition(currentNode);
			
	    final Animation unPopTextAnim = new TextAnimation(currentNode,pos.x()* halfWidth +halfWidth, pos.y()*halfHeight  +halfHeight, 1+pos.z(),1000, 0, popOutColor); 
		
		res.addAnimation(popTextAnim);
		res.addAnimation(pauseForInterval(1000));
		res.addAnimation(unPopTextAnim);
		res.addAnimation(pauseForInterval(1000));
		
		return res;

	}
	
	public static Animation makePopAnimation(final int currentNode, 
			final Configuration initialConfiguration, final double width, final double height, Color popInColor){
		

		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
	
		final double halfWidth = width /2.0; 	
		final double halfHeight = height /2.0;
		
		
		final Animation popTextAnim = new TextAnimation(currentNode,halfWidth, halfHeight, 2.0,1000, 0, popInColor); 
		
		
		
		
		return popTextAnim; 
		
	}
	
	public static Animation diplayPopAnimation(final int currentNode, 
			final Configuration initialConfiguration, final double width, final double height, Color popInColor){
		

		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
	
		final double halfWidth = width /2.0; 	
		final double halfHeight = height /2.0;
		
		final Position pos = initialConfiguration.getPosition(currentNode);
		
		final Animation displayPopTextAnim = new TextAnimation(currentNode,halfWidth, halfHeight, 2.0,1000, 0, popInColor); 
		
		
		return displayPopTextAnim; 
	}
	public static Animation makeUnPopAnimation(final int currentNode, 
			final Configuration initialConfiguration, final double width, final double height, Color popOutColor){
		
		
		 final double halfWidth = width /2.0; 
		
		 final double halfHeight = height /2.0;
		
		 final Position pos = initialConfiguration.getPosition(currentNode);
		 
		 final Animation unPopTextAnim = new TextAnimation(currentNode,pos.x()* halfWidth +halfWidth, pos.y()*halfHeight  +halfHeight, 1+pos.z(),1000, 0, popOutColor);
		 
		 return unPopTextAnim;
		
	}
	@SuppressWarnings("unused")
	private static Animation makePopAnimation(final String word,
			final Configuration initialConfiguration, final double width, final double height) {
		final SequentialAnimationComposite res = 
				new SequentialAnimationComposite(POP_ANIMATION_TAG);
		
		final Configuration wordIsPopped = 
				zoomWordAndRelatedWords(word, initialConfiguration) ;
		
		final Animation popAnimaiton = 
				TransitionAnimationMaker.animateTransition(width, height, Color.black,-1, Color.black,  initialConfiguration, wordIsPopped);
		
		final Animation unpopAnimaiton = 
				TransitionAnimationMaker.animateTransition(width, height, Color.black,-1, Color.black, wordIsPopped, initialConfiguration);
		
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
				return 4.0;
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

			@Override
			public void changePositions(List<Position> newPositions, final List<Position> newLogoPositions) {
				throw new RuntimeException("Not implemented - will not be needed");
				
			}

			@Override
			public int getImageCount() {
				return initial.getImageCount();
			}

			@Override
			public Position getImagePosition(int image) {
				return initial.getImagePosition(image);
			}
			
		};
		return res;
	}
	
	private static Animation pauseForInterval(final long millis) {
		return new TaggedAnimation() {

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
	
public static int getAnimationTagNumber(){
		
		
		return POP_ANIMATION_TAG;
		
	}

}
