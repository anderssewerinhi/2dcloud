package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RunMasterAndSlaveOnLocalhost {

	public static void main(String[] args) throws RemoteException, AlreadyBoundException, NotBoundException {
		MasterNode.main("localhost");
		ClientNode.main("localhost");
	}

}
