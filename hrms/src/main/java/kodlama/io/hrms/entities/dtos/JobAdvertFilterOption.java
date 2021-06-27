package kodlama.io.hrms.entities.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobAdvertFilterOption {
	private List<Integer> cityId;
	private List<Integer> jobPositionId;
	private List<Integer> workingMethodId;
	private List<Integer> workingTimeId;
}