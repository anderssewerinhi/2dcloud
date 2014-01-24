package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.util.List;

public interface WordsAndConnections {
	
	public List<String> getWords();
	
	public List<String> getConnectionsFromTo();
	
	public int getNumLogos();

}
