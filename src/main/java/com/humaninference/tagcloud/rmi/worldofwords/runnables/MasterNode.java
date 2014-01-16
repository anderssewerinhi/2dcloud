package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Random;

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
	
	// We like repoducable randomness while testing
	private final Random rnd = new Random(667); 

	public MasterNode(final String... clientLocations) throws RemoteException {
		super(clientLocations, RemoteInstanceFactory.RMI_FACTORY);
		worldOfWords = DataForWorld.makeRepoducablyRandomWorld();
	}
	
	@Override
	protected Animation makeNextAnimation(final int tag) {
		// I am gonna cheat and just pick a random word from the list... for now.
		final int idxOfRandomWord = rnd.nextInt(DataForWorld.WORDS.length);
		final String wordToPop = DataForWorld.WORDS[idxOfRandomWord];
		return worldOfWords.popWord(wordToPop);
	}

	@Override
	protected void setViewports(List<Client> clients) throws RemoteException {
		System.out.println("Shifting first clients viewport 100 pixels to the right");
		clients.get(0).setViewport(100.0, 0);
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
