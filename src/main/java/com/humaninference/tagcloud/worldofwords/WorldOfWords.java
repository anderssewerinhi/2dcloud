package com.humaninference.tagcloud.worldofwords;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.WorldFactory;
import com.humaninference.tagcloud.worldofwords.implementations.MakeInitialConfiguration;
import com.humaninference.tagcloud.worldofwords.implementations.PopAndUnpopWordAnimationMaker;
import com.humaninference.tagcloud.worldofwords.implementations.PositionFactory;
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
	
	private final Configuration initialConfiguration;

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
    
	public void addConnection(final String fromWord, final String toWord) {
    	mic.addConnection(fromWord, toWord);
    }
    
	public Animation popWord(final int currentNode) {
    	
    	return PopAndUnpopWordAnimationMaker.makeAnimation(currentNode, initialConfiguration, width, height);
    	
    	// New positions as follows:
    	
    	// New position for "Word" is center of word cloud, zoomed to BIG
    	// New position for all connected words is halfway between current and center of word cloud
    	// ... zoomed to halfway between current and BIG
    	
    	// Calculate changes to all involved edges (connections from word + connections to
    	// words connected to word)
    	
    	// Store original positions
    	
    	// Generate animations to new ones, and gather in a ParallelComposite, add to sequential composite
    	
    	// Generate animation for a brief pause, add to sequential composite
    	
    	// Generate animations to restore state, add to sequential composite
    	
    	// Generate animations to move cloud randomly (a small change for all words?) + edges. 
    	// Gather in parallel composite, add to sequential composite 
    }
    
	@Override
	public World makeWorld() {
		
		// Process: Place the words randomly on a sphere
		// Placement: See accepted answer at http://stackoverflow.com/questions/8839086/how-to-randomize-points-on-a-sphere-surface-evenly
		
		// Create at least one layer (more if we want Z-ordering)
		// Set Random seed to well-known value, so all nodes will get the same sequence of random numbers
		// For each word
		// Compute a 3D point
		// Scale x and y to size of the world + translate x to make room for info on 1st screen
		// Translate z to a zoom factor (and a z-order?)
		// Add the Text label (store idx)
		// Add a backing entry for the current X/Y/Z (and scaled xyz?) mapped to idx of word
		
		// Now we can do interesting things!
		
		return WorldFromConfiguration.makeWorld(width, height, initialConfiguration);
		
	}

	public int getNextNode(int curentNode) {
		
	 List<Integer> nodePosition = new ArrayList<Integer>(initialConfiguration.getRelatedWords(curentNode));
	 
	 int nextPosition = nodePosition.get(new Random().nextInt(nodePosition.size())); 
			
	 return nextPosition;
	}

}
