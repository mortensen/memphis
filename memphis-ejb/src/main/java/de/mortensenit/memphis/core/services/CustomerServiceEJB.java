package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.core.exceptions.ValidationFailedException;
import de.mortensenit.memphis.core.tools.CustomerFileComparator;
import de.mortensenit.memphis.model.Customer;
import de.mortensenit.memphis.model.CustomerFile;

/**
 * 
 * @author fmortensen
 * 
 */
@Stateless
public class CustomerServiceEJB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private PersistenceServiceEJB persistenceServiceEJB;

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Customer find(String id) {
		logger.info("Searching customer: " + id);
		Customer customer = new Customer();
		customer.setId(id);
		try {
			customer = persistenceServiceEJB.find(Customer.class, customer);
			if (customer == null)
				return null;
			sortFilesByDate(customer);
		} catch (PersistenceException | ValidationFailedException e) {
			logger.info("Customer not found: " + id);
			return null;
		}
		return customer;
	}

	/**
	 * 
	 * @return
	 */
	public List<Customer> list() {
		try {
			List<Customer> customers = persistenceServiceEJB.list(Customer.class);
			sortFilesByDate(customers.toArray(new Customer[0]));
			return customers;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}
	
	/**
	 * sort customer files by date
	 * @param customer
	 */
	private void sortFilesByDate(Customer... customers) {
		for (Customer customer : customers) {
			List<CustomerFile> files = customer.getFiles();
			Collections.sort(files, new CustomerFileComparator());
		}
	}
	
	/**
	 * 
	 * @param customer
	 */
	public void save(Customer customer) {
		logger.info("Saving customer " + customer.getCompany1());
		try {
			addFileDates(customer);
			persistenceServiceEJB.save(customer);
		} catch (PersistenceException | ValidationFailedException e) {
		}
	}

	/**
	 * 
	 * @param customer
	 */
	public void update(Customer customer) {
		logger.info("Updating customer " + customer.getCompany1());
		try {
			addFileDates(customer);
			persistenceServiceEJB.update(customer);
		} catch (PersistenceException | ValidationFailedException e) {
		}
	}
	
	/**
	 *set creation date before saving new file 
	 * @param customer
	 */
	private void addFileDates(Customer customer) {
		if (customer != null && customer.getFiles() != null && customer.getFiles().size() > 0) {
			for (CustomerFile file : customer.getFiles()) {
				if (file.getDate() == null) {
					file.setDate(new Date());
				}
			}
			
		}

	}
	
	/**
	 * 
	 * @param customer
	 */
	public void delete(Customer customer) {
		logger.info("Deleting customer " + customer.getId());
		try {
			customer = persistenceServiceEJB.find(Customer.class, customer);
			persistenceServiceEJB.delete(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}