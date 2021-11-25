package com.sai.espt.dominc;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


/**
 * The persistent class for the DownHisto database table.
 * 
 */
@Entity
//@NamedQuery(name="DownHisto.findAll", query="SELECT b FROM DownHisto b")
public class DownHisto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String dhlogin;
	private Date dhtglpotong;
	private String dhtipe;
	private Date dhtgldownload;
	
	@Id
	public Date getDhtglpotong() {
		return dhtglpotong;
	}
	public String getDhlogin() {
		return dhlogin;
	}
	public void setDhlogin(String dhlogin) {
		this.dhlogin = dhlogin;
	}
	public void setDhtglpotong(Date dhtglpotong) {
		this.dhtglpotong = dhtglpotong;
	}
	public String getDhtipe() {
		return dhtipe;
	}
	public void setDhtipe(String dhtipe) {
		this.dhtipe = dhtipe;
	}
	public Date getDhtgldownload() {
		return dhtgldownload;
	}
	public void setDhtgldownload(Date dhtgldownload) {
		this.dhtgldownload = dhtgldownload;
	}
	
	
}