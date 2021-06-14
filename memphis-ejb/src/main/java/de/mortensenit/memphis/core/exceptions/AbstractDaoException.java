package de.mortensenit.memphis.core.exceptions;

/**
 * Abstract superclass for all exceptions thrown programmatically in dao
 * implementations.
 * 
 * @author Frederik Mortensen
 */
public abstract class AbstractDaoException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * default constructor
	 */
	public AbstractDaoException() {
	}

	/**
	 * additional constructor
	 * 
	 * @param msg
	 */
	public AbstractDaoException(String msg) {
		super(msg);
	}

	/**
	 * additional constructor
	 * 
	 * @param cause
	 */
	public AbstractDaoException(Throwable cause) {
		super(cause);
	}

	/**
	 * additional constructor
	 * 
	 * @param message
	 * @param cause
	 */
	public AbstractDaoException(String message, Throwable cause) {
		super(message, cause);
	}

}
