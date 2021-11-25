package com.sai.utils.db;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vw_NEWID database table.
 * 
 */
@Entity
@Table(name="vw_NEWID")
@NamedQuery(name="Vw_NEWID.findAll", query="SELECT v FROM Vw_NEWID v")
public class Vw_NEWID implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="UNIQID")
	private String uniqid;

	public Vw_NEWID() {
	}

	public String getUniqid() {
		return this.uniqid;
	}

	public void setUniqid(String uniqid) {
		this.uniqid = uniqid;
	}

}