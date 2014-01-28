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
	
	public SetClientConfiguration(final ClientConfiguration from) {
		this.masterHostname = from.getMasterHostname();
		this.masterRMIPort = from.getMasterRmiPort();
		this.ourRMIPort = from.getOurRmiPort();
		this.ourRmiServiceName = from.getOurRmiServiceName();
		this.ourHumanReadableName = from.getOurHumanReadableName();
		this.fullScreen = from.runAsFullScreen();
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
		configFromUrl("file:".concat(args[0]));
	}

	public static ClientConfiguration configFromUrl(String configFromJar) {
		final ApplicationContext context = new ClassPathXmlApplicationContext(
				configFromJar);

		return (ClientConfiguration) context.getBean("configuration");
	}


}
