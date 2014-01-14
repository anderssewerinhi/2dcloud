package com.humaninference.tagcloud.rmi.demo;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.clientside.MasterRmiClient;
import com.humaninference.tagcloud.rmi.serverside.ClientRmiServer;

import demo.simpleapplication.ImageClient;

public class ClientNode  extends UnicastRemoteObject implements Client, ImageClient.Observer {
	
	private static final long serialVersionUID = 1L;

	private final Client wrapped;
	
	private final Master remoteMaster;
	
	private boolean serverIsReady = false; 
	
	private boolean imageClientIsReady = false;
	
	public ClientNode(final String masterAddress) throws RemoteException, NotBoundException {
		super();
		remoteMaster = new MasterRmiClient(masterAddress);
		System.out.println("Got a master reference");
		final ImageClient client = new ImageClient(this);
		System.out.println("Created wrapped image client");
		client.setMaster(remoteMaster);
		wrapped = client;
	}
	
	private void tellMasterClientIsReady() throws RemoteException {
		System.out.println("Telling master that this client is ready");
		remoteMaster.clientIsReady(); 
		System.out.println("Master should now know that this client is ready");
	}
	
	public void performAnimation(Animation animation) throws RemoteException {
		System.out.println("Got an animation request");
		wrapped.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) throws RemoteException {
		wrapped.setViewport(xTopLeft, yTopLeft);
	}

	public static final void main(final String... args) throws RemoteException, NotBoundException, AlreadyBoundException {
		
		if (args.length == 0) {
			startClientNode("localhost"); // Master is on the Windows laptop
		} else {
			startClientNode(args[0]);
		}
		
		
	}

	private static void startClientNode(final String masterLocation)
			throws RemoteException, NotBoundException, AlreadyBoundException {
		// Create a client node that can be exposed with RMI for the master to find
		final ClientNode client = new ClientNode(masterLocation);
		
		final ClientRmiServer server = new ClientRmiServer(client);
		
		server.startServer(); // Now the remote master can find us...
		
		client.serverIsReady();
	}

	protected synchronized void serverIsReady() {
		serverIsReady = true;
		if (imageClientIsReady) {
			// ..so tell remote master to go ahead
			try {
				tellMasterClientIsReady();
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			} 
		}
		
	}
	public synchronized void imageClientIsReady() {
		imageClientIsReady = true;
		if (serverIsReady) {
			// ..so tell remote master to go ahead
			try {
				tellMasterClientIsReady();
			} catch (RemoteException e) {
				throw new RuntimeException(e);
			} 
		}		
	}

}
