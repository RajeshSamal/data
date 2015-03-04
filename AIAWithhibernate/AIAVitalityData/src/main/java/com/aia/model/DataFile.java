package com.aia.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="PROCESSED_FILE")
public class DataFile {
	
	@Id
	@Column(name="FILE_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int fileId;
	@Column(name="FILE_NAME")
	private String fielName;
	@Column(name="PROCESS_DATE")
	@Temporal(TemporalType.DATE)
	private Date processDate;
	@Column(name="ELOQUA_PROCESS_DATE")
	@Temporal(TemporalType.DATE)
	private Date eloquaProcessDate;
	@Column(name="DUPLICATE_RECORDS")
	private int duplicateRecords;
	@Column(name="TOTAL_RECORDS")
	private int totalRecords;
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getFielName() {
		return fielName;
	}
	public void setFielName(String fielName) {
		this.fielName = fielName;
	}
	public Date getProcessDate() {
		return processDate;
	}
	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}
	public Date getEloquaProcessDate() {
		return eloquaProcessDate;
	}
	public void setEloquaProcessDate(Date eloquaProcessDate) {
		this.eloquaProcessDate = eloquaProcessDate;
	}
	public int getDuplicateRecords() {
		return duplicateRecords;
	}
	public void setDuplicateRecords(int duplicateRecords) {
		this.duplicateRecords = duplicateRecords;
	}
	public int getTotalRecords() {
		return totalRecords;
	}
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	

	
	
}
