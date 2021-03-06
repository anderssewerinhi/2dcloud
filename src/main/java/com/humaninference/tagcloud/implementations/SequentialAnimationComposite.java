package com.humaninference.tagcloud.implementations;

import java.util.LinkedList;
import java.util.List;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;

/**
 * 
 * @author andersprivat
 *
 * A simple composite that executes a list of animations in sequence
 */
public class SequentialAnimationComposite extends TaggedAnimation implements Animation.Observer {
		
	private static final long serialVersionUID = 1L;

	public SequentialAnimationComposite(int tag) {
		super(tag);
	}

	private final List<Animation> animations = new LinkedList<Animation>();
		
	public void addAnimation(final Animation animation) {
		animations.add(animation);
	}
	
	private static class ChainingObserver implements Observer {
		private final Observer finalObserver;
		private final int idxofThisAnimation;
		private final List<Animation> animations;
		private final World target;
		private final int tagOfSequentialAnimation;
		
		public ChainingObserver(final Observer finalObserver,
								final int idxofThisAnimation,
								final List<Animation> animations,
								final World target,
								final int tagOfSequentialAnimation) {
			this.finalObserver = finalObserver;
			this.idxofThisAnimation = idxofThisAnimation;
			this.animations = animations;
			this.target = target;
			this.tagOfSequentialAnimation = tagOfSequentialAnimation;
		}
		
		public void onAnimationFinished(final int tag) {
			if (idxofThisAnimation == animations.size() - 1) {
				finalObserver.onAnimationFinished(tagOfSequentialAnimation);
			} else {
				final Observer nextObserver = new ChainingObserver(finalObserver, idxofThisAnimation + 1, animations, target, tagOfSequentialAnimation);
				animations.get(idxofThisAnimation + 1).perform(target, nextObserver);
			}
		}
	}
	
	public synchronized void perform(final World target, final Observer obs) {
		if (animations.size() == 0) {
			obs.onAnimationFinished(tag());
		} else {
			final Observer nextObserver = new ChainingObserver(obs, 0, animations, target, tag());
			animations.get(0).perform(target, nextObserver);
		}
	}

	public synchronized void onAnimationFinished(int tag) {
		throw new RuntimeException("This should never happen!");
	}


}
