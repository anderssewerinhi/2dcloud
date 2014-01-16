package com.humaninference.tagcloud.worldofwords.implementations;


import com.humaninference.tagcloud.worldofwords.Configuration;
import com.humaninference.tagcloud.worldofwords.TransitionConfigurationMaker;

public class PlaceRandomly implements TransitionConfigurationMaker {
	

	@Override
	public Configuration makeConfigurationFrom(final Configuration source) {
		// For each word, make a new random position
		// TODO: Wrap new configuration in old one or copy? 
		
		throw new RuntimeException("Not implemented");
	}
	
	

}
