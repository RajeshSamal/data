package com.aia.model;

import java.util.Date;

public interface CommonModel {
	
	public String getMemberFirstNames();

	public void setMemberFirstNames(String memberFirstNames);
	public String getMemberSurname();

	public void setMemberSurname(String memberSurname);

	public String getEmailAddress();
	public void setEmailAddress(String emailAddress);

	public String getAiaVitalityMemberNumber();

	public void setAiaVitalityMemberNumber(String aiaVitalityMemberNumber);

	public String getEntityReferenceNumber();

	public void setEntityReferenceNumber(String entityReferenceNumber);

	public String getLanguagePreference();

	public void setLanguagePreference(String languagePreference);

	public String getRecordStatus();

	public void setRecordStatus(String recordStatus);

	public Date getProcessDate();

	public void setProcessDate(Date processDate);

	public String getRecordId();

	public void setRecordId(String recordId);
	
	public String getVitalityStatus();

	public void setVitalityStatus(String vitalityStatus);

	public String getPointsBalance();

	public void setPointsBalance(String pointsBalance);

	public String getGender();

	public void setGender(String gender);

	public String getClientId();

	public void setClientId(String clientId);

}
