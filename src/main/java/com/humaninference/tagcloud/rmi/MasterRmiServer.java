package com.humaninference.tagcloud.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.humaninference.tagcloud.Master;


public abstract class MasterRmiServer {

	protected abstract Master makeMaster();
	
	public void startServer() throws RemoteException, AlreadyBoundException {
		final MasterRmiAdaptor impl = new MasterRmiAdaptor(makeMaster());
		final Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT_MASTER);
		registry.bind(Constants.MASTER_NAME, impl);
		System.out.println("Start is started");
	}
	

}
