package com.humaninference.tagcloud.rmi.demo;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;
import com.humaninference.tagcloud.rmi.baseforrunnables.RemotablePFrameClient;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;
import com.humaninference.tagcloud.rmi.serverside.ClientRmiServer;
import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;

import demo.simpleapplication.ImageWorld;


public class ClientNode {
	
	private static final void createClientFromConfig(final ClientConfiguration config) throws RemoteException, NotBoundException, AlreadyBoundException {
		// The remote Master who will kick off the animations
		final Master rmiMaster = RemoteInstanceFactory.RMI_FACTORY.makeMaster(config.getMasterHostname());
		
		// Create a client node that can be exposed with RMI for the master to find
		// Needs a reference to the master, so it can tell the master that it's ready to accept animation requests
		// The client node will create a wrapped pFrame on itself. The coupling is a bit tight - sorry!
		final RemotablePFrameClient client = 
				new RemotablePFrameClient(
						rmiMaster, new ImageWorld(), config.runAsFullScreen(), config.getOurRmiPort(), 
						config.getOurRmiServiceName(), config.getOurHumanReadableName());
		
		// Expose it over RMI by wrapping it in a ClientRmiServer
		final ClientRmiServer server = new ClientRmiServer(client, config.getOurRmiPort(), config.getOurRmiServiceName());
		
		// ...then tell the RMI wrapper for my client to start up the RMI bits...
		server.startServer(); 
		
		// Now the remote master can find us...
		client.serverIsReady();

	}

	public static final void main(final String... args) throws RemoteException, NotBoundException, AlreadyBoundException {
		
		if (args.length < 1) {
			throw new RuntimeException(
					"You need to supply the IP address of the master as an argument");
		}
		
		final ClientConfiguration config = new ClientConfiguration() {
			
			@Override
			public boolean runAsFullScreen() {
				return true;
			}
			
			@Override
			public String getOurRmiServiceName() {
				return Constants.RMI_CLIENT_NAME;
			}
			
			@Override
			public int getOurRmiPort() {
				return Constants.RMI_PORT_CLIENT;
			}
			
			@Override
			public int getMasterRmiPort() {
				return Constants.RMI_PORT_MASTER;
			}
			
			@Override
			public String getMasterHostname() {
				return args[0];
			}

			@Override
			public String getOurHumanReadableName() {
				return "PLEASE USE A REAL CONFIGURATION FILE!!!";
			}
		};
		
		createClientFromConfig(config);
		/*
		// The remote Master who will kick off the animations
		final Master rmiMaster = RemoteInstanceFactory.RMI_FACTORY.makeMaster(args[0]);
		
		// Create a client node that can be exposed with RMI for the master to find
		// Needs a reference to the master, so it can tell the master that it's ready to accept animation requests
		// The client node will create a wrapped pFrame on itself. The coupling is a bit tight - sorry!
		final RemotablePFrameClient client = new RemotablePFrameClient(rmiMaster, new ImageWorld(), false);
		
		// Expose it over RMI by wrapping it in a ClientRmiServer
		final ClientRmiServer server = new ClientRmiServer(client);
		
		// ...then tell the RMI wrapper for my client to start up the RMI bits...
		server.startServer(); 
		
		// Now the remote master can find us...
		client.serverIsReady();
		*/
	}

}
