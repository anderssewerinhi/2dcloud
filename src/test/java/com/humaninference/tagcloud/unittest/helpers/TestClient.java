package com.humaninference.tagcloud.unittest.helpers;

import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;

public class TestClient implements Client {

	public Animation lastAnimation = null;
	
	public double vpX;
	
	public double vpY;
	
	@Override
	public void performAnimation(Animation animation)
			throws RemoteException {
		lastAnimation = animation;
	}

	@Override
	public void setViewport(double xTopLeft, double yTopLeft)
			throws RemoteException {
		vpX = xTopLeft;
		vpY = yTopLeft;
	}
	
}