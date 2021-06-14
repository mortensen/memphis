package de.mortensenit.memphis.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.mortensenit.memphis.model.validation.ValidUser;

/**
 * 
 * @author fmortensen
 * 
 */
@Entity
@Table(name="user")
@DiscriminatorValue("user")
@ValidUser
public class User extends AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="name")
	private String name;

	@NotNull
	@Size(min = 6, message = "Passwort zu kurz")
	@Column(name="password")
	private String password;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="last_login")
	private Date lastLogin;

	@Column(name="email")
	private String email;

	@Column(name="admin")
	private boolean administrator;

	@Transient
	private String plainPassword;

	@Transient
	private String repeatedPassword;

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User(String name, String password) {
		setName(name);
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getRepeatedPassword() {
		return repeatedPassword;
	}

	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdministrator() {
		return administrator;
	}

	public void setAdministrator(boolean administrator) {
		this.administrator = administrator;
	}

}