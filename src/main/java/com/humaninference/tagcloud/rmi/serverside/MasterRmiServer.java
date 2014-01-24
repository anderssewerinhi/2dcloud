package com.humaninference.tagcloud.rmi.serverside;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.apache.log4j.Logger;

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
public class MasterRmiServer {

	static Logger logger = Logger.getLogger(MasterRmiServer.class);
	
	private Master implementation;
	
	public MasterRmiServer(final Master implementation) {
		this.implementation = implementation;
	}
	
	public void startServer() throws RemoteException, AlreadyBoundException {
		final Registry registry = LocateRegistry.createRegistry(Constants.RMI_PORT_MASTER);
		registry.bind(Constants.RMI_MASTER_NAME, implementation);
		logger.trace("Master is started");
	}
	

}
