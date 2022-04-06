package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the UserKacab database table.
 * 
 */
@Entity
@NamedQuery(name="UserKacab.findAll", query="SELECT u FROM UserKacab u")
public class UserKacab implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String ukkodecab;

	private String uknamakacab;

	private String uklogin;

	private String ukpassword;

	public UserKacab() {
	}

	public String getUkkodecab() {
		return ukkodecab;
	}

	public void setUkkodecab(String ukkodecab) {
		this.ukkodecab = ukkodecab;
	}

	public String getUknamakacab() {
		return uknamakacab;
	}

	public void setUknamakacab(String uknamakacab) {
		this.uknamakacab = uknamakacab;
	}

	public String getUklogin() {
		return uklogin;
	}

	public void setUklogin(String uklogin) {
		this.uklogin = uklogin;
	}

	public String getUkpassword() {
		return ukpassword;
	}

	public void setUkpassword(String ukpassword) {
		this.ukpassword = ukpassword;
	}


}