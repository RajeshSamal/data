package com.eloqua.api.models;

import java.util.List;

public class CustomObjectData {

	private int id;
	private int contactId;
	private String currentStatus;
	private List<FieldValue> fieldValues;
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public List<FieldValue> getFieldValues() {
		return fieldValues;
	}
	public void setFieldValues(List<FieldValue> fieldValues) {
		this.fieldValues = fieldValues;
	}
	
}
