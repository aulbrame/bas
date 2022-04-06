package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the Signature database table.
 * 
 */
@Entity
@NamedQuery(name="Signature.findAll", query="SELECT s FROM Signature s")
public class Signature implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String signpwppt;

	private int sigview;

	public Signature() {
	}

	public String getSignpwppt() {
		return this.signpwppt;
	}

	public void setSignpwppt(String signpwppt) {
		this.signpwppt = signpwppt;
	}

	public int getSigview() {
		return this.sigview;
	}

	public void setSigview(int sigview) {
		this.sigview = sigview;
	}

}