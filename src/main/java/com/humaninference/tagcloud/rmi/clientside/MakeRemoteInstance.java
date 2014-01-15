package com.humaninference.tagcloud.rmi.clientside;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;

public class MakeRemoteInstance {
	
	public static Client makeClient(final String locationOfRemoteClient) throws RemoteException, NotBoundException {
		final Registry registry = LocateRegistry.getRegistry(locationOfRemoteClient, Constants.RMI_PORT_CLIENT);
		return (Client) registry.lookup(Constants.RMI_CLIENT_NAME);
	}
	
	public static Master makeMaster(final String locationOfRemoteClient) throws RemoteException, NotBoundException {
		final Registry registry = LocateRegistry.getRegistry(locationOfRemoteClient, Constants.RMI_PORT_MASTER);
		return (Master) registry.lookup(Constants.RMI_MASTER_NAME);
	}
}
