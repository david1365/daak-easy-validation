package classes;

import interfaces.ColorGroupConstants;
import model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

/* PROTECTED REGION ID(ColorGroupEntityImportsAndAnnotations) ENABLED START */
/* PROTECTED REGION END */
@Entity
@Table(name = "BS_COLOR_GROUP")
//@SuppressWarnings("serial")
public class ColorGroupEntity extends BaseEntity<Long> implements ColorGroupConstants {

	private Long code;
	private String title;
	private Integer grp;
	private Boolean isActive;
	private String description;
	private String createdBy;
	private Date creationTime;
	private String lastUpdatedBy;
	private Date lastUpdateTime;

	/* PROTECTED REGION ID(ColorGroupEntityFields) ENABLED START */
	/* PROTECTED REGION END */

	public ColorGroupEntity() {
	}

	public ColorGroupEntity(Long id) {
		setId(id);
	}

	@Id
	@GeneratedValue(generator = "sequence", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "sequence", sequenceName = "BS_COLOR_GROUP_SEQ", allocationSize = 1)
	@Column(name = "ID")
	@Override
	public Long getId() {
		return super.getId();
	}

	@Column(name = "CODE", nullable = false)
	public Long getCode() {
		return code;
	}

	public void setCode(Long code) {
		this.code = code;
	}

	@Column(name = "TITLE", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "GRP", nullable = false)
	public Integer getGrp() {
		return grp;
	}

	public void setGrp(Integer grp) {
		this.grp = grp;
	}

	@Column(name = "IS_ACTIVE", nullable = false)
	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "CREATED_BY", nullable = false)
	public String getCreatedBy() {
		return createdBy;
	}

		public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATION_DATE", nullable = false)

	public Date getCreationTime() {
		return creationTime;
	}


	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	@Column(name = "LAST_UPDATED_BY")
		public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Column(name = "LAST_UPDATE_DATE")
		public Date getLastUpdateTime() {
		return lastUpdateTime;
	}


	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

}
