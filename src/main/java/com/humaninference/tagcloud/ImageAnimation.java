package com.humaninference.tagcloud;

import edu.umd.cs.piccolo.nodes.PImage;

public class ImageAnimation implements Animation {
	
	private final double targetX;
	private final double targetY;
	private final long duration;
	private final int imgIdx;
	
	public ImageAnimation(final int imgIdx, final double targetX, final double targetY, final long duration) {
		this.imgIdx = imgIdx;
		this.targetX = targetX;
		this.targetY = targetY;
		this.duration = duration;
	}
	

	public void perform(final World target) {
		final PImage img = target.getImage(imgIdx);
		img.animateToPositionScaleRotation(targetX, targetY, 1.0, 0.0, duration);
	}

	public long duration() {
		return duration;
	}

}
