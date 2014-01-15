package com.humaninference.tagcloud;

/**
 * 
 * @author andersprivat
 *
 * A simple base class that adds the required field and methods for tagging an 
 * animation with an integer value.
 * 
 */
public abstract class TaggedAnimation implements Animation {

	private static final long serialVersionUID = 1L;
	
	private final int tag;
	
	public TaggedAnimation(final int tag) {
		this.tag = tag;
	}

	final public int tag() {
		return tag;
	}

}
