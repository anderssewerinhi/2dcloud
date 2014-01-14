package com.humaninference.tagcloud.rmi.serverside;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.rmi.Constants;

/**
 * 
 * @author andersprivat
 *
 * This  class starts an instance of a Client implementation, and exposes it
 * on the well-known port, with the well-known name.
 */
public abstract class ClientRmiServer {

	protected abstract Client makeClient();
	
	public void startServer() throws RemoteException, AlreadyBoundException {
		final Client impl = new ClientRmiAdaptor(makeClient());
		final Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT_CLIENT);
		registry.bind(Constants.RMI_CLIENT_NAME, impl);
		System.out.println("Start is started");
	}
	

}
