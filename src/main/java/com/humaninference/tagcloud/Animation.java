package com.humaninference.tagcloud;

public interface Animation {

	public interface Observer {
		void onAnimationFinished();
	}
	
	void perform(final World target, Observer obs);
	
	long duration();
}
