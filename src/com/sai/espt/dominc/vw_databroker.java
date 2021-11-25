package com.sai.espt.dominc;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the UserNpwp database table.
 * 
 */
@Entity
public class vw_databroker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String brologin;
	private String daftar;
	private String unnama;
	private String unidktp;
	private String broktp;
	private String galogin;
	
	public vw_databroker() {
	}

	public String getBrologin() {
		return brologin;
	}

	public void setBrologin(String brologin) {
		this.brologin = brologin;
	}

	public String getDaftar() {
		return daftar;
	}

	public void setDaftar(String daftar) {
		this.daftar = daftar;
	}

	public String getUnnama() {
		return unnama;
	}

	public void setUnnama(String unnama) {
		this.unnama = unnama;
	}

	public String getUnidktp() {
		return unidktp;
	}

	public void setUnidktp(String unidktp) {
		this.unidktp = unidktp;
	}

	public String getBroktp() {
		return broktp;
	}

	public void setBroktp(String broktp) {
		this.broktp = broktp;
	}

	public String getGalogin() {
		return galogin;
	}

	public void setGalogin(String galogin) {
		this.galogin = galogin;
	}
	
}