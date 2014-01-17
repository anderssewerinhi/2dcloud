package com.humaninference.tagcloud.implementations;

import java.awt.Color;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;

public class ImageAnimation extends ThreeDimensionalAnimation {

	private static final long serialVersionUID = 1L;

	public ImageAnimation(int nodeIdx, double targetX, double targetY, long duration, int tag, final Color color) {
		super(nodeIdx, targetX, targetY, 1.0, duration, tag, color);
	}

	@Override
	protected PNode getNode(World target) {
		return target.getImage(nodeIdx);
	}
	
}
