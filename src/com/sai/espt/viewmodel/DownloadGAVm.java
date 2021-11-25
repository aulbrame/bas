package com.sai.espt.viewmodel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;

import com.sai.espt.dao.SptAkhirDAO;
import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.domain.UserKacab;
import com.sai.espt.domain.UserNpwp;
import com.sai.espt.dominc.DownHisto;
import com.sai.espt.dominc.vw_databroker;
import com.sai.espt.dominc.Vbroker;
import com.sai.espt.model.MUserNpwpListModel;
import com.sai.utils.AppData;
import com.sai.utils.SysUtils;
import com.sai.utils.db.StoreHibernateUtil;

public class DownloadGAVm  {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private UserKacab oUser;
	
	
	@SuppressWarnings("unused")
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
	DecimalFormat decFormat = new DecimalFormat("####,###,###.##");
	private SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");

	private Integer filtTahun;
	private String filtTrxDate="";
	private String filtTypeID;
	private ListModelList<String> listBulan;
	private ListModelList<String> listTypes;
	private ListModelList<Object> listType;
	private List<Vbroker> listReport;
	private List<Vbroker> listFilter;
	private List<Vbroker> oList;
	private List<DownHisto> listDownload;
	private Map<String, Integer> mMonthIndex;
	private Map<String, String> mTypeIndex;
	private MUserNpwpListModel model;
	@SuppressWarnings("deprecation")
	int thisMonths = new Date().getMonth();
    

	@SuppressWarnings("unused")
	private String lblMessage;
	private Session session;	
	private vw_databroker objForm;
	private int pageStartNumber;
	private String filter;
	private String orderby;
	private int pageTotalSize;
	
	@Wire
	private Combobox cbTanggal;
	@Wire
	private Combobox cbSetIdType;
	@Wire
	private Treechildren root;
	@Wire
	private Window win;
	@Wire
	private Button btnDownload;
	@Wire
	private Label lblNotif;
	@Wire
	private Div divBulan;
	@Wire
	private Paging paging;	
	@Wire
	private Listbox listbox;
	@Wire
	private Groupbox gbSearch;
	private int totalsize = 0;
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AfterCompose
	@NotifyChange("objForm")
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);
		gbSearch.setVisible(false);
		oUser = (UserKacab) zkSession.getAttribute("oUserKacab");
		if (oUser == null)
			Executions.sendRedirect("/timeout.zul");
		else{
			filter = "galogin = '"+ oUser.getUklogin() +"'";
			
			paging.addEventListener("onPaging", new EventListener() {
	
				@Override
				public void onEvent(Event event) throws Exception {				
					PagingEvent pe = (PagingEvent) event;
					pageStartNumber = pe.getActivePage();
					refreshModel(pageStartNumber);
				}		
			});
	
			orderby = "unidktp";
	
			paging.setPageSize(SysUtils.PAGESIZE);
			totalsize = new UserNpwpDAO().pageCount(filter);
			model = new MUserNpwpListModel(1, SysUtils.PAGESIZE, filter, orderby,totalsize);
			pageTotalSize = model.getTotalSize(filter);
			paging.setTotalSize(pageTotalSize);
			listbox.setModel(model);
			
			if (listbox != null) {
				listbox.setItemRenderer(new ListitemRenderer<vw_databroker>() {
	
					@Override
					public void render(Listitem item, final vw_databroker data, int index)
							throws Exception {
						Listcell cell = new Listcell(String.valueOf((SysUtils.PAGESIZE * pageStartNumber) + index + 1));
						item.appendChild(cell);
						cell = new Listcell(data.getUnidktp());
						item.appendChild(cell);
						cell = new Listcell(data.getBrologin());
						item.appendChild(cell);	
						cell = new Listcell(data.getUnnama());
						item.appendChild(cell);						
					}
				});
			}
			
			listbox.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				
				@Override
				public void onEvent(Event event) throws Exception {
					if (listbox.getSelectedIndex() != -1) {
						gbSearch.setVisible(true);
						/*isInsert = false;
						btnSave.setLabel(Labels.getLabel("common.update"));
						btnCancel.setDisabled(false);		
						btnDelete.setDisabled(false);*/
					}							
				}
			});
			divBulan.setVisible(false);
			
			btnDownload.setDisabled(true);
			filtTypeID = "---Pilih---";
			mMonthIndex = new HashMap<>();
			
			listBulan = new ListModelList<String>(AppData.getMonth());
		    String[] bulan = listBulan.toArray(new String[0]);
		    
			filtTahun = Calendar.getInstance().get(Calendar.YEAR);
			System.out.println("bulan[thisMonths] = " + thisMonths + " + " + bulan[thisMonths]);
			if(thisMonths<1){
				filtTrxDate =  bulan[11];
				filtTahun = Calendar.getInstance().get(Calendar.YEAR) - 1;
			}
		    else
		    	filtTrxDate = thisMonths==0?bulan[thisMonths]:bulan[thisMonths-1];
		    for (int i = 0; i < bulan.length; i++) {
			      if(i == 12) break;
			      String month =  bulan[i];
			      mMonthIndex.put(month, (i+1));
			    }
		    mTypeIndex = new HashMap<>();
		   // 


		    //doReset();	
		    try{
		    	session = StoreHibernateUtil.openSession();

		    	listTypes = new ListModelList<String>();
	 		    listTypes.add("---Pilih---");
	 		    if(zkSession.getAttribute("oUser")!=null){
	 		    	if(thisMonths<1){
	 		    		thisMonths =  12;
	 				}
			    	listType = new ListModelList<Object>(AppData.getType(/*objForm.getUnidktp(),*/objForm.getBrologin(),filtTahun,thisMonths));
			    	Iterator itr = listType.iterator();
			    	
			    	while(itr.hasNext()){
			    	   Object[] obj = (Object[]) itr.next();
			    	   //now you have one array of Object for each row
			    	   //same way for all obj[2], obj[3], obj[4]
			    	   if(obj[0].equals(1)){
			    		   listTypes.add("Bounty Manager");
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
			    	}
					
			    	session.close();
		    	
	 		    }
		    } catch (Exception e) {
				lblMessage = "Error : " + e.getMessage();
				e.printStackTrace();
			}
		    System.out.println("Ini listtype = "+listType);
		}
		
	}
	
	//@NotifyChange({"filtTahun", "filtTrxDate", "filtTypeID","refreshModel"})
	public void refreshModel(int activePage) throws Exception {
		orderby = "unidktp";
		divBulan.setVisible(false);
		totalsize = new UserNpwpDAO().pageCount(filter);
		paging.setPageSize(SysUtils.PAGESIZE);
		pageTotalSize = model.getTotalSize(filter);
		model = new MUserNpwpListModel(activePage, SysUtils.PAGESIZE, filter, orderby,pageTotalSize);
		paging.setTotalSize(pageTotalSize);
		listbox.setModel(model);
		
		
	}

	@Command
	public void doDisBtn() throws Exception {
		btnDownload.setDisabled(true);
	}
	@Command
	public void doEnaBtn() throws Exception {
		btnDownload.setDisabled(false);
	}
	
	
	@Command
	public void doDownload() throws Exception {
		divBulan.setVisible(false);
		int iMonth = 0 ;
		if(filtTrxDate !=null)
		iMonth = mMonthIndex.get(filtTrxDate);
		String iType = mTypeIndex.get(filtTypeID);
		System.out.println("ini filtTypeID = "+filtTypeID);
		
		String filter = null;
		
		
		listReport = new SptAkhirDAO().listReport(filtTahun,iMonth,iType,objForm.getBroktp(),objForm.getBrologin());
	
		
		if (listReport == null || listReport.size() == 0) {
			//Clients.alert("Tipe belum ditentukan !!!");
			Clients.alert("  Tipe belum ditentukan !!!", "Warning !", "INFORMATION");
		} else {
			listFilter = new SptAkhirDAO().listReport(filtTahun,iMonth,iType,objForm.getBroktp(),objForm.getBrologin());
			for(Vbroker data: listFilter)
				filter = "dhlogin = '"+ oUser.getUklogin() +"' AND dhtglpotong = '" + data.getTanggalpotong() 
						+ "' AND dhtipe = '" + data.getIncometype() + "'";
			if(filter==null){
				System.out.println("masuk null");
			}else{
					int count = new SptAkhirDAO().countValidasi(filter);
					listDownload = new SptAkhirDAO().listDownload(filter);
					String dateNow = frmt.format(new Date());
					String dateDown = "";
					for(DownHisto dataH: listDownload)
						dateDown = frmt.format(dataH.getDhtgldownload());
					
					System.out.println(count);
				if(count==1){
					System.out.println("validasi count");
					if(dateDown.equals(dateNow)){
						SptAkhirDAO oDao = new SptAkhirDAO();
						Session session = StoreHibernateUtil.openSession();
						Transaction transaction = session.beginTransaction();
						try {
							for(Vbroker data: listFilter){
								oDao.delete(session, oUser.getUklogin(), data.getTanggalpotong(), data.getIncometype());					
								oDao.save(session, oUser.getUklogin(), data.getTanggalpotong(), data.getIncometype());				
							}
							transaction.commit();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("validasi dateNow");
						zkSession.setAttribute("objList", listFilter);
						@SuppressWarnings("rawtypes")
						Map parameters = new HashMap();
						zkSession.setAttribute("parameters", parameters);	
						zkSession.setAttribute("reportPath", "espt.jasper");
						//Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");			ini untuk popup
						System.out.println("reportPath pertama = " + zkSession.getAttribute("reportPath"));
						Jasperreport report = new Jasperreport();
						report.setSrc("/view/espt.jasper");
						report.setParameters(parameters);
						report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
								(java.util.Collection) listFilter));
				        report.setType("pdf");
				        
						Filedownload.save(report.getReport(), "espt21.pdf");
					}else{
						 System.out.println("gak masuk");
	 	 			   	 divBulan.setVisible(true);
	 	 				 divBulan.setClass("alert alert-danger");
	 	 			   	 lblNotif.setValue("Bukti potong sudah pernah di Download, pada tanggal " + dateDown);
					}
				}else{

 	 			   	divBulan.setVisible(true);
 	 			   	divBulan.setClass("alert alert-success");
 	 			   	lblNotif.setValue("Bukti Potong harap disimpan dengan baik, copy tidak diberikan.");
					//System.out.println("else validasi count");
					SptAkhirDAO oDao = new SptAkhirDAO();
					Session session = StoreHibernateUtil.openSession();
					Transaction transaction = session.beginTransaction();
					try {
						for(Vbroker data: listFilter){
							oDao.save(session, oUser.getUklogin(), data.getTanggalpotong(), data.getIncometype());				
						}
						transaction.commit();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					zkSession.setAttribute("objList", listReport);
					@SuppressWarnings("rawtypes")
					Map parameters = new HashMap();
					zkSession.setAttribute("parameters", parameters);	
					zkSession.setAttribute("reportPath", "espt.jasper");
					//Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");			ini untuk popup
					Jasperreport report = new Jasperreport();
					report.setSrc("/view/espt.jasper");
					report.setParameters(parameters);
					report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
							(java.util.Collection) listFilter));
			        report.setType("pdf");
			        
					Filedownload.save(report.getReport(), "espt21.pdf");
					System.out.println("reportPath pertama = " + zkSession.getAttribute("reportPath"));
				}
			}
			
		}
	}
	
	@Command
	@NotifyChange({"filtTahun", "filtTrxDate", "filtTypeID","doReset","objForm"})
	public void doReset() throws Exception {
		divBulan.setVisible(false);
		btnDownload.setDisabled(false);
		cbSetIdType.setDisabled(false);
	    String[] bulan = listBulan.toArray(new String[0]);
	    listTypes.add("---Pilih---");
	    filtTypeID = "---Pilih---";
		filtTahun = Calendar.getInstance().get(Calendar.YEAR);
		if(thisMonths<1)
	    	filtTrxDate =  bulan[12];
	    else
	    	filtTrxDate =  bulan[thisMonths-1];
		filtTypeID = "---Pilih---";
		doChangeYear(filtTahun);
	}
	
	@Command
	@NotifyChange({"doChangeYear","filtTypeID","objForm"})
	public void doChangeYear(@BindingParam("year") Integer y) {
		try {
			divBulan.setClass("alert alert-danger");
			//lblNotif.setValue("Batas waktu hanya 3 bulan terakhir");
			lblNotif.setValue("Batas waktu hanya 11 bulan terakhir");
			btnDownload.setDisabled(false);
			filtTypeID = "---Pilih---";
			int iMonth = mMonthIndex.get(filtTrxDate);
			int x = Calendar.getInstance().get(Calendar.YEAR) - 1;
			int BatasBulan = Calendar.getInstance().get(Calendar.MONTH) + 2;
			System.out.println("x + years "+x+" + "+y);
			listTypes.clear();listType.clear();mTypeIndex.clear();
			int iMonthMax = Calendar.getInstance().get(Calendar.MONTH);
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
			
			if(x<=y){
				divBulan.setVisible(false);
				cbSetIdType.setDisabled(false);
				
			}else{
				divBulan.setVisible(true);
				cbSetIdType.setDisabled(true);
				
			}
			if(x==y){
				if(BatasBulan == 12){
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
				}else if(BatasBulan == 11){
					if(iMonth == 12){
						System.out.println("false");
	 	 			   	divBulan.setVisible(false);
	 					cbSetIdType.setDisabled(false);
					}else{
						System.out.println("true");
	 	 			   	divBulan.setVisible(true);
	 					cbSetIdType.setDisabled(true);
					}
				}else if(Calendar.getInstance().get(Calendar.MONTH) <= 10){
					if(iMonth>=BatasBulan){
	 	 			   	divBulan.setVisible(false);
	 					cbSetIdType.setDisabled(false);
					}else{
	 	 			   	divBulan.setVisible(true);
	 					cbSetIdType.setDisabled(true);
					}
				}else{
 	 			   	divBulan.setVisible(true);
 					cbSetIdType.setDisabled(true);
				}
				System.out.println("BatasBulan  + iMonth = "+ BatasBulan + " + " + iMonth);
			}
 		    listTypes.add("---Pilih---");
			
	    	listType = new ListModelList<Object>(AppData.getType(/*oUser.getUnidktp(),*/objForm.getBrologin(),y,iMonth));
	    	
	    	@SuppressWarnings("rawtypes")
			Iterator itr = listType.iterator();
	    	while(itr.hasNext()){
	    	   Object[] obj = (Object[]) itr.next();
	    	   
	    	   if(obj[0].equals(1)){
	    		   listTypes.add("Bounty Manager");
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
	    	}
		}catch (Exception e) {
			e.printStackTrace();
		}	

		btnDownload.setDisabled(false);
	}
	

	@SuppressWarnings({ "unused", "rawtypes" })
	@Command
	@NotifyChange({"doSelect","filtTypeID","filtTrxDate","objForm","listTypes"})
	public void doSelect() {

		filtTypeID = "---Pilih---";
		 try{
		    	session = StoreHibernateUtil.openSession();
		    	listTypes = new ListModelList<String>();
	 		    listTypes.add("---Pilih---");
	 		    mTypeIndex = new HashMap<>();
				int iMonth = mMonthIndex.get(filtTrxDate);
			    	listType = new ListModelList<Object>(AppData.getType(objForm.getBrologin(),filtTahun,iMonth));
			    	Iterator itr = listType.iterator();
			    	
			    	while(itr.hasNext()){
			    	   Object[] obj = (Object[]) itr.next();
			    	   //now you have one array of Object for each row
			    	   //same way for all obj[2], obj[3], obj[4]
			    	   if(obj[0].equals(1)){
			    		   listTypes.add("Bounty Manager");
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
			    	}
					System.out.println("listTypes = " + listTypes);
					System.out.println("mTypeIndex = " + mTypeIndex.get("COMM"));
			    	session.close();
			    	
		    } catch (Exception e) {
				lblMessage = "Error : " + e.getMessage();
				e.printStackTrace();
			}
	}
	
	@SuppressWarnings({ "rawtypes"})
	@Command
	@NotifyChange({"doChangeMonth","filtTypeID","filtTrxDate","objForm"})
	public void doChangeMonth(@BindingParam("month") String x) {
		try {
			divBulan.setClass("alert alert-danger");
			btnDownload.setDisabled(false);
			filtTypeID = "---Pilih---";
			int iMonth = mMonthIndex.get(x);
			System.out.println("iMonth : "+iMonth);
			listTypes.clear();listType.clear();mTypeIndex.clear();
			int iYear = filtTahun;
			System.out.println("filtTahun : "+filtTahun);
			int BatasTahun = Calendar.getInstance().get(Calendar.YEAR) - 1;
			int iMonthMin = Calendar.getInstance().get(Calendar.MONTH) - 3;
			int BatasBulan = Calendar.getInstance().get(Calendar.MONTH) + 2;
 		    listTypes.add("---Pilih---");
 		    lblNotif.setValue("Batas waktu hanya 11 bulan terakhir");
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
			if(BatasTahun<=iYear){
				divBulan.setVisible(false);
				cbSetIdType.setDisabled(false);
				
			}else{
				divBulan.setVisible(true);
				cbSetIdType.setDisabled(true);
				
			}
			
			if(BatasTahun==iYear){
	 			  if(BatasBulan == 12){
		 			   	divBulan.setVisible(true);
						cbSetIdType.setDisabled(true);
					}else if(BatasBulan == 11){
						if(iMonth == 12){
							System.out.println("false");
		 	 			   	divBulan.setVisible(false);
		 					cbSetIdType.setDisabled(false);
						}else{
							System.out.println("true");
		 	 			   	divBulan.setVisible(true);
		 					cbSetIdType.setDisabled(true);
						}
					}else if(Calendar.getInstance().get(Calendar.MONTH) <= 10){
						if(iMonth>=BatasBulan){
		 	 			   	divBulan.setVisible(false);
		 					cbSetIdType.setDisabled(false);
						}else{
		 	 			   	divBulan.setVisible(true);
		 					cbSetIdType.setDisabled(true);
						}
					}else{
		 			   	divBulan.setVisible(true);
						cbSetIdType.setDisabled(true);
					}
				}
 		    System.out.println("x = "+ x);
	    	listType = new ListModelList<Object>(AppData.getType(/*oUser.getUnidktp(),*/objForm.getBrologin(),filtTahun,iMonth));
	    	
			Iterator itr = listType.iterator();
	    	while(itr.hasNext()){
	    	   Object[] obj = (Object[]) itr.next();
	    	   if(obj[0].equals(1)){
	    		   listTypes.add("Bounty Manager");
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
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}	
	
	public String getFiltTrxDate() {
		return filtTrxDate;
	}

	public void setFiltTrxDate(String filtTrxDate) {
		this.filtTrxDate = filtTrxDate;
	}

	public ListModelList<String> getListBulan() {
		return listBulan;
	}

	public void setListBulan(ListModelList<String> listBulan) {
		this.listBulan = listBulan;
	}

	public Map<String, Integer> getmMonthIndex() {
		return mMonthIndex;
	}

	public void setmMonthIndex(Map<String, Integer> mMonthIndex) {
		this.mMonthIndex = mMonthIndex;
	}

	public Combobox getCbTanggal() {
		return cbTanggal;
	}

	public void setCbTanggal(Combobox cbTanggal) {
		this.cbTanggal = cbTanggal;
	}
	public ListModelList<String> getMonth() {
		ListModelList<String> lm = null;
		try {
			lm = new ListModelList<String>(AppData.getMonth());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}
	public ListModelList<String> getMonthID() {
		ListModelList<String> lm = null;
		try {
			lm = new ListModelList<String>(AppData.getMonthID());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
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

	public ListModelList<Object> getListType() {
		return listType;
	}

	public void setListType(ListModelList<Object> listType) {
		this.listType = listType;
	}

	public ListModelList<String> getListTypes() {
		return listTypes;
	}

	public void setListTypes(ListModelList<String> listTypes) {
		this.listTypes = listTypes;
	}

	public List<Vbroker> getListReport() {
		return listReport;
	}

	public void setListReport(List<Vbroker> listReport) {
		this.listReport = listReport;
	}

	public List<Vbroker> getoList() {
		return oList;
	}

	public void setoList(List<Vbroker> oList) {
		this.oList = oList;
	}

	public Integer getFiltTahun() {
		return filtTahun;
	}

	public void setFiltTahun(Integer filtTahun) {
		this.filtTahun = filtTahun;
	}

	public List<Vbroker> getListFilter() {
		return listFilter;
	}

	public void setListFilter(List<Vbroker> listFilter) {
		this.listFilter = listFilter;
	}

	public List<DownHisto> getListDownload() {
		return listDownload;
	}

	public void setListDownload(List<DownHisto> listDownload) {
		this.listDownload = listDownload;
	}

	public vw_databroker getObjForm() {
		return objForm;
	}

	public void setObjForm(vw_databroker objForm) {
		this.objForm = objForm;
	}
	
	
}
