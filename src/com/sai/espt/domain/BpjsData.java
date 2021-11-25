package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BpjsData database table.
 * 
 */
@Entity
@NamedQuery(name="BpjsData.findAll", query="SELECT b FROM BpjsData b")
public class BpjsData implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BpjsDataPK id;

	private String bdhubkeluarga;

	private String bdkodebu;

	private String bdnama;

	private String bdnamabu;

	public BpjsData() {
	}

	public BpjsDataPK getId() {
		return this.id;
	}

	public void setId(BpjsDataPK id) {
		this.id = id;
	}

	public String getBdhubkeluarga() {
		return this.bdhubkeluarga;
	}

	public void setBdhubkeluarga(String bdhubkeluarga) {
		this.bdhubkeluarga = bdhubkeluarga;
	}

	public String getBdkodebu() {
		return this.bdkodebu;
	}

	public void setBdkodebu(String bdkodebu) {
		this.bdkodebu = bdkodebu;
	}

	public String getBdnama() {
		return this.bdnama;
	}

	public void setBdnama(String bdnama) {
		this.bdnama = bdnama;
	}

	public String getBdnamabu() {
		return this.bdnamabu;
	}

	public void setBdnamabu(String bdnamabu) {
		this.bdnamabu = bdnamabu;
	}

}