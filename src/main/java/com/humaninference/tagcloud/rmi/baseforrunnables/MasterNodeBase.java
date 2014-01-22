package com.humaninference.tagcloud.rmi.baseforrunnables;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;

public abstract class MasterNodeBase extends UnicastRemoteObject {

	protected static final int FIRST_ANIMATION_TAG = -1;
	private static final long serialVersionUID = 1L;
	private final List<Client> clients = new LinkedList<Client>();

	protected abstract Animation makeNextAnimation(final int tag);

	private int numClientsReady = 0;
	protected final List<String> clientLocations = new LinkedList<String>();
	
	private final RemoteInstanceFactory clientFactory;
	private final int numClientsToExpect;

	public MasterNodeBase(final int numClientsToExpect, final RemoteInstanceFactory clientFactory ) throws RemoteException {
		super();
		this.numClientsToExpect = numClientsToExpect;
		this.clientFactory = clientFactory;
	}


	public synchronized void clientIsReady(final String clientAddress) throws RemoteException {
		System.out.println("Got the message that a client is ready (had " + numClientsReady + " ready clients before)");
		clientLocations.add(clientAddress);
		if (++numClientsReady >= numClientsToExpect) {
			System.out.println("Creating the remote client references now");
			System.out.println("Locations for clients are: ");
			for (final String loc : clientLocations) {
				System.out.println(loc);;
			}
			// OK, all the clients have a reference to us, and have checked in.
			// Now we can create references to them using the well-known addresses.
			for (final String address : clientLocations) {
				try {
					System.out.println("For " + address + "...");
					final Client remoteClient = clientFactory.makeClient(address);
					System.out.println("Done creating remote client for " + address);
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
			System.out.println("Will start the first animation now");
			startAnAnimation(FIRST_ANIMATION_TAG);
			System.out.println("First animation started");
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
				System.out.println("Animation ready - about to send to " + clients.size() + " clients");
				for (final Client c : clients) {
					System.out.println("Sending...");
					c.performAnimation(imgAnim);
					System.out.println("Sent");
				}
				System.out.println("Done sending animation");
			}

}