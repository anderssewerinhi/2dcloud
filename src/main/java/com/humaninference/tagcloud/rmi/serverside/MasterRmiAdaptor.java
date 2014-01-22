package com.humaninference.tagcloud.rmi.serverside;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.humaninference.tagcloud.Master;

/**
 * 
 * @author andersprivat
 *
 * An RMI facade for a Master running on the master node.
 * 
 * This allows us to test a Master implementation locally without the whole RMI setup.
 * 
 */
public class MasterRmiAdaptor extends UnicastRemoteObject implements Master {
	
	private static final long serialVersionUID = 1L;
	
	private final Master wrapped;
	
	public MasterRmiAdaptor(final Master wrapped) throws RemoteException {
		this.wrapped = wrapped;
	}
	
	public void clientIsReady(final String clientAddress) throws RemoteException  {
		wrapped.clientIsReady(clientAddress);
	}

	public void animationIsFinished(int tag) throws RemoteException {
		wrapped.animationIsFinished(tag);
	}

}
