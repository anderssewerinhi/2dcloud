package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.rmi.baseforrunnables.RemotablePFrameClient;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;
import com.humaninference.tagcloud.rmi.serverside.ClientRmiServer;

import demo.simpleapplication.ImageWorld;


public class ClientNode {
	

	public static final void main(final String... args) throws RemoteException, NotBoundException, AlreadyBoundException {
		
		if (args.length < 1) {
			throw new RuntimeException(
					"You need to supply the IP address of the master as an argument");
		}
		
		// The remote Master who will kick off the animations
		final Master rmiMaster = RemoteInstanceFactory.RMI_FACTORY.makeMaster(args[0]);
		
		// Create a client node that can be exposed with RMI for the master to find
		// Needs a reference to the master, so it can tell the master that it's ready to accept animation requests
		// The client node will create a wrapped pFrame on itself. The coupling is a bit tight - sorry!
		
		final World world = DataForWorld.makeRepoducablyRandomWorld().makeWorld();
		final RemotablePFrameClient client = new RemotablePFrameClient(rmiMaster, world);
		
		// Expose it over RMI by wrapping it in a ClientRmiServer
		final ClientRmiServer server = new ClientRmiServer(client);
		
		// ...then tell the RMI wrapper for my client to start up the RMI bits...
		server.startServer(); 
		
		// Now the remote master can find us...
		client.serverIsReady();
	}

}
