package com.humaninference.tagcloud;

/**
 * 
 * @author andersprivat
 *
 * An interface for an animation. 
 * 
 * Note tag() method - this allows the recipient of callback onAnimationFInished to 
 * coordinate several threads of animations if so desired.
 * 
 * Duration() may 
 */
public interface Animation {

	public interface Observer {
		void onAnimationFinished(final int tag);
	}
	
	public abstract void perform(final World target, Observer obs);
		
	public abstract int tag();
}
