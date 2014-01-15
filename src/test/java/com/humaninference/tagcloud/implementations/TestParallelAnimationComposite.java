package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.unittest.helpers.TestAnimation;
import com.humaninference.tagcloud.unittest.helpers.TestObserver;
import com.humaninference.tagcloud.unittest.helpers.TestWorld;

import junit.framework.TestCase;

public class TestParallelAnimationComposite extends TestCase {
	
	public void testCanCreate() {
		new ParallelAnimationComposite(42);
	}
	
	public void testCanExecuteOneAnimation() {
		
		final TestAnimation animation = new TestAnimation(7);
		assertEquals(7, animation.tag());
		
		final ParallelAnimationComposite parallelAnimation = new ParallelAnimationComposite(42);
		assertEquals(42, parallelAnimation.tag());
		parallelAnimation.addAnimation(animation);
		
		final TestObserver obs = new TestObserver();
		assertEquals(-1, obs.lastTag);

		final World world = new TestWorld();
		
		parallelAnimation.perform(world, obs);
		
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
		
		final ParallelAnimationComposite parallelAnimation = new ParallelAnimationComposite(42);
		assertEquals(42, parallelAnimation.tag());
		parallelAnimation.addAnimation(animation);
		parallelAnimation.addAnimation(animation2);
		
		final TestObserver obs = new TestObserver();
		assertEquals(-1, obs.lastTag);

		final World world = new TestWorld();
		
		parallelAnimation.perform(world, obs);
		
		assertEquals(1, animation.numExecutions);
		assertSame(world, animation.lastWorld);  
		
		assertEquals(1, animation2.numExecutions);
		assertSame(world, animation2.lastWorld);
		
		assertEquals(1, obs.numExecutions);
		assertEquals(42, obs.lastTag);
		
	}
}
