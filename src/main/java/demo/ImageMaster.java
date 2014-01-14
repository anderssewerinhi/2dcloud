package demo;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.ImageAnimation;
import com.humaninference.tagcloud.Master;

import demo.simpleapplication.ImageClient;

public class ImageMaster implements Master {

	private final List<Client> clients = new LinkedList<Client>();
	
	private final static int NUM_CLIENTS = 2;
	
	private int numClientsReady = 0;
	
	public ImageMaster() {
		for (int i = 0; i < NUM_CLIENTS; ++i) {
			final ImageClient imageClient = new ImageClient();
			imageClient.setMaster(this);
			clients.add(imageClient);
		}
	}
	
	public synchronized void clientIsReady() throws RemoteException  {
		if (++numClientsReady >= NUM_CLIENTS) {
			numClientsReady = 0;
			final Client c1 = clients.get(1);
			c1.setViewport(100.0, 0);
			
			startAnAnimation();
		}
	}

	public synchronized void animationIsFinished(final int tag) throws RemoteException  {
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
	
	public final static void main(final String... args) {
		
		new ImageMaster();
		
		
	}

}
