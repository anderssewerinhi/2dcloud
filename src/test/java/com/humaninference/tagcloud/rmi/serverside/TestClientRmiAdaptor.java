package com.humaninference.tagcloud.rmi.serverside;

import java.rmi.RemoteException;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.Client;
import com.humaninference.tagcloud.unittest.helpers.TestAnimation;
import com.humaninference.tagcloud.unittest.helpers.TestClient;

import junit.framework.TestCase;

public class TestClientRmiAdaptor extends TestCase {

	private static final double NON_GARBAGE_VALUE_2 = 84.0;
	private static final double NON_GARBAGE_VALUE_1 = 42.0;
	static final double GARBAGE_VALUE = -1.0;
	private static final int GARBAGE_VALUE_INT = -1;

	public void testCanCreate() throws RemoteException {
		new ClientRmiAdaptor(new TestClient());
	}
	
	public void testPassOnAnimation() throws RemoteException {
		final Animation a = new TestAnimation(GARBAGE_VALUE_INT);
		final TestClient tc = new TestClient();
		final Client c = new ClientRmiAdaptor(tc);
		
		assertNotSame(tc.lastAnimation, a);
		c.performAnimation(a);
		assertSame(tc.lastAnimation, a);
	}
	
	public void testSetViewport() throws RemoteException {

		final TestClient tc = new TestClient();
		tc.vpX = GARBAGE_VALUE;
		tc.vpY = GARBAGE_VALUE;
		
		final Client c = new ClientRmiAdaptor(tc);

		assertEquals(GARBAGE_VALUE, tc.vpX);
		assertEquals(GARBAGE_VALUE, tc.vpY);
		c.setViewport(NON_GARBAGE_VALUE_1, NON_GARBAGE_VALUE_2);
		assertEquals(NON_GARBAGE_VALUE_1, tc.vpX);
		assertEquals(NON_GARBAGE_VALUE_2, tc.vpY);
		
	}
}
