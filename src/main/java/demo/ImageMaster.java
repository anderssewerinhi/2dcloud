package demo;

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
	
	public synchronized void clientIsReady() {
		if (++numClientsReady >= NUM_CLIENTS) {
			final Client c1 = clients.get(1);
			c1.setViewport(100.0, 0);
			
			startRandomMovements();
		}
	}

	public void animationIsFinished() {
		// TODO Auto-generated method stub

	}
	
	private void startRandomMovements() {
		final Random rnd = new Random();
		final TimerTask task = new TimerTask() {

			@Override
			public void run() {
				final double newX = 200.0 * rnd.nextDouble();
				final double newY = 200.0 * rnd.nextDouble();
				final long duration = rnd.nextInt(500);
				final Animation imgAnim = new ImageAnimation(0, newX, newY, duration);
				for (final Client c : clients) {
					c.performAnimation(imgAnim);
				}
			}
			
		};
		
		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	public final static void main(final String... args) {
		
		new ImageMaster();
		
		
	}

}
