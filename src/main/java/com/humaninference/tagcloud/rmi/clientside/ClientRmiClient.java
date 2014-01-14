package com.humaninference.tagcloud.rmi.clientside;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.rmi.Constants;


public class ClientRmiClient implements Client {
	
	private final Client remote;
	
	public ClientRmiClient(final String locationOfRemoteClient) throws RemoteException, NotBoundException {
		final Registry registry = LocateRegistry.getRegistry(locationOfRemoteClient, Constants.RMI_PORT_CLIENT);
		remote = (Client) registry.lookup(Constants.RMI_CLIENT_NAME);
	}

	public void performAnimation(Animation animation) {
		remote.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) {
		remote.setViewport(xTopLeft, yTopLeft);
	}

}
