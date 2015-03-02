package com.aia.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * HkAchieveGold class
 */
@Entity
public class HKAchieveGold implements CommonModel {

	@Id
	@Column(name="HK_ACHIEVE_GOLD_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int hkAchieveGoldId;
	@Column(name="MEMBER_FIRST_NAMES")
	private String memberFirstNames;
	@Column(name="MEMBER_SURNAME")
	private String memberSurname;
	@Column(name="EMAIL_ADDRESS")
	private String emailAddress;
	@Column(name="AIA_VITALITY_MEMBER_NUMBER")
	private String aiaVitalityMemberNumber;
	@Column(name="ENTITY_REFERENCE_NUMBER")
	private String entityReferenceNumber;
	@Column(name="LANGUAGE_PREFERENCE")
	private String languagePreference;
	@Column(name="RECORD_STATUS")
	private String recordStatus;
	@Column(name="PROCESS_DATE")
	private Date processDate;
	@Column(name="RECORD_ID")
	private String recordId;
	@Column(name="VITALITY_STATUS")
	private String vitalityStatus;
	@Column(name="POINTS_BALANCE")
	private String pointsBalance;
	@Column(name="GENDER")
	private String gender;
	@Column(name="CLIENT_ID")
	private String clientId;
	

	public HKAchieveGold() {
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
