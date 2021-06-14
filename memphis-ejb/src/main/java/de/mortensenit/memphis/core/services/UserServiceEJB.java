package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.AuthenticationFailedException;
import de.mortensenit.memphis.core.exceptions.SecurityServiceException;
import de.mortensenit.memphis.core.exceptions.ValidationFailedException;
import de.mortensenit.memphis.model.User;

/**
 * 
 * @author fmortensen
 * 
 */
@Stateless
public class UserServiceEJB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private PersistenceServiceEJB persistenceServiceEJB;

	@EJB
	private SecurityServiceEJB securityService;

	/**
	 * 
	 * @param user
	 * @throws AuthenticationFailedException
	 */
	public User login(User user) throws AuthenticationFailedException {
		return login(user.getName(), user.getPlainPassword());
	}

	/**
	 * 
	 * @param username
	 * @param plainPassword
	 * @throws AuthenticationFailedException
	 */
	public User login(String username, String plainPassword) throws AuthenticationFailedException {
		logger.info("Trying to login with user " + username);
		List<User> users = list();
		try {
			for (User user : users) {
				if (user.getName().equals(username) && user.getPassword().equals(securityService.encrypt(plainPassword))) {
					logger.info("Login succeeded with user: " + username);
					return user;
				}
			}
		} catch (Exception e) {
			throw new AuthenticationFailedException("Login failed!");
		}
		throw new AuthenticationFailedException("User not found!");
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public User find(String id) {
		logger.info("Searching user: " + id);
		User user = new User();
		user.setId(id);
		try {
			user = persistenceServiceEJB.find(User.class, user);
		} catch (PersistenceException | ValidationFailedException e) {
			logger.info("User not found: " + id);
			return null;
		}
		return user;
	}

	/**
	 * 
	 * @return
	 */
	public List<User> list() {
		try {
			return persistenceServiceEJB.list(User.class);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void save(User user) {
		logger.info("Saving user " + user.getName());
		try {
			persistenceServiceEJB.save(user);
		} catch (PersistenceException | ValidationFailedException e) {
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void update(User user) {
		logger.info("Updating user " + user.getName());
		try {
			String encryped = null;
			try {
				encryped = securityService.encrypt(user.getPlainPassword());
			} catch (SecurityServiceException e) {
				e.printStackTrace();
			}
			user.setPassword(encryped);
			user.setPlainPassword(null);

			persistenceServiceEJB.update(user);
		} catch (PersistenceException | ValidationFailedException e) {
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void delete(User user) {
		logger.info("Deleting user " + user.getId());
		try {
			user = persistenceServiceEJB.find(User.class, user);
			if (user != null) {
				persistenceServiceEJB.delete(user);
			} else {
				logger.error("Nichts zum löschen gefunden.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}