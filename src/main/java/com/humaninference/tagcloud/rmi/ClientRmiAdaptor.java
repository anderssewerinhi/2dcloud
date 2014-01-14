package com.humaninference.tagcloud.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;

/**
 * 
 * @author andersprivat
 * 
 * Wraps a Client object in an unicasting RMI thingamajig.
 * 
 */
public class ClientRmiAdaptor extends UnicastRemoteObject implements Client {

	private final Client wrapped;

	protected ClientRmiAdaptor(final Client wrapped) throws RemoteException {
		super();
		this.wrapped = wrapped;
	}

	private static final long serialVersionUID = 1L;

	public void performAnimation(final Animation animation) {
		wrapped.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) {
		wrapped.setViewport(xTopLeft, yTopLeft);
	}

}
