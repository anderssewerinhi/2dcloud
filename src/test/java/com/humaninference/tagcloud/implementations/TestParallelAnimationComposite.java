package com.humaninference.tagcloud.implementations;

import com.humaninference.tagcloud.Animation;
import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;
import junit.framework.TestCase;

public class TestParallelAnimationComposite extends TestCase {
	
	public void testCanCreate() {
		new ParallelAnimationComposite(42);
	}
	
	private class TestAnimation extends TaggedAnimation {
		
		public TestAnimation(int tag) {
			super(tag);
		}

		private static final long serialVersionUID = 1L;
		
		public int numExecutions = 0; 
		
		public World lastWorld = null;

		@Override
		public void perform(World target, Observer obs) {
			lastWorld = target;
			++numExecutions;
			obs.onAnimationFinished(tag());
		}

	}
	
	private class TestObserver implements Animation.Observer {

		@Override
		public void onAnimationFinished(int tag) {
			++numExecutions;
			lastTag = tag;
		}
		
		public int numExecutions = 0; 
		
		public int lastTag = -1;

	}
	
	private class TestWorld implements World {
		
		@Override
		public PText getTextLabel(int idx) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public PPath getEdge(int idx) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public PImage getImage(int idx) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public PLayer getLayer() {
			throw new RuntimeException("Not implemented");
		}
		
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
