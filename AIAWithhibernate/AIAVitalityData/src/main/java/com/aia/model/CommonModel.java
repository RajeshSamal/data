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
	
	public String getVitalityStatus();

	public void setVitalityStatus(String vitalityStatus);

	public String getPointsBalance();

	public void setPointsBalance(String pointsBalance);

	public String getGender();

	public void setGender(String gender);

	public String getClientId();

	public void setClientId(String clientId);
	
	public String getExpiryDate();
	
	public void setExpiryDate(String expiryDate) ;
	
	public String getSmokerInd();
	
	public void setSmokerInd(String smokerInd);
	
	public String getPointsToMantainStatus();
	
	public void setPointsToMantainStatus(String pointsToManatinStatus) ;
	
	public String getPointsToApproach();

	public void setPointsToApproach(String pointsToApproach);
	
	

}
