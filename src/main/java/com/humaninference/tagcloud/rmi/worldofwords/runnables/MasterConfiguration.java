package com.humaninference.tagcloud.rmi.worldofwords.runnables;

import java.util.List;

public interface MasterConfiguration {
	int getNumClientsToExpect();
	List<Integer> getXaxisOffsetForClient();
}
