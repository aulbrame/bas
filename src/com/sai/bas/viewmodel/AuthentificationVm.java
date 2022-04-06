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

public class AuthentificationVm {
	
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
		System.out.println("ini mobile = " + Executions.getCurrent().getBrowser("mobile"));
		/*System.out.println("ZK ClientInfo ");
        System.out.println("getBrowser() " + Executions.getCurrent().getBrowser());
        System.out.println("getContextPath() " + Executions.getCurrent().getContextPath());
        System.out.println("getLocalAddr() " + Executions.getCurrent().getLocalAddr());
        System.out.println("getLocalName() " + Executions.getCurrent().getLocalName());
        System.out.println("getLocalPort() " + Executions.getCurrent().getLocalPort());
        System.out.println("getRemoteAddr() " + Executions.getCurrent().getRemoteAddr());
        System.out.println("getRemoteHost() " + Executions.getCurrent().getRemoteHost());
        System.out.println("getRemoteUser() " + Executions.getCurrent().getRemoteUser());
        System.out.println("getScheme() " + Executions.getCurrent().getScheme());
        System.out.println("getServerName() " + Executions.getCurrent().getServerName());
        System.out.println("getServerPort() " + Executions.getCurrent().getServerPort());
        System.out.println("getUserAgent() " + Executions.getCurrent().getUserAgent());
        System.out.println("getDesktop() " + Executions.getCurrent().getDesktop());
        System.out.println("getContextPath() " + Executions.getCurrent().getContextPath());
        System.out.println("--------------------------------------------------------------------------------------------------");

        AuthentificationVm obj = new AuthentificationVm();
		ServerLocation location = obj.getLocation("");//SysUtils.getIPAddress());
		System.out.println(location);
		
		HttpServletRequest request = ServletActionContext.getRequest();
		String IP = request.getRemoteAddr();
		String countryCode = Utilities.fetchUrl( "http://api.hostip.info/country.php?ip=" + IP );
		if ( countryCode != null){
		    String country = CountryUtil.COUNTRY_MAP.get( countryCode );
		    System.out.println("COUNTRY: " + country);
		}
       
        StringBuilder result = new StringBuilder();
        
        result.append("-------------------------------------------------------------- ");
        result.append("ZK Session ");
        org.zkoss.zk.ui.Session sess = Sessions.getCurrent();
        result.append(".getLocalAddr() " + sess.getLocalAddr() + " ");
        result.append(".getLocalName() " + sess.getLocalName() + " ");
        result.append(".getRemoteAddr() " + sess.getRemoteAddr() + " ");
        result.append(".getRemoteHost() " + sess.getRemoteHost() + " ");
        result.append(".getServerName() " + sess.getServerName() + " ");
        result.append(".getWebApp().getAppName() " + sess.getWebApp().getAppName() + " ");

        HttpSession hses = (HttpSession) sess.getNativeSession();
        result.append("-------------------------------------------------------------------------------------------------- ");
        result.append("HttpSession ");
        result.append(".getId() " + hses.getId() + " ");
        result.append(".getCreationTime() " + new Date(hses.getCreationTime()).toString() + " ");
        result.append(".getLastAccessedTime() " + new Date(hses.getLastAccessedTime()).toString() + " ");

        result.append("-------------------------------------------------------------------------------------------------- ");
        result.append("ServletContext ");
        ServletContext sCon = hses.getServletContext();
        result.append(".getServerInfo() " + sCon.getServerInfo() + " ");
        result.append(".getServletContextName() " + sCon.getServletContextName() + " ");

        result.append("-------------------------------------------------------------------------------------------------- ");
        result.append("ZK Executions ");
        result.append(".getHeader('user-agent') " + Executions.getCurrent().getHeader("user-agent") + " ");
        result.append(".getHeader('accept-language') " + Executions.getCurrent().getHeader("accept-language") + " ");
        result.append(".getHeader('referer') " + Executions.getCurrent().getHeader("referer") + " ");
        result.append(".getHeader('connection') " + Executions.getCurrent().getHeader("connection") + " ");
        result.append(".getHeader('zk-sid') " + Executions.getCurrent().getHeader("zk-sid") + " ");
        result.append(".getHeader('origin') " + Executions.getCurrent().getHeader("origin") + " ");
        result.append(".getHeader('host') " + Executions.getCurrent().getHeader("host") + " ");
        result.append(".getHeader('cookie') " + Executions.getCurrent().getHeader("cookie") + " ");
        result.append("-------------------------------------------------------------------------------------------------- ");
*/
    

	}
	 /*public ServerLocation getLocation(String ipAddress) {

			File file = new File(
			    "D:\\geo\\GeoLiteCity.dat");
			return getLocation(ipAddress, file);

		  }
	 public ServerLocation getLocation(String ipAddress, File file) {

			ServerLocation serverLocation = null;

			try {

			serverLocation = new ServerLocation();

			LookupService lookup = new LookupService(file,LookupService.GEOIP_MEMORY_CACHE);
			Location locationServices = lookup.getLocation(ipAddress);
			System.out.println(locationServices.countryCode);
			System.out.println(locationServices.countryName);
			System.out.println(locationServices.region);
			System.out.println(regionName.regionNameByCode(
		             locationServices.countryCode, locationServices.region));
			System.out.println(locationServices.city);
			System.out.println(locationServices.postalCode);
			System.out.println(String.valueOf(locationServices.latitude));
			System.out.println(String.valueOf(locationServices.longitude));

			} catch (IOException e) {
				System.err.println(e.getMessage());
			}

			return serverLocation;

		  }*/
	
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
											oDao.autoSetSptAkhir(session, lhidlogin);
											transaction.commit();
										} catch (Exception e) {
											e.printStackTrace();
										}
										
										zkSession.setAttribute("oUser", oForm);						
										Executions.sendRedirect("/view/index.zul");
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
