package com.eloqua.api.models;

public class CustomObjectField {

	private int id;
	private String name;
	private String dataType;
	private String defaultValue;
	private String displayType;
	// private String internalName;
	private String type;
	private String depth;

	public String getDepth() {
		return depth;
	}

	public void setDepth(String depth) {
		this.depth = depth;
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

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	/*
	 * public String getInternalName() { return internalName; } public void
	 * setInternalName(String internalName) { this.internalName = internalName;
	 * }
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
