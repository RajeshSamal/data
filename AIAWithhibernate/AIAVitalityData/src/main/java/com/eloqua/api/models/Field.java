package com.eloqua.api.models;

public class Field {
	private String defaultValue;
    private String internalName;
    private boolean hasReadOnlyConstraint;
    private boolean hasNotNullConstraint;
    private boolean hasUniquenessConstraint;
    private String name;
    private String statement;
    
    
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getInternalName() {
		return internalName;
	}
	public void setInternalName(String internalName) {
		this.internalName = internalName;
	}
	public boolean isHasReadOnlyConstraint() {
		return hasReadOnlyConstraint;
	}
	public void setHasReadOnlyConstraint(boolean hasReadOnlyConstraint) {
		this.hasReadOnlyConstraint = hasReadOnlyConstraint;
	}
	public boolean isHasNotNullConstraint() {
		return hasNotNullConstraint;
	}
	public void setHasNotNullConstraint(boolean hasNotNullConstraint) {
		this.hasNotNullConstraint = hasNotNullConstraint;
	}
	public boolean isHasUniquenessConstraint() {
		return hasUniquenessConstraint;
	}
	public void setHasUniquenessConstraint(boolean hasUniquenessConstraint) {
		this.hasUniquenessConstraint = hasUniquenessConstraint;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatement() {
		return statement;
	}
	public void setStatement(String statement) {
		this.statement = statement;
	}
    
}