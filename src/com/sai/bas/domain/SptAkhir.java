package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the SptAkhir database table.
 * 
 */
@Entity
@NamedQuery(name="SptAkhir.findAll", query="SELECT s FROM SptAkhir s")
public class SptAkhir implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String saidktp;

	private int bmgr;

	private int bmkt;

	private int bwpb;

	private int comm;

	private Timestamp sacreatedate;

	private Timestamp satanggal;

	private int sclo;

	private int senm;

	public SptAkhir() {
	}

	public String getSaidktp() {
		return this.saidktp;
	}

	public void setSaidktp(String saidktp) {
		this.saidktp = saidktp;
	}

	public int getBmgr() {
		return this.bmgr;
	}

	public void setBmgr(int bmgr) {
		this.bmgr = bmgr;
	}

	public int getBmkt() {
		return this.bmkt;
	}

	public void setBmkt(int bmkt) {
		this.bmkt = bmkt;
	}

	public int getBwpb() {
		return this.bwpb;
	}

	public void setBwpb(int bwpb) {
		this.bwpb = bwpb;
	}

	public int getComm() {
		return this.comm;
	}

	public void setComm(int comm) {
		this.comm = comm;
	}

	public Timestamp getSacreatedate() {
		return this.sacreatedate;
	}

	public void setSacreatedate(Timestamp sacreatedate) {
		this.sacreatedate = sacreatedate;
	}

	public Timestamp getSatanggal() {
		return this.satanggal;
	}

	public void setSatanggal(Timestamp satanggal) {
		this.satanggal = satanggal;
	}

	public int getSclo() {
		return this.sclo;
	}

	public void setSclo(int sclo) {
		this.sclo = sclo;
	}

	public int getSenm() {
		return this.senm;
	}

	public void setSenm(int senm) {
		this.senm = senm;
	}

}