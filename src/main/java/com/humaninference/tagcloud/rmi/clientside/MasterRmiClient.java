package com.humaninference.tagcloud.rmi.clientside;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;


public class MasterRmiClient implements Master {
	
	private final Master remote;
	
	public MasterRmiClient(final String locationOfRemoteClient) throws RemoteException, NotBoundException {
		final Registry registry = LocateRegistry.getRegistry(locationOfRemoteClient, Constants.RMI_PORT_MASTER);
		remote = (Master) registry.lookup(Constants.RMI_MASTER_NAME);
	}

	public void clientIsReady() throws RemoteException {
		remote.clientIsReady();
	}

	public void animationIsFinished(int tag) throws RemoteException {
		remote.animationIsFinished(tag);
	}


}
