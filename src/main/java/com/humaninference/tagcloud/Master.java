package com.humaninference.tagcloud;

/**
 * 
 * @author andersprivat
 *
 * A very simple master. 
 * 
 * I assume that
 *   - The clients all have well-known addresses
 *   - The clients will call a notify method once they are done with an animation
 *   - The clients will all need to have told the master that they are ready before
 *     animation starts.
 */
public interface Master {
	
	/**
	 * No reference needed - the master knows, and only needs to count the number of
	 * ready clients.
	 */
	void clientIsReady();
	
	/**
	 * All clients notify the master when the current animation is done. This way we 
	 * can try to combat clock drift by having the clients synch up every once in a
	 * while.
	 */
	void animationIsFinished();

}
