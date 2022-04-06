package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the Branch database table.
 * 
 */
@Entity
@NamedQuery(name="Branch.findAll", query="SELECT b FROM Branch b")
public class Branch implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String brcode;

	private String braddress;

	private String braddress02;

	private String bradmin;

	private String brbranchmanager;

	private String brchiefopt;

	private String brcity;

	private String brcodecomm;

	private String brcodenew;

	private String brcomcode;

	private String brcountry;

	private String brdescription;

	private String bremail;

	private String brextention;

	private String brfaximile;

	private String brfinance;

	private String brinit;

	private String brinitial;

	private String brinitialcbo;

	private String brinitialceo;

	private String brinsert;

	private Timestamp brinsertdate;

	private String brkecamatan;

	private String brkelurahan;

	private String brname;

	private String brnpwp;

	private String brpostcode;

	private String brprovince;

	private String brrtrw;

	private String brsecretary;

	private String brstspindah;

	private String brtelephone;

	private String brtraineecount;

	private String brupdate;

	private Timestamp brupdatedate;

	private int stspusat;

	public Branch() {
	}

	public String getBrcode() {
		return this.brcode;
	}

	public void setBrcode(String brcode) {
		this.brcode = brcode;
	}

	public String getBraddress() {
		return this.braddress;
	}

	public void setBraddress(String braddress) {
		this.braddress = braddress;
	}

	public String getBraddress02() {
		return this.braddress02;
	}

	public void setBraddress02(String braddress02) {
		this.braddress02 = braddress02;
	}

	public String getBradmin() {
		return this.bradmin;
	}

	public void setBradmin(String bradmin) {
		this.bradmin = bradmin;
	}

	public String getBrbranchmanager() {
		return this.brbranchmanager;
	}

	public void setBrbranchmanager(String brbranchmanager) {
		this.brbranchmanager = brbranchmanager;
	}

	public String getBrchiefopt() {
		return this.brchiefopt;
	}

	public void setBrchiefopt(String brchiefopt) {
		this.brchiefopt = brchiefopt;
	}

	public String getBrcity() {
		return this.brcity;
	}

	public void setBrcity(String brcity) {
		this.brcity = brcity;
	}

	public String getBrcodecomm() {
		return this.brcodecomm;
	}

	public void setBrcodecomm(String brcodecomm) {
		this.brcodecomm = brcodecomm;
	}

	public String getBrcodenew() {
		return this.brcodenew;
	}

	public void setBrcodenew(String brcodenew) {
		this.brcodenew = brcodenew;
	}

	public String getBrcomcode() {
		return this.brcomcode;
	}

	public void setBrcomcode(String brcomcode) {
		this.brcomcode = brcomcode;
	}

	public String getBrcountry() {
		return this.brcountry;
	}

	public void setBrcountry(String brcountry) {
		this.brcountry = brcountry;
	}

	public String getBrdescription() {
		return this.brdescription;
	}

	public void setBrdescription(String brdescription) {
		this.brdescription = brdescription;
	}

	public String getBremail() {
		return this.bremail;
	}

	public void setBremail(String bremail) {
		this.bremail = bremail;
	}

	public String getBrextention() {
		return this.brextention;
	}

	public void setBrextention(String brextention) {
		this.brextention = brextention;
	}

	public String getBrfaximile() {
		return this.brfaximile;
	}

	public void setBrfaximile(String brfaximile) {
		this.brfaximile = brfaximile;
	}

	public String getBrfinance() {
		return this.brfinance;
	}

	public void setBrfinance(String brfinance) {
		this.brfinance = brfinance;
	}

	public String getBrinit() {
		return this.brinit;
	}

	public void setBrinit(String brinit) {
		this.brinit = brinit;
	}

	public String getBrinitial() {
		return this.brinitial;
	}

	public void setBrinitial(String brinitial) {
		this.brinitial = brinitial;
	}

	public String getBrinitialcbo() {
		return this.brinitialcbo;
	}

	public void setBrinitialcbo(String brinitialcbo) {
		this.brinitialcbo = brinitialcbo;
	}

	public String getBrinitialceo() {
		return this.brinitialceo;
	}

	public void setBrinitialceo(String brinitialceo) {
		this.brinitialceo = brinitialceo;
	}

	public String getBrinsert() {
		return this.brinsert;
	}

	public void setBrinsert(String brinsert) {
		this.brinsert = brinsert;
	}

	public Timestamp getBrinsertdate() {
		return this.brinsertdate;
	}

	public void setBrinsertdate(Timestamp brinsertdate) {
		this.brinsertdate = brinsertdate;
	}

	public String getBrkecamatan() {
		return this.brkecamatan;
	}

	public void setBrkecamatan(String brkecamatan) {
		this.brkecamatan = brkecamatan;
	}

	public String getBrkelurahan() {
		return this.brkelurahan;
	}

	public void setBrkelurahan(String brkelurahan) {
		this.brkelurahan = brkelurahan;
	}

	public String getBrname() {
		return this.brname;
	}

	public void setBrname(String brname) {
		this.brname = brname;
	}

	public String getBrnpwp() {
		return this.brnpwp;
	}

	public void setBrnpwp(String brnpwp) {
		this.brnpwp = brnpwp;
	}

	public String getBrpostcode() {
		return this.brpostcode;
	}

	public void setBrpostcode(String brpostcode) {
		this.brpostcode = brpostcode;
	}

	public String getBrprovince() {
		return this.brprovince;
	}

	public void setBrprovince(String brprovince) {
		this.brprovince = brprovince;
	}

	public String getBrrtrw() {
		return this.brrtrw;
	}

	public void setBrrtrw(String brrtrw) {
		this.brrtrw = brrtrw;
	}

	public String getBrsecretary() {
		return this.brsecretary;
	}

	public void setBrsecretary(String brsecretary) {
		this.brsecretary = brsecretary;
	}

	public String getBrstspindah() {
		return this.brstspindah;
	}

	public void setBrstspindah(String brstspindah) {
		this.brstspindah = brstspindah;
	}

	public String getBrtelephone() {
		return this.brtelephone;
	}

	public void setBrtelephone(String brtelephone) {
		this.brtelephone = brtelephone;
	}

	public String getBrtraineecount() {
		return this.brtraineecount;
	}

	public void setBrtraineecount(String brtraineecount) {
		this.brtraineecount = brtraineecount;
	}

	public String getBrupdate() {
		return this.brupdate;
	}

	public void setBrupdate(String brupdate) {
		this.brupdate = brupdate;
	}

	public Timestamp getBrupdatedate() {
		return this.brupdatedate;
	}

	public void setBrupdatedate(Timestamp brupdatedate) {
		this.brupdatedate = brupdatedate;
	}

	public int getStspusat() {
		return this.stspusat;
	}

	public void setStspusat(int stspusat) {
		this.stspusat = stspusat;
	}

}