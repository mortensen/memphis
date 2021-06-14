package de.mortensenit.memphis.utils;

import java.util.Collection;
import java.util.Iterator;

/**
 * Collection helper methods. A little utility class
 * 
 * @author Frederik Mortensen
 */
@SuppressWarnings("rawtypes")
public class CollectionUtils {

	/**
	 * check wether all given collections are filled
	 * 
	 * @param collections
	 * @return
	 */
	public static boolean isNotNullOrEmpty(Collection... collections) {
		if (collections == null)
			return false;
		for (Collection collection : collections) {
			if (collection == null || collection.isEmpty())
				return false;
			Iterator iter = collection.iterator();
			while (iter.hasNext()) {
				Object o = iter.next();
				if (o == null)
					return false;
				if (o instanceof Collection)
					return isNotNullOrEmpty((Collection) o);
			}
		}
		return true;
	}

	/**
	 * checks wether all collections are empty
	 * 
	 * @param collections
	 * @return
	 */
	public static boolean isNullOrEmpty(Collection... collections) {
		if (collections == null)
			return true;
		for (Collection collection : collections) {
			if (collection != null && !collection.isEmpty())
				return false;
		}
		return true;
	}

}
