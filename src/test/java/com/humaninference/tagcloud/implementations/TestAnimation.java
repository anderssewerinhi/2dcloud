package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

class TestAnimation extends TaggedAnimation {
	
	public TestAnimation(int tag) {
		super(tag);
	}

	private static final long serialVersionUID = 1L;
	
	public int numExecutions = 0; 
	
	public World lastWorld = null;

	@Override
	public void perform(World target, Observer obs) {
		lastWorld = target;
		++numExecutions;
		obs.onAnimationFinished(tag());
	}

}