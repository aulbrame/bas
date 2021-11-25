package com.sai.espt.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.dominc.vw_databroker;


public class MUserNpwpListModel extends AbstractPagingListModel<vw_databroker> implements Sortable<vw_databroker> {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<vw_databroker> oList;  

	public MUserNpwpListModel(int startPageNumber, int pageSize, String filter, String orderby, int totalsize) {
		super(startPageNumber, pageSize, filter, orderby,totalsize);
	}
	
	@Override
	protected List<vw_databroker> getPageData(int itemStartNumber, int pageSize, String filter, String orderby, int totalsize) {		
		UserNpwpDAO oDao = new UserNpwpDAO();		
		try {
			oList = oDao.listPaging(itemStartNumber, pageSize, filter, orderby,totalsize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oList;
	}

	@Override
	public int getTotalSize(String filter) {
		UserNpwpDAO oDao = new UserNpwpDAO();	
		try {
			_size = oDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	public void sort(Comparator<vw_databroker> cmpr, boolean ascending) {		
		Collections.sort(oList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);	
		
	}

	@Override
	public String getSortDirection(Comparator<vw_databroker> cmpr) {
		return null;
	}
}
