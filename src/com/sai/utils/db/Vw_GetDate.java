package com.sai.utils.db;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the vw_GetDate database table.
 * 
 */
@Entity
@Table(name="vw_GetDate")
@NamedQuery(name="Vw_GetDate.findAll", query="SELECT v FROM Vw_GetDate v")
public class Vw_GetDate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="Now")
	private Timestamp now;

	public Vw_GetDate() {
	}

	public Timestamp getNow() {
		return this.now;
	}

	public void setNow(Timestamp now) {
		this.now = now;
	}

}