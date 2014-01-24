package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.io.File;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.SwingUtilities;

import org.apache.log4j.xml.DOMConfigurator;

public class RunMasterAndSlaveOnLocalhost {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
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
							MasterNode.main("1");
	        				ClientNode.main("localhost");
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
