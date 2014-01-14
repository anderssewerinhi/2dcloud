package com.humaninference.tagcloud.rmi.serverside;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;

/**
 * 
 * @author andersprivat
 *
 * This class starts an instance of a Master implementation, and exposes it
 * on the well-known port, with the well-known name.
 * 
 */
public abstract class MasterRmiServer {

	protected abstract Master makeMaster();
	
	public void startServer() throws RemoteException, AlreadyBoundException {
		final RemoteMaster impl = new MasterRmiAdaptor(makeMaster());
		final Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT_MASTER);
		registry.bind(Constants.RMI_MASTER_NAME, impl);
		System.out.println("Start is started");
	}
	

}
