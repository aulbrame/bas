package com.sai.espt.viewmodel;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import com.sai.espt.dao.UserNpwpDAO;
import com.sai.espt.domain.UserKacab;
import com.sai.utils.db.StoreHibernateUtil;

public class ChangePassGATrxVm {	
	org.zkoss.zk.ui.Session zkSession = Sessions.getCurrent();	
	Session session;
	Transaction transaction;	
	UserNpwpDAO oDao = new UserNpwpDAO();
	private UserKacab oUser;
	private String oldPass;
	private String newPass;
	private String confirmNewPass;
	private String lblMessage;

	@Wire("#compFocs")
	Textbox compFocs;
	
	@Wire
	private Label lblNotif;	
	@Wire
	private Div divBulan;
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		divBulan.setVisible(false);
		compFocs.setFocus(true);
	}
	
	@Command("save")
	@NotifyChange("*")
	public void save() {		
		oUser = (UserKacab) zkSession.getAttribute("oUserKacab");
		try {
			UserKacab oForm = (UserKacab) oDao.getByPkGA("'"+oUser.getUklogin()+"'");
			if(oldPass !=null || newPass !=null || confirmNewPass !=null ){
				if (oForm.getUkpassword().equals(oldPass.trim().toUpperCase())) {
					if(newPass !=null || confirmNewPass !=null){
						if(newPass.trim().toUpperCase().equals(confirmNewPass.toUpperCase())){
							session = StoreHibernateUtil.openSession();
							try {					
								transaction = session.beginTransaction();
								oForm.setUkpassword(newPass.trim().toUpperCase());
								oDao.saveOrUpdateGA(session, oForm);
								transaction.commit();	
			 	 			   	divBulan.setClass("alert alert-success");				
					 		    lblNotif.setValue("Password Berhasil diubah");
			 	 			   	divBulan.setVisible(true);
			 	 			   	
								oldPass = "";
								newPass = "";
								confirmNewPass = "";
								compFocs.setFocus(true);
							} catch (Exception e) {
								transaction.rollback();
								lblMessage = "Error : " + e.getMessage();
								e.printStackTrace();
							} finally {
								session.close();
							}
						}else{
							
		 	 				divBulan.setClass("alert alert-danger");
				 		    lblNotif.setValue("Password tidak sama");
		 	 			   	divBulan.setVisible(true);
						}
				}else{
					divBulan.setClass("alert alert-danger");
		 		    lblNotif.setValue("Password tidak sama");
 	 			   	divBulan.setVisible(true);
				}
				} else {			

	 				divBulan.setClass("alert alert-danger");
		 		    lblNotif.setValue("Password lama salah");
 	 			   	divBulan.setVisible(true);
				}
			}else{
 				divBulan.setClass("alert alert-danger");
	 		    lblNotif.setValue("Silahkan isi Password lama");
	 			divBulan.setVisible(true);
			}
		} catch (Exception e) {
			lblMessage = "Error : " + e.getMessage();
			e.printStackTrace();			
		}		
	}

	public String getOldPass() {
		return oldPass;
	}

	public void setOldPass(String oldPass) {
		this.oldPass = oldPass;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmNewPass() {
		return confirmNewPass;
	}

	public void setConfirmNewPass(String confirmNewPass) {
		this.confirmNewPass = confirmNewPass;
	}

	public String getLblMessage() {
		return lblMessage;
	}

	public void setLblMessage(String lblMessage) {
		this.lblMessage = lblMessage;
	}
			
}
