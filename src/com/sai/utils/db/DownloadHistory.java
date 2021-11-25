package com.sai.utils.db;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the DownloadHistory database table.
 * 
 */
@Entity
@NamedQuery(name="DownloadHistory.findAll", query="SELECT d FROM DownloadHistory d")
public class DownloadHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	private int dhbulan;

	private double dhcount;

	private Timestamp dhdate;

	private String dhfilename;

	private String dhidktp;

	private int dhtahun;

	private String dhtipe;

	public DownloadHistory() {
	}

	public int getDhbulan() {
		return this.dhbulan;
	}

	public void setDhbulan(int dhbulan) {
		this.dhbulan = dhbulan;
	}

	public double getDhcount() {
		return this.dhcount;
	}

	public void setDhcount(double dhcount) {
		this.dhcount = dhcount;
	}

	public Timestamp getDhdate() {
		return this.dhdate;
	}

	public void setDhdate(Timestamp dhdate) {
		this.dhdate = dhdate;
	}

	public String getDhfilename() {
		return this.dhfilename;
	}

	public void setDhfilename(String dhfilename) {
		this.dhfilename = dhfilename;
	}

	public String getDhidktp() {
		return this.dhidktp;
	}

	public void setDhidktp(String dhidktp) {
		this.dhidktp = dhidktp;
	}

	public int getDhtahun() {
		return this.dhtahun;
	}

	public void setDhtahun(int dhtahun) {
		this.dhtahun = dhtahun;
	}

	public String getDhtipe() {
		return this.dhtipe;
	}

	public void setDhtipe(String dhtipe) {
		this.dhtipe = dhtipe;
	}

}