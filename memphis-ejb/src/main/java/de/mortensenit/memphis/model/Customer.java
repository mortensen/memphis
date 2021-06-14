package de.mortensenit.memphis.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 * @author fmortensen
 * 
 */
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "company1")
	private String company1;

	@Column(name = "company2")
	private String company2;

	@Column(name = "name1")
	private String name1;

	@Column(name = "street")
	private String street;

	@Column(name = "zip_code")
	private String zipCode;

	@Column(name = "city")
	private String city;

	@Column(name = "phone1")
	private String phoneNumber1;

	@Column(name = "fax1")
	private String faxNumber1;

	@Column(name = "mobile1")
	private String mobileNumber1;

	@Column(name = "email")
	private String email;

	@Column(name = "url")
	private String url;

	@Column(name = "customer_number")
	private String customerNumber;

	@Column(name = "customer_class")
	private String customerClass;

	@Column(name = "sales2013")
	private String sales2013;

	@Column(name = "sales2014")
	private String sales2014;

	@Column(name = "notices")
	private String notices;

	@OneToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	private List<CustomerFile> files = new ArrayList<CustomerFile>();

	public String getCompany1() {
		return company1;
	}

	public void setCompany1(String company1) {
		this.company1 = company1;
	}

	public String getCompany2() {
		return company2;
	}

	public void setCompany2(String company2) {
		this.company2 = company2;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getFaxNumber1() {
		return faxNumber1;
	}

	public void setFaxNumber1(String faxNumber1) {
		this.faxNumber1 = faxNumber1;
	}

	public String getMobileNumber1() {
		return mobileNumber1;
	}

	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getCustomerClass() {
		return customerClass;
	}

	public void setCustomerClass(String customerClass) {
		this.customerClass = customerClass;
	}

	public String getSales2013() {
		return sales2013;
	}

	public void setSales2013(String sales2013) {
		this.sales2013 = sales2013;
	}

	public String getSales2014() {
		return sales2014;
	}

	public void setSales2014(String sales2014) {
		this.sales2014 = sales2014;
	}

	public List<CustomerFile> getFiles() {
		return files;
	}

	public void setFiles(List<CustomerFile> files) {
		this.files = files;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getNotices() {
		return notices;
	}

	public void setNotices(String notices) {
		this.notices = notices;
	}

}
