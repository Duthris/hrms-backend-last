package kodlama.io.hrms.entities.dtos;

import java.time.LocalDate;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import kodlama.io.hrms.entities.concretes.JobPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobExperienceAddDto {
	
	private int cvId;
	private String companyName;
	private LocalDate startingDate;
	private LocalDate endDate;
	private boolean isQuited;
	private int jobPositionId;
}
