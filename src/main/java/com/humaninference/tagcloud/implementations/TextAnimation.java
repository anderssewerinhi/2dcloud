package com.humaninference.tagcloud.implementations;

import java.awt.Color;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;

public class TextAnimation extends ThreeDimensionalAnimation {

	private static final long serialVersionUID = 1L;


	public TextAnimation(final int imgIdx, final double targetX, final double targetY, final double targetZoom, final long duration, final int tag, final Color color) {
		super(imgIdx, targetX, targetY, targetZoom, duration, tag,color); 
	}
	

	@Override
	protected PNode getNode(final World target) {
		return target.getTextLabel(nodeIdx);
	}


}
