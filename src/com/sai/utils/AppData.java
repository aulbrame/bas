package com.sai.utils;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import com.sai.espt.dao.SptAkhirDAO;
import com.sai.espt.domain.SptAkhir;

public class AppData {
	
	public static List<String> getMonth() throws Exception {
		List<String> listMonth = new ArrayList<>();
		String[] months = new DateFormatSymbols().getMonths();
	    for (int i = 0; i < months.length; i++) {
	      if(i == 12) break;
	      String month = months[i];
	      listMonth.add(month);
	      System.out.println("month = " + month);
	    }
		return listMonth;
	}
	public static List<String> getMonthID() throws Exception {
		List<String> listMonthID = new ArrayList<>();
	    for (int i = 0; i < 12; i++) {
	      String monthid = i+"";
	      listMonthID.add(monthid);
	      System.out.println("monthid = " + monthid);
	    }
		return listMonthID;
	}
	public static List<Object> getType(String unlogin, int years,int months) throws Exception {
		//
		//
		List<Object> list = null;
		list = new SptAkhirDAO().listByFilter(unlogin, years, months);
		return list;
	}

}
