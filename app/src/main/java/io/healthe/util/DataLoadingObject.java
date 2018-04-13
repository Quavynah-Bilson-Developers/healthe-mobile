package io.healthe.util;


/**
 * Data loading interface for loading data from the database
 */
public interface DataLoadingObject {
	boolean isDataLoading();
	void registerCallback(DataLoadingCallbacks callbacks);
	void unregisterCallback(DataLoadingCallbacks callbacks);
	
	interface DataLoadingCallbacks {
		void dataStartedLoading();
		void dataFinishedLoading();
	}
}
