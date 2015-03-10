package com.aia.model;

import java.util.Date;

public interface CommonDBColumn {

	
	public String getFileType();
	public void setFileType(String fileType);

	public Date getLastModifiedDate();
	public void setLastModifiedDate(Date lastModifiedDate) ;
	
	public String getFileName() ;
	public void setFileName(String fileName) ;
	
	public Date getProcessDate();
	public void setProcessDate(Date processDate) ;

	public String getRecordId() ;
	public void setRecordId(String recordId) ;
	
	public String getRecordStatus();
	public void setRecordStatus(String recordStatus);
}
