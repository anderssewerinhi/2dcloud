package com.humaninference.tagcloud.implementations;

import java.awt.Color;

import javax.swing.SwingUtilities;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PNode;
import edu.umd.cs.piccolo.activities.PActivity;
import edu.umd.cs.piccolo.activities.PActivity.PActivityDelegate;
import edu.umd.cs.piccolo.nodes.PText;

public abstract class ThreeDimensionalAnimation extends TaggedAnimation {

	private static final long serialVersionUID = 1L;

	protected abstract PNode getNode(final World target);

	protected final double targetX;
	protected final double targetY;
	protected final double targetZoom;
	protected final long duration;
	protected final int nodeIdx;

	public ThreeDimensionalAnimation(final int nodeIdx, final double targetX, final double targetY, final double targetZoom, final long duration, final int tag) {
		super(tag);
		this.nodeIdx = nodeIdx;
		this.targetX = targetX;
		this.targetY = targetY;
		this.targetZoom = targetZoom;
		this.duration = duration;
		if (duration == 0) {
			throw new RuntimeException("Duration must be > 0");
		}
		}

	public void perform(final World target, final Observer obs) {
		final PNode n = getNode(target);
		perform(obs, n);
	}

	protected void perform(final Observer obs, final PNode n) {
		SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
		final PActivity act = 
				n.animateToPositionScaleRotation(targetX, targetY, targetZoom, 0.0, duration);
		final PActivityDelegate del = new PActivityDelegate() {

			public void activityStepped(PActivity arg0) {
			}

			public void activityStarted(PActivity arg0) {
			}

			public void activityFinished(PActivity arg0) {
				SwingUtilities.invokeLater(
						new Runnable() {
							public void run() {

								obs.onAnimationFinished(tag());
							}
						}
						);
			}
		};
		act.setDelegate(del);
					}
				}
				);
	}

	public long duration() {
		return duration;
	}

}