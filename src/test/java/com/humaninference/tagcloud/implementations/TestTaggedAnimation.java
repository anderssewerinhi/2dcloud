package com.humaninference.tagcloud.implementations;

import java.util.Random;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.Animation.Observer;
import com.humaninference.tagcloud.implementations.TaggedAnimation;

import junit.framework.TestCase;

public class TestTaggedAnimation extends TestCase {
	
	private static class TestClass extends TaggedAnimation {

		public TestClass(final int tag) {
			super(tag);
		}

		public void perform(World target, Observer obs) {
			// Do nothing
		}
		
	}
	
	public void testCanCreate() {
		new TestClass(0);
	}
	
	public void testTagWorks() {
		final int rnd = new Random().nextInt();
		final TestClass t = new TestClass(rnd);
		assertEquals(rnd, t.tag());
	}

}
