package de.mortensenit.memphis.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * Superclass of all entities that will be persisted to the database <br>
 * <br>
 * 
 * @author Frederik Mortensen
 */
@MappedSuperclass
public abstract class AbstractEntity implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="id")
	private String id;

	/**
	 * Standard constructor
	 */
	public AbstractEntity() {
		if (id == null) {
			id = UUID.randomUUID().toString();
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		return (o == this || (o instanceof AbstractEntity && id
				.equals(((AbstractEntity) o).id)));
	}

	@Override
	public String toString() {
		return id;
	}

}
