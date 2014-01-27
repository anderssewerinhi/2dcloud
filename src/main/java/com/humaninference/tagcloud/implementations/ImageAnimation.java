package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;

public class ImageAnimation extends ThreeDimensionalAnimation {

	private static final long serialVersionUID = 1L;

	public ImageAnimation(int nodeIdx, double targetX, double targetY, double targetZoom, long duration, int tag) {
		super(nodeIdx, targetX, targetY, targetZoom, duration, tag);
	}

	@Override
	protected PNode getNode(World target) {
		return target.getImage(nodeIdx);
	}
	
}
