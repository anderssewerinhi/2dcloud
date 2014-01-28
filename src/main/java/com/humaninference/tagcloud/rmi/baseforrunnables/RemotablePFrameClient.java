package com.humaninference.tagcloud.rmi.baseforrunnables;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.implementations.PFrameClient;

public class RemotablePFrameClient extends UnicastRemoteObject implements PFrameClient.Observer, Client {

	static Logger logger = Logger.getLogger(RemotablePFrameClient.class);
			
	private static final long serialVersionUID = 1L;
	protected final Client wrapped;
	protected final Master remoteMaster;
	private boolean serverIsReady = false;
	private boolean imageClientIsReady = false;
	private final int rmiPortClient;
	private final String rmiClientName;
	private final String ourHumanReadableName;
	int animationIdx = 0;

	private final String rmiIpAddress;

	public RemotablePFrameClient(final Master remoteMaster, final World world, 
			final boolean runAsFullScreen, final int rmiPortClient, final String rmiIpAddress, final String rmiClientName, final String ourHumanReadableName) throws RemoteException, NotBoundException {
		super();
		this.remoteMaster = remoteMaster;
		this.rmiPortClient = rmiPortClient;
		this.rmiClientName = rmiClientName;
		this.ourHumanReadableName = ourHumanReadableName;
		this.rmiIpAddress = rmiIpAddress;
		logger.trace("Got a master reference");
		// Create the wrapped client that will do the actual graphics 
		// (needs a reference to the master...)...
		wrapped = new PFrameClient("Image client", this, world, remoteMaster, runAsFullScreen);
		logger.trace("Created wrapped image client");

	}
	
	private void tellMasterClientIsReady() throws RemoteException {
		logger.trace("Telling master that this client is ready");
		remoteMaster.clientIsReady(rmiIpAddress, rmiPortClient, rmiClientName, ourHumanReadableName); 
		logger.trace("Master should now know that this client is ready");
	}

	public void performAnimation(Animation animation) throws RemoteException {
		logger.trace(String.format("Got an animation request (#%d)", ++animationIdx));
		wrapped.performAnimation(animation);
	}

	public void setViewport(double xTopLeft, double yTopLeft) throws RemoteException {
		wrapped.setViewport(xTopLeft, yTopLeft);
	}

	public synchronized void serverIsReady() {
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