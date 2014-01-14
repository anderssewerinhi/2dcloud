package com.humaninference.tagcloud;

import java.rmi.Remote;

/**
 * 
 * @author andersprivat
 *
 * A very simple animation client. 
 * 
 * We assume that the master is on a well-known location.
 * 
 * 
 */
public interface Client extends Remote {
	
	/**
	 * Perform this animation, then notify the Master that the animation is finished.
	 *  
	 * @param animation
	 */
	void performAnimation(final Animation animation);
	
	/**
	 * This may be overkill, but it's a nice feature that the master can set the
	 * viewport for the cliente.
	 * 
	 * @param xTopLeft X position of top left corner of the view port on the world.
	 * @param yTopLeft Y position of top left corner of the view port on the world
	 */
	void setViewport(final double xTopLeft, final double yTopLeft);

}
