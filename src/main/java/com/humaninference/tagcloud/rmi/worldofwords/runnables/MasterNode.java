package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.rmi.baseforrunnables.MasterNodeBase;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;
import com.humaninference.tagcloud.rmi.serverside.MasterRmiServer;
import com.humaninference.tagcloud.worldofwords.WorldOfWords;

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
	
	private static final long serialVersionUID = 1L;
		
	private final WorldOfWords worldOfWords; 
	
	private int currentNode = 0; 
	
	private final static Logger logger = Logger.getLogger(MasterNode.class);
	
	public MasterNode(final int numClientsToExpect, String filePath ) throws RemoteException {
		super(numClientsToExpect, RemoteInstanceFactory.RMI_FACTORY);
		worldOfWords = DataForWorld.makeRepoducablyRandomWorld(filePath);
	 
	}
	
	@Override
	protected Animation makeNextAnimation(final int tag) {
		// I am gonna cheat and just pick a random word from the list... for now.
		//final int idxOfRandomWord = rnd.nextInt(DataForWorld.WORDS.length);
		//final String wordToPop = DataForWorld.WORDS[idxOfRandomWord];
	    currentNode = worldOfWords.getNextNode(currentNode); 
		return worldOfWords.popWord(currentNode);
	}

	@Override
	protected void setViewports(List<Client> clients) throws RemoteException {
//		final double totalWidth = numClientsToExpect * BEAMER_WIDTH_PIXELS + (numClientsToExpect - 1) * BEAMER_SPACING_PIXELS;
		System.err.println("***** REMOVE THIS ONCE DEBUGGING IS OVER ***** Shifting first clients viewport 3000 pixels to the right");
		clients.get(0).setViewport(1920.0 * 2.0, 0);
	}

	public static void main(final String... args)
			throws RemoteException, AlreadyBoundException {
		if (new File("log4j-configure.xml").exists()) {
			DOMConfigurator.configure("log4j-configure.xml");
		} else {
			final String currentDir = new File(".").getAbsolutePath();
			System.out.println("No log4j config file found in " + currentDir);
		}

		startMasterWithParameters(args);
	}

	public static void startMasterWithParameters(final String... args)
			throws RemoteException, AlreadyBoundException {
		final int numberOfClients;
		final String fileNameOfWorldModel;
		if (args.length == 0) {
			numberOfClients = 1;
			fileNameOfWorldModel = "file:spring-config-world.xml";
		} else {
			if (args.length != 2) {
				throw new RuntimeException("Usage: <number of clients to expect> <file name for world definition>");
			}
			numberOfClients = Integer.parseInt(args[0]);
			fileNameOfWorldModel = args[1];
		}
		logger.trace("Expecting " + numberOfClients + " clients");
		logger.trace("Loading world definition from file '" + fileNameOfWorldModel + "'");
		final MasterNode node = new MasterNode(numberOfClients, fileNameOfWorldModel);
		final MasterRmiServer server = new MasterRmiServer(node);
		server.startServer();
	}

	


}
