package de.mortensenit.memphis.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.model.User;
import de.mortensenit.memphis.utils.StringUtils;

/**
 * User entity validation using JSR-303
 * 
 * @author Frederik Mortensen
 */
public class UserValidator implements ConstraintValidator<ValidUser, User> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * init validation
	 * 
	 * @param constraintAnnotation
	 */
	@Override
	public void initialize(ValidUser constraintAnnotation) {
	}

	/**
	 * User must - have a username set - have a password set - include at least 2 numbers, 1
	 * special char and 3 letters
	 * 
	 * @param user
	 * @param constraintContext
	 * @return
	 */
	@Override
	public boolean isValid(User user,
			ConstraintValidatorContext constraintContext) {
		logger.debug("Validating user {}", user != null ? user.getName()
				: "<null>");
		if (StringUtils.isNullOrEmpty(user.getName(), user.getPassword()))
			return false;
		return checkPasswordSecurity(user.getPlainPassword());
	}

	/**
	 * a valid used password must contain at least 2 digits, one special char
	 * and 3 characters
	 * 
	 * @param plainPassword
	 *            the plain, not encrypted password
	 * @return
	 */
	private boolean checkPasswordSecurity(String plainPassword) {
		logger.debug("Validating password security.");
		if (plainPassword == null)
			return true;
		int digitCount = 0;
		int specialCharCount = 0;
		int charCount = 0;
		String chars = "abcdefghijklmnopqrstuvwxyzäöüßABCDEFGHIJKLMNOPQRSTUVWXYZÄÖÜ";

		for (Character c : plainPassword.toCharArray()) {
			try {
				Integer.parseInt(c.toString());
				digitCount++;
			} catch (NumberFormatException e) {
				if (chars.contains(c.toString()))
					charCount++;
				else
					specialCharCount++;
			}
		}
		if (digitCount >= 2 && specialCharCount >= 1 && charCount >= 3)
			return true;
		return false;
	}
}