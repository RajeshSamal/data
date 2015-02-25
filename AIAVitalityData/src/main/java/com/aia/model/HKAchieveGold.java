package com.aia.model;
import java.util.Date;

/**
 * HkAchieveGold class
 */
public class HKAchieveGold implements java.io.Serializable {

	private int hkAchieveGoldId;
	private String memberFirstNames;
	private String memberSurname;
	private String emailAddress;
	private String aiaVitalityMemberNumber;
	private String entityReferenceNumber;
	private String languagePreference;
	private String recordStatus;
	private Date processDate;
	private String recordId;
	private String vitalityStatus;
	private String pointsBalance;
	private String gender;
	private String clientId;
	

	public HKAchieveGold() {
	}

	public HKAchieveGold(int hkAchieveGoldId, String recordId) {
		this.hkAchieveGoldId = hkAchieveGoldId;
		this.recordId = recordId;
	}

	public HKAchieveGold(int hkAchieveGoldId, String memberFirstNames,
			String memberSurname, String emailAddress,
			String aiaVitalityMemberNumber, String entityReferenceNumber,
			String languagePreference, String recordStatus, Date processDate,
			String recordId) {
		this.hkAchieveGoldId = hkAchieveGoldId;
		this.memberFirstNames = memberFirstNames;
		this.memberSurname = memberSurname;
		this.emailAddress = emailAddress;
		this.aiaVitalityMemberNumber = aiaVitalityMemberNumber;
		this.entityReferenceNumber = entityReferenceNumber;
		this.languagePreference = languagePreference;
		this.recordStatus = recordStatus;
		this.processDate = processDate;
		this.recordId = recordId;
	}

	public int getHKAchieveGoldId() {
		return this.hkAchieveGoldId;
	}

	public void setHKAchieveGoldId(int hkAchieveGoldId) {
		this.hkAchieveGoldId = hkAchieveGoldId;
	}

	public String getMemberFirstNames() {
		return this.memberFirstNames;
	}

	public void setMemberFirstNames(String memberFirstNames) {
		this.memberFirstNames = memberFirstNames;
	}

	public String getMemberSurname() {
		return this.memberSurname;
	}

	public void setMemberSurname(String memberSurname) {
		this.memberSurname = memberSurname;
	}

	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getAiaVitalityMemberNumber() {
		return this.aiaVitalityMemberNumber;
	}

	public void setAiaVitalityMemberNumber(String aiaVitalityMemberNumber) {
		this.aiaVitalityMemberNumber = aiaVitalityMemberNumber;
	}

	public String getEntityReferenceNumber() {
		return this.entityReferenceNumber;
	}

	public void setEntityReferenceNumber(String entityReferenceNumber) {
		this.entityReferenceNumber = entityReferenceNumber;
	}

	public String getLanguagePreference() {
		return this.languagePreference;
	}

	public void setLanguagePreference(String languagePreference) {
		this.languagePreference = languagePreference;
	}

	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Date getProcessDate() {
		return this.processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public String getRecordId() {
		return this.recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	public String getVitalityStatus() {
		return vitalityStatus;
	}

	public void setVitalityStatus(String vitalityStatus) {
		this.vitalityStatus = vitalityStatus;
	}

	public String getPointsBalance() {
		return pointsBalance;
	}

	public void setPointsBalance(String pointsBalance) {
		this.pointsBalance = pointsBalance;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

}
