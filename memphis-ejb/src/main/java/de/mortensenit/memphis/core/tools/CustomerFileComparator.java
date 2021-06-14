package de.mortensenit.memphis.core.tools;

import java.util.Comparator;

import de.mortensenit.memphis.model.CustomerFile;

/**
 * Sort all files by date with the newest on top
 * @author fmortensen
 *
 */
public class CustomerFileComparator implements Comparator<CustomerFile> {

	@Override
	public int compare(CustomerFile file1, CustomerFile file2) {
		return file2.getDate().compareTo(file1.getDate());
	}
	

}
