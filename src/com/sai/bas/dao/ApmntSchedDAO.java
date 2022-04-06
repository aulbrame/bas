package com.sai.bas.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.sai.bas.domain.AppointmentSchedule;
import com.sai.utils.SysUtils;
import com.sai.utils.db.StoreHibernateUtil;

public class ApmntSchedDAO {
	private static Session session;
	
	public int pageCount(String filter) throws Exception {
		int count = 0;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		session = StoreHibernateUtil.openSession();
		count = Integer.parseInt((String) session.createSQLQuery("select count(*) from AppointmentSchedule "
				+ "where " + filter).uniqueResult().toString());
		session.close();
        return count;
    }
	
	@SuppressWarnings("unchecked")
	public List<AppointmentSchedule> listPaging(int first, int second, String filter, String orderby, int totalsize) throws Exception {
		List<AppointmentSchedule> asList = null;
		if (filter == null || "".equals(filter))
			filter = "0 = 0";
		System.out.println("totalsize = "+totalsize);
    	session = StoreHibernateUtil.openSession();
    	int total = Integer.parseInt((String) session.createSQLQuery("select count(*) from dbo.AppointmentSchedule (nolock)"
				+ "where " + filter).uniqueResult().toString());
    	if(second>total){
    		second = total;
    		first = second - first;
    	}else{
    		first = SysUtils.PAGESIZE;
    	}
    	asList = session.createSQLQuery("select TOP " + first + " * from (SELECT TOP " + second + " * FROM dbo.AppointmentSchedule (nolock) where " + filter +" ORDER BY APSCSequenceNo ASC) a " +
    			"order by APSCSequenceNo DESC  "
				).addEntity(AppointmentSchedule.class).list();		

		session.close();
		return asList;	
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getBranch(String appUserId) throws Exception {
		List<String> listBranch = null;
		session = StoreHibernateUtil.openSession();
		listBranch = session.createSQLQuery("select brcode+'-'+brname from Branch where brcode in (select distinct bubrcode from BranchUser where buuserid='"+appUserId+"')").list();
		session.close();
		return listBranch;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getTeam(String appUserId) throws Exception {
		List<String> listMTeam = null;
		session = StoreHibernateUtil.openSession();
		listMTeam = session.createSQLQuery("select bmtbrcode+'/'+bmtteam+'('+bmtname+')' from BranchMarketingTeam where bmtbrcode in (select distinct bubrcode from BranchUser where buuserid='"+appUserId+"')").list();
		session.close();
		return listMTeam;	
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getDriverName(String brcode) throws Exception {
		List<String> listDriverName = null;
		session = StoreHibernateUtil.openSession();
		listDriverName = session.createSQLQuery("select VDREmpName+'('+VDREmpNo+')' from VehicleDriver where VDRBrCode='"+brcode+"'").list();
		session.close();
		return listDriverName;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getVehicleNo(String brcode) throws Exception {
		List<String> listVehicleNo = null;
		session = StoreHibernateUtil.openSession();
		listVehicleNo = session.createSQLQuery("select VDNo from VehicleDescription where VDBrCode='"+brcode+"'").list();
		session.close();
		return listVehicleNo;
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getEmployeeName(String brcode) throws Exception {
		List<String> listEmployeeName = null;
		session = StoreHibernateUtil.openSession();
		listEmployeeName = session.createSQLQuery("select empname+' ['+empposition+']'+' - '+empno as full_name from Employee where empworkstatus='A' and empbrcode='"+brcode+"'").list();
		session.close();
		return listEmployeeName;
	}
	
	public int getLastSeqNo() {
		int lastSeqNo = 0;
		session = StoreHibernateUtil.openSession();
		lastSeqNo = Integer.parseInt((String) session.createSQLQuery("select top 1 APSCSequenceNo from AppointmentSchedule order by APSCSequenceNo desc").uniqueResult().toString());
		session.close();
		return lastSeqNo;
	}
	
	public void save(Session session, Date dateApp, Date time, String meetClient, String VNo, String Empno, 
			String MTeam, String pic, String ClientStatus, String ClientPhone, String ClientName, 
			String AppointmentPlc, String AppointmentStatus, String Capab, String description, 
			String driver, String BRCode, String Appointment, String AccountNo, String interested, 
			int marginAmount, String userActivity) throws Exception{
		int SeqNoNext = getLastSeqNo()+1;
		Query query = session.createSQLQuery(
				"INSERT INTO AppointmentSchedule (APSCDate, APSCSequenceNo, APSCBrCode, APSCEmpNoPIC, APSCEmpNo, APSCEmpTeam, APSCVDNo, APSCDriver, APSCType, APSCClientMeets, APSCClientStatus, APSCClientName, APSCMobilePhone, APSCAccountNo, APSCTime, APSCLocation, APSCStatus, APSCInterested, APSCCapability, APSCAmount, APSCDescription, APSCInsert, APSCInsertDate, APSCUpdate, APSCUpdateDate)"
				+ " Values (:APSCDate, :APSCSequenceNo, :APSCBrCode, :APSCEmpNoPIC, :APSCEmpNo, :APSCEmpTeam, :APSCVDNo, :APSCDriver, :APSCType, :APSCClientMeets, :APSCClientStatus, :APSCClientName, :APSCMobilePhone, :APSCAccountNo, :APSCTime, :APSCLocation, :APSCStatus, :APSCInterested, :APSCCapability, :APSCAmount, :APSCDescription, :APSCInsert, :APSCInsertDate, :APSCUpdate, :APSCUpdateDate)");
		query.setParameter("APSCDate", dateApp);
		query.setParameter("APSCSequenceNo", SeqNoNext);
		query.setParameter("APSCBrCode", BRCode);
		query.setParameter("APSCEmpNoPIC", pic);
		query.setParameter("APSCEmpNo", Empno);
		query.setParameter("APSCEmpTeam", MTeam);
		query.setParameter("APSCVDNo", VNo);
		query.setParameter("APSCDriver", driver);
		query.setParameter("APSCType", Appointment);
		query.setParameter("APSCClientMeets", meetClient);
		query.setParameter("APSCClientStatus", ClientStatus);
		query.setParameter("APSCClientName", ClientName);
		query.setParameter("APSCMobilePhone", ClientPhone);
		query.setParameter("APSCAccountNo", AccountNo);
		query.setParameter("APSCTime", time);
		query.setParameter("APSCLocation", AppointmentPlc);
		query.setParameter("APSCStatus", AppointmentStatus);
		query.setParameter("APSCInterested", interested);
		query.setParameter("APSCCapability", Capab);
		query.setParameter("APSCAmount", marginAmount);
		query.setParameter("APSCDescription", description);
		query.setParameter("APSCInsert", userActivity);
		query.setParameter("APSCInsertDate", new Date());
		query.setParameter("APSCUpdate", userActivity);
		query.setParameter("APSCUpdateDate", new Date());
		query.executeUpdate();
	}
	
	public void update(Session session, Date dateApp, Date time, int SeqNo, String meetClient, String VNo, String Empno, 
			String MTeam, String pic, String ClientStatus, String ClientPhone, String ClientName, 
			String AppointmentPlc, String AppointmentStatus, String Capab, String description, 
			String driver, String BRCode, String Appointment, String AccountNo, String interested, 
			int marginAmount, String userActivity) throws Exception{
		Query query = session.createSQLQuery("UPDATE AppointmentSchedule SET"
				//+ " APSCDate='"+dateApp+"',"
				//+ " APSCTime='"+time+"',"
				+ " APSCDate=:APSCDate,"
				+ " APSCSequenceNo="+SeqNo+","
				+ " APSCBrCode='"+BRCode+"',"
				+ " APSCEmpNoPIC='"+pic+"',"
				+ " APSCEmpNo='"+Empno+"',"
				+ " APSCEmpTeam='"+MTeam+"',"
				+ " APSCVDNo='"+VNo+"',"
				+ " APSCDriver='"+driver+"',"
				+ " APSCType='"+Appointment+"',"
				+ " APSCClientMeets='"+meetClient+"',"
				+ " APSCClientStatus='"+ClientStatus+"',"
				+ " APSCClientName='"+ClientName+"',"
				+ " APSCMobilePhone='"+ClientPhone+"',"
				+ " APSCAccountNo='"+AccountNo+"',"
				+ " APSCTime=:APSCTime,"
				+ " APSCLocation='"+AppointmentPlc+"',"
				+ " APSCStatus='"+AppointmentStatus+"',"
				+ " APSCInterested='"+interested+"',"
				+ " APSCCapability='"+Capab+"',"
				+ " APSCAmount="+marginAmount+","
				+ " APSCDescription='"+description+"',"
				+ " APSCUpdate='"+userActivity+"',"
				+ " APSCUpdateDate=:APSCUpdateDate"
				+ " WHERE APSCDate=:APSCDate AND APSCSequenceNo="+SeqNo+" AND APSCBrCode='"+BRCode+"'");
		query.setParameter("APSCDate", dateApp);
		query.setParameter("APSCTime", time);
		query.setParameter("APSCUpdateDate", new Date());
		query.executeUpdate();
	}
}
