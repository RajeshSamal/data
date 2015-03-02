package com.aia.model;

import java.io.Serializable;

/**
 * Represents the base model class of the application.
 * @param <T> the model type
 * @author ck
 *
 */
public abstract class AbstractBaseModel<T> implements Serializable {

	/** The auto generated UID */
	private static final long serialVersionUID = 8485528234391928992L;

	/** Representing the key of the model */
	private Long id;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Returning class name and the key id.
	 * @return the string representation of the object
	 */
	public String toString() {
		return getClass() + ": id=" + getId();
	}
	
	/**
	 * Default hash code implementation which takes "id" if not null.
	 * If id is NULL then it will use the super class given hash code.
	 * @return the hash code of the object
	 */
	public int hashCode() {
		if (null != id) {
			return id.hashCode();
		}
		return super.hashCode();
	}
}
