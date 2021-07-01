package kodlama.io.hrms.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidatePersonalInfoDto {
	private int id;
	private String firstName;
	private String lastName;
	private String birthYear;
	private String email;
}
