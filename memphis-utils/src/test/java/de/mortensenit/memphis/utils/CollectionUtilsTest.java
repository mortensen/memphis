package de.mortensenit.memphis.utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * JUnit test for checking correct behaviour of CollectionUtils class
 * 
 * @author Frederik Mortensen
 */
public class CollectionUtilsTest {

	/**
	 * checks functionality of collection helper method
	 */
	@Test
	@SuppressWarnings("rawtypes")
	public void testIsNotNullOrEmpty() {
		assertFalse(CollectionUtils.isNotNullOrEmpty(new ArrayList()));
		assertFalse(CollectionUtils.isNotNullOrEmpty((ArrayList) null));
		List<Object> list1 = new ArrayList<Object>();
		assertFalse(CollectionUtils.isNotNullOrEmpty(list1));
		list1.add("x");
		assertTrue(CollectionUtils.isNotNullOrEmpty(list1));
		List<Object> list2 = new ArrayList<Object>();
		list2.add("x");
		list2.add(null);
		assertFalse(CollectionUtils.isNotNullOrEmpty(list1, list2));
	}

	/**
	 * checks functionality of collection helper method
	 */
	@Test
	@SuppressWarnings("rawtypes")
	public void testIsNullOrEmpty() {
		assertTrue(CollectionUtils.isNullOrEmpty(new ArrayList()));
		assertTrue(CollectionUtils.isNullOrEmpty((ArrayList) null));
		List<Object> list1 = new ArrayList<Object>();
		assertTrue(CollectionUtils.isNullOrEmpty(list1));
		list1.add("x");
		assertFalse(CollectionUtils.isNullOrEmpty(list1));
		List<Object> list2 = new ArrayList<Object>();
		list2.add("x");
		list2.add(null);
		assertFalse(CollectionUtils.isNullOrEmpty(list1, list2));
		list1 = new ArrayList<Object>();
		list2 = new ArrayList<Object>();
		assertTrue(CollectionUtils.isNullOrEmpty(list1, list2));
	}
}
