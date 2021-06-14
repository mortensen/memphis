package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.ValidationFailedException;
import de.mortensenit.memphis.model.AbstractEntity;

/**
 * @see PersistenceService
 * @author Frederik Mortensen
 */
@Stateless
public class PersistenceServiceEJB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private PersistenceJpa persistenceJpa;

	/**
	 * @see PersistenceService
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void save(T entity)
			throws PersistenceException, ValidationFailedException {
		logger.debug("Persisting entity {} with type {}.", new Object[] {
				entity != null ? entity.getId() : "<null>",
				entity != null ? entity.getClass().getSimpleName() : "<null>" });
		persistenceJpa.save(entity);
	}

	/**
	 * @see PersistenceService
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void update(T entity)
			throws PersistenceException, ValidationFailedException {
		logger.debug("Updating entity {} with type {}.", new Object[] {
				entity != null ? entity.getId() : "<null>",
				entity != null ? entity.getClass().getSimpleName() : "<null>" });
		persistenceJpa.update(entity);
	}

	/**
	 * @see PersistenceService
	 * @param <T>
	 * @param entity
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> void delete(T entity)
			throws PersistenceException, ValidationFailedException {
		logger.debug("Deleting entity {} with type {}.", new Object[] {
				entity != null ? entity.getId() : "<null>",
				entity != null ? entity.getClass().getSimpleName() : "<null>" });
		persistenceJpa.delete(entity);
	}

	/**
	 * @see PersistenceService
	 * @param <T>
	 * @param clazz
	 * @param entity
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> T find(Class<T> clazz, T entity)
			throws PersistenceException, ValidationFailedException {
		logger.debug("Searching entity {} with type {}.", new Object[] {
				entity != null ? entity.getId() : "<null>",
				clazz != null ? clazz.getSimpleName() : "<null>" });
		return persistenceJpa.find(clazz, entity);
	}

	/**
	 * @see PersistenceService
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws PersistenceException
	 * @throws ValidationFailedException
	 */
	public <T extends AbstractEntity> List<T> list(Class<T> clazz)
			throws PersistenceException, ValidationFailedException {
		logger.debug("Listing entities with type {}.",
				clazz != null ? clazz.getSimpleName() : "<null>");
		return persistenceJpa.list(clazz);
	}
}
