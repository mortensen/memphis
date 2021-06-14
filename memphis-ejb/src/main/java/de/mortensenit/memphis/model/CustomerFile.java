package de.mortensenit.memphis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author fmortensen
 * 
 */
@Entity
@Table(name = "file")
@DiscriminatorValue("file")
@Inheritance(strategy = InheritanceType.JOINED)
public class CustomerFile extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	private Customer customer;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	@Column(name = "data")
	private byte[] data;

	@Column(name = "filename")
	private String filename;

	@Column(name = "description")
	private String description;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "size")
	private long size;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}