package interfaces;


import model.TrackableEntity;

import java.io.Serializable;

public interface ExternalJobTypeConstants extends Serializable, TrackableEntity {

	String CODE = "code";
	String CODE_FROM = "codeFrom";
	String CODE_TO = "codeTo";
	String TITLE = "title";
	String GRP = "grp";
	String IS_ACTIVE = "isActive";
	String DESCRIPTION = "description";

	/* PROTECTED REGION ID(ExternalJobTypeConstants) ENABLED START */
	/* PROTECTED REGION END */

}
