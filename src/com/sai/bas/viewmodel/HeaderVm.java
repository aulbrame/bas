package com.sai.espt.viewmodel;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zul.Window;

import com.sai.espt.dao.UserNpwpDAO;
import com.sai.utils.db.StoreHibernateUtil;

public class HeaderVm {

	private UserNpwpDAO oDao = new UserNpwpDAO();
	private org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();
		
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);						
	}
	
	@Command
	public void doChangePassword() {													
		Window win = (Window) Executions.createComponents("/changepass.zul", null, null);
		win.setClosable(true);							
		win.doModal();		
	}
	
	@Command
	public void doLogout() {	
		int lhseqno = (int) zkSession.getAttribute("lhseqno");
		System.out.println("ini lhseqno = "+ zkSession.getAttribute("lhseqno"));
		Session session = StoreHibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			oDao.update(session, lhseqno);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (zkSession.getAttribute("oUser") != null) {				
			zkSession.removeAttribute("oUser");	
		}
		if (zkSession.getAttribute("oUserKacab") != null) {				
				zkSession.removeAttribute("oUserKacab");	
		}	
		if (zkSession.getAttribute("lhseqno") != null) {				
			zkSession.removeAttribute("lhseqno");			
		}	
		Executions.sendRedirect("/logout.zul");
	}
	
	@Command
	public void doLogoutMobile() {	
		int lhseqno = (int) zkSession.getAttribute("lhseqno");
		System.out.println("ini lhseqno = "+ zkSession.getAttribute("lhseqno"));
		Session session = StoreHibernateUtil.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			oDao.update(session, lhseqno);
			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (zkSession.getAttribute("oUser") != null) {				
			zkSession.removeAttribute("oUser");	
		}
		if (zkSession.getAttribute("oUserKacab") != null) {				
				zkSession.removeAttribute("oUserKacab");	
		}	
		if (zkSession.getAttribute("lhseqno") != null) {				
			zkSession.removeAttribute("lhseqno");			
		}	
		Executions.sendRedirect("/view/mobile/logout.zul");
	}
}
