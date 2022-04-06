package com.sai.espt.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.FloatType;

import com.sai.espt.domain.UserKacab;
import com.sai.espt.domain.UserNpwp;
import com.sai.espt.dominc.vw_databroker;
import com.sai.utils.SysUtils;
import com.sai.utils.db.StoreHibernateUtil;

public class UserNpwpDAO {
	
	private Session session;

	public UserNpwp login(Session session, String loginid) throws HibernateException, Exception {
		UserNpwp oForm = null;
		if (loginid != null) loginid = loginid.trim().toUpperCase();
		oForm = (UserNpwp) session.createQuery("from UserNpwp where unlogin = '" + loginid + "'").uniqueResult();
		return oForm;
	}

	public UserKacab loginKacab(Session session, String loginid) throws HibernateException, Exception {
		UserKacab oForm = null;
		if (loginid != null) loginid = loginid.trim().toUpperCase();
		oForm = (UserKacab) session.createQuery("from UserKacab where uklogin = '" + loginid + "'").uniqueResult();
		return oForm;
	}
	
	@SuppressWarnings("unchecked")
	public List<vw_databroker> listPaging(int first, int second, String filter, String orderby, int totalsize) throws Exception {		
    	List<vw_databroker> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	System.out.println("totalsize = "+totalsize);
    	session = StoreHibernateUtil.openSession();
    	int total = Integer.parseInt((String) session.createSQLQuery("select count(*) from dbo.vw_databroker (nolock)"
				+ "where " + filter).uniqueResult().toString());
    	if(second>total){
    		second = total;
    		first = second - first;
    	}else{
    		first = SysUtils.PAGESIZE;
    	}
    	oList = session.createSQLQuery("select TOP " + first + " * from (SELECT TOP " + second + " * FROM dbo.vw_databroker (nolock) where " + filter +" ORDER BY unnama ASC) a " +
    			"order by unnama DESC  "
				//+ "where " + filter + " order by " + orderby + " limit " + second +" offset " + first)
				).addEntity(vw_databroker.class).list();		

		session.close();
        return oList;
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
	public List<UserNpwp> listByFilter(String filter, String orderby) throws Exception {		
    	List<UserNpwp> oList = null;
    	if (filter == null || "".equals(filter))
			filter = "0 = 0";
    	session = StoreHibernateUtil.openSession();
		oList = session.createQuery("from UserNpwp where " + filter + " order by " + orderby).list();
		session.close();
        return oList;
    }	
	
	public UserNpwp findByPk(Integer pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		UserNpwp oForm = (UserNpwp) session.createQuery("from UserNpwp where unidktp = " + pk).uniqueResult();
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

	public UserNpwp getByPk(String pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		UserNpwp oForm = (UserNpwp) session.createQuery("from UserNpwp where unlogin = " + pk).uniqueResult();
		session.close();
		return oForm;
	}
	public UserKacab getByPkGA(String pk) throws Exception {
		session = StoreHibernateUtil.openSession();
		UserKacab oForm = (UserKacab) session.createQuery("from UserKacab where uklogin = " + pk).uniqueResult();
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
	
	public void delete(Session session, UserNpwp oForm) throws HibernateException, Exception {
		session.delete(oForm);    
    }

	public void saveOrUpdate(Session sessionLoc, UserNpwp oForm) throws HibernateException, Exception {
		sessionLoc.saveOrUpdate(oForm);
	}
	public void saveOrUpdateGA(Session sessionLoc, UserKacab oForm) throws HibernateException, Exception {
		sessionLoc.saveOrUpdate(oForm);
	}

}

