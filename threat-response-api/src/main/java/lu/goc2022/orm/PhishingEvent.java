package lu.goc2022.orm;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "TBL_PHISHING_EVENTS")
@Entity
@Data
public class PhishingEvent {

	@Id
	private Long id;

	@Column(name = "creation_date")
	private Timestamp creationDate;

	@Column(name = "email_from")
	private String emailFrom;

	@Column(name = "email_subject")
	private String emailSubject;

	@Column(name = "email_host")
	private String emailHost;

	@Column(name = "suspicious_url_host")
	private String disabledUrlHost;

}
