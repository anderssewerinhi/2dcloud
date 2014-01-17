package com.humaninference.tagcloud;

import java.io.Serializable;

/**
 * 
 * @author andersprivat
 *
 * An interface for an animation. 
 * 
 * Note tag() method - this allows the recipient of callback onAnimationFInished to 
 * coordinate several threads of animations if so desired. 
 * 
 * A typical use case would be to have each tag value correspond to a layer in the
 * world.
 * 
 * Duration() may 
 */
public interface Animation extends Serializable {
	
	/** 
	 * Set the tag to this if you actually don't care about the tag of your animation.
	 * 
	 * This will happen quite often.
	 */
	public static final int TAG_IS_NOT_IMPORTANT = -42;

	public interface Observer {
		void onAnimationFinished(final int tag);
	}
	
	public abstract void perform(final World target, Observer obs);
		
	public abstract int tag();
}
