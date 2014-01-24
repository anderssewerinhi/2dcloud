package com.humaninference.tagcloud;

import java.rmi.Remote;
import java.rmi.RemoteException;



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
public interface Master extends Remote {
	
	/**
	 * No reference needed - the master knows, and only needs to count the number of
	 * ready clients.
	 * 
	 * @throws RemoteException The "usual" RMI reasons.
	 */
	void clientIsReady(final String addressOfClient, final int portOfClient, final String clientServiceName, final String clientHumanReadableId) throws RemoteException ;
	
	/**
	 * All clients notify the master when the current animation is done. This way we 
	 * can try to combat clock drift by having the clients synch up every once in a
	 * while.
	 * 
	 * @param tag The tag of the animation that finished
	 * @throws RemoteException The "usual" RMI reasons.
	 */
	void animationIsFinished(final int tag) throws RemoteException;

}
