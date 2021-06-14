package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.EmptyResultException;
import de.mortensenit.memphis.core.exceptions.ValidationFailedException;
import de.mortensenit.memphis.model.AbstractEntity;

/**
 * @see PersistenceServiceDao
 * @author Frederik Mortensen
 */
@Stateless
public class PersistenceJpa implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@PersistenceContext(unitName = "memphisPU")
	protected EntityManager entityManager;

	private enum Action {

		SAVE, UPDATE, DELETE, FIND, LIST
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void save(T entity)
			throws PersistenceException, ValidationFailedException {
		executeCommand(null, Action.SAVE, entity);
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void update(T entity)
			throws PersistenceException, ValidationFailedException {
		executeCommand(null, Action.UPDATE, entity);
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void delete(T entity)
			throws PersistenceException, ValidationFailedException {
		executeCommand(null, Action.DELETE, entity);
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param clazz
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> T find(Class<T> clazz, T entity)
			throws PersistenceException, ValidationFailedException {
		return executeCommand(clazz, Action.FIND, entity).get(0);
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> List<T> list(Class<T> clazz)
			throws PersistenceException, ValidationFailedException {
		return executeCommand(clazz, Action.LIST, null);
	}

	/**
	 * helper method to avoid repeating myself with errorhandling (DRY-pattern)
	 * 
	 * @param <T>
	 * @param clazz
	 *            the entity type to handle, probably extends
	 *            <code>de.mortensenit.memphis.model.AbstractEntity</code>
	 * @param action
	 *            the action to start, like persisting or deleting
	 * @param entity
	 *            actual business object to handle
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <T extends AbstractEntity> List<T> executeCommand(Class<T> clazz,
			Action action, T entity) throws PersistenceException,
			ValidationFailedException {

		logger.debug(
				"Starting persistence action {} for type {} and object {}.",
				new Object[] { action.toString(),
						clazz != null ? clazz.getSimpleName() : "<null>",
						entity != null ? entity.getId() : "<null>" });

		List<T> resultList = new ArrayList<T>();

		switch (action) {
		case SAVE:
			try {
				entityManager.persist(entity);
			} catch (ConstraintViolationException e) {
				logger.debug(
						"JPA persist method throws constraint violations!", e);
				throw new ValidationFailedException(e.getConstraintViolations());
			}
			break;
		case UPDATE:
			try {
				entityManager.merge(entity);
			} catch (ConstraintViolationException e) {
				logger.debug(
						"JPA persist method throws constraint violations!", e);
				throw new ValidationFailedException(e.getConstraintViolations());
			}
			break;
		case DELETE:
			try {
				entityManager.remove(entity);
			} catch (ConstraintViolationException e) {
				logger.debug(
						"JPA persist method throws constraint violations!", e);
				throw new ValidationFailedException(e.getConstraintViolations());
			}
			break;
		case FIND:
			entity = entityManager.find(clazz, entity.getId());
			resultList.add(entity);
			break;
		case LIST:
			TypedQuery typedQuery = entityManager.createQuery("select t from "
					+ clazz.getSimpleName() + " t", clazz);
			resultList = typedQuery.getResultList();
			break;
		default:
			logger.error("Found unknown action type while trying to execute JTA command!");
			throw new PersistenceException(
					"Found unknown action type while trying to execute JTA command!");
		}

		return resultList;
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param clazz
	 * @param queryName
	 * @param parameters
	 * @return
	 * @throws EmptyResultException
	 * @throws PersistenceException
	 */
	public <T extends AbstractEntity> T getSingleResult(Class<T> clazz,
			String queryName, Map<String, Object> parameters)
			throws EmptyResultException, PersistenceException {
		logger.debug("Starting single result named query {}.", queryName);
		try {
			TypedQuery<T> query = entityManager.createNamedQuery(queryName,
					clazz);
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
			T foundEntity = query.getSingleResult();
			return foundEntity;
		} catch (RuntimeException e) {
			if (e instanceof NoResultException) {
				throw new EmptyResultException(e);
			}
			logger.error(
					"Unhandled exception is thrown while trying to execute single resulting named query {}!",
					queryName);
			throw e;
		}
	}

	/**
	 * @see PersistenceServiceDao
	 * @param <T>
	 * @param clazz
	 * @param queryName
	 * @param parameters
	 * @return
	 * @throws EmptyResultException
	 * @throws PersistenceException
	 */
	public <T extends AbstractEntity> List<T> getResultList(Class<T> clazz,
			String queryName, Map<String, Object> parameters)
			throws EmptyResultException, PersistenceException {
		logger.debug("Starting result list named query {}.", queryName);
		try {
			TypedQuery<T> query = entityManager.createNamedQuery(queryName,
					clazz);
			for (String key : parameters.keySet()) {
				query.setParameter(key, parameters.get(key));
			}
			List<T> foundEntities = query.getResultList();
			return foundEntities;
		} catch (RuntimeException e) {
			if (e instanceof NoResultException) {
				throw new EmptyResultException(e);
			}
			logger.error(
					"Unhandled exception is thrown while trying to execute result list named query {}!",
					queryName);
			throw e;
		}
	}

	/**
	 * @see PersistenceServiceDao
	 * @param entityManager
	 */
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/**
	 * @see PersistenceServiceDao
	 * @return
	 */
	public EntityManager getEntityManager() {
		return entityManager;
	}
}
