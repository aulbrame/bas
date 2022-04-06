/*package com.sai.espt.viewmodel;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.chainsaw.Main;
import org.zkoss.bind.annotation.AfterCompose;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;
import org.zkoss.zul.Window;

import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.domain.UserNpwp;
import com.sai.utils.AppData;

public class DownloadVmBACKUP  {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	private String filter;
	private UserNpwp oUser;
	
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy");
	
	private int filtTrxYear;
	private String filtTrxDate;
	private String filtTypeID;
	private ListModelList<String> listBulan;
	private ListModelList<String> listType;
	private Map<String, Integer> mMonthIndex;
	private Map<String, String> mTypeIndex;

	int thisMonths = new Date().getMonth();
    String thisYears = dateFormatter.format(new Date());
	
	@Wire
	private Combobox cbTanggal;
	@Wire
	private Combobox cbSetIdType;
	@Wire
	private Treechildren root;
	@Wire
	private Window win;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws Exception {
		Selectors.wireComponents(view, this, false);
		filtTypeID = "---Pilih---";
		mMonthIndex = new HashMap<>();
		listBulan = new ListModelList<String>(AppData.getMonth());
	    String[] bulan = listBulan.toArray(new String[0]);
	    filtTrxYear = Integer.parseInt(thisYears);
	    System.out.println("ini tahun = " + filtTrxYear + " +  " + thisYears);
	    if(thisMonths<1)
	    	filtTrxDate =  bulan[12];
	    else
	    	filtTrxDate =  bulan[thisMonths-1];
	    for (int i = 0; i < bulan.length; i++) {
		      if(i == 12) break;
		      String month =  bulan[i];
		      mMonthIndex.put(month, (i+1));
		    }
	    mTypeIndex = new HashMap<>();
	    //listType = new ListModelList<String>(AppData.getType());
	    String[] type = listType.toArray(new String[0]);
	    String[] typeId = AppData.getIdType().toArray(new String[0]);
	    for (int i = 0; i < type.length; i++) {
		      String types = type[i];
		      String typeIds = typeId[i];
		      mTypeIndex.put(types, typeIds);
		      System.out.println("types and typeIds = " + types + typeIds);
		    }
	    
	    
	    listIdType = new ListModelList<String>(AppData.getType());
	    for (int i = 0; i < AppData.getMonth().size(); i++) {
		      if(i == 12) break;
		      String month = listBulan.get(i);
		      System.out.println("month = " + month);
		}
		try {			
			oUser = (UserNpwp) zkSession.getAttribute("oUser");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Command
	public void doDownload() {
		filter = "";	
		int iMonth = mMonthIndex.get(filtTrxDate);
		String iType = mTypeIndex.get(filtTypeID);
		Clients.alert("Tahun + Tanggal + Type = "+ filtTrxYear + " + " + iMonth + " + " + iType);
	}
	
	@Command
	@NotifyChange({"filtTrxYear", "filtTrxDate", "filtTypeID"})
	public void doReset() {

	    String[] bulan = listBulan.toArray(new String[0]);
		
		filter = "";
		filtTrxYear = Integer.parseInt(thisYears);
		if(thisMonths<1)
	    	filtTrxDate =  bulan[12];
	    else
	    	filtTrxDate =  bulan[thisMonths-1];
		filtTypeID = "---Pilih---";
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
	public ListModelList<String> getType() {
		ListModelList<String> lm = null;
		try {
			//lm = new ListModelList<String>(AppData.getType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lm;
	}
	public ListModelList<String> getTypeID() {
		ListModelList<String> lm = null;
		try {
			lm = new ListModelList<String>(AppData.getIdType());
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

	public ListModelList<String> getListType() {
		return listType;
	}

	public void setListType(ListModelList<String> listType) {
		this.listType = listType;
	}

	public Map<String, String> getmTypeIndex() {
		return mTypeIndex;
	}

	public void setmTypeIndex(Map<String, String> mTypeIndex) {
		this.mTypeIndex = mTypeIndex;
	}

	public int getFiltTrxYear() {
		return filtTrxYear;
	}

	public void setFiltTrxYear(int filtTrxYear) {
		this.filtTrxYear = filtTrxYear;
	}
}
*/