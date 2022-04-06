package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the UserNpwp database table.
 * 
 */
@Entity
@NamedQuery(name="UserNpwp.findAll", query="SELECT u FROM UserNpwp u")
public class UserNpwp implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String unidktp;

	private Timestamp createdate;

	private String unemail;

	private String unkodecab;

	private String unlogin;

	private String unnama;

	private String unpassword;

	private String ununiqid;

	public UserNpwp() {
	}

	public String getUnidktp() {
		return this.unidktp;
	}

	public void setUnidktp(String unidktp) {
		this.unidktp = unidktp;
	}

	public Timestamp getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public String getUnemail() {
		return this.unemail;
	}

	public void setUnemail(String unemail) {
		this.unemail = unemail;
	}

	public String getUnkodecab() {
		return this.unkodecab;
	}

	public void setUnkodecab(String unkodecab) {
		this.unkodecab = unkodecab;
	}

	public String getUnlogin() {
		return this.unlogin;
	}

	public void setUnlogin(String unlogin) {
		this.unlogin = unlogin;
	}

	public String getUnnama() {
		return this.unnama;
	}

	public void setUnnama(String unnama) {
		this.unnama = unnama;
	}

	public String getUnpassword() {
		return this.unpassword;
	}

	public void setUnpassword(String unpassword) {
		this.unpassword = unpassword;
	}

	public String getUnuniqid() {
		return this.ununiqid;
	}

	public void setUnuniqid(String ununiqid) {
		this.ununiqid = ununiqid;
	}

}