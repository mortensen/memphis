package de.mortensenit.memphis.core.exceptions;


/**
 * The executed security service action failed
 * 
 * @author Frederik Mortensen
 */
public class SecurityServiceException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;

	/**
	 * @see Exception
	 * @param message
	 */
	public SecurityServiceException(String message) {
		super(message);
	}

	/**
	 * @see Exception
	 * @param message
	 * @param cause
	 */
	public SecurityServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see Exception
	 * @param cause
	 */
	public SecurityServiceException(Throwable cause) {
		super(cause);
	}
}
