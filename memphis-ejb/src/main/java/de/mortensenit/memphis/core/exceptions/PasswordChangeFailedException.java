package de.mortensenit.memphis.core.exceptions;

/**
 * The aproach to change the password failed.
 * 
 * @author Frederik Mortensen
 */
public class PasswordChangeFailedException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;

	/**
	 * The cause of the PasswordChangeFailedException
	 */
	public enum FailReason {

		/** one of the passwords was not filled */
		MISSING_PARAMETER,
		/** the old password doesn´t match the one actually in use */
		WRONG_OLD_PASSWORD,
		/** new password and repeated new password don´t match */
		PASSWORD_MISSMATCH,
		/** update on database failed */
		PERSISTENCE_ERROR,
		/** Security service call failed */
		SECURITY_SERVICE_ERROR
	}

	private FailReason reason;

	/**
	 * extended constructor
	 * 
	 * @param msg
	 */
	public PasswordChangeFailedException(FailReason reason) {
		this.reason = reason;
	}

	public FailReason getReason() {
		return reason;
	}

	public void setReason(FailReason reason) {
		this.reason = reason;
	}
}
