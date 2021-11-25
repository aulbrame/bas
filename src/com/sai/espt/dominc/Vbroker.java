package com.sai.espt.dominc;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Vbroker implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer tahun;
	private Integer bulan;
	private String incometype;
	private String nikependudukan;
	private String nomorurut;
	private String npwpbroker;
	private String namabrokerktp;
	private String alamatbroker;
	private String kodeobjekpajak;
	private Integer penghasilan;
	private Integer dpp;
	private Double tarif;
	private Integer pph21;
	private String npwpperusahaan;
	private String namaperusahaan;
	private Date tanggalpotong;
	private Integer view_signature;
	private String pngfile;
	private String nullfile;
	@Id
	public Integer getTahun() {
		return tahun;
	}
	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}
	public Integer getBulan() {
		return bulan;
	}
	public void setBulan(Integer bulan) {
		this.bulan = bulan;
	}
	public String getIncometype() {
		return incometype;
	}
	public void setIncometype(String incometype) {
		this.incometype = incometype;
	}
	public String getNikependudukan() {
		return nikependudukan;
	}
	public void setNikependudukan(String nikependudukan) {
		this.nikependudukan = nikependudukan;
	}
	public String getNomorurut() {
		return nomorurut;
	}
	public void setNomorurut(String nomorurut) {
		this.nomorurut = nomorurut;
	}
	public String getNpwpbroker() {
		return npwpbroker;
	}
	public void setNpwpbroker(String npwpbroker) {
		this.npwpbroker = npwpbroker;
	}
	public String getNamabrokerktp() {
		return namabrokerktp;
	}
	public void setNamabrokerktp(String namabrokerktp) {
		this.namabrokerktp = namabrokerktp;
	}
	public String getAlamatbroker() {
		return alamatbroker;
	}
	public void setAlamatbroker(String alamatbroker) {
		this.alamatbroker = alamatbroker;
	}
	public String getKodeobjekpajak() {
		return kodeobjekpajak;
	}
	public void setKodeobjekpajak(String kodeobjekpajak) {
		this.kodeobjekpajak = kodeobjekpajak;
	}
	public Integer getPenghasilan() {
		return penghasilan;
	}
	public void setPenghasilan(Integer penghasilan) {
		this.penghasilan = penghasilan;
	}
	public Integer getDpp() {
		return dpp;
	}
	public void setDpp(Integer dpp) {
		this.dpp = dpp;
	}
	public Double getTarif() {
		return tarif;
	}
	public void setTarif(Double tarif) {
		this.tarif = tarif;
	}
	public Integer getPph21() {
		return pph21;
	}
	public void setPph21(Integer pph21) {
		this.pph21 = pph21;
	}
	public String getNpwpperusahaan() {
		return npwpperusahaan;
	}
	public void setNpwpperusahaan(String npwpperusahaan) {
		this.npwpperusahaan = npwpperusahaan;
	}
	public String getNamaperusahaan() {
		return namaperusahaan;
	}
	public void setNamaperusahaan(String namaperusahaan) {
		this.namaperusahaan = namaperusahaan;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTanggalpotong() {
		return tanggalpotong;
	}
	public void setTanggalpotong(Date tanggalpotong) {
		this.tanggalpotong = tanggalpotong;
	}
	public Integer getView_signature() {
		return view_signature;
	}
	public void setView_signature(Integer view_signature) {
		this.view_signature = view_signature;
	}
	public String getPngfile() {
		return pngfile;
	}
	public void setPngfile(String pngfile) {
		this.pngfile = pngfile;
	}
	public String getNullfile() {
		return nullfile;
	}
	public void setNullfile(String nullfile) {
		this.nullfile = nullfile;
	}
	
	
	
	
	

}