package com.sai.bas.viewmodel;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

//import javax.activation.MimetypesFileTypeMap;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Path;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sai.bas.dao.ApmntSchedDAO;
import com.sai.bas.domain.AppUser;
import com.sai.bas.domain.AppointmentSchedule;
import com.sai.espt.model.AppointmentScheduledListModel;
import com.sai.utils.SysUtils;
import com.sai.utils.db.StoreHibernateUtil;

public class DoAppointmentSchedule extends SelectorComposer<Component>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private AppUser oUser;
	
	
	@SuppressWarnings("unused")
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
	DecimalFormat decFormat = new DecimalFormat("####,###,###.##");
	private String orderby;

	private Integer filtTahun;
	private String filtNikMarketing;
	private String filtEmployee="";
	private String filtTypeID;
	private ListModelList<String> listBranchEmployee;
	private ListModelList<String> listBranch;
	private ListModelList<String> listMTeam;
	private ListModelList<String> listDriverName;
	private ListModelList<String> listVehicleNo;
	private ListModelList<String> listEmployeeName;
	private ListModelList<String> listPICName;
	private ListModelList<String> listName;
	private Map<String, Integer> mMonthIndex;
	private Map<String, String> mTypeIndex;
	private int pageStartNumber;
	private AppointmentScheduledListModel model;
    
	@SuppressWarnings("unused")
	private String lblMessage;
	private Session session;
	
	@Wire
	Window modalDialog;
	private Textbox NameEmployee;
	private Combobox BranchEmployee;
	
	@Wire
	private Datebox dateFrom;
	@Wire
	private Datebox dateTo;
	@Wire
	private Combobox Branch;
	
	@Wire
	private Datebox dateAppointment;
	@Wire
	private Timebox timeAppointment;
	@Wire
	private Textbox MeetClient;
	@Wire
	private Combobox VehicleNo;
	@Wire
	private Combobox EmployeeName;
	@Wire
	private Combobox marketingTeam;
	@Wire
	private Combobox pic;
	@Wire
	private Textbox CStatus;
	@Wire
	private Textbox CPhone;
	@Wire
	private Textbox CName;
	@Wire
	private Textbox AppmntPlace;
	@Wire
	private Textbox AppmntStat;
	@Wire
	private Combobox capability;
	@Wire
	private Textbox desc;
	@Wire
	private Combobox driver;
	@Wire
	private Textbox appmnt;
	@Wire
	private Textbox accNo;
	@Wire
	private Combobox isInterested;
	@Wire
	private Intbox mrgAmount;
	@Wire
	private Treechildren root;
	@Wire
	private Window win;
	@Wire
	private Button btnDownload;
	@Wire
	private Label lblNotif;
	@Wire
	private Paging paging;
	@Wire
	private Button btnSubmit;
	@Wire
	private Button btnUpdate;
	@Wire
	private Button btnNew;
	private int totalsize = 0;
	private String filter;

	// …

	// Inside the initialization method
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (AppUser) zkSession.getAttribute("oUser");
		if (oUser == null){
			Executions.sendRedirect("/timeout.zul");	
		}else{
			filtNikMarketing = oUser.getUappid() + " / " + oUser.getappuserpassword();
			if(oUser.getUappid().equals(""))
			filtNikMarketing = oUser.getappuserpassword();
			refreshModel(oUser.getappuserid());
			/*paging.addEventListener("onPaging", new EventListener() {
				
				@Override
				public void onEvent(Event event) throws Exception {				
					PagingEvent pe = (PagingEvent) event;
					pageStartNumber = pe.getActivePage();
					refreshModel(pageStartNumber);
				}		
			});*/
		}
	}

	@NotifyChange({"filtTahun", "filtTrxDate", "filtTypeID","refreshModel","filtNikMarketing"})
	public void refreshModel(String AppUserId) throws Exception{
		dateFrom.setValue(new Date());
		dateTo.setValue(new Date());
		Branch.setValue("");
		
		btnSubmit.setVisible(true);
		btnUpdate.setVisible(false);
		btnNew.setVisible(false);
		listBranch = new ListModelList <String>(ApmntSchedDAO.getBranch(AppUserId));
		//listDriverName = new ListModelList<String>();
		//listVehicleNo = new ListModelList<String>();
		//listEmployeeName = new ListModelList<String>();
		//listPICName = new ListModelList<String>();
		listMTeam = new ListModelList<String>(ApmntSchedDAO.getTeam(AppUserId));
		
		dateAppointment.setValue(new Date());
		timeAppointment.setValue(new Date());
		MeetClient.setValue("");
		VehicleNo.setValue("");
		EmployeeName.setValue("");
		marketingTeam.setValue("");
		pic.setValue("");
		CStatus.setValue("");
		CPhone.setValue("");
		CName.setValue("");
		AppmntPlace.setValue("");
		AppmntStat.setValue("");
		capability.setValue("");
		desc.setValue("");
		driver.setValue("");
		appmnt.setValue("");
		accNo.setValue("");
		isInterested.setValue("");
		mrgAmount.setValue(0);
		/*if(BMTName.getItems().size()==0) {
			BMTName.addEventListener("onClick", new EventListener() {
	
				@Override
				public void onEvent(Event event) throws Exception {
					Window window= (Window)Executions.createComponents("/view/employeeDialog.zul", null, null);
					window.doModal();
					listBranchEmployee = new ListModelList <String>(ApmntSchedDAO.getBranch());
					for(int i=0;i<listBranchEmployee.getSize();i++) {
						BranchEmployee = (Combobox)Path.getComponent("/modalDialog/BranchEmployee");
						BranchEmployee.appendItem(listBranchEmployee.get(i));
					}
				}
				
			});
		}*/
	}
	/*@Listen("onClick = #btnSearch")
    public void showModal(Event e) throws Exception {
        doSearchEmployee();
    }*/

	/*@SuppressWarnings("unchecked")
	private void doSearchEmployee() throws Exception {
		NameEmployee = (Textbox)Path.getComponent("/modalDialog/nameEmployee");
		BranchEmployee = (Combobox)Path.getComponent("/modalDialog/BranchEmployee");
		String name = NameEmployee.getValue();
		String brch = BranchEmployee.getValue();
		if(brch.equals(""))
			Messagebox.show("Branch cannot empty!.");
		String[] branch= BranchEmployee.getValue().split("-");
		brch = branch[0];
		session = StoreHibernateUtil.openSession();
		listBMTName = new ListModelList<String>(session.createSQLQuery("select empname+' ['+empposition+']'+' - '+empno as full_name from Employee where empworkstatus='A' and empname like '"+name+"%' and empbrcode='"+brch+"'").list());
		session.close();
		modalDialog.detach();
		//refreshModel();
	}*/
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Command
	public void doSearch() throws Exception{
		Date dateFrm = dateFrom.getValue();
		Date dateTo_ = dateTo.getValue();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String brch = Branch.getValue();
		if(brch.equals(""))
			Messagebox.show("Branch cannot empty!.");
		String[] branch= Branch.getValue().split("-");
		brch = branch[0];
		filter = "APSCDate Between '"+f.format(dateFrm)+"' and '"+f.format(dateTo_)+"' and APSCBrCode = '"+brch+"'";
		
		paging.setVisible(true);
		paging.addEventListener("onPaging", new EventListener() {
			
			@Override
			public void onEvent(Event event) throws Exception {				
				PagingEvent pe = (PagingEvent) event;
				pageStartNumber = pe.getActivePage();
				getFormUpdate(pageStartNumber);
			}		
		});
		
		getFormUpdate(pageStartNumber);
	}
	
	public void getFormUpdate(int activePage) throws Exception{
		orderby = "APSCDate";
		totalsize = new ApmntSchedDAO().pageCount(filter);
		paging.setPageSize(SysUtils.PAGESIZE);
		model = new AppointmentScheduledListModel(activePage, SysUtils.PAGESIZE, filter, orderby,totalsize);
		paging.setTotalSize(totalsize);
		if(totalsize > 0) {
			AppointmentSchedule data = (AppointmentSchedule) model.getElementAt(0);
			System.out.println(model.getElementAt(0));
			
			dateAppointment.setValue(data.getAPSCDate());
			timeAppointment.setValue(data.getAPSCTime());
			MeetClient.setValue(data.getAPSCClientMeets());
			marketingTeam.setValue(getTeam(data.getAPSCEmpTeam(),data.getAPSCBrCode()));
			EmployeeName.setValue(getEmployeeName(data.getAPSCEmpNo()));
			VehicleNo.setValue(data.getAPSCVDNo());
			driver.setValue(getDriver(data.getAPSCDriver()));
			desc.setValue(data.getAPSCDescription());
			pic.setValue(getEmployeeName(data.getAPSCEmpNoPIC()));
			CStatus.setValue(data.getAPSCClientStatus());
			CPhone.setValue(data.getAPSCMobilePhone());
			CName.setValue(data.getAPSCClientName());
			AppmntPlace.setValue(data.getAPSCLocation());
			AppmntStat.setValue(data.getAPSCStatus());
			capability.setValue(data.getAPSCCapability());
			appmnt.setValue(data.getAPSCType());
			accNo.setValue(data.getAPSCAccountNo());
			isInterested.setValue(data.getAPSCInterested());
			mrgAmount.setValue(data.getAPSCAmount());
			btnSubmit.setVisible(false);
			btnUpdate.setVisible(true);
			btnNew.setVisible(true);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public String getDriver(String VDEmpNo) {
		String DriverName = "";
		if(VDEmpNo!=null) {
			session = StoreHibernateUtil.openSession();
			List lstDriver = session.createSQLQuery("select VDREmpName+'('+VDREmpNo+')' from VehicleDriver where VDREmpNo='"+VDEmpNo+"'").list();
			DriverName = lstDriver.get(0).toString();
			session.close();
		}
		return DriverName;	
	}
	
	@SuppressWarnings("rawtypes")
	public String getTeam(String APSCEmpTeam, String APSCBrCode) throws Exception{
		String team = "";
		if(APSCEmpTeam!=null && APSCBrCode!=null) {
			session = StoreHibernateUtil.openSession();
			List lstTeam = session.createSQLQuery("select bmtbrcode+'/'+bmtteam+'('+bmtname+')' from BranchMarketingTeam where bmtteam='"+APSCEmpTeam+"' and bmtbrcode='"+APSCBrCode+"'").list();
			team = lstTeam.get(0).toString();
			session.close();
		}
		return team;
	}
	
	@SuppressWarnings("rawtypes")
	public String getEmployeeName(String APSCEmpNo) throws Exception{
		List lsBMTName = null;
		String BMTName = "";
		if(APSCEmpNo!=null){
			session = StoreHibernateUtil.openSession();
			lsBMTName = session.createSQLQuery("select empname+' ['+empposition+']'+' - '+empno as full_name from Employee where empno='"+APSCEmpNo+"'").list();
			BMTName = lsBMTName.get(0).toString();
			session.close();
		}
		
		return BMTName;	 
	}
	
	@Command
	public void doSubmit() throws Exception {
		Date dateApp = dateAppointment.getValue();
		Date time = timeAppointment.getValue();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String MeetC = MeetClient.getValue();
		String VNo = VehicleNo.getValue();
		
		String Mteam = marketingTeam.getValue().substring(marketingTeam.getValue().indexOf("/")+1,marketingTeam.getValue().lastIndexOf("("));
		String ClientStatus = CStatus.getValue();
		String ClientPhone = CPhone.getValue();
		String ClientName = CName.getValue();
		String AppointmentPlc = AppmntPlace.getValue();
		String AppointmentStatus = AppmntStat.getValue();
		
		String descrip = desc.getValue();
		String drive = driver.getValue().substring(driver.getValue().indexOf("(")+1,driver.getValue().lastIndexOf(")"));
		String appointment = appmnt.getValue();
		String accountNo = accNo.getValue();
		
		int marginAmount = mrgAmount.getValue();
		
		String interested = isInterested.getValue();
		String capab = capability.getValue();
		if(interested.equals("Yes") || capab.equals("Yes")) {
			interested = "Y";
			capab = "Y";
		}
		else if(interested.equals("No") || capab.equals("No")) {
			interested = "N";
			capab = "N";
		}
		
		if(f.format(dateApp).equals(null))
			Messagebox.show("Please input Date!.");
		if(MeetC.equals(null) || MeetC.equals("")) 
			Messagebox.show("Please input Meeting Client!.");
		if(VNo.equals(null) || VNo.equals("")) 
			Messagebox.show("Please input Vehicle Nummber!.");
		if(EmployeeName.getValue().equals(null) || EmployeeName.getValue().equals("")) 
			Messagebox.show("Please input Marketing!.");
		if(Mteam.equals(null) || Mteam.equals("")) 
			Messagebox.show("Please input Marketing Team!.");
		if(pic.getValue().equals(null) || pic.getValue().equals("")) 
			Messagebox.show("Please input PIC!.");
		if(ClientStatus.equals(null) || ClientStatus.equals("")) 
			Messagebox.show("Please input PIC!.");
		
		String[] EmployeeNameSplit = EmployeeName.getValue().split("-");
		String Empno = EmployeeNameSplit[1].trim();
		String BRCode = Empno.substring(0, 2);
		
		String[] picSlip = pic.getValue().split("-");
		String pic_ = picSlip[1].trim();
		
		session = StoreHibernateUtil.openSession();
		ApmntSchedDAO appointmentSch = new ApmntSchedDAO();
		Transaction transaction = session.beginTransaction();
		appointmentSch.save(session, dateApp, time, MeetC, VNo, Empno, Mteam, pic_, 
				ClientStatus, ClientPhone, ClientName, AppointmentPlc, AppointmentStatus, capab,
				descrip, drive, BRCode, appointment, accountNo, interested, marginAmount, oUser.getappuserid());
		transaction.commit();
		refreshModel(oUser.getappuserid());
	}
	
	@Command
	public void doUpdate() throws Exception {
		AppointmentSchedule data = (AppointmentSchedule) model.getElementAt(0);
		Date dateApp = dateAppointment.getValue();
		Date time = timeAppointment.getValue();
		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String MeetC = MeetClient.getValue();
		String VNo = VehicleNo.getValue();
		
		String Mteam = marketingTeam.getValue().substring(marketingTeam.getValue().indexOf("/")+1,marketingTeam.getValue().lastIndexOf("("));
		
		String ClientStatus = CStatus.getValue();
		String ClientPhone = CPhone.getValue();
		String ClientName = CName.getValue();
		String AppointmentPlc = AppmntPlace.getValue();
		String AppointmentStatus = AppmntStat.getValue();
		String capab = capability.getValue();
		String descrip = desc.getValue();
		String drive = driver.getValue().substring(driver.getValue().indexOf("(")+1,driver.getValue().lastIndexOf(")"));
		String appointment = appmnt.getValue();
		String accountNo = accNo.getValue();
		String interested = isInterested.getValue();
		int marginAmount = mrgAmount.getValue();
		
		String[] EmployeeNameSplit = EmployeeName.getValue().split("-");
		String Empno = EmployeeNameSplit[1].trim();
		String BRCode = Empno.substring(0, 2);
		
		String[] picSlip = pic.getValue().split("-");
		String pic_ = picSlip[1].trim();
		
		if(interested.equals("Yes") || capab.equals("Yes")) {
			interested = "Y";
			capab = "Y";
		}
		else if(interested.equals("No") || capab.equals("No")) {
			interested = "N";
			capab = "N";
		}
		
		session = StoreHibernateUtil.openSession();
		ApmntSchedDAO appointmentSch = new ApmntSchedDAO();
		Transaction transaction = session.beginTransaction();
		appointmentSch.update(session, dateApp, time, data.getAPSCSequenceNo(), MeetC, VNo, Empno, Mteam, pic_, 
				ClientStatus, ClientPhone, ClientName, AppointmentPlc, AppointmentStatus, capab,
				descrip, drive, BRCode, appointment, accountNo, interested, marginAmount, oUser.getappuserid());
		transaction.commit();
		doSearch();
	}
	
	@Command
	@NotifyChange({"doChangeEmployee","filtEmployee"})
	public void doReset() throws Exception {
		paging.setVisible(false);
		driver.getChildren().clear();
		EmployeeName.getChildren().clear();
		pic.getChildren().clear();
		VehicleNo.getChildren().clear();
		refreshModel(oUser.getappuserid()); 
	}
	
	@Command
	@NotifyChange({"doChangeYear","filtTypeID"})
	public void doChangeYear(@BindingParam("year") Integer y) {
		try {
			//lblNotif.setValue("Batas waktu hanya 3 bulan terakhir");
			lblNotif.setValue("Batas waktu hanya 11 bulan terakhir");
			btnDownload.setDisabled(false);
			filtTypeID = "---Pilih---";
			int iMonth = mMonthIndex.get(filtEmployee);
			int x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			int BatasBulan = Calendar.getInstance().get(Calendar.MONTH) + 2;
			System.out.println("x + years "+x+" + "+y);
			System.out.println("BatasBulan = "+BatasBulan);
			/*if(iMonthMax == 1 || iMonthMax == 2 || iMonthMax == 3){
				System.out.println("gak masuk");
				if(x==y || y==Calendar.getInstance().get(Calendar.YEAR)){
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
				}else{
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
				}
			}else{
				System.out.println("masuk sni");
				if(y!=Calendar.getInstance().get(Calendar.YEAR)){
					System.out.println("lebih dalam");
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
				}else{
					System.out.println("lebih dalam else");
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
				}
			}*/
			
 		    
		}catch (Exception e) {
			e.printStackTrace();
		}	

		btnDownload.setDisabled(false);
	}
	
	@Command
	@NotifyChange({"doChangeEmployee","filtEmployee"})
	public void doChangeEmployee(@BindingParam("employee") String x) {
		try {
			//btnDownload.setDisabled(false);
			//filtTypeID = "---Pilih---";
			//int iMonth = mMonthIndex.get(x);
			//System.out.println("iMonth : "+iMonth);
			//listDriverName.clear();
			//listVehicleNo.clear();
			//listEmployeeName.clear();
			//listPICName.clear();
			//int iYear = filtTahun;
			//System.out.println("filtTahun : "+filtTahun);
			//int BatasTahun = Calendar.getInstance().get(Calendar.YEAR) - 1;
			//int BatasBulan = Calendar.getInstance().get(Calendar.MONTH) + 2;
 		    //listTypes.add("---Pilih---");
 		    //lblNotif.setValue("Batas waktu hanya 11 bulan terakhir");
 		    /*if(Calendar.getInstance().get(Calendar.MONTH) == 1){
 		    	if(iMonth == 10 || iMonth ==11 ||iMonth ==12 ||iMonth ==1){
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
 		    	}else{
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
 		    	}
 			}else if(Calendar.getInstance().get(Calendar.MONTH) == 2){
					System.out.println("masuk 2");
 		    	if(iMonth ==11 ||iMonth ==12 || iMonth == 1 || iMonth == 2){
 					System.out.println("masuk sini");
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
 		    	}else{
 					System.out.println("masuk sini else");
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
 		    	}
 			}else if(Calendar.getInstance().get(Calendar.MONTH) == 3){
 		    	if(iMonth == 3 ||iMonth == 12 || iMonth == 1 || iMonth == 2){
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
 		    	}else{
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
 		    	}
 			}else{
 				 if(iMonth<=iMonthMin){
 				   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
 	 			   	//lblMsgBulan.setValue("Batas waktu 3 bulan terakhir tidak bisa di unduh");
 	 			   	//bukti potong harap disimpan dengan baik dan tidak diberikan copy
 				}else{
 	 			   	divBulan.setVisible(false);
 	 			   	
 					cbSetIdType.setDisabled(false);
 				}
 			}*/
			System.out.println("count driver: "+driver.getItemCount());
			if(driver.getItemCount()>0) {
				driver.getChildren().clear();
			}
			System.out.println("count employee: "+EmployeeName.getItemCount());
			if(EmployeeName.getItemCount()>0) {
				EmployeeName.getChildren().clear();
				pic.getChildren().clear();
			}
			
			if(VehicleNo.getItemCount()>0) {
				VehicleNo.getChildren().clear();
			}
			//listEmployeeName = new ListModelList<String>();
			String brch = Branch.getValue();
			if(brch.equals(""))
				Messagebox.show("Branch cannot empty!.");
			String[] branch= Branch.getValue().split("-");
			brch = branch[0];
			listName = new ListModelList<String>(ApmntSchedDAO.getEmployeeName(brch));
	    	
			for(int i=0;i<listName.getSize();i++) {
				EmployeeName.appendItem(listName.get(i));
				pic.appendItem(listName.get(i));
				//listEmployeeName.add(listName.get(i));
				//listPICName.add(listName.get(i));
			}
			//setListEmployeeName(listEmployeeName);
			//setListPICName(listPICName);
			
			listName.clear();
			listName = new ListModelList<String>(ApmntSchedDAO.getDriverName(brch));
			
			for(int i=0;i<listName.getSize();i++) {
				driver.appendItem(listName.get(i));
				//listDriverName.add(listName.get(i));
			}
			//setListDriverName(listDriverName);
			
			listName.clear();
			listName = new ListModelList<String>(ApmntSchedDAO.getVehicleNo(brch));
			
			for(int i=0;i<listName.getSize();i++) {
				VehicleNo.appendItem(listName.get(i));
				//listVehicleNo.add(listName.get(i));
			}
			//setListVehicleNo(listVehicleNo);
			/*@SuppressWarnings("rawtypes")
			Iterator itr = listName.iterator();
	    	while(itr.hasNext()){
	    	   String[] obj = (String[]) itr.next();
	    	   if(obj[0].equals(1)){
	    		   listEmployeeName.add("Bounty Manager");
	    		   mTypeIndex.put("Bounty Manager","BMGR");
	    	   }
	    	   if(obj[1].equals(1)){
	    		   listTypes.add("Bounty New Account");
	    		   mTypeIndex.put("Bounty New Account","BMKT");
	    	   }
	    	   if(obj[2].equals(1)){
	    		   listTypes.add("Bounty WPB");
	    		   mTypeIndex.put("Bounty WPB","BWPB");
	    	   }
	    	   if(obj[3].equals(1)){
	    		   listTypes.add("Commission");
	    		   mTypeIndex.put("Commission","COMM");
	    	   }
	    	   if(obj[4].equals(1)){
	    		   listTypes.add("Payroll Closing");
	    		   mTypeIndex.put("Payroll Closing","SCLO");
	    	   }
	    	   if(obj[5].equals(1)){
	    		   listTypes.add("Payroll End Month");
	    		   mTypeIndex.put("Payroll End Month","SENM");
	    	   }
	    	}*/
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	
	public String getFiltEmployee() {
		return filtEmployee;
	}

	public void setFiltEmployee(String filtEmployee) {
		this.filtEmployee = filtEmployee;
	}
	
	public Datebox getDateAppointment() {
		return dateAppointment;
	}

	public void setDateAppointment(Datebox dateAppointment) {
		this.dateAppointment = dateAppointment;
	}
	
	public ListModelList<String> getListBranch(){
		 return listBranch;	
	}
		
	public void setListBranch(ListModelList<String> listBranch) {
		this.listBranch = listBranch;
	}
	
	public ListModelList<String> getListBranchEmployee(){
		 return listBranchEmployee;	
	}
		
	public void setListBranchEmployee(ListModelList<String> listBranchEmployee) {
		this.listBranchEmployee = listBranchEmployee;
	}

	public ListModelList<String> getListMTeam() {
		return listMTeam;
	}

	public void setListMTeam(ListModelList<String> listMTeam) {
		this.listMTeam = listMTeam;
	}
	
	public ListModelList<String> getListDriverName(){
		return listDriverName;	
	}
	
	public void setListDriverName(ListModelList<String> listDriverName) {
		this.listDriverName = listDriverName;
	}
	
	public ListModelList<String> getListVehicleNo(){
		return listVehicleNo;	
	}
	
	public void setListVehicleNo(ListModelList<String> listVehicleNo) {
		this.listVehicleNo = listVehicleNo;
	}
	
	public ListModelList<String> getListEmployeeName(){
		return listEmployeeName;	
	}
	
	public void setListEmployeeName(ListModelList<String> listEmployeeName) {
		this.listEmployeeName = listEmployeeName;
	}
	
	public ListModelList<String> getListPICName(){
		return listPICName;	
	}
	
	public void setListPICName(ListModelList<String> listPICName) {
		this.listPICName = listPICName;
	}

	public Map<String, Integer> getmMonthIndex() {
		return mMonthIndex;
	}

	public void setmMonthIndex(Map<String, Integer> mMonthIndex) {
		this.mMonthIndex = mMonthIndex;
	}

	public Combobox getMarketingTeam() {
		return marketingTeam;
	}

	public void setMarketingTeam(Combobox marketingTeam) {
		this.marketingTeam = marketingTeam;
	}

	public String getFiltTypeID() {
		return filtTypeID;
	}

	public void setFiltTypeID(String filtTypeID) {
		this.filtTypeID = filtTypeID;
	}

	public Map<String, String> getmTypeIndex() {
		return mTypeIndex;
	}

	public void setmTypeIndex(Map<String, String> mTypeIndex) {
		this.mTypeIndex = mTypeIndex;
	}

	public Integer getFiltTahun() {
		return filtTahun;
	}

	public void setFiltTahun(Integer filtTahun) {
		this.filtTahun = filtTahun;
	}

	public String getFiltNikMarketing() {
		return filtNikMarketing;
	}

	public void setFiltNikMarketing(String filtNikMarketing) {
		this.filtNikMarketing = filtNikMarketing;
	}	
}
