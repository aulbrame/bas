package com.sai.espt.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sai.espt.domain.SptAkhir;
import com.sai.espt.dominc.DownHisto;
import com.sai.espt.dominc.Vbroker;
import com.sai.utils.db.StoreHibernateUtil;

public class SptAkhirDAO {
	
	private Session session;
	
	
	@SuppressWarnings("unchecked")
	public List<SptAkhir> listPaging(int first, int second, String filter, String orderby) throws Exception {		
    	List<SptAkhir> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
    	oList = session.createSQLQuery("select * from SptAkhir "
				+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				.addEntity(SptAkhir.class).list();		

		session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from SptAkhir "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }

	@SuppressWarnings("unchecked")
	public List<Object> listByFilter(String unlogin, int years,int months) throws Exception {	
		//
		List<Object> oList = null;
       	session = StoreHibernateUtil.openSession();
       	oList = session.createSQLQuery("select bmgr,bmkt,bwpb,comm,sclo,senm "
       			+ "from dbo.SptAkhir (nolock) "
       			+ "where salogin = '" + unlogin + "' AND "
       			+ "year(satanggal) = '" + years + "' AND "
       			+ "month(satanggal) = '" + months + "'"
       			).list();   
        session.close();
        return oList;
    }	
	

	@SuppressWarnings("unchecked")
	public List<Vbroker> listReport(int years, int months,String types,String noktp,String unlogin) throws Exception {	
		//
		List<Vbroker> oList = null;
       	session = StoreHibernateUtil.openSession();
       	oList = session.createSQLQuery(
       			"spr_espt21_broker "+ years +","+months+",'"+types+"','"+noktp+"','"+unlogin+"'" 
       			).addEntity(Vbroker.class)
       			.list();   
        session.close();
        return oList;
    }
	
	/*
 	@SuppressWarnings("unchecked")
	public List<Object> getNik(int years, int months,String types,String noktp) throws Exception {	
		//
		List<Object> oList = null;
       	session = StoreHibernateUtil.openSession();
       	oList = session.createSQLQuery(
       			"spr_espt21_broker where tahun = "+ years +" and bulan = "+months+
       			" incometype = '"+types+"' '"+noktp+"'" 
       			).list();   
        session.close();
        return oList;
    }*/
	
	@SuppressWarnings("unchecked")
	public List<DownHisto> listDownload(String filter) throws Exception {	
		//
		List<DownHisto> oList = null;
       	session = StoreHibernateUtil.openSession();
       	oList = session.createSQLQuery(
       			"select * from DownHisto where "+ filter
       			).addEntity(DownHisto.class)
       			.list();   
        session.close();
        return oList;
    }
	
	public int countValidasi(String filter) throws Exception {
		int count = 0;
		/*if (filter == null || "".equals(filter) )
			filter = "0 = 0";*/
		session = StoreHibernateUtil.openSession();
       	count = Integer.parseInt((String) session.createSQLQuery("select count(*) from DownHisto where " 
       	+ filter).uniqueResult().toString());
        session.close();
        return count;
    }
	
	public SptAkhir findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		SptAkhir oForm = (SptAkhir) session.createQuery("from SptAkhir where unidktp = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from SptAkhir order by " + fieldname).list();   
        session.close();
        return oList;
	}
		
	public void save(Session session,String nik,Date dhtglpotong,String dhtipe) throws HibernateException, Exception {
		Query query = session.createSQLQuery("INSERT INTO downhisto (dhlogin, dhtgldownload,dhtglpotong,dhtipe) VALUES (:dhlogin, :dhtgldownload, :dhtglpotong, :dhtipe)");
		query.setParameter("dhlogin", nik);
		query.setParameter("dhtgldownload", new Date());
		query.setParameter("dhtglpotong", dhtglpotong);
		query.setParameter("dhtipe", dhtipe);
		query.executeUpdate();
	}
	
	public void delete(Session session,String nik,Date dhtglpotong,String dhtipe) throws HibernateException, Exception {
		Query query = session.createSQLQuery("DELETE FROM downhisto where dhlogin=:dhlogin AND dhtipe=:dhtipe AND dhtglpotong=:dhtglpotong");
		query.setParameter("dhlogin", nik);
		query.setParameter("dhtglpotong", dhtglpotong);
		query.setParameter("dhtipe", dhtipe);
		query.executeUpdate();    
    }

}

