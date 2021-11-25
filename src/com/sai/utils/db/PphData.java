package com.sai.utils.db;
/*package com.sai.espt.domain;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;


*//**
 * The persistent class for the PphData database table.
 * 
 *//*
@Entity
@NamedQuery(name="PphData.findAll", query="SELECT p FROM PphData p")
public class PphData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="AlamatBroker")
	private String alamatBroker;

	@Column(name="BranchKode")
	private String branchKode;

	@Column(name="Bulan")
	private int bulan;

	@Column(name="DPP")
	private BigDecimal dpp;

	@Column(name="IncomeType")
	private String incomeType;

	@Column(name="Jabatan")
	private String jabatan;

	@Column(name="JabatanBroker")
	private String jabatanBroker;

	@Column(name="JabatanBulan")
	private String jabatanBulan;

	@Column(name="Keterangan")
	private String keterangan;

	@Column(name="KodeCabangPayroll")
	private String kodeCabangPayroll;

	@Column(name="KodeObjekPajak")
	private String kodeObjekPajak;

	@Column(name="KodeObjekPajakeSPT")
	private String kodeObjekPajakeSPT;

	@Column(name="KotaPerusahaan")
	private String kotaPerusahaan;

	@Column(name="NamaBroker")
	private String namaBroker;

	@Column(name="NamaBrokerKTP")
	private String namaBrokerKTP;

	@Column(name="NamaCabang")
	private String namaCabang;

	@Column(name="NamaPerusahaan")
	private String namaPerusahaan;

	private String NIKependudukan;

	private String NIKependudukaneSPT;

	private String NIKMarketing;

	private String NIKPayroll;

	@Column(name="NomorUrut")
	private String nomorUrut;

	private String NPWPBroker;

	private String NPWPKaryawan;

	private String NPWPPerusahaan;

	@Column(name="NPWPPT")
	private String npwppt;

	private String NPWPStatus;

	private String pdffilename;

	@Column(name="Penghasilan")
	private BigDecimal penghasilan;

	private BigDecimal PPh21;

	private String pphnpwp;

	private int pphupload;

	@Column(name="Tahun")
	private int tahun;

	@Column(name="TandaTangan")
	private String tandaTangan;

	@Column(name="Tanggal")
	private Timestamp tanggal;

	@Column(name="TanggalPotong")
	private Timestamp tanggalPotong;

	@Column(name="Tarif")
	private BigDecimal tarif;

	@Column(name="Terbilang")
	private String terbilang;

	public PphData() {
	}

	public String getAlamatBroker() {
		return this.alamatBroker;
	}

	public void setAlamatBroker(String alamatBroker) {
		this.alamatBroker = alamatBroker;
	}

	public String getBranchKode() {
		return this.branchKode;
	}

	public void setBranchKode(String branchKode) {
		this.branchKode = branchKode;
	}

	public int getBulan() {
		return this.bulan;
	}

	public void setBulan(int bulan) {
		this.bulan = bulan;
	}

	public BigDecimal getDpp() {
		return this.dpp;
	}

	public void setDpp(BigDecimal dpp) {
		this.dpp = dpp;
	}

	public String getIncomeType() {
		return this.incomeType;
	}

	public void setIncomeType(String incomeType) {
		this.incomeType = incomeType;
	}

	public String getJabatan() {
		return this.jabatan;
	}

	public void setJabatan(String jabatan) {
		this.jabatan = jabatan;
	}

	public String getJabatanBroker() {
		return this.jabatanBroker;
	}

	public void setJabatanBroker(String jabatanBroker) {
		this.jabatanBroker = jabatanBroker;
	}

	public String getJabatanBulan() {
		return this.jabatanBulan;
	}

	public void setJabatanBulan(String jabatanBulan) {
		this.jabatanBulan = jabatanBulan;
	}

	public String getKeterangan() {
		return this.keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public String getKodeCabangPayroll() {
		return this.kodeCabangPayroll;
	}

	public void setKodeCabangPayroll(String kodeCabangPayroll) {
		this.kodeCabangPayroll = kodeCabangPayroll;
	}

	public String getKodeObjekPajak() {
		return this.kodeObjekPajak;
	}

	public void setKodeObjekPajak(String kodeObjekPajak) {
		this.kodeObjekPajak = kodeObjekPajak;
	}

	public String getKodeObjekPajakeSPT() {
		return this.kodeObjekPajakeSPT;
	}

	public void setKodeObjekPajakeSPT(String kodeObjekPajakeSPT) {
		this.kodeObjekPajakeSPT = kodeObjekPajakeSPT;
	}

	public String getKotaPerusahaan() {
		return this.kotaPerusahaan;
	}

	public void setKotaPerusahaan(String kotaPerusahaan) {
		this.kotaPerusahaan = kotaPerusahaan;
	}

	public String getNamaBroker() {
		return this.namaBroker;
	}

	public void setNamaBroker(String namaBroker) {
		this.namaBroker = namaBroker;
	}

	public String getNamaBrokerKTP() {
		return this.namaBrokerKTP;
	}

	public void setNamaBrokerKTP(String namaBrokerKTP) {
		this.namaBrokerKTP = namaBrokerKTP;
	}

	public String getNamaCabang() {
		return this.namaCabang;
	}

	public void setNamaCabang(String namaCabang) {
		this.namaCabang = namaCabang;
	}

	public String getNamaPerusahaan() {
		return this.namaPerusahaan;
	}

	public void setNamaPerusahaan(String namaPerusahaan) {
		this.namaPerusahaan = namaPerusahaan;
	}

	public String getNIKependudukan() {
		return this.NIKependudukan;
	}

	public void setNIKependudukan(String NIKependudukan) {
		this.NIKependudukan = NIKependudukan;
	}

	public String getNIKependudukaneSPT() {
		return this.NIKependudukaneSPT;
	}

	public void setNIKependudukaneSPT(String NIKependudukaneSPT) {
		this.NIKependudukaneSPT = NIKependudukaneSPT;
	}

	public String getNIKMarketing() {
		return this.NIKMarketing;
	}

	public void setNIKMarketing(String NIKMarketing) {
		this.NIKMarketing = NIKMarketing;
	}

	public String getNIKPayroll() {
		return this.NIKPayroll;
	}

	public void setNIKPayroll(String NIKPayroll) {
		this.NIKPayroll = NIKPayroll;
	}

	public String getNomorUrut() {
		return this.nomorUrut;
	}

	public void setNomorUrut(String nomorUrut) {
		this.nomorUrut = nomorUrut;
	}

	public String getNPWPBroker() {
		return this.NPWPBroker;
	}

	public void setNPWPBroker(String NPWPBroker) {
		this.NPWPBroker = NPWPBroker;
	}

	public String getNPWPKaryawan() {
		return this.NPWPKaryawan;
	}

	public void setNPWPKaryawan(String NPWPKaryawan) {
		this.NPWPKaryawan = NPWPKaryawan;
	}

	public String getNPWPPerusahaan() {
		return this.NPWPPerusahaan;
	}

	public void setNPWPPerusahaan(String NPWPPerusahaan) {
		this.NPWPPerusahaan = NPWPPerusahaan;
	}

	public String getNpwppt() {
		return this.npwppt;
	}

	public void setNpwppt(String npwppt) {
		this.npwppt = npwppt;
	}

	public String getNPWPStatus() {
		return this.NPWPStatus;
	}

	public void setNPWPStatus(String NPWPStatus) {
		this.NPWPStatus = NPWPStatus;
	}

	public String getPdffilename() {
		return this.pdffilename;
	}

	public void setPdffilename(String pdffilename) {
		this.pdffilename = pdffilename;
	}

	public BigDecimal getPenghasilan() {
		return this.penghasilan;
	}

	public void setPenghasilan(BigDecimal penghasilan) {
		this.penghasilan = penghasilan;
	}

	public BigDecimal getPPh21() {
		return this.PPh21;
	}

	public void setPPh21(BigDecimal PPh21) {
		this.PPh21 = PPh21;
	}

	public String getPphnpwp() {
		return this.pphnpwp;
	}

	public void setPphnpwp(String pphnpwp) {
		this.pphnpwp = pphnpwp;
	}

	public int getPphupload() {
		return this.pphupload;
	}

	public void setPphupload(int pphupload) {
		this.pphupload = pphupload;
	}

	public int getTahun() {
		return this.tahun;
	}

	public void setTahun(int tahun) {
		this.tahun = tahun;
	}

	public String getTandaTangan() {
		return this.tandaTangan;
	}

	public void setTandaTangan(String tandaTangan) {
		this.tandaTangan = tandaTangan;
	}

	public Timestamp getTanggal() {
		return this.tanggal;
	}

	public void setTanggal(Timestamp tanggal) {
		this.tanggal = tanggal;
	}

	public Timestamp getTanggalPotong() {
		return this.tanggalPotong;
	}

	public void setTanggalPotong(Timestamp tanggalPotong) {
		this.tanggalPotong = tanggalPotong;
	}

	public BigDecimal getTarif() {
		return this.tarif;
	}

	public void setTarif(BigDecimal tarif) {
		this.tarif = tarif;
	}

	public String getTerbilang() {
		return this.terbilang;
	}

	public void setTerbilang(String terbilang) {
		this.terbilang = terbilang;
	}

}*/