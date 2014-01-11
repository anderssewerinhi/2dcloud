package com.humaninference.tagcloud;

import java.util.LinkedList;
import java.util.List;

/**
 * 
 * @author andersprivat
 *
 * A simple composite that executes a list of animations in sequence
 */
public class SequentialAnimationComposite implements Animation, Animation.Observer {
	
	private long duration = 0;
	
	private final List<Animation> animations = new LinkedList<Animation>();
		
	public void addAnimation(final Animation animation) {
		duration += animation.duration();
	}
	
	private static class ChainingObserver implements Observer {
		private final Observer finalObserver;
		private final int idxofThisAnimation;
		private final List<Animation> animations;
		private final World target;
		
		public ChainingObserver(final Observer finalObserver,
								final int idxofThisAnimation,
								final List<Animation> animations,
								final World target) {
			this.finalObserver = finalObserver;
			this.idxofThisAnimation = idxofThisAnimation;
			this.animations = animations;
			this.target = target;
		}
		
		public void onAnimationFinished() {
			if (idxofThisAnimation == animations.size() - 1) {
				finalObserver.onAnimationFinished();
			} else {
				final Observer nextObserver = new ChainingObserver(finalObserver, idxofThisAnimation + 1, animations, target);
				animations.get(idxofThisAnimation + 1).perform(target, nextObserver);
			}
		}
	}
	
	public synchronized void perform(final World target, final Observer obs) {
		if (animations.size() == 0) {
			obs.onAnimationFinished();
		} else {
			final Observer nextObserver = new ChainingObserver(obs, 0, animations, target);
			animations.get(0).perform(target, nextObserver);
		}
	}

	public long duration() {
		return duration;
	}

	public synchronized void onAnimationFinished() {
		throw new RuntimeException("This should never happen!");
	}

}
