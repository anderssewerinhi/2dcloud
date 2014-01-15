package com.humaninference.tagcloud;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 
 * @author andersprivat
 *
 * A very simple animation client. 
 * 
 */
public interface Client extends Remote {
	
	/**
	 * Perform this animation, then notify the Master that the animation is finished.
	 *  
	 * @param animation The animation to perform.
	 * @throws RemoteException The "usual" RMI reasons.
	 */
	void performAnimation(final Animation animation) throws RemoteException  ;
	
	/**
	 * This may be overkill, but it's a nice feature that the master can set the
	 * viewport for the client.
	 * 
	 * @param xTopLeft X position of top left corner of the view port on the world.
	 * @param yTopLeft Y position of top left corner of the view port on the world
	 * @throws RemoteException The "usual" RMI reasons.
	 */
	void setViewport(final double xTopLeft, final double yTopLeft) throws RemoteException  ;

}
