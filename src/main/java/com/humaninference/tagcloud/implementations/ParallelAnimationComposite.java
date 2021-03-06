package com.humaninference.tagcloud.implementations;

import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;

/**
 * 
 * @author andersprivat
 *
 * A simple composite that executes a list of animations in parallel
 */
public class ParallelAnimationComposite extends TaggedAnimation implements Animation.Observer {
	
	static Logger logger = Logger.getLogger(ParallelAnimationComposite.class);
		
	private static final long serialVersionUID = 1L;

	public ParallelAnimationComposite(final int tag) {
		super(tag);
	}


	private final List<Animation> animations = new LinkedList<Animation>();
	
	private int numDone = 0;

	private Observer obs = null;
	
	public void addAnimation(final Animation animation) {
		animations.add(animation);
	}
	public synchronized void perform(final World target, final Observer obs) {
		numDone = 0;
		this.obs = obs;
		for (final Animation animation : animations) {
			animation.perform(target, this);
		}
	}


	public synchronized void onAnimationFinished(final int tag) {
		logger.trace("Done " + numDone + " of " + animations.size());
		if (++numDone >= animations.size()) {
			obs.onAnimationFinished(tag()); // NB! We pass on the tag for the composite!
		}
	}

}
