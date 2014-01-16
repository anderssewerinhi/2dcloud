package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

public class LineAnimation extends TaggedAnimation {

	final double toLeftX;
	final double toLeftY;
	final double toRightX;
	final double toRightY;
	
	public LineAnimation(final int tag, final double toLeftX, final double toLeftY, 
			final double toRightX, final double toRightY) {
		super(tag);
		this.toLeftX = toLeftX;
		this.toLeftY = toLeftY;
		this.toRightX = toRightX;
		this.toRightY = toRightY;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void perform(final World target, final Observer obs) {
		// TODO Auto-generated method stub

	}

}
