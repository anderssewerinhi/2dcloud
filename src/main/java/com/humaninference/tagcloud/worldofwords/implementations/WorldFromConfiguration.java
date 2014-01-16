package com.humaninference.tagcloud.worldofwords.implementations;

import com.humaninference.tagcloud.World;
import com.humaninference.tagcloud.worldofwords.Configuration;

import demo.simpleapplication.ImageWorld;

public class WorldFromConfiguration {

	public static World makeWorld(final double width, final double height, final Configuration configuration) {
		
		return new ImageWorld(); // Just for testing purposes
//		throw new RuntimeException("Not implemented");
	}
}
