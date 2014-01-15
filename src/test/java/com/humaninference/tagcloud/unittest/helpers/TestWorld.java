package com.humaninference.tagcloud.unittest.helpers;

import com.humaninference.tagcloud.World;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

public class TestWorld implements World {
	
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