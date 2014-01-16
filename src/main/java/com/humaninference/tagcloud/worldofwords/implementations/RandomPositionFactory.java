package com.humaninference.tagcloud.worldofwords.implementations;

import java.text.DecimalFormat;
import java.util.Random;

import com.humaninference.tagcloud.worldofwords.Configuration.Position;

/**
 * 
 * @author andersprivat
 * 
 * Algorithm from http://stackoverflow.com/questions/8839086/how-to-randomize-points-on-a-sphere-surface-evenly
 * 
 */
public class RandomPositionFactory implements PositionFactory {
	private final Random rnd;

	public RandomPositionFactory(final long seed) {
		this.rnd = new Random(seed);
	}

	@Override
	public Position newPosition() {
		final double z = rnd.nextDouble() * 2.0 - 1.0; // In range -1.0 to 1.0
		final double rxy = Math.sqrt(1.0 - z * z);
		final double phi = rnd.nextDouble() * 2.0 * Math.PI;
		final double x = rxy * Math.cos(phi);
		final double y = rxy * Math.sin(phi);
		
		final DecimalFormat df = new DecimalFormat("#.###");
		return new Position() {

			@Override
			public double x() {
				return x;
			}

			@Override
			public double y() {
				return y;
			}

			@Override
			public double z() {
				return z;
			}
			
			@Override
			public String toString() {
				return super.toString() + String.format("  x:%s y:%s z:%s", df.format(x), df.format(y), df.format(z));
			}
			
		};
				 
	}
	
	public static final void main(final String... strings) {
		final RandomPositionFactory rf = new RandomPositionFactory(42);
		for ( int i = 0; i < 10; ++i) {
			System.out.println(rf.newPosition().toString());
		}
	}
	

}
