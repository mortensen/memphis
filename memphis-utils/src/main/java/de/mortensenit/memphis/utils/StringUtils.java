package de.mortensenit.memphis.utils;

/**
 * String helper methods. A little utility class
 * 
 * @author Frederik Mortensen
 */
public class StringUtils {

	/**
	 * check wether all given strings are filled
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String... values) {
		if (values == null)
			return false;
		for (String value : values) {
			if (value == null || value.trim().length() == 0)
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @param values
	 * @return
	 */
	public static boolean isNullOrEmpty(String... values) {
		if (values == null)
			return true;
		for (String value : values) {
			if (value != null && value.trim().length() > 0)
				return false;
		}
		return true;
	}

}
