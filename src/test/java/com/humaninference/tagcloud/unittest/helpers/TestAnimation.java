package com.humaninference.tagcloud.unittest.helpers;

import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.TaggedAnimation;

public class TestAnimation extends TaggedAnimation {
	
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