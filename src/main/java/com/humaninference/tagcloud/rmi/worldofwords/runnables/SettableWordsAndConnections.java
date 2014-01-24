package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;

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
	
	public static WordsAndConnections fromDefaultConfig() {
		final String configFromJar = "file:src/main/resources/com/humaninference/tagcloud/rmi/worldofwords/runnables/spring-config.xml";
		return fromUrl(configFromJar);
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
