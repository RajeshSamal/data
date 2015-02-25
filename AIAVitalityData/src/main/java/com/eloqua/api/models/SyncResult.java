package com.eloqua.api.models;


public class SyncResult {
	private int count;
	private String createdAt;
	private String message;
	private String syncUri;
	private String severity;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSyncUri() {
		return syncUri;
	}

	public void setSyncUri(String syncUri) {
		this.syncUri = syncUri;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

}
