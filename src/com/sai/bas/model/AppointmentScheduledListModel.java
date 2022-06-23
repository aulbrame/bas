package com.sai.bas.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.ext.Sortable;

import com.sai.bas.dao.ApmntSchedDAO;
import com.sai.bas.domain.AppointmentSchedule;

public class AppointmentScheduledListModel extends AbstractPagingListModel<AppointmentSchedule> implements Sortable<AppointmentSchedule> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _size = -1;
	private List<AppointmentSchedule> asList;
	
	public AppointmentScheduledListModel(int startPageNumber, int pageSize, String filter, String orderby,
			int totalsize) {
		super(startPageNumber, pageSize, filter, orderby, totalsize);
	}

	@Override
	public int getTotalSize(String filter) {
		ApmntSchedDAO asDao = new  ApmntSchedDAO();
		try {
			_size = asDao.pageCount(filter);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return _size;
	}

	@Override
	protected List<AppointmentSchedule> getPageData(int itemStartNumber, int pageSize, String filter, String orderby,
			int totalsize) {
		ApmntSchedDAO asDao = new  ApmntSchedDAO();
		try {
			asList = asDao.listPaging(itemStartNumber, pageSize, filter, orderby,totalsize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return asList;
	}

	@Override
	public String getSortDirection(Comparator<AppointmentSchedule> cmpr) {
		return null;
	}

	@Override
	public void sort(Comparator<AppointmentSchedule> cmpr, boolean ascending) {
		Collections.sort(asList, cmpr);
        fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}
	

}
