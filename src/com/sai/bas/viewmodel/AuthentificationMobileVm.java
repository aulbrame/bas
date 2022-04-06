package com.sai.espt.viewmodel;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.w3c.dom.css.ViewCSS;
import org.zkforge.bwcaptcha.Captcha;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.ClientInfoEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.maxmind.geoip.regionName;
import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.domain.UserKacab;
import com.sai.espt.domain.UserNpwp;
import com.sai.utils.AppData;
import com.sai.utils.SysUtils;
import com.sai.utils.db.StoreHibernateUtil;
//import com.sun.corba.se.spi.activation.LocatorPackage.ServerLocation;

public class AuthentificationMobileVm {
	
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
	
	private String unlogin;
	private String unpassword;
	private String lblMessage;
	private String typeLogin;
	
	private UserNpwpDAO oDao = new UserNpwpDAO();
	
	private Session session;	

	Pattern macpt = null;
	
	int minexpirydays = 0;
	int maxexpirydays = 0;	

	Pattern mac_pattern;
	private List<Object> listType;

	int mac_validation_attempt = 0;
	HttpSession s1;
	
	private String macaddress = "";
	
	private boolean login_success_flag = false;
	
	@SuppressWarnings("unused")
	private Transaction trns ;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) throws IOException  {
		Selectors.wireComponents(view, this, false);
	}
	 
	@Command
	@NotifyChange("lblMessage")
	public void doLogin() {				
		try {			

			if (zkSession.getAttribute("oUser") != null) {				
				zkSession.removeAttribute("oUser");	
			}
			if (zkSession.getAttribute("oUserKacab") != null) {				
					zkSession.removeAttribute("oUserKacab");	
			}	
			if (zkSession.getAttribute("lhseqno") != null) {				
				zkSession.removeAttribute("lhseqno");			
			}
				if(typeLogin != null ){
					if(typeLogin.equals("BROKER")){
						if (unlogin != null && !unlogin.trim().equals("") && unpassword != null && !unpassword.trim().equals("")) {
							session = StoreHibernateUtil.openSession();
							String replace = unlogin;
							replace.replace("'", "");
							replace.replace(""+'"', "");
							UserNpwp oForm = oDao.login(session, unlogin.replace("'", ""));
							if (oForm != null) {
								
								if (unpassword != null) unpassword = unpassword.trim();								
								String passencrypted = unpassword;//SysUtils.encryptionCommand(unpassword);
								if (oForm.getUnpassword().equals(passencrypted)) {	
										int count = new UserNpwpDAO().countKey();
										String mac = "";
										String ip = "";
										InetAddress ips;
										InetAddress ipz;
										ips = InetAddress.getLocalHost();
										
										if(Executions.getCurrent().getRemoteAddr().equals("127.0.0.1")){
											ip = ips.getHostAddress();
											NetworkInterface network = NetworkInterface.getByInetAddress(ips);
			
											byte[] macs = network.getHardwareAddress();
			
											System.out.print("Current MAC address : ");
			
											StringBuilder sb = new StringBuilder();
											for (int i = 0; i < macs.length; i++) {
												sb.append(String.format("%02X%s", macs[i], (i < macs.length - 1) ? "-" : ""));
											}
											mac = sb.toString();
										}
										else{
											ip = SysUtils.getIPAddress();
											
											mac = SysUtils.getMACAddress(ip);
											//mac = SysUtils.getMac(ip);
											System.out.println("ini mac and ip = "+ mac + " + " + ip);
										}
										String lhidlogin = oForm.getUnlogin();
										//Map lhseqno = new HashMap();
										zkSession.setAttribute("lhseqno", count);
										Session session = StoreHibernateUtil.openSession();
										Transaction transaction = session.beginTransaction();
										try {
											oDao.save(session, count, lhidlogin, mac.toUpperCase(), ip);
											transaction.commit();
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										zkSession.setAttribute("oUser", oForm);						
										Executions.sendRedirect("/view/mobile/index.zul");
								} else {
									lblMessage = "Login Failed : Invalid your password";
								}
							}else{
								lblMessage = "Login Failed : Invalid your Login Id";
							}						
							session.close();
						} else {
							lblMessage = "Login Failed : Invalid your Login Id";
						}
					}else if(typeLogin.equals("GA")){
						if (unlogin != null && !unlogin.trim().equals("") && unpassword != null && !unpassword.trim().equals("")) {
							session = StoreHibernateUtil.openSession();
							String replace = unlogin;
							replace.replace("'", "");
							replace.replace(""+'"', "");
							UserKacab oForm = oDao.loginKacab(session, unlogin.replace("'", ""));
							if (oForm != null) {
								
								if (unpassword != null) unpassword = unpassword.trim();								
								String passencrypted = unpassword;//SysUtils.encryptionCommand(unpassword);
								if (oForm.getUkpassword().equals(passencrypted)) {	
										int count = new UserNpwpDAO().countKey();
										String mac = "";
										String ip = "";
										InetAddress ips;
										InetAddress ipz;
										ips = InetAddress.getLocalHost();
										
										if(Executions.getCurrent().getRemoteAddr().equals("127.0.0.1")){
											ip = ips.getHostAddress();
											NetworkInterface network = NetworkInterface.getByInetAddress(ips);
			
											byte[] macs = network.getHardwareAddress();
			
											System.out.print("Current MAC address : ");
			
											StringBuilder sb = new StringBuilder();
											for (int i = 0; i < macs.length; i++) {
												sb.append(String.format("%02X%s", macs[i], (i < macs.length - 1) ? "-" : ""));
											}
											mac = sb.toString();
										}
										else{
											ip = SysUtils.getIPAddress();
											
											mac = SysUtils.getMACAddr(ip);
											if(mac==null|| mac.equals(""))
											mac = SysUtils.getMACAddress(ip);
											System.out.println("ini mac and ip = "+ mac + " + " + ip);
										}
										String lhidlogin = oForm.getUklogin();
										//Map lhseqno = new HashMap();
										zkSession.setAttribute("lhseqno", count);
										Session session = StoreHibernateUtil.openSession();
										Transaction transaction = session.beginTransaction();
										try {
											oDao.save(session, count, lhidlogin, mac.toUpperCase(), ip);
											transaction.commit();
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										zkSession.setAttribute("oUserKacab", oForm);						
										Executions.sendRedirect("/view/ga.zul");
								} else {
									lblMessage = "Login Failed : Invalid your password";
								}				
							} else {
								lblMessage = "Login Failed : Invalid your Login Id";
							}							
							session.close();
						}
					} else {
						lblMessage = "Login Failed : Invalid your Login Id";
					}

					}else if(unlogin==null){
						lblMessage = "Login Failed : Invalid your Login Id";
					}else if(unpassword==null){
						lblMessage = "Login Failed : Invalid your password";
					}else{
						lblMessage = "Login Failed : Invalid your Type ...";
					}
				
		} catch (Exception e) {
			lblMessage = "Error : " + e.getMessage();
			e.printStackTrace();
		}		
	}
	public String getUnlogin() {
		return unlogin;
	}

	public void setUnlogin(String unlogin) {
		this.unlogin = unlogin;
	}

	public String getUnpassword() {
		return unpassword;
	}

	public void setUnpassword(String unpassword) {
		this.unpassword = unpassword;
	}

	public String getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(String lblMessage) {
		this.lblMessage = lblMessage;
	}

	public String getTypeLogin() {
		return typeLogin;
	}

	public void setTypeLogin(String typeLogin) {
		this.typeLogin = typeLogin;
	}


	
}
