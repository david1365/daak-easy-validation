package model;

import java.util.Date;

/**
 * A trackable entity is an entity which has 'createdBy' String property, 'creationTime' {@link Date} property, 'lastUpdatedBy' String property and 
 * 'lastUpdateTime' {@link Date} property and their getter and setter methods. In a trackable entity, these field should not be displayed in create/edit view 
 * and in server-side these fields will be set in 'BaseEntityDao.setTrackableFields' method. In order to convert an entity to a trackable entity, it is needed 
 * that Constants of target entity, implements this interface.
 *
 */
public interface TrackableEntity {

	String CREATED_BY = "createdBy";
	String CREATION_TIME = "creationTime";
	String CREATION_TIME_FROM = "creationTimeFrom";
	String CREATION_TIME_TO = "creationTimeTo";
	String LAST_UPDATED_BY = "lastUpdatedBy";
	String LAST_UPDATE_TIME = "lastUpdateTime";
	String LAST_UPDATE_TIME_FROM = "lastUpdateTimeFrom";
	String LAST_UPDATE_TIME_TO = "lastUpdateTimeTo";

	Object getId();

	String getCreatedBy();

	void setCreatedBy(String creatorUsername);

	Date getCreationTime();

	void setCreationTime(Date creationTime);

	String getLastUpdatedBy();

	void setLastUpdatedBy(String lastUpdatorUsername);

	Date getLastUpdateTime();

	void setLastUpdateTime(Date lastUpdateTime);

}
