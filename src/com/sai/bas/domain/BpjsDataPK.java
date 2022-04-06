package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the BpjsData database table.
 * 
 */
@Embeddable
public class BpjsDataPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Temporal(TemporalType.TIMESTAMP)
	private java.util.Date bdtgl;

	private String bdcardno;

	public BpjsDataPK() {
	}
	public java.util.Date getBdtgl() {
		return this.bdtgl;
	}
	public void setBdtgl(java.util.Date bdtgl) {
		this.bdtgl = bdtgl;
	}
	public String getBdcardno() {
		return this.bdcardno;
	}
	public void setBdcardno(String bdcardno) {
		this.bdcardno = bdcardno;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BpjsDataPK)) {
			return false;
		}
		BpjsDataPK castOther = (BpjsDataPK)other;
		return 
			this.bdtgl.equals(castOther.bdtgl)
			&& this.bdcardno.equals(castOther.bdcardno);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.bdtgl.hashCode();
		hash = hash * prime + this.bdcardno.hashCode();
		
		return hash;
	}
}