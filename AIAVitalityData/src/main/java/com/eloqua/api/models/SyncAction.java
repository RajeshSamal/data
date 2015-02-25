package com.eloqua.api.models;

public class SyncAction {
	private SyncActionType action;
    private String destinationUri;
    
    
	public SyncActionType getAction() {
		return action;
	}
	public void setAction(SyncActionType action) {
		this.action = action;
	}
	public String getDestinationUri() {
		return destinationUri;
	}
	public void setDestinationUri(String destinationUri) {
		this.destinationUri = destinationUri;
	}
    
    
    
}
