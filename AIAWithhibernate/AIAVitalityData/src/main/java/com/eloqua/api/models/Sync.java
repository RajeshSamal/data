package com.eloqua.api.models;

public class Sync {
	private String createdAt;
    private String createdBy;
    private SyncStatusType status;
    private String syncedInstanceUri;
    private String syncEndedAt;
    private String syncStartedAt;
    private String uri;
    
    
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public SyncStatusType getStatus() {
		return status;
	}
	public void setStatus(SyncStatusType status) {
		this.status = status;
	}
	public String getSyncedInstanceUri() {
		return syncedInstanceUri;
	}
	public void setSyncedInstanceUri(String syncedInstanceUri) {
		this.syncedInstanceUri = syncedInstanceUri;
	}
	public String getSyncEndedAt() {
		return syncEndedAt;
	}
	public void setSyncEndedAt(String syncEndedAt) {
		this.syncEndedAt = syncEndedAt;
	}
	public String getSyncStartedAt() {
		return syncStartedAt;
	}
	public void setSyncStartedAt(String syncStartedAt) {
		this.syncStartedAt = syncStartedAt;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}

}
