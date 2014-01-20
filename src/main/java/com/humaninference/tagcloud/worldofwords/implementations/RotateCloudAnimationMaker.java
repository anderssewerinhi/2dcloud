package com.humaninference.tagcloud.worldofwords.implementations;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.Configuration.Position;

public class RotateCloudAnimationMaker {

	public static Configuration makeAnimation(final Configuration from, final double degreesInRadians) {
		final List<Configuration.Position> rotated = new ArrayList<Configuration.Position>(from.getWordCount());
		for (int i = 0; i < from.getWordCount(); ++i) {
			rotated.add(rotateAroundEquator(from.getPosition(i), degreesInRadians));
		}
		final List<Configuration.Position> rotatedImages = new ArrayList<Configuration.Position>(from.getImageCount());
		for (int i = 0; i < from.getImageCount(); ++i) {
			rotatedImages.add(rotateAroundEquator(from.getImagePosition(i), degreesInRadians));
		}
		from.changePositions(rotated, rotatedImages);
		return from;
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
