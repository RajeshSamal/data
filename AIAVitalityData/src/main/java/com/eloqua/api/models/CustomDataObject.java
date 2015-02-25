package com.eloqua.api.models;

import java.util.List;

public class CustomDataObject {

	private String type;
	private String displayNameFieldId;
	private List<FieldValue> fieldValues;
	private String uniqueCodeFieldId;
	private long accessedAt;
	private long createdAt;
	private long createdBy;
	private String depth;
	private String description;
	private long folderId;
	private long id;
	private String name;
	private String permissions;
	private long scheduledFor;
	private long updatedAt;
	private long updatedBy;

	@Override
	public String toString() {
		return "Custom Id ::: " + id + " ::: Name ::: " + name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDisplayNameFieldId() {
		return displayNameFieldId;
	}

	public void setDisplayNameFieldId(String displayNameFieldId) {
		this.displayNameFieldId = displayNameFieldId;
	}

	public List<FieldValue> getFieldValues() {
		return fieldValues;
	}

	public void setFieldValues(List<FieldValue> fieldValues) {
		this.fieldValues = fieldValues;
	}

	public String getUniqueCodeFieldId() {
		return uniqueCodeFieldId;
	}

	public void setUniqueCodeFieldId(String uniqueCodeFieldId) {
		this.uniqueCodeFieldId = uniqueCodeFieldId;
	}

	public long getAccessedAt() {
		return accessedAt;
	}

	public void setAccessedAt(long accessedAt) {
		this.accessedAt = accessedAt;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getFolderId() {
		return folderId;
	}

	public void setFolderId(long folderId) {
		this.folderId = folderId;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public long getScheduledFor() {
		return scheduledFor;
	}

	public void setScheduledFor(long scheduledFor) {
		this.scheduledFor = scheduledFor;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(long updatedBy) {
		this.updatedBy = updatedBy;
	}

}
