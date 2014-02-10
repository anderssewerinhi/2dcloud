package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

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
        if (new File("log4j-configure.xml").exists()) {
            DOMConfigurator.configure("log4j-configure.xml");
        } else {
            final String currentDir = new File(".").getAbsolutePath();
            System.out.println("No log4j config file found in " + currentDir);
        }
        startClientWithParameters(args);
    }

	public static void startClientWithParameters(final String... args)
			throws RemoteException, AlreadyBoundException, NotBoundException {
		final String worldModelSpringFile; 
	    final String clientConfigurationSpringFile; 
		if (args.length == 2) {
			logger.trace("Loading config from " + args[0]);
			clientConfigurationSpringFile = "file:".concat(args[0]);
			worldModelSpringFile = "file:".concat(args[1]);
		} else {
			System.out.println("Use arguments <spring file for client configuration> <spring file for world model> to override defaults");
			logger.trace("Using default location for the config file");
			clientConfigurationSpringFile = "file:spring-config-client.xml";
			worldModelSpringFile = "file:spring-config-world.xml";
		}
		final ClientConfiguration config = SetClientConfiguration.configFromUrl(clientConfigurationSpringFile);
		System.out.println("Loading from " + clientConfigurationSpringFile);
		createClientFromConfig(config, worldModelSpringFile);
	}

	public static void createClientFromConfig(final ClientConfiguration config, final String worldModelSpringFile ) throws RemoteException, AlreadyBoundException, NotBoundException {
		// The remote Master who will kick off the animations
		final Master rmiMaster = RemoteInstanceFactory.RMI_FACTORY.makeMaster(config.getMasterHostname());
		logger.trace("Master is on host " + config.getMasterHostname());
		
		// Create a client node that can be exposed with RMI for the master to find
		// Needs a reference to the master, so it can tell the master that it's ready to accept animation requests
		// The client node will create a wrapped pFrame on itself. The coupling is a bit tight - sorry!
		
		final World world = DataForWorld.makeRepoducablyRandomWorld(worldModelSpringFile).makeWorld();
		
		SwingUtilities.invokeLater(
				new Runnable() {

					@Override
					public void run() {
						RemotablePFrameClient client;
						try {
							client = new RemotablePFrameClient(
									rmiMaster, world, config.runAsFullScreen(), config.getOurRmiPort(), config.getOurRMIIP(),
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
