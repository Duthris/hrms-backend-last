package kodlama.io.hrms.business.abstracts;

import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.CandidateJobExperience;
import kodlama.io.hrms.entities.dtos.JobExperienceAddDto;

public interface CandidateJobExperienceService {
	
	DataResult<List<CandidateJobExperience>> getAll();
	DataResult<List<CandidateJobExperience>> orderCandidateJobExperiencesByStartingDateDesc(int id);
	
	Result add(JobExperienceAddDto jobExperienceAddDto);
	Result delete(int jobExperienceId);

	DataResult<List<CandidateJobExperience>> getByCvId(int id);
}
