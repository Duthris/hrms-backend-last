package kodlama.io.hrms.entities.dtos;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SchoolAddDto {
	
	private int cvId;
	private String schoolName;
	private String department;
	private Date startingDate;
	private Date graduationDate;
	private boolean isGraduated;
	
}
