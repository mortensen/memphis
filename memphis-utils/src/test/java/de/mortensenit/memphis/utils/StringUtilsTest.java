package de.mortensenit.memphis.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * JUnit test for checking correct behaviour of StringUtils class
 * 
 * @author Frederik Mortensen
 */
public class StringUtilsTest {

	/**
	 * checks functionality of string helper method
	 */
	@Test
	public void testIsNotNullOrEmpty() {
		assertFalse(StringUtils.isNotNullOrEmpty(""));
		assertFalse(StringUtils.isNotNullOrEmpty((String) null));
		assertTrue(StringUtils.isNotNullOrEmpty("notempty"));
		assertTrue(StringUtils.isNotNullOrEmpty("a", "b", "c"));
		assertFalse(StringUtils.isNotNullOrEmpty("a", null, "c"));
		assertFalse(StringUtils.isNotNullOrEmpty("a", "", "c"));
	}

	/**
	 * checks functionality of string helper method
	 */
	@Test
	public void testIsNullOrEmpty() {
		assertTrue(StringUtils.isNullOrEmpty(""));
		assertTrue(StringUtils.isNullOrEmpty((String) null));
		assertFalse(StringUtils.isNullOrEmpty("notempty"));
		assertFalse(StringUtils.isNullOrEmpty("a", "b", "c"));
		assertFalse(StringUtils.isNullOrEmpty("a", null, "c"));
		assertFalse(StringUtils.isNullOrEmpty("a", "", "c"));
	}
}
