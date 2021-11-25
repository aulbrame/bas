package com.sai.espt.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.zkoss.zul.AbstractListModel;

public abstract class AbstractPagingListModel<T> extends AbstractListModel<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6613208067174831719L;
	
	private int startPageNumber;
	private int pageSize;
	private int itemStartNumber;
	private String filter;
	private String orderby;
	private int totalsize;
	private Session session;
	
	//internal use only
	private List<T> items = new ArrayList<T>();
	
	public AbstractPagingListModel(int startPageNumber, int pageSize, String filter, String orderby, int totalsize) {
		super();
		
		this.startPageNumber = startPageNumber;
		this.pageSize = pageSize;
		this.itemStartNumber = startPageNumber * pageSize;
		this.filter = filter;
		this.orderby = orderby;
		
		items = getPageData(itemStartNumber, pageSize + itemStartNumber, this.filter, this.orderby,this.totalsize);
	}
	
	public abstract int getTotalSize(String filter);
	protected abstract List<T> getPageData(int itemStartNumber, int pageSize, String filter, String orderby, int totalsize);
	
	@Override
	public Object getElementAt(int index) {
		return items.get(index);
	}

	@Override
	public int getSize() {
		return items.size();
	}
	
	public int getStartPageNumber() {
		return this.startPageNumber;
	}
	
	public int getPageSize() {
		return this.pageSize;
	}
	
	public int getItemStartNumber() {
		return itemStartNumber;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
}
