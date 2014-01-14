package com.humaninference.tagcloud.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Client;


public abstract class ClientRmiServer {

	protected abstract Client makeClient();
	
	public void startServer() throws RemoteException, AlreadyBoundException {
		final ClientRmiAdaptor impl = new ClientRmiAdaptor(makeClient());
		final Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT_CLIENT);
		registry.bind(Constants.CLIENT_NAME, impl);
		System.out.println("Start is started");
	}
	

}
