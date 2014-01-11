package com.humaninference.tagcloud;

import edu.umd.cs.piccolo.PLayer;
import edu.umd.cs.piccolo.nodes.PImage;
import edu.umd.cs.piccolo.nodes.PPath;
import edu.umd.cs.piccolo.nodes.PText;

/**
 * 
 * @author andersprivat
 *
 * A very simple world that consists of text labels, lines and images.
 * 
 */
public interface World {
	
	PText getTextLabel(int idx);
	
	PPath getEdge(int idx);
	
	PImage getImage(int idx);
	
	PLayer getLayer();
}
