package com.humaninference.tagcloud.rmi.demo;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.PFrameClient;

public class RemotablePFrameClient extends UnicastRemoteObject implements PFrameClient.Observer, Client {

	private static final long serialVersionUID = 1L;
	protected final Client wrapped;
	protected final Master remoteMaster;
	private boolean serverIsReady = false;
	private boolean imageClientIsReady = false;
	int animationIdx = 0;

	public RemotablePFrameClient(final Master remoteMaster, final World world) throws RemoteException, NotBoundException {
		super();
		this.remoteMaster = remoteMaster;
		System.out.println("Got a master reference");
		// Create the wrapped client that will do the actual graphics 
		// (needs a reference to the master...)...
		wrapped = new PFrameClient("Image client", this, world, remoteMaster);
		System.out.println("Created wrapped image client");

	}
	
	private void tellMasterClientIsReady() throws RemoteException {
		System.out.println("Telling master that this client is ready");
		remoteMaster.clientIsReady(); 
		System.out.println("Master should now know that this client is ready");
	}

	public void performAnimation(Animation animation) throws RemoteException {
		System.out.println(String.format("Got an animation request (#%d)", ++animationIdx));
		wrapped.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) throws RemoteException {
		wrapped.setViewport(xTopLeft, yTopLeft);
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

	public synchronized void pframeClientIsReady() {
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