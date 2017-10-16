package model;

import java.io.Serializable;

/**
 * This class is parent of all entities (which usually saved/retrieved in/from database by using hibernate). This class is {@link Serializable}.<br/>
 * Note that all {@link Serializable} classes (like Models) should:<br/>
 * <ol>
 * 	<li>implements {@link Serializable} directly or indirectly.
 * 	<li>have default constructor (constructor without parameter).
 * </ol>
 * 
 * @param <I> Id Class
 */
@SuppressWarnings("serial")
public abstract class BaseEntity<I extends Serializable> implements BaseConstants {

	protected I id;

	public I getId() {
		return id;
	}

	public void setId(I id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object object) {
		if (object == null) {
			return false;
		}
		if (object == this) { // Quick return if same object passed.
			return true;
		}
		if (this.getClass().equals(object.getClass())) {
			if (getId() != null) {
				return getId().equals(((BaseEntity<?>) object).getId());
			} else {
				return super.equals(object);
			}
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getId() != null ? getId().hashCode() : super.hashCode();
	}

	@Override
	public String toString() {
		return " - id=" + getId();
	}

}
