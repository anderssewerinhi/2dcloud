package com.humaninference.tagcloud.rmi.demo;

import java.awt.Color;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.implementations.ImageAnimation;
import com.humaninference.tagcloud.rmi.baseforrunnables.MasterNodeBase;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;
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
public class MasterNode extends MasterNodeBase implements Master {
	
	static Logger logger = Logger.getLogger(MasterNode.class);
	
	private static final long serialVersionUID = 1L;

	public MasterNode(final int numClientsToExpect) throws RemoteException {
		super(numClientsToExpect, RemoteInstanceFactory.RMI_FACTORY);
	}
	
	@Override
	protected Animation makeNextAnimation(final int tag) {
		final Random rnd = new Random();
		final double newX = 200.0 * rnd.nextDouble();
		final double newY = 200.0 * rnd.nextDouble();
		final long duration = rnd.nextInt(500) + 1; // No animations with duration 0
		final Animation imgAnim = new ImageAnimation(0, newX, newY, 1.0,  duration, 0);
		return imgAnim;
	}

	@Override
	protected void setViewports(List<Client> clients) throws RemoteException {
		logger.trace("Shifting first clients viewport 100 pixels to the right");
		clients.get(0).setViewport(100.0, 0);
	}

	public static void main(final String... args)
			throws RemoteException, AlreadyBoundException {
		final MasterNode node;
		if (args.length ==0) {
			node = new MasterNode(1);
		} else {
			if (args.length > 1) {
				throw new RuntimeException("Too many arguments - only need one, the number of clients to expect");
			}
			node = new MasterNode(Integer.parseInt(args[0]));
		}
		final MasterRmiServer server = new MasterRmiServer(node);
		server.startServer();
	}

	


}
