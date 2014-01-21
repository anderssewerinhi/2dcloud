package com.humaninference.tagcloud.worldofwords;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.WorldFactory;
import com.humaninference.tagcloud.implementations.ParallelAnimationComposite;
import com.humaninference.tagcloud.implementations.SequentialAnimationComposite;
import com.humaninference.tagcloud.worldofwords.implementations.MakeInitialConfiguration;
import com.humaninference.tagcloud.worldofwords.implementations.PopAndUnpopWordAnimationMaker;
import com.humaninference.tagcloud.worldofwords.implementations.PositionFactory;
import com.humaninference.tagcloud.worldofwords.implementations.RotateCloudAnimationMaker;
import com.humaninference.tagcloud.worldofwords.implementations.TransitionAnimationMaker;
import com.humaninference.tagcloud.worldofwords.implementations.WorldFromConfiguration;

/**
 * 
 * @author andersprivat
 * 
 * This will be a facade for a composed group of implementations of the various
 * interfaces.
 *
 */
public class WorldOfWords implements WorldFactory {
	
	private final MakeInitialConfiguration mic;
	
	private Configuration initialConfiguration;

	private final double width;

	private final double height;
	
	public WorldOfWords(final PositionFactory pf, final double width, final double height) {
		mic = new MakeInitialConfiguration(pf);
		this.width = width;
		this.height = height;
		initialConfiguration = mic.makeConfiguration();

	}

	public void addWord(final String word) {
    	mic.addWord(word);
    }
	
	public void addLogo() {
		mic.addLogo();
	}
	
    
	public void addConnection(final String fromWord, final String toWord) {
    	mic.addConnection(fromWord, toWord);
    }
    	
	public Animation popWord(final int currentNode) {
    	
						
		final Color popInColor = Color.red; 
		final Color popOutColor = Color.black; 
		final Color rotateColor = Color.black;
	 
    	
		final SequentialAnimationComposite sequentialAnimations = 
				new SequentialAnimationComposite(PopAndUnpopWordAnimationMaker.getAnimationTagNumber());
		
    	final Configuration rotated = 
    			RotateCloudAnimationMaker.makeAnimation(initialConfiguration, 0.75);
    	final Animation rotate = 
    			TransitionAnimationMaker.animateTransition(width, height,rotateColor,currentNode, popInColor,   
    					initialConfiguration, rotated);
    	
    	//final Animation pop = PopAndUnpopWordAnimationMaker.makeAnimation(currentNode, rotated, width, height, popInColor, popOutColor);
    	//final SequentialAnimationComposite sequence = new SequentialAnimationComposite(999);
    	final ParallelAnimationComposite parallel1 = new ParallelAnimationComposite(1000); 
    	
		final Animation zoom = PopAndUnpopWordAnimationMaker.makePopAnimation(currentNode,initialConfiguration, width, height, popInColor);
    	parallel1.addAnimation(zoom);
    	parallel1.addAnimation(rotate);
    	
    
    
    	final ParallelAnimationComposite parallel2 = new ParallelAnimationComposite(1000); 
    	
    	final Animation keepZoom =  PopAndUnpopWordAnimationMaker.diplayPopAnimation(currentNode, initialConfiguration, width, height, popInColor);
    	
    	parallel2.addAnimation(keepZoom);
    	parallel2.addAnimation(rotate);
    	
    	
    	
    	
       /* final Animation unPopAnimation = PopAndUnpopWordAnimationMaker.makeUnPopAnimation(currentNode, initialConfiguration, width, height, popOutColor);
    	
    	final ParallelAnimationComposite parallel3 = new ParallelAnimationComposite(1000); 
    	
    	parallel3.addAnimation(unPopAnimation); 
    	parallel3.addAnimation(rotate);
    	
        */	
    		
    	
    	sequentialAnimations.addAnimation(parallel1);
       
    	sequentialAnimations.addAnimation(parallel2); 
    	//sequentialAnimations.addAnimation(parallel3);
    	
    	initialConfiguration = rotated;
    	return sequentialAnimations;
    }
    
	@Override
	public World makeWorld() {		
		return WorldFromConfiguration.makeWorld(width, height, initialConfiguration);
		
	}

	public int getNextNode(int curentNode) {
		
	 final Set<Integer> relatedWords = initialConfiguration.getRelatedWords(curentNode);
	 List<Integer> nodePosition = new ArrayList<Integer>(relatedWords);
	 
	 int nextPosition = nodePosition.get(new Random().nextInt(nodePosition.size())); 
			
	 return nextPosition;
	}

}
