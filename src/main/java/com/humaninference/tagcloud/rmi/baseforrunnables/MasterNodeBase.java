package com.humaninference.tagcloud.rmi.baseforrunnables;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;

public abstract class MasterNodeBase extends UnicastRemoteObject {
	
	static Logger logger = Logger.getLogger(MasterNodeBase.class);

	protected static final int FIRST_ANIMATION_TAG = -1;
	private static final long serialVersionUID = 1L;
	private final List<Client> clients = new LinkedList<Client>();

	protected abstract Animation makeNextAnimation(final int tag);

	private int numClientsReady = 0;
	protected final List<String> clientLocations = new LinkedList<String>();
	protected final List<String> clientHumanReadableNames = new LinkedList<String>();
	
	private final RemoteInstanceFactory clientFactory;
	protected final int numClientsToExpect;

	public MasterNodeBase(final int numClientsToExpect, final RemoteInstanceFactory clientFactory ) throws RemoteException {
		super();
		this.numClientsToExpect = numClientsToExpect;
		this.clientFactory = clientFactory;
	}


	public synchronized void clientIsReady(final String clientAddress, final int clientPortNumber, final String clientServiceName, final String humanReadableClientName) throws RemoteException {
		logger.trace("Got the message that a client is ready (had " + numClientsReady + " ready clients before)");
		clientLocations.add(clientAddress);
		clientHumanReadableNames.add(humanReadableClientName);
		if (++numClientsReady >= numClientsToExpect) {
			logger.trace("Creating the remote client references now");
			logger.trace("Locations for clients are: ");
			for (final String loc : clientLocations) {
				logger.trace(loc);;
			}
			// OK, all the clients have a reference to us, and have checked in.
			// Now we can create references to them using the well-known addresses.
			for (final String address : clientLocations) {
				try {
					logger.trace("Trying to create RMI client for " + address + " on port " + clientPortNumber + "...");
					final Client remoteClient = clientFactory.makeClient(address, clientPortNumber, clientServiceName);
					logger.trace("Done creating remote client for " + address + " on port " + clientPortNumber + "...");
					clients.add(remoteClient);
				} catch (RemoteException e) {
					throw new RuntimeException("Can't RMI to client on " + address, e);
				} catch (NotBoundException e) {
					throw new RuntimeException("Can't RMI to client on " + address, e);
				}
			}
			
			// Great! We are good to go!
			numClientsReady = 0;
			
			setViewports(clients);
			logger.trace("Will start the first animation now");
			startAnAnimation(FIRST_ANIMATION_TAG);
			logger.trace("First animation started");
		}
	}

	protected abstract void setViewports(final List<Client> clients)  throws RemoteException ;
	
	public synchronized void animationIsFinished(final int tag)
			throws RemoteException {
				if (++numClientsReady >= numClientsToExpect) {
					numClientsReady = 0;
					startAnAnimation(tag);
				}
			}

	private synchronized void startAnAnimation(final int tag)
			throws RemoteException {
				final Animation imgAnim = makeNextAnimation(tag);
				logger.trace("Animation ready - about to send to " + clients.size() + " clients");
				for (final Client c : clients) {
					logger.trace("Sending...");
					c.performAnimation(imgAnim);
					logger.trace("Sent");
				}
				logger.trace("Done sending animation");
			}

}