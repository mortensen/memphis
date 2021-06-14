package de.mortensenit.memphis.core.exceptions;

/**
 * Invalid username / password or user is not allowed to login
 * 
 * @author Frederik Mortensen
 */
public class AuthenticationFailedException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;

	/**
	 * extended constructor
	 * 
	 * @param msg
	 */
	public AuthenticationFailedException(String msg) {
		super(msg);
	}

	/**
	 * @see Exception
	 * @param cause
	 */
	public AuthenticationFailedException(Throwable cause) {
		super(cause);
	}

}
