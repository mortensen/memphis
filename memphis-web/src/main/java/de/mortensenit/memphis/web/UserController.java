package de.mortensenit.memphis.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.AuthenticationFailedException;
import de.mortensenit.memphis.core.services.UpdateServiceEJB;
import de.mortensenit.memphis.core.services.UserServiceEJB;
import de.mortensenit.memphis.model.User;

/**
 * All methods acording to webapp users
 * 
 * @author Frederik Mortensen
 */
@Named
@SessionScoped
public class UserController implements Serializable {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private User user = new User();

	@EJB
	private UserServiceEJB userService;

	@EJB
	private UpdateServiceEJB updateServiceEJB;

	@Inject
	private SessionController sessionController;

	/**
	 * trying to login user via database, setup a new session and put the user
	 * into the session. Reset the login window and show an error, if login
	 * failed.
	 * 
	 * @return
	 */
	public String login() {
		logger.info("At first check or needed db updates");
		updateServiceEJB.update();

		logger.info("Next try login for user: " + user.getName());
		try {
			sessionController.setSessionUser(userService.login(user));
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);
			session.setAttribute("user", sessionController.getSessionUser());
		} catch (AuthenticationFailedException e) {
			sessionController.setHintMessage("Anmeldung fehlgeschlagen.");
			return "";
		} finally {
			user = new User();
		}
		return "/pages/customers.xhtml";
	}

	/**
	 * logout the user, destroy the session and move to index page
	 * 
	 * @return
	 */
	public String logout() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);
		session.removeAttribute("user");
		sessionController.setSessionUser(null);
		sessionController.setHintMessage("Benutzer erfolgreich abgemeldet");
		return "/index.xhtml";
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String edit(String userId) {
		for (User user : userService.list()) {
			if (user.getId().equals(userId)) {
				user.setPlainPassword(null);
				user.setPassword(null);
				user.setRepeatedPassword(null);
				this.user = user;
				break;
			}
		}
		return "edit_user.xhtml";
	}

	/**
	 * 
	 * @return
	 */
	public String update() {
		userService.update(user);
		sessionController.setHintMessage("Benutzer aktualisiert!");
		return "users.xhtml";
	}

	/**
	 * 
	 * @param userId
	 * @return
	 */
	public String delete(String userId) {
		User user = new User();
		user.setId(userId);
		userService.delete(user);
		sessionController.setHintMessage("Benutzer gelöscht!");
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String addUser() {
		user = new User();
		return "edit_user.xhtml";
	}

	/**
	 * 
	 * @return
	 */
	public List<User> getUsers() {
		return userService.list();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getTotal() {
		return userService.list().size() + "";
	}

}