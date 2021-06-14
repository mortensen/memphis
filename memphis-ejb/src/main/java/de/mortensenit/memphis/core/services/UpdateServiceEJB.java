package de.mortensenit.memphis.core.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.mortensenit.memphis.model.Customer;
import de.mortensenit.memphis.model.User;
import de.mortensenit.memphis.model.Version;

/**
 * 
 * @author fmortensen
 * 
 */
@Stateless
public class UpdateServiceEJB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@EJB
	private UserServiceEJB userServiceEJB;

	@EJB
	private CustomerServiceEJB customerServiceEJB;

	@EJB
	SecurityServiceEJB securityServiceEJB;

	@EJB
	private PersistenceServiceEJB persistenceServiceEJB;

	/**
	 * gets all installed packages to check what needs to be installed
	 * 
	 * @return installed versions
	 */
	public List<Version> listVersions() {
		try {
			List<Version> versions = persistenceServiceEJB.list(Version.class);
			// TODO: allgemeines Interface für Sortiermethode -> customer list
			// und hier, get date ... damit ich einen generischen comparator hab
			return versions;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
			return null;
		}
	}

	/**
	 * start update, check what is installed and install the newer packages
	 */
	public void update() {
		List<Version> versions = listVersions();
		List<String> versionsInstalled = new ArrayList<String>();
		for (Version version : versions) {
			versionsInstalled.add(version.getInstalled());
		}
		if (!versionsInstalled.contains("1.0")) {
			install_1_0();
		}
	}

	/**
	 * install initial version 1.0
	 */
	private void install_1_0() {

		logger.debug("Installing version 1.0");

		try {
			User admin = new User();
			admin.setId("1");
			admin.setName("admin");
			admin.setEmail("mortensen.frederik@gmail.com");
			admin.setAdministrator(true);
			admin.setLastLogin(new Date());
			admin.setPlainPassword("memphis100$");
			admin.setRepeatedPassword("memphis100$");
			admin.setPassword(securityServiceEJB.encrypt(admin
					.getPlainPassword()));
			userServiceEJB.save(admin);

			Customer customer = new Customer();
			customer.setId("1");
			customer.setCity("Regenstauf");
			customer.setCompany1("IT Service Mortensen");
			customer.setCustomerClass("A");
			customer.setCustomerNumber("1");
			customer.setEmail("mortensen.frederik@gmail.com");
			customer.setFaxNumber1("+49-0-0");
			customer.setMobileNumber1("+49-172-8540966");
			customer.setName1("Frederik Mortensen");
			customer.setNotices("Testkunde");
			customer.setPhoneNumber1("+49-940-0");
			customer.setSales2013("0 €");
			customer.setSales2014("0 €");
			customer.setStreet("Keltenweg 16");
			customer.setUrl("www.google.de");
			customer.setZipCode("93128");
			customerServiceEJB.save(customer);

			Version version = new Version();
			version.setInstalled("1.0");
			version.setDate(new Date());
			persistenceServiceEJB.save(version);

		} catch (Exception e) {
			logger.error("Setup failed!", e);
			throw new RuntimeException(e);
		}

		logger.debug("Version 1.0 installed");

	}

}