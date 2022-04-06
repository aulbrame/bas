package com.sai.bas.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class AppointmentSchedule implements Serializable{
	
	@Id
	private Date APSCDate;
	private int APSCSequenceNo;
	private String APSCBrCode;
	private String APSCEmpNoPIC;
	private String APSCEmpNo;
	private String APSCEmpTeam;
	private String APSCVDNo;
	private String APSCDriver;
	private String APSCType;
	private String APSCClientMeets;
	private String APSCClientStatus;
	private String APSCClientName;
	private String APSCMobilePhone;
	private String APSCAccountNo;
	private Date APSCTime;
	private String APSCLocation;
	private String APSCStatus;
	private String APSCInterested;
	private String APSCCapability;
	private int APSCAmount;
	private String APSCDescription;
	private String APSCInsert;
	private Date APSCInsertDate;
	private String APSCUpdate;	
	private Date APSCUpdateDate;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppointmentSchedule() {	
	}

	public Date getAPSCDate() {
		return APSCDate;
	}
	
	public void setAPSCDate(Date APSCDAte) {
		this.APSCDate = APSCDAte;
	}
	
	public int getAPSCSequenceNo() {
		return APSCSequenceNo;	
	}
	
	public void setAPSCSequenceNo(int APSCSequenceNo) {
		this.APSCSequenceNo = APSCSequenceNo;
	}
	
	public String getAPSCBrCode() {
		return APSCBrCode;	
	}
	
	public void setAPSCBrCode(String APSCBrCode) {
		this.APSCBrCode = APSCBrCode;
	}
	
	public String getAPSCEmpNoPIC() {
		return APSCEmpNoPIC;	
	}
	
	public void setAPSCEmpNoPIC(String APSCEmpNoPIC) {
		this.APSCEmpNoPIC = APSCEmpNoPIC;
	}
	
	public String getAPSCEmpNo() {
		return APSCEmpNo;
	}
	
	public void setAPSCEmpNo(String APSCEmpNo) {
		this.APSCEmpNo = APSCEmpNo;
	}
	
	public String getAPSCEmpTeam() {
		return APSCEmpTeam;
	}
	
	public void setAPSCEmpTeam(String APSCEmpTeam) {
		this.APSCEmpTeam = APSCEmpTeam;
	}
	
	public String getAPSCVDNo() {
		return APSCVDNo;
	}
	
	public void setAPSCVDNo(String APSCVDNo) {
		this.APSCVDNo = APSCVDNo;
	}
	
	public String getAPSCDriver() {
		return APSCDriver;
	}
	
	public void setAPSCDriver(String APSCDriver) {
		this.APSCDriver = APSCDriver;
	}
	
	public String getAPSCType() {
		return APSCType;
	}
	
	public void setAPSCType(String APSCType) {
		this.APSCType = APSCType;
	}
	
	public String getAPSCClientMeets() {
		return APSCClientMeets;
	}
	
	public void setAPSCClientMeets(String APSCClientMeets) {
		this.APSCClientMeets = APSCClientMeets;
	}
	
	public String getAPSCClientStatus() {
		return APSCClientStatus;
	}
	
	public void setAPSCClientStatus(String APSCClientStatus) {
		this.APSCClientStatus = APSCClientStatus;
	}
	
	public String getAPSCClientName() {
		return APSCClientName;
	}
	
	public void setAPSCClientName(String APSCClientName) {
		this.APSCClientName = APSCClientName;
	}
	
	public String getAPSCMobilePhone() {
		return APSCMobilePhone;
	}
	
	public void setAPSCMobilePhone(String APSCMobilePhone) {
		this.APSCMobilePhone = APSCMobilePhone;
	}
	
	public String getAPSCAccountNo() {
		return APSCAccountNo;
	}
	
	public void setAPSCAccountNo(String APSCAccountNo) {
		this.APSCAccountNo = APSCAccountNo;
	}
	
	public Date getAPSCTime() {
		return APSCTime;
	}
	
	public void setAPSCTime(Date APSCTime) {
		this.APSCTime = APSCTime;
	}
	
	public String getAPSCLocation() {
		return APSCLocation;
	}
	
	public void setAPSCLocation(String APSCLocation) {
		this.APSCLocation = APSCLocation;
	}
	
	public String getAPSCStatus() {
		return APSCStatus;
	}
	
	public void setAPSCStatus(String APSCStatus) {
		this.APSCStatus = APSCStatus;
	}
	
	public String getAPSCInterested() {
		if(APSCInterested.equals("Y"))
			APSCInterested = "Yes";
		else if(APSCInterested.equals("N")) 
			APSCInterested = "No";
		return APSCInterested;
	}
	
	public void setAPSCInterested(String APSCInterested) {
		if(APSCInterested.equals("Yes"))
			APSCInterested = "Y";
		else if(APSCInterested.equals("No")) 
			APSCInterested = "N";
		this.APSCInterested = APSCInterested;
	}
	
	public String getAPSCCapability() {
		if(APSCCapability.equals("Y"))
			APSCCapability = "Yes";
		else if(APSCCapability.equals("N")) 
			APSCCapability = "No";
		return APSCCapability;
	}
	
	public void setAPSCCapability(String APSCCapability) {
		if(APSCCapability.equals("Yes"))
			APSCCapability = "Y";
		else if(APSCCapability.equals("No")) 
			APSCCapability = "N";
		this.APSCCapability = APSCCapability;
	}
	
	public int getAPSCAmount() {
		return APSCAmount;
	}
	
	public void setAPSCAmount(int APSCAmount) {
		this.APSCAmount = APSCAmount;
	}
	
	public String getAPSCDescription() {
		return APSCDescription;
	}
	
	public void setAPSCDescription(String APSCDescription) {
		this.APSCDescription = APSCDescription;
	}
	
	public String getAPSCInsert() {
		return APSCInsert;
	}
	
	public void setAPSCInsert(String APSCInsert) {
		this.APSCInsert = APSCInsert;
	}
	
	public Date getAPSCInsertDate() {
		return APSCInsertDate;
	}
	
	public void setAPSCInsertDate(Date APSCInsertDate) {
		this.APSCInsertDate = APSCInsertDate;
	}
	
	public String getAPSCUpdate() {
		return APSCUpdate;
	}
	
	public void setAPSCUpdate(String APSCUpdate) {
		this.APSCUpdate = APSCUpdate;
	}
	
	public Date getAPSCUpdateDate() {
		return APSCUpdateDate;
	}
	
	public void setAPSCUpdateDate(Date APSCUpdateDate) {
		this.APSCUpdateDate = APSCUpdateDate;
	}
}
