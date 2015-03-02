package com.eloqua.api.models;

import java.util.Date;

public class CustomObjectSearchResponse {

	private Date createdAt;
	private String createdBy;
	private String displayNameFieldUri;
	private String emailAddressFieldUri;
	private String name;
	private String uniqueFieldUri;
	private String uri;

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getDisplayNameFieldUri() {
		return displayNameFieldUri;
	}

	public void setDisplayNameFieldUri(String displayNameFieldUri) {
		this.displayNameFieldUri = displayNameFieldUri;
	}

	public String getEmailAddressFieldUri() {
		return emailAddressFieldUri;
	}

	public void setEmailAddressFieldUri(String emailAddressFieldUri) {
		this.emailAddressFieldUri = emailAddressFieldUri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUniqueFieldUri() {
		return uniqueFieldUri;
	}

	public void setUniqueFieldUri(String uniqueFieldUri) {
		this.uniqueFieldUri = uniqueFieldUri;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
