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
@Table(name = "HK_MIDYEAR_ASSESSMENT")
public class HKMidYearAssessment implements CommonModel,CommonDBColumn {

	@Id
	@Column(name = "HK_MIDYEAR_ASSESSMENT_ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int hkMidYearAssessmentId;
	@Column(name = "MEMBER_FIRST_NAMES")
	private String memberFirstNames;
	@Column(name = "MEMBER_SURNAME")
	private String memberSurname;
	@Column(name = "EMAIL_ADDRESS")
	private String emailAddress;
	@Column(name = "AIA_VITALITY_MEMBER_NUMBER")
	private String aiaVitalityMemberNumber;
	@Column(name = "ENTITY_REFERENCE_NUMBER")
	private String entityReferenceNumber;
	@Column(name = "VITALITY_STATUS")
	private String vitalityStatus;
	@Column(name = "POINTS_BALANCE")
	private String pointsBalance;
	@Column(name = "CLIENT_ID")
	private String clientId;
	@Column(name = "LANGUAGE_PREFERENCE")
	private String languagePreference;
	@Column(name = "MEMBER_EXPIRY_DATE")
	private String expiryDate;
	@Column(name = "RECORD_ID")
	private String recordId;
	@Column(name = "FILE_TYPE")
	private String fileType;
	@Column(name = "FILE_NAME")
	private String fileName;
	@Column(name = "LAST_MODIFIED_DATE")
	@Temporal(TemporalType.DATE)
	private Date lastModifiedDate;
	@Column(name = "RECORD_STATUS")
	private String recordStatus;
	@Column(name = "PROCESS_DATE")
	@Temporal(TemporalType.DATE)
	private Date processDate;

	public HKMidYearAssessment() {
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
		return "";
	}

	public void setGender(String gender) {
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public int getHkMidYearAssessmentId() {
		return hkMidYearAssessmentId;
	}

	public void setHkMidYearAssessmentId(int hkMidYearAssessmentId) {
		this.hkMidYearAssessmentId = hkMidYearAssessmentId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	

	public String getSmokerInd() {
		return "";
	}

	public void setSmokerInd(String smokerIndicator) {
		
	}

	public String getPointsToMantainStatus() {
		return "";
	}

	public void setPointsToMantainStatus(String pointsToManatinStatus) {
		
	}
	
	public String getPointsToApproach() {
		return "";
	}

	public void setPointsToApproach(String pointsToApproach) {
		
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof HKMidYearAssessment) {
			HKMidYearAssessment that = (HKMidYearAssessment) other;
			if(this.getEmailAddress() !=null){
				result = (this.getEmailAddress().equalsIgnoreCase(that
						.getEmailAddress()));
			}
			
		}
		return result;
	}
	@Override
	 public int hashCode(){
		    return emailAddress.toLowerCase().hashCode();
		  }
}
