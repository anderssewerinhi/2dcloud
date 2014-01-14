package com.humaninference.tagcloud.rmi.demo;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.ImageAnimation;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.Constants;
import com.humaninference.tagcloud.rmi.clientside.ClientRmiClient;
import com.humaninference.tagcloud.rmi.serverside.MasterRmiServer;

/**
 * 
 * @author andersprivat
 *
 * Must be started first! 
 * 
 * Must be exposed on a well-known address via a MasterRmiServer instance!
 * 
 * Waits for the clients to hook up, then creates RMI connections 
 * to them and starts animation.
 * 
 */
public class MasterNode extends UnicastRemoteObject implements Master {
	
	private static final long serialVersionUID = 1L;

	private final List<Client> clients = new LinkedList<Client>();
	
	private final String clientLocations[];
	
	private final static int NUM_CLIENTS = 1; // Was 2
	
	private int numClientsReady = 0;
	
	public MasterNode(final String... clientLocations) throws RemoteException {
		super();
		this.clientLocations = clientLocations;
	}
	
	public synchronized void clientIsReady() throws RemoteException  {
		if (++numClientsReady >= NUM_CLIENTS) {
			// OK, all the clients have a reference to us, and have checked in.
			// Now we can create references to them using the well-known addresses.
			for (final String address : clientLocations) {
				try {
					final Client remoteClient = new ClientRmiClient(address);
					clients.add(remoteClient);
				} catch (RemoteException e) {
					throw new RuntimeException("Can't RMI to client on " + address, e);
				} catch (NotBoundException e) {
					throw new RuntimeException("Can't RMI to client on " + address, e);
				}
			}
			
			// Great! We are good to go!
			
			numClientsReady = 0;
			/*
			final Client c1 = clients.get(1);
			c1.setViewport(100.0, 0);
			*/
			startAnAnimation();
		}
	}

	public synchronized void animationIsFinished(final int tag) throws RemoteException {
		if (++numClientsReady >= NUM_CLIENTS) {
			numClientsReady = 0;
			startAnAnimation();
		}
	}
	
	private synchronized void startAnAnimation() throws RemoteException {
		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
		final Animation imgAnim = new ImageAnimation(0, newX, newY, duration, 0);
		for (final Client c : clients) {
			c.performAnimation(imgAnim);
		}
	}
	
	public static void main(final String... clientLocations)
			throws RemoteException, AlreadyBoundException {
		final MasterNode node = new MasterNode(clientLocations);
		final MasterRmiServer server = new MasterRmiServer(node);
		server.startServer();
	}
	


}
