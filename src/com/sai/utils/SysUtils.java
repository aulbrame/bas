package com.sai.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.zk.ui.Executions;

//import sun.misc.BASE64Encoder;

public class SysUtils {
	
	public static final int PAGESIZE = 20;
	public static final int MODELSIZE = 1000;
	public static final int IDPROCS_INSERT = 0;
	public static final int IDPROCS_UPDATE = 1;
	public static final int IDPROCS_DELETE = 2;
	public static final int IDPROCS_VIEW = 3;
	public static final String USERS_PASSWORD_DEFAULT = "password";
	
	public static final String JASPER_PATH = "/jasper";
	public static final String FILES_ROOT_PATH = "/files";
	public static final String REPORT_PATH = "/report/";	
	
	public static final String SCHEDULER_ENABLE_LABEL = "ENABLE";
	public static final String SCHEDULER_ENABLE_VALUE = "1";
	public static final String SCHEDULER_DISABLE_LABEL = "DISABLE";
	public static final String SCHEDULER_DISABLE_VALUE = "0";
	public static final String SCHEDULER_REPEAT_PERMINUTE = "PER MINUTE";
	public static final String SCHEDULER_REPEAT_ATHOUR = "AT HOUR";
		
	/*public static String encryptionCommand(String text) throws NoSuchAlgorithmException, Exception {
		String textencrypted;
		MessageDigest md5;		
		md5 = MessageDigest.getInstance("MD5");
		byte textbyte[] = md5.digest(text.getBytes());
		BASE64Encoder base64enc = new BASE64Encoder();
		textencrypted = base64enc.encode(textbyte);		
		return textencrypted;
	}*/

	public static String dateFyyyyMMdd(String strDate) throws Exception {
		try {
			return (strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate
					.substring(6, 8));
		} catch (Exception e) {
			return null;
		}		
	}
	
	public static String dateFyyyyMMddHHmmss(String strDate) throws Exception {
		try {
			return (strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate
					.substring(6, 8));
		} catch (Exception e) {
			return null;
		}		
	}
	 public static String getMACAddress(String ip){ 
	        String str = ""; 
	        String macAddress = ""; 
	        try { 
	            Process p = Runtime.getRuntime().exec("nbtstat -A " + ip); 
	            InputStreamReader ir = new InputStreamReader(p.getInputStream()); 
	            LineNumberReader input = new LineNumberReader(ir); 
	            for (int i = 1; i <100; i++) { 
	                str = input.readLine(); 
	                if (str != null) { 
	                    if (str.indexOf("MAC Address") > 1) { 
	                        macAddress = str.substring(str.indexOf("MAC Address") + 14, str.length()); 
	                        break; 
	                    } 
	                } 
	            } 
	        } catch (IOException e) { 
	            e.printStackTrace(System.out); 
	        } 
	        return macAddress; 
	    }
	 static Pattern macpt = null;
	 public static String getMac(String ip) {

		    // Find OS and set command according to OS
		    String OS = System.getProperty("os.name").toLowerCase();
		    System.out.println(OS);
		    String[] cmd;
		    if (OS.contains("win")) {
		        // Windows
		        macpt = Pattern
		                .compile("[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+");
		        String[] a = { "arp", "-a", ip };
		        cmd = a;
		    } else {
		        // Mac OS X, Linux
		        macpt = Pattern
		                .compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");
		        String[] a = { "arp", ip };
		        cmd = a;
		    }

		    try {
		        // Run command
		        Process p = Runtime.getRuntime().exec(cmd);
		        p.waitFor();
		        // read output with BufferedReader
		        BufferedReader reader = new BufferedReader(new InputStreamReader(
		                p.getInputStream()));
		        String line = reader.readLine();

		        // Loop trough lines
		        while (line != null) {
		            Matcher m = macpt.matcher(line);

		            // when Matcher finds a Line then return it as result
		            if (m.find()) {
		                System.out.println("Found");
		                System.out.println("MAC: " + m.group(0));
		                return m.group(0);
		            }

		            line = reader.readLine();
		        }

		    } catch (IOException e1) {
		        e1.printStackTrace();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    // Return empty string if no MAC is found
		    return "";
		}
	 public static String getIPAddress(){
		 	String ip = "";
		 	
			ip = Executions.getCurrent().getHeader("X-Forwarded-For");
			System.out.println("_client_ip 2 : "+ip);	
			if(ip==null)
		 	ip = Executions.getCurrent().getHeader("ExpClientIP");
			System.out.println("_client_ip 1 :"+ip);				
			if(ip==null)	
			ip = Executions.getCurrent().getRemoteAddr();
			System.out.println("_client_ip 3 : "+ip);
			return ip;
	 }

	 public static String getMACAddr(String ip) {

		    // Find OS and set command according to OS
		    String OS = System.getProperty("os.name").toLowerCase();

		    String[] cmd;
		    if (OS.contains("win")) {
		        // Windows
		        macpt = Pattern
		                .compile("[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+-[0-9a-f]+");
		        String[] a = { "arp", "-a", ip };
		        cmd = a;
		    } else {
		        // Mac OS X, Linux
		        macpt = Pattern
		                .compile("[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+:[0-9a-f]+");
		        String[] a = { "arp", ip };
		        cmd = a;
		    }

		    try {
		        // Run command
		        Process p = Runtime.getRuntime().exec(cmd);
		        p.waitFor();
		        // read output with BufferedReader
		        BufferedReader reader = new BufferedReader(new InputStreamReader(
		                p.getInputStream()));
		        String line = reader.readLine();

		        // Loop trough lines
		        while (line != null) {
		            Matcher m = macpt.matcher(line);

		            // when Matcher finds a Line then return it as result
		            if (m.find()) {
		                //System.out.println("Found");
		                //System.out.println("MAC: " + m.group(0));
		                return m.group(0);
		            }

		            line = reader.readLine();
		        }

		    } catch (IOException e1) {
		        e1.printStackTrace();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }

		    // Return empty string if no MAC is found
		    return "";
		}
}
