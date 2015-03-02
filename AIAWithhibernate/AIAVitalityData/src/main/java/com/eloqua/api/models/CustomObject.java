package com.eloqua.api.models;

import java.util.List;

public class CustomObject {

	private String displayNameFieldId;
	private List<CustomObjectField> fields;
	private int id;
	private String name;
	private int page;
	private int pageSize;
	private String searchTerm;
	private String uniqueCodeFieldId;
	private String uri;
	private String internalName;

	public String getDisplayNameFieldId() {
		return displayNameFieldId;
	}

	public void setDisplayNameFieldId(String displayNameFieldId) {
		this.displayNameFieldId = displayNameFieldId;
	}

	public List<CustomObjectField> getFields() {
		return fields;
	}

	public void setFields(List<CustomObjectField> fields) {
		this.fields = fields;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public String getUniqueCodeFieldId() {
		return uniqueCodeFieldId;
	}

	public String getInternalName() {
		return internalName;
	}

	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}

	public void setUniqueCodeFieldId(String uniqueCodeFieldId) {
		this.uniqueCodeFieldId = uniqueCodeFieldId;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

}
