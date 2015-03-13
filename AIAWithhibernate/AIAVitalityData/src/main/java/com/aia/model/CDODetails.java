/**
 * 
 */
package com.aia.model;

import java.io.Serializable;

import com.aia.model.AbstractBaseModel;


/**
 * @author verticurl
 *
 */
public class CDODetails extends AbstractBaseModel<CDODetails> implements Serializable{

	
	private String languagePreference;
	private String clientId;
	private String gender;
	private String pointBalance;
	private String vitalityStatus;
	private String entityReferenceNumber;
	private String aiaVitalityMemberNumber;
	private String emailAddress;
	private String memberSurname;
	private String memberFirstName;
	private String memeberExpiryDate;
	private String pointsToMantain;
	private String smokerInd;
	private String pointsToApproach;
	
	
	
	public String getPointsToMantain() {
		return pointsToMantain;
	}
	public void setPointsToMantain(String pointsToMantain) {
		this.pointsToMantain = pointsToMantain;
	}
	public String getSmokerInd() {
		return smokerInd;
	}
	public void setSmokerInd(String smokerInd) {
		this.smokerInd = smokerInd;
	}
	public String getPointsToApproach() {
		return pointsToApproach;
	}
	public void setPointsToApproach(String pointsToApproach) {
		this.pointsToApproach = pointsToApproach;
	}
	public String getMemeberExpiryDate() {
		return memeberExpiryDate;
	}
	public void setMemeberExpiryDate(String memeberExpiryDate) {
		this.memeberExpiryDate = memeberExpiryDate;
	}
	public String getLanguagePreference() {
		return languagePreference;
	}
	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPointBalance() {
		return pointBalance;
	}
	public void setPointBalance(String pointBalance) {
		this.pointBalance = pointBalance;
	}
	public String getVitalityStatus() {
		return vitalityStatus;
	}
	public void setVitalityStatus(String vitalityStatus) {
		this.vitalityStatus = vitalityStatus;
	}
	public String getEntityReferenceNumber() {
		return entityReferenceNumber;
	}
	public void setEntityReferenceNumber(String entityReferenceNumber) {
		this.entityReferenceNumber = entityReferenceNumber;
	}
	public String getAiaVitalityMemberNumber() {
		return aiaVitalityMemberNumber;
	}
	public void setAiaVitalityMemberNumber(String aiaVitalityMemberNumber) {
		this.aiaVitalityMemberNumber = aiaVitalityMemberNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getMemberSurname() {
		return memberSurname;
	}
	public void setMemberSurname(String memberSurname) {
		this.memberSurname = memberSurname;
	}
	public String getMemberFirstName() {
		return memberFirstName;
	}
	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}
	
	
}
