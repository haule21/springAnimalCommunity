package haule.raelfarm.jpa;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "access_log_test")
public class AccessLog {

	@Id
	@Column(name="IP_ADDRESS")
	private String IP_ADDRESS;
	
	@Id
	@Column(name="ACCESS_DATE")
	private Date ACCESS_DATE;
	
	@Id
	@Column(name="ACCESS_USERID")
	private String ACCESS_USERID;
}
