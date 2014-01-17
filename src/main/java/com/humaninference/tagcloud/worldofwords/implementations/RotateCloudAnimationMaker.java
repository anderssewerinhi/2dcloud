package com.humaninference.tagcloud.worldofwords.implementations;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

public class RotateCloudAnimationMaker {

	public static Configuration makeAnimation(final Configuration from, final double degreesInRadians) {
		final List<Configuration.Position> rotated = new ArrayList<Configuration.Position>(from.getWordCount());
		for (int i = 0; i < from.getWordCount(); ++i) {
			rotated.add(rotateAroundEquator(from.getPosition(i), degreesInRadians));
		}
		return makeConfigurationWithNewPositions(from, rotated);
	}
	
	private static Configuration makeConfigurationWithNewPositions(
			final Configuration from, final List<Position> rotated) {
		
		// TODO: This should REALLY not be a wrapper, but I am in a hurry...
		return new Configuration() {
			
			@Override
			public int getWordCount() {
				return from.getWordCount();
			}
			
			@Override
			public String getWord(int word) {
				return from.getWord(word);
			}
			
			@Override
			public Set<Integer> getRelatedWords(int word) {
				return from.getRelatedWords(word);
			}
			
			@Override
			public Position getPosition(int word) {
				return rotated.get(word);
			}
			
			@Override
			public int getLineCount() {
				return from.getLineCount();
			}
			
			@Override
			public Line getLine(int line) {
				return from.getLine(line);
			}
		};
		
	}

	private static class SimplePosition implements Position {
		
		private final double x, y, z;

		public SimplePosition(final double x, final double y, final double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
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
		
		
	}
	private static Position rotateAroundEquator(final Position position, final double degreesInRadians) {
		final AffineTransform transform = new AffineTransform();
		transform.rotate(degreesInRadians, 0, 0);
		final Point2D original = new Point2D.Double(position.x(), position.z());
		final Point2D newPoint = transform.transform(original, null);
		return new SimplePosition(newPoint.getX(), position.y(), newPoint.getY());
	}
}
