package com.sai.bas.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sai.bas.domain.AppUser;
import com.sai.utils.db.StoreHibernateUtil;

public class AppUserDAO {
	
	private Session session;

	public AppUser login(Session session, String loginid) throws HibernateException, Exception {
		AppUser oForm = null;
		if (loginid != null) loginid = loginid.trim().toUpperCase();
		oForm = (AppUser) session.createQuery("from AppUser where appuserid = '" + loginid + "'").uniqueResult();
		return oForm;
	}	
	
	@SuppressWarnings("unchecked")
	public List<Object> getMac() throws Exception {	
		//
		List<Object> oList = null;
       	session = StoreHibernateUtil.openSession();
       	oList = session.createSQLQuery("select net_address from master..sysprocesses where spid = @@SPID").list();
       	
        session.close();
        return oList;
    }	
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from vw_databroker "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<AppUser> listByFilter(String filter, String orderby) throws Exception {		
    	List<AppUser> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from UserNpwp where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public AppUser findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		AppUser oForm = (AppUser) session.createQuery("from UserNpwp where unidktp = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	
	@SuppressWarnings("rawtypes")
	public List listStr(String fieldname) throws Exception {
		List oList = new ArrayList();
       	session = StoreHibernateUtil.openSession();
       	oList = session.createQuery("select " + fieldname + " from UserNpwp order by " + fieldname).list();   
        session.close();
        return oList;
	}

	public int countKey() throws Exception {
		int count = 0;
		/*if (filter == null || "".equals(filter) )
			filter = "0 = 0";*/
		session = StoreHibernateUtil.openSession();
       	count = Integer.parseInt((String) session.createSQLQuery(
       			"select isnull(max(lhseqno),0) + 1" +
       			"from dbo.LogHistory (nolock) ").uniqueResult().toString());
        session.close();
        return count;
    }

	public AppUser getByPk(String pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		AppUser oForm = (AppUser) session.createQuery("from AppUser where unlogin = " + pk).uniqueResult();
		session.close();
		return oForm;
	}

	public void save(Session session, int lhseqno,String lhidlogin,String lhmac,String lhip) throws HibernateException, Exception {
		Query query = session.createSQLQuery("INSERT INTO loghistory (lhseqno, lhidlogin,lhtgllogin,lhmac,lhip) VALUES (:lhseqno, :lhidlogin, :lhtgllogin, :lhmac,:lhip)");
		query.setParameter("lhseqno", lhseqno);
		query.setParameter("lhidlogin", lhidlogin);
		query.setParameter("lhtgllogin", new Date());
		query.setParameter("lhmac", lhmac);
		query.setParameter("lhip", lhip);
		query.executeUpdate();
	}
	
	public void autoSetSptAkhir(Session session,String lhidlogin) throws HibernateException, Exception {
		Query query = session.createSQLQuery("exec dbo.sp_auto_insert_lastspt_bynik '"+lhidlogin+"'");
		query.executeUpdate();
	}
	public void update(Session session, int lhseqno) throws HibernateException, Exception {
		Query query = session.createSQLQuery("UPDATE loghistory set lhtgllogout = :lhtgllogout where  lhseqno = :lhseqno");
		query.setParameter("lhseqno", lhseqno);
		query.setParameter("lhtgllogout", new Date());
		query.executeUpdate();
	}
	
	public void delete(Session session, AppUser oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

	public void saveOrUpdate(Session sessionLoc, AppUser oForm) throws HibernateException, Exception {
		sessionLoc.saveOrUpdate(oForm);
	}

}

