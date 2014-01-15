package com.humaninference.tagcloud.rmi.clientside;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;


/**
 * 
 * @author andersprivat
 *
 * An easily mockable interface for the class factory for the family of related "remote" connections.
 * 
 * In actual use it will usually be the RMI factory that is used...
 */
public interface RemoteInstanceFactory {

	public Client makeClient(final String locationOfRemoteClient) throws RemoteException, NotBoundException;
	
	public Master makeMaster(final String locationOfRemoteMaster) throws RemoteException, NotBoundException;
	
	public static final RemoteInstanceFactory RMI_FACTORY = new RemoteInstanceFactory() {

		@Override
		public Client makeClient(String locationOfRemoteClient)
				throws RemoteException, NotBoundException {
			final Registry registry = LocateRegistry.getRegistry(locationOfRemoteClient, Constants.RMI_PORT_CLIENT);
			return (Client) registry.lookup(Constants.RMI_CLIENT_NAME);
		}

		@Override
		public Master makeMaster(String locationOfRemoteMaster)
				throws RemoteException, NotBoundException {
			final Registry registry = LocateRegistry.getRegistry(locationOfRemoteMaster, Constants.RMI_PORT_MASTER);
			return (Master) registry.lookup(Constants.RMI_MASTER_NAME);
		}
		
	};

}
