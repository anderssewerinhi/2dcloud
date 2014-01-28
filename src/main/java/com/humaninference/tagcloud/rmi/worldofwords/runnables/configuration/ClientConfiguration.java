package com.humaninference.tagcloud.rmi.worldofwords.runnables.configuration;

public interface ClientConfiguration {
	
	String getMasterHostname();
	int getMasterRmiPort();
	int getOurRmiPort();
	String getOurRmiServiceName();
	String getOurHumanReadableName();
	boolean runAsFullScreen();
	String getOurRMIIP();

}
