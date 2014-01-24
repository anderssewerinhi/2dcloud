package com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.implementation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration.ClientConfiguration;


public class SetClientConfiguration implements ClientConfiguration {

	private String masterHostname;
	private int masterRMIPort; 
	private int ourRMIPort; 
	private String ourRmiServiceName;
	private String ourHumanReadableName;
	private boolean fullScreen;
	    
	public SetClientConfiguration() {
	 	    
	}

	@Override
	public String getMasterHostname() {
		
		return masterHostname;
	}

	@Override
	public int getMasterRmiPort() {
		
		return masterRMIPort; 
	}

	@Override
	public int getOurRmiPort() {
		
		return ourRMIPort;
	}

	@Override
	public String getOurRmiServiceName() {
	
		return ourRmiServiceName; 
	}

	@Override
	public boolean runAsFullScreen() {
		
		return fullScreen;
	}
	
	public void setMasterHostname(String name){
		
		this.masterHostname= name;  
	}
    
	public void setMasterRMIPort(int port) {
		this.masterRMIPort= port;
	}
 
	public void setOurRMIPort(int port){
		
		this.ourRMIPort = port;
	} 
	public void setourRmiServiceName(String name){
		this.ourRmiServiceName= name;
	}
	public void setfullScreen(boolean screen){
	  this.fullScreen = screen;
	}

	@Override
	public String getOurHumanReadableName() {
		return ourHumanReadableName;
	}
	
	public void setOurHumanReadableName(final String ourHumanReadableName) {
		this.ourHumanReadableName = ourHumanReadableName;
	}

public static void main(String args[]) throws Exception {

	
	final ApplicationContext context = new ClassPathXmlApplicationContext(
			"file:src/main/resources/com/humaninference/tagcloud/rmi/worldofwords/runnables/configuration/implementation/spring-config.xml");
	
    ClientConfiguration setConfiguration = (ClientConfiguration) context.getBean("configuration");
    
	
 
	
}


}
