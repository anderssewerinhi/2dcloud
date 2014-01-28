package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;
import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.implementation.SetClientConfiguration;

public class RunMasterAndSlaveOnLocalhost {
	
	private final static Logger logger = Logger.getLogger(RunMasterAndSlaveOnLocalhost.class);

	 
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {

		if (new File("log4j-configure.xml").exists()) {
			DOMConfigurator.configure("log4j-configure.xml");
		} else {
			final String currentDir = new File(".").getAbsolutePath();
			System.out.println("No log4j config file found in " + currentDir);
		}

		final String worldModelSpringFile;
		final String clientConfigurationSpringFile;
		if (args.length != 2) {
			System.out.println("Use arguments <spring file for client configuration> <spring file for world model> to override defaults");
			clientConfigurationSpringFile = "file:spring-config-client.xml";
			worldModelSpringFile = "file:spring-config-world.xml";
		} else {
			clientConfigurationSpringFile = "file:".concat(args[0]);
			worldModelSpringFile = "file:".concat(args[1]);
		}
		
		logger.debug("Loading client configuration from file '" + clientConfigurationSpringFile + "'");
		logger.debug("Loading world model from file '" + worldModelSpringFile + "'");
		
        SwingUtilities.invokeLater(
        		new Runnable() {
        			public void run() {
        				try {
							MasterNode.startMasterWithParameters("1",worldModelSpringFile);
							
							final ClientConfiguration cfg = SetClientConfiguration.configFromUrl(clientConfigurationSpringFile);
							final SetClientConfiguration overridable = new SetClientConfiguration(cfg);
							overridable.setMasterHostname("localhost"); // NB: Only used in this test example
	        				ClientNode.createClientFromConfig(overridable, worldModelSpringFile);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (AlreadyBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (NotBoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			}
        		}
        		);
	}

}
