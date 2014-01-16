package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;

public class TextAnimation extends ThreeDimensionalAnimation {

	private static final long serialVersionUID = 1L;


	public TextAnimation(final int imgIdx, final double targetX, final double targetY, final double targetZoom, final long duration, final int tag) {
		super(imgIdx, targetX, targetY, targetZoom, duration, tag); 
	}
	

	@Override
	protected PNode getNode(final World target) {
		return target.getTextLabel(nodeIdx);
	}


}
