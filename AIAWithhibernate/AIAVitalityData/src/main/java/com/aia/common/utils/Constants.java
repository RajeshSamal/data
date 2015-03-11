package com.aia.common.utils;

import com.aia.dao.DataFileDao;
import com.aia.dao.EloquaDao;
import com.aia.dao.FTPDao;
import com.aia.dao.HkagDAO;
import com.aia.dao.HkapDAO;
import com.aia.dao.HkasDAO;
import com.aia.dao.Hker1DAO;
import com.aia.dao.Hker2DAO;
import com.aia.dao.Hker3DAO;

public interface Constants {
	String ROLE_ADMIN = "ROLE_ADMIN";
	String ELQ_ERROR = "error";
	String ELQ_WARNING = "warning";
	
	String RECORD_SAVED = "1";
	String RECORD_SENT = "2";
	String RECORD_PROCESSED = "3";
	String RECORD_DUPLICATE = "4";
	
	String HK_GOLD_ARCHIVE = "HKAG";
	String HK_SILVER_ARCHIVE = "HKAS";
	String HK_PLATINUM_ARCHIVE = "HKAP";
	String HK_ENGAGEMENT_REMINDER1 = "HKER1";
	String HK_ENGAGEMENT_REMINDER2 = "HKER2";
	String HK_ENGAGEMENT_REMINDER3 = "HKER3";
	
	String ELOQUA = "ELOQUA";
	String FTP="FTP";
	String DATAFILE="DATAFILE";
	String CUSTOM="CUSTOM";
	
	
}
