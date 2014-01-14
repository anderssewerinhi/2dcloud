package com.humaninference.tagcloud.rmi.demo;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.clientside.MasterRmiClient;
import com.humaninference.tagcloud.rmi.serverside.ClientRmiServer;

import demo.simpleapplication.ImageClient;

public class ClientNode implements Client {
	
	private final Client wrapped;
	
	private final Master remoteMaster;
	
	public ClientNode(final String masterAddress) throws RemoteException, NotBoundException {
		remoteMaster = new MasterRmiClient(masterAddress);
		final ImageClient client = new ImageClient();
		client.setMaster(remoteMaster);
		wrapped = client;
	}
	
	private void tellMasterCLientIsReady() {
		remoteMaster.clientIsReady(); 
	}
	
	public void performAnimation(Animation animation) {
		wrapped.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) {
		wrapped.setViewport(xTopLeft, yTopLeft);
	}

	public static final void main(final String... args) throws RemoteException, NotBoundException, AlreadyBoundException {
		
		startClientNode("192.168.1.102"); // Master is on the Window slaptop
		
		
	}

	private static void startClientNode(final String masterLocation)
			throws RemoteException, NotBoundException, AlreadyBoundException {
		// Create a client node that can be exposed with RMI for the master to find
		final ClientNode client = new ClientNode(masterLocation);
		
		final ClientRmiServer server = new ClientRmiServer() {
			
			@Override
			protected Client makeClient() {
				return client;
			}
		};
		
		server.startServer(); // Now the remote master can find us...
		
		client.tellMasterCLientIsReady(); // ..so tell remote master to go ahead
	}

}
