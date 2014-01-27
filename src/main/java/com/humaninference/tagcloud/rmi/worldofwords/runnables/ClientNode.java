package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.humaninference.tagcloud.Master;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.rmi.baseforrunnables.RemotablePFrameClient;
import com.humaninference.tagcloud.rmi.clientside.RemoteInstanceFactory;
import com.humaninference.tagcloud.rmi.serverside.ClientRmiServer;
import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;
import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.implementation.SetClientConfiguration;


public class ClientNode {
	
	private static final Logger logger = Logger.getLogger(ClientNode.class);
	


	public static final void main(final String... args) throws RemoteException, NotBoundException, AlreadyBoundException {
		
		
		String filePath = null; 
	    String configFromJar = null; 
		final ClientConfiguration config;
		
		if (args.length == 0) {
			logger.trace("Using default location for the config file");
			config = SetClientConfiguration.configFromDefaultConfig("spring-config-client.xml");
		} else {
			
			logger.trace("Loading config from " + args[0]);
			
			filePath = "file:".concat(args[0]);
			configFromJar = "file:".concat(args[1]);
			
			config = SetClientConfiguration.configFromUrl(filePath);
		}
		
		System.out.println("Loadding fomr " + configFromJar);
		createClientFromConfig(config, configFromJar);
	}

	public static void createClientFromConfig(final ClientConfiguration config, final String configFromJar ) throws RemoteException, AlreadyBoundException, NotBoundException {
		// The remote Master who will kick off the animations
		final Master rmiMaster = RemoteInstanceFactory.RMI_FACTORY.makeMaster(config.getMasterHostname());
		
		// Create a client node that can be exposed with RMI for the master to find
		// Needs a reference to the master, so it can tell the master that it's ready to accept animation requests
		// The client node will create a wrapped pFrame on itself. The coupling is a bit tight - sorry!
		
		final World world = DataForWorld.makeRepoducablyRandomWorld(configFromJar).makeWorld();
		
		SwingUtilities.invokeLater(
				new Runnable() {

					@Override
					public void run() {
						RemotablePFrameClient client;
						try {
							client = new RemotablePFrameClient(
									rmiMaster, world, config.runAsFullScreen(), config.getOurRmiPort(), 
									config.getOurRmiServiceName(), config.getOurHumanReadableName());
							// Expose it over RMI by wrapping it in a ClientRmiServer
							final ClientRmiServer server = new ClientRmiServer(client, config.getOurRmiPort(), config.getOurRmiServiceName());
							
							// ...then tell the RMI wrapper for my client to start up the RMI bits...
							server.startServer(); 
							
							// Now the remote master can find us...
							client.serverIsReady();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AlreadyBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					
				}
				);
		
	}

}
