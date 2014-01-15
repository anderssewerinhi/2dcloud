package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.Animation;

class TestObserver implements Animation.Observer {

	@Override
	public void onAnimationFinished(int tag) {
		++numExecutions;
		lastTag = tag;
	}
	
	public int numExecutions = 0; 
	
	public int lastTag = -1;

}