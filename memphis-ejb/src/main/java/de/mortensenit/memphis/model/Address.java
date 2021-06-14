package de.mortensenit.memphis.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author fmortensen
 * 
 */
@Entity
@Table(name="address")
@DiscriminatorValue("address")
@Inheritance(strategy = InheritanceType.JOINED)
public class Address extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="address_type")
	private AddressType addressType;

	@ManyToOne
	private Customer customer;

	@Column(name="company1")
	private String company1;

	@Column(name="company2")
	private String company2;

	@Column(name="company3")
	private String company3;

	@Column(name="street")
	private String street;

	@Column(name="zip")
	private String zipCode;

	@Column(name="city")
	private String city;

	@Column(name="phone")
	private String phone;

	@Column(name="fax")
	private String fax;

	@Column(name="email")
	private String email;

	@Column(name="url")
	private String url;

	@Column(name="notes")
	private String notes;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

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

	public String getCompany3() {
		return company3;
	}

	public void setCompany3(String company3) {
		this.company3 = company3;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}
	
	public AddressType[] getAddressTypes() {
		return AddressType.values();
	}

}
