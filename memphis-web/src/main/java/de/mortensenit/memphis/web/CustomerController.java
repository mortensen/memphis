package de.mortensenit.memphis.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import de.mortensenit.memphis.core.services.CustomerServiceEJB;
import de.mortensenit.memphis.core.services.PersistenceServiceEJB;
import de.mortensenit.memphis.core.services.SecurityServiceEJB;
import de.mortensenit.memphis.model.Address;
import de.mortensenit.memphis.model.AddressType;
import de.mortensenit.memphis.model.Customer;
import de.mortensenit.memphis.model.CustomerFile;
import de.mortensenit.memphis.utils.StringUtils;

/**
 * All methods acording to webapp customers
 * 
 * @author Frederik Mortensen
 */
@Named
@SessionScoped
// TODO: überall errorhandling
// TODO: überall logging
public class CustomerController implements Serializable {

	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	private static final long serialVersionUID = 1L;

	@EJB
	private CustomerServiceEJB customerService;

	@EJB
	private SecurityServiceEJB securityService;

	@EJB
	private PersistenceServiceEJB persistenceService;

	@Inject
	private SessionController sessionController;

	private List<Customer> customers;

	private Customer customer = new Customer();

	private Address address = new Address();

	private String filter1 = "";

	private String filter2 = "";

	private Part file;

	private String fileDescription;

	private String customerChanges;
	
	/**
	 * edits the chosen customer from the customer list
	 * 
	 * @param customerId
	 * @return
	 */
	public String edit(String customerId) {
		for (Customer customer : customerService.list()) {
			if (customer.getId().equals(customerId)) {
				this.customer = customer;
				break;
			}
		}
		fileDescription = "";
		return "edit_customer.xhtml";
	}

	/**
	 * updates the customer and its addresses (old and new) and files
	 * 
	 * @return
	 */
	public String update() {
		customerService.update(customer);
		sessionController.setHintMessage("Kunde gespeichert!");
		return deleteFilter();
	}

	/**
	 * deletes the customer and its addresses
	 * 
	 * @param customerId
	 * @return
	 */
	public String delete() {
		logger.log(Level.INFO, "Kundenlöschung gestartet.");
		customerService.delete(customer);
		sessionController.setHintMessage("Kunde gelöscht!");
		return deleteFilter();
	}

	/**
	 * 
	 * @param customerId
	 * @return
	 */
	public String prepareDelete(String customerId) {
		customer = customerService.find(customerId);
		customer.setId(customerId);
		return "prepare_customer_delete.xhtml";
	}

	/**
	 * 
	 * @return
	 */
	public String prepareUpdate() {
		StringBuffer changes = new StringBuffer();

		Customer persistentCustomer = customerService.find(customer.getId());
		if (persistentCustomer == null) {
			customerChanges = "Neuanlage des Kunden.";
			return "prepare_customer_update.xhtml";
		}

		changeHelper("Stadt", customer.getCity(), persistentCustomer.getCity(),
				changes);
		changeHelper("Firma 1", customer.getCompany1(),
				persistentCustomer.getCompany1(), changes);
		changeHelper("Firma 2", customer.getCompany2(),
				persistentCustomer.getCompany2(), changes);
		changeHelper("Kundenklasse", customer.getCustomerClass(),
				persistentCustomer.getCustomerClass(), changes);
		changeHelper("Kundennummer", customer.getCustomerNumber(),
				persistentCustomer.getCustomerNumber(), changes);
		changeHelper("Email", customer.getEmail(),
				persistentCustomer.getEmail(), changes);
		changeHelper("Fax Nummer 1", customer.getFaxNumber1(),
				persistentCustomer.getFaxNumber1(), changes);
		changeHelper("Mobil Nummer 1", customer.getMobileNumber1(),
				persistentCustomer.getMobileNumber1(), changes);
		changeHelper("Name 1", customer.getName1(),
				persistentCustomer.getName1(), changes);
		changeHelper("Telefon 1", customer.getPhoneNumber1(),
				persistentCustomer.getPhoneNumber1(), changes);
		changeHelper("Umsatz 2013", customer.getSales2013(),
				persistentCustomer.getSales2013(), changes);
		changeHelper("Umsatz 2014", customer.getSales2014(),
				persistentCustomer.getSales2014(), changes);
		changeHelper("Strasse", customer.getStreet(),
				persistentCustomer.getStreet(), changes);
		changeHelper("URL", customer.getUrl(), persistentCustomer.getUrl(),
				changes);
		changeHelper("Postleitzahl", customer.getZipCode(),
				persistentCustomer.getZipCode(), changes);
		
		if ((persistentCustomer.getNotices() == null && customer.getNotices() != null)
				|| (persistentCustomer.getNotices() != null && customer
						.getNotices() == null)
				|| (persistentCustomer.getNotices() != null
						&& customer.getNotices() != null && !persistentCustomer
						.getNotices().equals(customer.getNotices()))) {
			changes.append("Geänderter Eintrag - Notizen.<br />");
		}

		if ((persistentCustomer.getFiles() == null || persistentCustomer
				.getFiles().size() == 0)
				&& (customer.getFiles() == null || customer.getFiles().size() == 0)) {
			// do nothing, no files existent
		} else if ((persistentCustomer.getFiles() == null || persistentCustomer
				.getFiles().size() == 0)
				&& (customer.getFiles() != null && customer.getFiles().size() > 0)) {
			changes.append("Neue Dateien hinzugefügt: ");
			for (CustomerFile file : customer.getFiles()) {
				changes.append(file.getFilename() + ", ");
			}
			// remove trailing ,
			changes.replace(changes.length() - 2, changes.length() - 1, "");
			changes.append("<br />");
		} else if (persistentCustomer.getFiles() != null && persistentCustomer.getFiles().size() > 0 && (customer.getFiles() == null || customer.getFiles().size() == 0) || customer.getFiles().size() < persistentCustomer.getFiles().size()) {
			changes.append("Angehängte Dateien wurden gelöscht!<br />");
		} else if (persistentCustomer.getFiles() != null && customer.getFiles() != null && persistentCustomer.getFiles().size() > 0 && customer.getFiles().size() > 0 && persistentCustomer.getFiles().size() == customer.getFiles().size()) {
			if (!customer.getFiles().containsAll(persistentCustomer.getFiles()))
			changes.append("Angehängte Dateien wurden geändert!<br />");
		} else if (persistentCustomer.getFiles() != null && customer.getFiles() != null && persistentCustomer.getFiles().size() > 0 && customer.getFiles().size() > 0 && persistentCustomer.getFiles().size() != customer.getFiles().size()) {
			changes.append("Angehängte Dateien wurden geändert!<br />");
		}
		
		customerChanges = changes.toString();
		if (customerChanges.trim().length() == 0) {
			sessionController.setHintMessage("Keine Änderungen gefunden!");
			return "";
		}

		return "prepare_customer_update.xhtml";
	}

	/**
	 * Build a string with all changes done to the customer, to later show the
	 * result and ask the user, if saving these changes is correct.
	 * 
	 * @param oldValue
	 * @param newValue
	 * @param result
	 */
	private void changeHelper(String fieldName, String newValue,
			String oldValue, StringBuffer result) {
		if (oldValue == null && newValue == null) {
			return;
		} else if (oldValue != null && newValue != null
				&& oldValue.equals(newValue)) {
			return;
		} else if (oldValue == null && newValue != null) {
			result.append("Neuer Eintrag - " + fieldName + ": " + newValue
					+ "<br />");
		} else if (oldValue != null && newValue == null) {
			result.append("Gelöschter Eintrag - " + fieldName + ": " + oldValue
					+ "<br />");
		} else {
			result.append("Geänderter Eintrag - " + fieldName + ": " + newValue
					+ "<br />");
		}
	}

	/**
	 * updates the customer list view to only show filtered items
	 * 
	 * @return
	 */
	public String getFilteredCustomers() {
		List<Customer> customers = customerService.list();
		List<Customer> filteredCustomers = new ArrayList<Customer>();
		if (customers == null) {
			this.customers = filteredCustomers;
			return "customers.xhtml";
		}
		for (Customer customer : customers) {
			if (StringUtils.isNotNullOrEmpty(filter1)) {
				if (customer.getCompany1().toLowerCase()
						.contains(filter1.toLowerCase()))
					filteredCustomers.add(customer);
			} else if (StringUtils.isNotNullOrEmpty(filter2)) {
				if (customer.getZipCode().startsWith(filter2)
						|| customer.getCity().toLowerCase()
								.startsWith(filter2.toLowerCase())) {
					filteredCustomers.add(customer);
				}

			}
		}
		this.customers = filteredCustomers;
		return "customers.xhtml";
	}

	/**
	 * removes the customer list filter
	 * 
	 * @return
	 */
	public String deleteFilter() {
		setFilter1("");
		setFilter2("");
		customers = customerService.list();
		return "customers.xhtml";
	}

	/**
	 * opens the mask for editing a new customer, not yet persisted
	 * 
	 * @return
	 */
	public String addCustomer() {
		customer = new Customer();
		return "edit_customer.xhtml";
	}

	/**
	 * counts all existing customers
	 * 
	 * @return
	 */
	public String getTotal() {
		List<Customer> customers = customerService.list();
		if (customers != null) {
			return customers.size() + "";
		}
		return "0";
	}

	/**
	 * counts all (existing and new) files to the actual customer
	 * 
	 * @return
	 */
	public String getTotalFiles() {
		return customer.getFiles().size() + "";
	}

	/**
	 * uploading files to database
	 */
	public String upload() {
		InputStream in = null;
		try {
			byte[] content = new byte[(int) file.getSize()];
			in = file.getInputStream();
			in.read(content);
			in.close();
			CustomerFile customerFile = new CustomerFile();
			customerFile.setCustomer(customer);
			customerFile.setData(content);
			customerFile.setFilename(file.getSubmittedFileName()); // file.getName();
			customerFile.setContentType(file.getContentType());
			customerFile.setDescription(fileDescription);
			customerFile.setSize(file.getSize());
			customer.getFiles().add(0, customerFile);
			sessionController
					.setHintMessage("Datei hochgeladen. Kunde speichern, um Änderungen zu übernehmen.");

		} catch (IOException e) {
			sessionController.setHintMessage("Upload fehlgeschlagen! "
					+ e.getLocalizedMessage());
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (Exception e) {
				//
			}
		}
		return "";
	}

	/**
	 * 
	 * @return
	 */
	public String download(CustomerFile file) {
		try {
			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			ec.responseReset();
			ec.setResponseContentType(file.getContentType());
			ec.setResponseContentLength((int) file.getSize());
			ec.setResponseHeader("Content-Disposition",
					"attachment; filename=\"" + file.getFilename() + "\"");
			OutputStream output = ec.getResponseOutputStream();
			output.write(file.getData());
			fc.responseComplete();
		} catch (IOException e) {
			sessionController.setHintMessage("Download fehlgeschlagen! "
					+ e.getLocalizedMessage());
		}
		return "";
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public String deleteFile(String fileId) {
		for (CustomerFile file : customer.getFiles()) {
			if (file.getId().equals(fileId)) {
				customer.getFiles().remove(file);
				break;
			}
			sessionController
					.setHintMessage("Datei gelöscht. Kunde speichern, um Änderungen zu übernehmen!");
		}
		return "edit_customer.xhtml";
	}

	/**
	 * gets all entries from the addres type enum to fill the dropdown items
	 * 
	 * @return
	 */
	public AddressType[] getAddressTypes() {
		return AddressType.values();
	}

	/**
	 * 
	 * @return
	 */
	public List<Customer> getCustomers() {
		if (customers == null)
			customers = customerService.list();
		return customers;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public String getFileDescription() {
		return fileDescription;
	}

	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}

	public String getFilter1() {
		return filter1;
	}

	public void setFilter1(String filter1) {
		this.filter1 = filter1;
	}

	public String getFilter2() {
		return filter2;
	}

	public void setFilter2(String filter2) {
		this.filter2 = filter2;
	}

	public String getCustomerChanges() {
		return customerChanges;
	}

	public void setCustomerChanges(String customerChanges) {
		this.customerChanges = customerChanges;
	}

}