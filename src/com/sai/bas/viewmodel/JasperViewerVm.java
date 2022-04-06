package com.sai.espt.viewmodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.metainfo.ComponentDefinition;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkex.zul.Jasperreport;

public class JasperViewerVm {

	static DecimalFormat df = new DecimalFormat("###,###");
   // System.out("Harga Yen: %s %n" + kurensiJepang.format(harga));

	org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	
	String reportPath;
	@SuppressWarnings("rawtypes")
	Map parameters = new HashMap();
	List<Object> objList = new ArrayList<Object>();
	
	@Wire
	private Jasperreport report;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@NotifyChange("*")
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		/*this.reportPath = (String) zkSession.getAttribute("reportPath");
		this.parameters = (Map) zkSession.getAttribute("parameters");
        
        report.setSrc(reportPath);
        report.setParameters(parameters);
        report.setType("pdf");*/
		try {			
			this.objList = (List<Object>) zkSession.getAttribute("objList");
			this.parameters = (Map) zkSession.getAttribute("parameters");
			this.reportPath = (String) zkSession.getAttribute("reportPath");
			
	        report.setSrc(reportPath);
	        report.setParameters(parameters);
	        report.setDatasource(new net.sf.jasperreports.engine.data.JRBeanCollectionDataSource(
					(java.util.Collection) objList));
	        report.setType("pdf");
	        System.out.println("reportPath = "+ report.getSrc());
	        if(zkSession.getAttribute("objList")!=null)
	        	zkSession.removeAttribute("objList");
		    if(zkSession.getAttribute("parameters")!=null)
	        	zkSession.removeAttribute("parameters");
			if(zkSession.getAttribute("reportPath")!=null)
	        	zkSession.removeAttribute("reportPath");
	        	       
		} catch (Exception e) {
			e.printStackTrace();
		}		
		
	}
}
