package com.sai.espt.viewmodel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Treechildren;
import org.zkoss.zul.Treeitem;
import org.zkoss.zul.Treerow;

import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.domain.UserKacab;
import com.sai.espt.domain.UserNpwp;

public class UserInitializationVm  {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();

	private UserNpwp oUser;
	private UserKacab oUserKacab;

	/*private String filtTrxDate;
	private ListModelList<String> listBulan;
	private Map<String, Integer> mMonthIndex;*/
	
	@Wire
	private Combobox cbTanggal;
	@Wire
	private Treechildren root;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		/*List<String> listMonth = new ArrayList<>();
		mMonthIndex = new HashMap<>();
		String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length; i++) {
	      if(i == 12) break;
	      String month = months[i];
	      listMonth.add(month);
	      mMonthIndex.put(month, (i+1));
	      System.out.println("month = " + month);
	    }
	    try {
			listBulan = new ListModelList<String>(listMonth);
			if(cbTanggal!=null){
				cbTanggal.setModel(listBulan);			
				cbTanggal.setItemRenderer(new ComboitemRenderer<String>() {
	
					@Override
					public void render(Comboitem item, String data, int index) throws Exception {
						item.setLabel(data);
						item.setId(""+index);
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		try {			
			if(oUser!=null)
			oUser = (UserNpwp) zkSession.getAttribute("oUser");
			if(oUserKacab!=null)
			oUserKacab = (UserKacab) zkSession.getAttribute("oUser");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*public String getFiltTrxDate() {
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
	}*/

	public Combobox getCbTanggal() {
		return cbTanggal;
	}

	public void setCbTanggal(Combobox cbTanggal) {
		this.cbTanggal = cbTanggal;
	}
	

}
