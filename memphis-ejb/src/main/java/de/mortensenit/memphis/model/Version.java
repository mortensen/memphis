package de.mortensenit.memphis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "version")
@DiscriminatorValue("version")
@Inheritance(strategy = InheritanceType.JOINED)
public class Version extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "installed")
	private String installed;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date")
	private Date date;

	public String getInstalled() {
		return installed;
	}

	public void setInstalled(String installed) {
		this.installed = installed;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
