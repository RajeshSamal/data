package com.eloqua.api.models;

import java.util.List;
import java.util.Map;

public class Import 
{
    private Map<String, String> fields;
    private String importPriorityUri;
    private String identifierFieldName;
    private boolean isSyncTriggeredOnImport;
    private long kbUsed;
    private String name;
    private Integer secondsToRetainData;
    private Integer secondsToAutoDelete;
    private List<SyncAction> syncActions;
    private String updateRule;
    private String uri;
    
    private boolean isUpdatingMultipleMatchedRecords;   
    private String createdBy;
    private String createdAt;
    private String updatedBy;
    private String updatedAt;
    
	public Map<String, String> getFields() {
		return fields;
	}
	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}
	public String getImportPriorityUri() {
		return importPriorityUri;
	}
	public void setImportPriorityUri(String importPriorityUri) {
		this.importPriorityUri = importPriorityUri;
	}
	public String getIdentifierFieldName() {
		return identifierFieldName;
	}
	public void setIdentifierFieldName(String identifierFieldName) {
		this.identifierFieldName = identifierFieldName;
	}
	public boolean isSyncTriggeredOnImport() {
		return isSyncTriggeredOnImport;
	}
	public void setSyncTriggeredOnImport(boolean isSyncTriggeredOnImport) {
		this.isSyncTriggeredOnImport = isSyncTriggeredOnImport;
	}
	public long getKbUsed() {
		return kbUsed;
	}
	public void setKbUsed(long kbUsed) {
		this.kbUsed = kbUsed;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSecondsToRetainData() {
		return secondsToRetainData;
	}
	public void setSecondsToRetainData(Integer secondsToRetainData) {
		this.secondsToRetainData = secondsToRetainData;
	}
	public Integer getSecondsToAutoDelete() {
		return secondsToAutoDelete;
	}
	public void setSecondsToAutoDelete(Integer secondsToAutoDelete) {
		this.secondsToAutoDelete = secondsToAutoDelete;
	}
	public List<SyncAction> getSyncActions() {
		return syncActions;
	}
	public void setSyncActions(List<SyncAction> syncActions) {
		this.syncActions = syncActions;
	}
	public String getUpdateRule() {
		return updateRule;
	}
	public void setUpdateRule(String updateRule) {
		this.updateRule = updateRule;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public boolean isUpdatingMultipleMatchedRecords() {
		return isUpdatingMultipleMatchedRecords;
	}
	public void setUpdatingMultipleMatchedRecords(
			boolean isUpdatingMultipleMatchedRecords) {
		this.isUpdatingMultipleMatchedRecords = isUpdatingMultipleMatchedRecords;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	public String getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
