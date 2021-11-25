package com.sai.espt.dominc;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the LogHistory database table.
 * 
 */
@Entity
public class LogHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int lhseqno;
	private String lhidlogin;
	private Date lhtgllogin;
	private String lhtgllogout;
	private String lhmac;
	private String lhip;

	public LogHistory() {
	}

	public int getLhseqno() {
		return lhseqno;
	}

	public void setLhseqno(int lhseqno) {
		this.lhseqno = lhseqno;
	}

	public String getLhidlogin() {
		return lhidlogin;
	}

	public void setLhidlogin(String lhidlogin) {
		this.lhidlogin = lhidlogin;
	}

	public Date getLhtgllogin() {
		return lhtgllogin;
	}

	public void setLhtgllogin(Date lhtgllogin) {
		this.lhtgllogin = lhtgllogin;
	}

	public String getLhtgllogout() {
		return lhtgllogout;
	}

	public void setLhtgllogout(String lhtgllogout) {
		this.lhtgllogout = lhtgllogout;
	}

	public String getLhmac() {
		return lhmac;
	}

	public void setLhmac(String lhmac) {
		this.lhmac = lhmac;
	}

	public String getLhip() {
		return lhip;
	}

	public void setLhip(String lhip) {
		this.lhip = lhip;
	}


}