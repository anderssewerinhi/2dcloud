package com.humaninference.tagcloud.worldofwords;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;

public interface TransitionAnimationMaker {

	Animation animateTransition(final World stage, final Configuration from, final Configuration to);
	
}
