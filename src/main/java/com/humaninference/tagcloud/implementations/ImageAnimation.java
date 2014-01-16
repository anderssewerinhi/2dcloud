package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;

public class ImageAnimation extends ThreeDimensionalAnimation {

	public ImageAnimation(int nodeIdx, double targetX, double targetY, long duration, int tag) {
		super(nodeIdx, targetX, targetY, 1.0, duration, tag);
	}

	@Override
	protected PNode getNode(World target) {
		return target.getImage(nodeIdx);
	}
	
}
