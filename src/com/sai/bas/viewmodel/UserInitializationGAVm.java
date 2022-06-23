package com.sai.bas.viewmodel;

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

import com.sai.bas.dao.AppUserDAO;
import com.sai.bas.domain.AppUser;

public class UserInitializationGAVm  {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	
	@Wire
	private Combobox cbTanggal;
	@Wire
	private Treechildren root;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		try {		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Combobox getCbTanggal() {
		return cbTanggal;
	}

	public void setCbTanggal(Combobox cbTanggal) {
		this.cbTanggal = cbTanggal;
	}
	

}
