package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.apache.log4j.xml.DOMConfigurator;

import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;
import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.implementation.SetClientConfiguration;

public class RunMasterAndSlaveOnLocalhost {

	 
	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
		
		final String filePath = "file:".concat(args[0]);
		
		final String configFromJar = "file:".concat(args[1]);
		
		System.out.println("file is" + filePath );
		
		if (new File("log4j-configure.xml").exists()) {
			DOMConfigurator.configure("log4j-configure.xml");
		} else {
			final String currentDir = new File(".").getAbsolutePath();
			System.out.println("No log4j config file found in " + currentDir);
		}
        SwingUtilities.invokeLater(
        		new Runnable() {
        			public void run() {
        				try {
							MasterNode.main("1",configFromJar);
							
							final ClientConfiguration cfg = SetClientConfiguration.configFromDefaultConfig(filePath);
							final SetClientConfiguration overridable = new SetClientConfiguration(cfg);
							overridable.setMasterHostname("localhost");
	        				ClientNode.createClientFromConfig(overridable, configFromJar);
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
