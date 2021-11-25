package com.sai.utils.test;

import java.io.File;
import java.util.List;

import com.sai.espt.dao.SptAkhirDAO;
import com.sai.espt.dominc.Vbroker;


public class doangtest {

public static void main(String[] args) {

	List<Vbroker> listFilter = null;
	try {
		listFilter = new SptAkhirDAO().listReport(2017,2,"COMM","","BA06389");
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	listFilter.get(0).setPngfile(listFilter.get(0).getNullfile());
	for(Vbroker data: listFilter){
		
		System.out.println(data.getPngfile());
		File f = new File(listFilter.get(0).getPngfile());
		if(f.exists() && !f.isDirectory());else
			listFilter.get(0).setPngfile(listFilter.get(0).getNullfile());
		
	}
		
		//filter = "dhlogin = '"+ oUser.getUnlogin() + "' AND dhtglpotong = '" + data.getTanggalpotong() 
	
}
}
