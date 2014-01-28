package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SettableWordsAndConnections implements WordsAndConnections {

	private List<String> words;
	
	private List<String> connectionsFromTo;
	
	private int numLogos;
	
	public void setWords(final List<String> words) {
		this.words = new ArrayList<String>(words);
	}
	
	public void setConnectionsFromTo(final List<String> connectionsFromTo) {
		this.connectionsFromTo = new ArrayList<String>(connectionsFromTo);
	}
	
	@Override
	public List<String> getWords() {
		return words;
	}

	@Override
	public List<String> getConnectionsFromTo() {
		return connectionsFromTo;
	}
	
	public static WordsAndConnections fromUrl(String configFromJar) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				configFromJar);
		return (WordsAndConnections) context.getBean("wordsandconnections");
	}

	@Override
	public int getNumLogos() {
		return numLogos;
	}
	
	public void setNumLogos(final int numLogos) {
		this.numLogos = numLogos;
	}

}
