package io.healthe.util;

import android.content.Context;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * Base class for loading data; extending types are responsible for providing implementations of
 * {@link #onDataLoaded(Object)} to do something with the data and {@link #cancelLoading()} to
 * cancel any activity.
 *
 * @param <T>
 */
public abstract class ContentDataManager<T> implements DataLoadingObject {
	
	private final AtomicInteger loadingCount;
	private List<DataLoadingObject.DataLoadingCallbacks> loadingCallbacks;
	
	public ContentDataManager(@NonNull Context context) {
		this.loadingCount = new AtomicInteger(0);
	}
	
	public abstract void onDataLoaded(T data);
	
	public abstract void cancelLoading();
	
	@Override
	public boolean isDataLoading() {
		return loadingCount.get() > 0;
	}
	
	@Override
	public void registerCallback(DataLoadingCallbacks callbacks) {
		if (loadingCallbacks == null) {
			loadingCallbacks = new ArrayList<>(1);
		}
		loadingCallbacks.add(callbacks);
	}
	
	@Override
	public void unregisterCallback(DataLoadingCallbacks callbacks) {
		if (loadingCallbacks != null && loadingCallbacks.contains(callbacks)) {
			loadingCallbacks.remove(callbacks);
		}
	}
	
	protected void loadStarted() {
		if (loadingCount.getAndIncrement() == 0) {
			dispatchLoadingStartedCallbacks();
		}
	}
	
	protected void loadFinished() {
		if (loadingCount.decrementAndGet() == 0) {
			dispatchLoadingFinishedCallbacks();
		}
	}
	
	protected void dispatchLoadingStartedCallbacks() {
		if (loadingCallbacks == null || loadingCallbacks.isEmpty()) return;
		for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
			loadingCallback.dataStartedLoading();
		}
	}
	
	protected void dispatchLoadingFinishedCallbacks() {
		if (loadingCallbacks == null || loadingCallbacks.isEmpty()) return;
		for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
			loadingCallback.dataFinishedLoading();
		}
	}
	
	protected void resetLoadingCount() {
		loadingCount.set(0);
	}
}
