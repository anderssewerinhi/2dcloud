package com.humaninference.tagcloud;

import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PActivity.PActivityDelegate;
import edu.umd.cs.piccolo.nodes.PImage;

public class ImageAnimation extends TaggedAnimation {
	
	private final double targetX;
	private final double targetY;
	private final long duration;
	private final int imgIdx;
	
	public ImageAnimation(final int imgIdx, final double targetX, final double targetY, final long duration, final int tag) {
		super(tag);
		this.imgIdx = imgIdx;
		this.targetX = targetX;
		this.targetY = targetY;
		this.duration = duration;
	}
	

	public void perform(final World target, final Observer obs) {
		final PImage img = target.getImage(imgIdx);
		final PActivityDelegate del = new PActivityDelegate() {
			
			public void activityStepped(PActivity arg0) {
			}
			
			public void activityStarted(PActivity arg0) {
			}
			
			public void activityFinished(PActivity arg0) {
				obs.onAnimationFinished(tag());
			}
		};
		final PActivity act = 
				img.animateToPositionScaleRotation(targetX, targetY, 1.0, 0.0, duration);
		act.setDelegate(del);
	}


	public long duration() {
		return duration;
	}


}
