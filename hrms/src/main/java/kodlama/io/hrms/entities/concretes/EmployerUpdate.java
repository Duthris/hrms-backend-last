package kodlama.io.hrms.entities.concretes;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="employer_update")
public class EmployerUpdate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="company_name")
	private String companyName;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Column(name="web_site")
	private String webSite;
	
	@Column(name="email")
	private String email;
	
	@Column(name="is_verified")
	private boolean isVerified;
	
	@Column(name="verify_date")
	private LocalDate verifyDate;
	
	@Column(name="created_date")
	private LocalDate createdDate;
	
	@Column(name="employer_id")
	private int employerId;
	
	@Column(name="employee_id")
	private Integer employeeId;
}
