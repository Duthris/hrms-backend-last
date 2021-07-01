package kodlama.io.hrms.business.abstracts;

import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.Candidate;
import kodlama.io.hrms.entities.concretes.Employee;
import kodlama.io.hrms.entities.dtos.CandidatePersonalInfoDto;

public interface CandidateService {
	DataResult<List<Candidate>> getAll();
	Result register(Candidate candidate);
	DataResult<Candidate> getByEMail(String email);
	DataResult<Candidate> getByNationalityId(String nationalityId);
	
	Result update(CandidatePersonalInfoDto candidatePersonalInfoDto);
}
