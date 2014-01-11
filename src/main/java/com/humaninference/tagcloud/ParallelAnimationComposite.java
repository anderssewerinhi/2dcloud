package com.humaninference.tagcloud;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author andersprivat
 *
 * A simple composite that executes a list of animations in parallel
 */
public class ParallelAnimationComposite implements Animation, Animation.Observer {
	
	private long duration = 0;
	
	private final List<Animation> animations = new LinkedList<Animation>();
	
	private int numDone = 0;

	private Observer obs = null;
	
	public void addAnimation(final Animation animation) {
		duration = Math.max(duration, animation.duration());
		
	}
	public synchronized void perform(final World target, final Observer obs) {
		numDone = 0;
		for (final Animation animation : animations) {
			animation.perform(target, this);
		}
	}

	public long duration() {
		return duration;
	}

	public synchronized void onAnimationFinished() {
		if (++numDone >= animations.size()) {
			obs.onAnimationFinished();
		}
	}

}
