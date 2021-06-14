package de.mortensenit.memphis.core.exceptions;


/**
 * The client registration could not be finished
 * 
 * @author Frederik Mortensen
 */
public class RegistrationFailedException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;

	/**
	 * @see Exception
	 * @param message
	 * @param cause
	 */
	public RegistrationFailedException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @see Exception
	 * @param cause
	 */
	public RegistrationFailedException(Throwable cause) {
		super(cause);
	}

}
