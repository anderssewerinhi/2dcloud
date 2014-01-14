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
	
	private int numClientsReady = 0;
	
	public MasterNode(final String... clientLocations) throws RemoteException {
		super();
		this.clientLocations = clientLocations;
	}
	
	public synchronized void clientIsReady() throws RemoteException  {
		System.out.println("Got the message that a client is ready (had " + numClientsReady + " ready clients before)");

		if (++numClientsReady >= clientLocations.length) {
			System.out.println("Creating the remote client references now");
			System.out.println("Locations for clients are: " + clientLocations);
			// OK, all the clients have a reference to us, and have checked in.
			// Now we can create references to them using the well-known addresses.
			for (final String address : clientLocations) {
				try {
					System.out.println("For " + address + "...");
					final Client remoteClient = new ClientRmiClient(address);
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
			/*
			final Client c1 = clients.get(1);
			c1.setViewport(100.0, 0);
			*/
			System.out.println("Will start the first animation now");
			startAnAnimation();
			System.out.println("First animation started");
		}
	}

	public synchronized void animationIsFinished(final int tag) throws RemoteException {
		if (++numClientsReady >= clientLocations.length) {
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
		System.out.println("Animation ready - about to send to " + clients.size() + " clients");
		for (final Client c : clients) {
			System.out.println("Sending...");
			c.performAnimation(imgAnim);
			System.out.println("Sent");
		}
		System.out.println("Done sending animation");
	}
	
	public static void main(final String... args)
			throws RemoteException, AlreadyBoundException {
		final MasterNode node;
		if (args.length ==0) {
			node = new MasterNode("localhost");
		} else {
			node = new MasterNode(args);
		}
		final MasterRmiServer server = new MasterRmiServer(node);
		server.startServer();
	}
	


}
