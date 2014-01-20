package com.humaninference.tagcloud.implementations;

import java.awt.Color;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.nodes.PText;

public class TextAnimation extends ThreeDimensionalAnimation {

	private static final long serialVersionUID = 1L;
	
	private final Color textColor;


	public TextAnimation(final int imgIdx, final double targetX, final double targetY, final double targetZoom, final long duration, final int tag, final Color color) {
		super(imgIdx, targetX, targetY, targetZoom, duration, tag);
		this.textColor = color;
	}
	
	@Override
	protected void perform(final Observer obs, final PNode n) {
		final PText textNode = (PText) n;
		textNode.setTextPaint(textColor);
		super.perform(obs, n);

	}

	

	@Override
	protected PNode getNode(final World target) {
		return target.getTextLabel(nodeIdx);
	}


}
