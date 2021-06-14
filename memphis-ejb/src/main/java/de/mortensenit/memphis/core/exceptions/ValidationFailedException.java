package de.mortensenit.memphis.core.exceptions;

import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * Needed to throw a checked exception when validation found invalid entities to
 * persist. Stateless session beans should not throw RuntimeExceptions, so this
 * checked exception will be used.
 * 
 * @author Frederik Mortensen
 */
public class ValidationFailedException extends AbstractDaoException {

	private static final long serialVersionUID = 1L;
	
	private final Set<ConstraintViolation<?>> constraintViolations;

	/**
	 * additional constructor
	 * 
	 * @param message
	 * @param set
	 */
	public ValidationFailedException(String message,
			Set<ConstraintViolation<?>> set) {
		super(message);
		constraintViolations = set;
	}

	/**
	 * additional constructor
	 * 
	 * @param set
	 */
	public ValidationFailedException(Set<ConstraintViolation<?>> set) {
		constraintViolations = set;
	}

	/**
	 * getter that returns a set of constraint validations found by the
	 * validator
	 * 
	 * @return
	 */
	public Set<ConstraintViolation<?>> getConstraintViolations() {
		return constraintViolations;
	}
}