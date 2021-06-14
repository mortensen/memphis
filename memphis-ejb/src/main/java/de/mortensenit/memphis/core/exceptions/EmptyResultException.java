package de.mortensenit.memphis.core.exceptions;

/**
 * The search for a specific entity with a named query failed.
 * 
 * @author Frederik Mortensen
 */
public class EmptyResultException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;

	public EmptyResultException(Exception e) {
	}

}
