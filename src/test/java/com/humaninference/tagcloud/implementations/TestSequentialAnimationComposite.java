package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;

import junit.framework.TestCase;

public class TestSequentialAnimationComposite extends TestCase {
	
	public void testCanCreate() {
		new SequentialAnimationComposite(42);
	}
	
	public void testCanExecuteOneAnimation() {
		
		final TestAnimation animation = new TestAnimation(7);
		assertEquals(7, animation.tag());
		
		final SequentialAnimationComposite sequentialAnimation = new SequentialAnimationComposite(42);
		assertEquals(42, sequentialAnimation.tag());
		sequentialAnimation.addAnimation(animation);
		
		final TestObserver obs = new TestObserver();
		assertEquals(-1, obs.lastTag);

		final World world = new TestWorld();
		
		sequentialAnimation.perform(world, obs);
		
		assertEquals(1, animation.numExecutions);
		assertSame(world, animation.lastWorld);
		
		assertEquals(1, obs.numExecutions);
		assertEquals(42, obs.lastTag);
		
	}

	public void testCanExecuteMoreThanOneAnimation() {
		final TestAnimation animation = new TestAnimation(7);
		assertEquals(7, animation.tag());
		
		final TestAnimation animation2 = new TestAnimation(8);
		assertEquals(8, animation2.tag());
		
		final SequentialAnimationComposite sequentialAnimation = new SequentialAnimationComposite(42);
		assertEquals(42, sequentialAnimation.tag());
		sequentialAnimation.addAnimation(animation);
		sequentialAnimation.addAnimation(animation2);
		
		final TestObserver obs = new TestObserver();
		assertEquals(-1, obs.lastTag);

		final World world = new TestWorld();
		
		sequentialAnimation.perform(world, obs);
		
		assertEquals(1, animation.numExecutions);
		assertSame(world, animation.lastWorld);  
		
		assertEquals(1, animation2.numExecutions);
		assertSame(world, animation2.lastWorld);
		
		assertEquals(1, obs.numExecutions);
		assertEquals(42, obs.lastTag);
		
	}
	
	@SuppressWarnings("serial")
	private class TestSequentialAnimation extends TestAnimation {
		
		private final TestAnimation previous;
		
		public TestSequentialAnimation(final int tag, final TestAnimation previous) {
			super(tag);
			this.previous = previous;
		}
		
		@Override
		public void perform(World target, Observer obs) {
			assertEquals(1, previous.numExecutions);
			assertSame(target, previous.lastWorld);
			super.perform(target, obs);
		}

	}
	public void testAnimationsArePerformedInSequence() {
		final TestAnimation animation = new TestAnimation(7);
		assertEquals(7, animation.tag());
		
		final TestAnimation animation2 = new TestSequentialAnimation(8, animation);
		assertEquals(8, animation2.tag());
		
		final SequentialAnimationComposite sequentialAnimation = new SequentialAnimationComposite(42);
		assertEquals(42, sequentialAnimation.tag());
		sequentialAnimation.addAnimation(animation);
		sequentialAnimation.addAnimation(animation2);
		
		final TestObserver obs = new TestObserver();
		assertEquals(-1, obs.lastTag);

		final World world = new TestWorld();
		
		sequentialAnimation.perform(world, obs);
		
		assertEquals(1, animation.numExecutions);
		assertSame(world, animation.lastWorld);  
		
		assertEquals(1, animation2.numExecutions);
		assertSame(world, animation2.lastWorld);
		
		assertEquals(1, obs.numExecutions);
		assertEquals(42, obs.lastTag);
		
	}

}
