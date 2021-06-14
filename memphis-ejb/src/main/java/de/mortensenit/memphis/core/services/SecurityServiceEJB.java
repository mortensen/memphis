package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.SecurityServiceException;
import de.mortensenit.memphis.utils.StringUtils;

/**
 * 
 * @author fmortensen
 * 
 */
@Stateless
public class SecurityServiceEJB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	public boolean compareEncryptedValues(String source, String target)
			throws SecurityServiceException {
		logger.debug("Comparing encrypted values...");
		if (StringUtils.isNullOrEmpty(source, target)) {
			return false;
		}
		return encrypt(source).equals(encrypt(target));
	}

	public String encrypt(String plain) throws SecurityServiceException {
		logger.debug("Starting encryption...");
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(plain.getBytes());

			byte byteData[] = md.digest();

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16)
						.substring(1));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new SecurityServiceException(e);
		}
	}

}