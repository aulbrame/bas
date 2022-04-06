package com.sai.espt.viewmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkex.zul.Jasperreport;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Window;

import com.sai.espt.dao.SptAkhirDAO;
import com.sai.espt.domain.UserNpwp;
import com.sai.espt.dominc.DownHisto;
import com.sai.espt.dominc.Vbroker;
import com.sai.utils.AppData;
import com.sai.utils.db.StoreHibernateUtil;

public class DownloadVm  {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private UserNpwp oUser;
	
	
	@SuppressWarnings("unused")
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
	DecimalFormat decFormat = new DecimalFormat("####,###,###.##");
	private SimpleDateFormat frmt = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMMM-yyyy");

	private Integer filtTahun;
	private String filtNikMarketing;
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

	@SuppressWarnings("deprecation")
	int thisMonths = new Date().getMonth();
    

	@SuppressWarnings("unused")
	private String lblMessage;
	private Session session;	
	
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
	

	// …

	// Inside the initialization method
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);
		oUser = (UserNpwp) zkSession.getAttribute("oUser");
		if (oUser == null){
			Executions.sendRedirect("/timeout.zul");	
		}else{
			filtNikMarketing = oUser.getUnidktp() + " / " + oUser.getUnlogin();
			if(oUser.getUnidktp().equals(""))
			filtNikMarketing = oUser.getUnlogin();
			refreshModel();
		}
	}
	
	@SuppressWarnings({"rawtypes"})
	@NotifyChange({"filtTahun", "filtTrxDate", "filtTypeID","refreshModel","filtNikMarketing"})
	public void refreshModel() throws Exception{
		
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
 		    	System.out.println("ini filtNikMarketing = "+filtNikMarketing);
 		    	if(thisMonths<1){
 		    		thisMonths =  12;
 				}
		    	listType = new ListModelList<Object>(AppData.getType(/*oUser.getUnidktp(),*/oUser.getUnlogin(),filtTahun,thisMonths));
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
		
		
		listReport = new SptAkhirDAO().listReport(filtTahun,iMonth,iType,oUser.getUnidktp(),oUser.getUnlogin());
	
		
		if (listReport == null || listReport.size() == 0) {
			//Clients.alert("Tipe belum ditentukan !!!");
			Clients.alert("  Tipe belum ditentukan !!!", "Warning !", "INFORMATION");
		} else {
			listFilter = new SptAkhirDAO().listReport(filtTahun,iMonth,iType,oUser.getUnidktp(),oUser.getUnlogin());
			for(Vbroker data: listFilter)
				filter = "dhlogin = '"+ oUser.getUnlogin() + "' AND dhtglpotong = '" + data.getTanggalpotong() 
						+ "' AND dhtipe = '" + data.getIncometype() + "'";
			if(filter==null){
				System.out.println("masuk null");
			}else{
					int count = new SptAkhirDAO().countValidasi(filter);
					listDownload = new SptAkhirDAO().listDownload(filter);
					String dateNow = frmt.format(new Date());
					Date dateDown = new Date();
					for(DownHisto dataH: listDownload)
						dateDown = dataH.getDhtgldownload();
					
					System.out.println(count);
				if(count==1){
					System.out.println("validasi count");
					if(frmt.format(dateDown).equals(dateNow)){
						SptAkhirDAO oDao = new SptAkhirDAO();
						Session session = StoreHibernateUtil.openSession();
						Transaction transaction = session.beginTransaction();
						try {
							for(Vbroker data: listFilter){
								oDao.delete(session, oUser.getUnlogin(), data.getTanggalpotong(), data.getIncometype());					
								oDao.save(session, oUser.getUnlogin(), data.getTanggalpotong(), data.getIncometype());				
							}
							transaction.commit();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						System.out.println("validasi dateNow");
						zkSession.setAttribute("objList", listFilter);
						@SuppressWarnings("rawtypes")
						Map parameters = new HashMap();
						/*zkSession.setAttribute("parameters", parameters);	
						zkSession.setAttribute("reportPath", "espt.jasper");
						

						Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");
						*/
						File f = new File(listFilter.get(0).getPngfile());
						if(f.exists() && !f.isDirectory());else
							listFilter.get(0).setPngfile(listFilter.get(0).getNullfile());
						
						Jasperreport report = new Jasperreport();
						report.setSrc("/view/espt.jasper");
						report.setParameters(parameters);
						report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
								(java.util.Collection) listFilter));
				        report.setType("pdf");
				        
						Filedownload.save(report.getReport(), "espt21.pdf");
	 	 			   	/*divBulan.setVisible(true);
	 	 			   	divBulan.setClass("alert alert-success");
	 	 			   	lblNotif.setValue("Bukti Potong harap disimpan dengan baik, copy tidak diberikan.");*/
					}else{
						 System.out.println("gak masuk");
	 	 			   	 divBulan.setVisible(true);
	 	 				 divBulan.setClass("alert alert-danger");
	 	 			   	 lblNotif.setValue("Bukti potong sudah pernah di Download, pada tanggal " + dateFormat.format(dateDown));
					}
					if(zkSession.getAttribute("objList")!=null)
			        	zkSession.removeAttribute("objList");
				    if(zkSession.getAttribute("parameters")!=null)
			        	zkSession.removeAttribute("parameters");
					if(zkSession.getAttribute("reportPath")!=null)
			        	zkSession.removeAttribute("reportPath");
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
							oDao.save(session, oUser.getUnlogin(), data.getTanggalpotong(), data.getIncometype());				
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
					//Executions.getCurrent().sendRedirect("/view/jasperViewer.zul", "_blank");

					File f = new File(listFilter.get(0).getPngfile());
					if(f.exists() && !f.isDirectory());else
						listFilter.get(0).setPngfile(listFilter.get(0).getNullfile());
					
					Jasperreport report = new Jasperreport();
					report.setSrc("/view/espt.jasper");
					report.setParameters(parameters);
					report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
							(java.util.Collection) listFilter));
			        report.setType("pdf");
			        
					Filedownload.save(report.getReport(), "espt21.pdf");
					
					/*report.setSrc("/view/espt.jasper");
					report.setParameters(parameters);
					report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
							(java.util.Collection) listFilter));
			        report.setType("pdf");
			        FileInputStream inputStream;
			        try {
			            String saveDosPath = "espt21";
						File dosfile = new File(saveDosPath);
			            if (dosfile.exists()) {
			                inputStream = new FileInputStream(dosfile);
			                Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(dosfile), dosfile.getName());
			            }

			        } catch (FileNotFoundException e) {
			            // TODO Auto-generated catch block
			            e.printStackTrace();
			        }
					System.out.println("reportPath pertama = " + zkSession.getAttribute("reportPath"));*/
				}
			}
			
		}
	}
	
	@Command
	@NotifyChange({"filtTahun", "filtTrxDate", "filtTypeID","doReset"})
	public void doReset() throws Exception {
		divBulan.setVisible(false);
		btnDownload.setDisabled(false);
		cbSetIdType.setDisabled(false);
	    String[] bulan = listBulan.toArray(new String[0]);

		filtTahun = Calendar.getInstance().get(Calendar.YEAR);
		if(thisMonths<1){
			filtTrxDate =  bulan[11];
			filtTahun = Calendar.getInstance().get(Calendar.YEAR) - 1;
		}
	    	
	    else
	    	filtTrxDate =  thisMonths==0?bulan[thisMonths]:bulan[thisMonths-1];
		filtTypeID = "---Pilih---";
		doChangeYear(filtTahun);
	}
	
	@Command
	@NotifyChange({"doChangeYear","filtTypeID"})
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
			
	    	listType = new ListModelList<Object>(AppData.getType(/*oUser.getUnidktp(),*/oUser.getUnlogin(),y,iMonth));
	    	
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
	@SuppressWarnings({ "rawtypes"})
	@Command
	@NotifyChange({"doChangeMonth","filtTypeID","filtTrxDate","filtTahun"})
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
	    	listType = new ListModelList<Object>(AppData.getType(/*oUser.getUnidktp(),*/oUser.getUnlogin(),filtTahun,iMonth));
	    	
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

	public String getFiltNikMarketing() {
		return filtNikMarketing;
	}

	public void setFiltNikMarketing(String filtNikMarketing) {
		this.filtNikMarketing = filtNikMarketing;
	}	
}
