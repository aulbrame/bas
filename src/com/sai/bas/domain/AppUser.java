package com.sai.bas.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the UserNpwp database table.
 * 
 */
@Entity
@NamedQuery(name="AppUser.findAll", query="SELECT u FROM AppUser u")
public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String appid;

	private Timestamp appuserexpired;

	private String appuserid;

	private String appusername;

	private String appuserpassword;

	private String appuserauthority;

	private String appuserprivatepassword;

	private String appemailoffice;

	public AppUser() {
	}

	public String getUappid() {
		return this.appid;
	}

	public void setUappid(String uappid) {
		this.appid = uappid;
	}

	public Timestamp getAppUserExpired() {
		return this.appuserexpired;
	}

	public void setAppUserExpired(Timestamp appuserexpired) {
		this.appuserexpired = appuserexpired;
	}

	public String getappuserid() {
		return this.appuserid;
	}

	public void setappuserid(String appuserid) {
		this.appuserid = appuserid;
	}

	public String getappusername() {
		return this.appusername;
	}

	public void setappusername(String appusername) {
		this.appusername = appusername;
	}

	public String getappuserpassword() {
		return this.appuserpassword;
	}

	public void setappuserpassword(String appuserpassword) {
		this.appuserpassword = appuserpassword;
	}

	public String getuserauthority() {
		return this.appuserauthority;
	}

	public void setuserauthority(String userauthority) {
		this.appuserauthority = userauthority;
	}

	public String getappuserprivatepassword() {
		return this.appuserprivatepassword;
	}

	public void setappuserprivatepassword(String appuserprivatepassword) {
		this.appuserprivatepassword = appuserprivatepassword;
	}

	public String getappemailoffice() {
		return this.appemailoffice;
	}

	public void setappemailoffice(String appemailoffice) {
		this.appemailoffice = appemailoffice;
	}

}