package com.humaninference.tagcloud;

public abstract class TaggedAnimation implements Animation {

	private final int tag;
	
	public TaggedAnimation(final int tag) {
		this.tag = tag;
	}

	final public int tag() {
		return tag;
	}

}
