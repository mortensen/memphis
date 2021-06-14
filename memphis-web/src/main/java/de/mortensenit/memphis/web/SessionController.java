package de.mortensenit.memphis.web;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import de.mortensenit.memphis.model.User;

/**
 * Account service managed bean for managing user accounts.
 * 
 * @author Frederik Mortensen
 */
@Named
@SessionScoped
public class SessionController implements Serializable {

	private static final long serialVersionUID = 1L;
	private User sessionUser;
	private String hintMessage;

	/**
	 * returns the hint message to be shown in the webapp as message
	 * 
	 * @return
	 */
	public String getHintMessage() {
		String shownHintMessage = hintMessage;
		hintMessage = null;
		return shownHintMessage;
	}

	/**
	 * sets a new hint message to be shown in the webapp as a message
	 * 
	 * @param hintMessage
	 */
	public void setHintMessage(String hintMessage) {
		this.hintMessage = hintMessage;
	}

	/**
	 * returns the currently logged in session user
	 * 
	 * @return
	 */
	public User getSessionUser() {
		return sessionUser;
	}

	/**
	 * self explaining
	 * 
	 * @return
	 */
	public boolean isAdministrator() {
		return getSessionUser() != null ? getSessionUser().isAdministrator()
				: false;
	}

	/**
	 * sets the currently logged in session user
	 * 
	 * @param sessionUser
	 */
	public void setSessionUser(User sessionUser) {
		this.sessionUser = sessionUser;
	}

	/**
	 * check if use is logged in
	 * 
	 * @return
	 */
	public boolean isLoggedIn() {
		if (sessionUser == null)
			return false;
		return true;
	}

}
