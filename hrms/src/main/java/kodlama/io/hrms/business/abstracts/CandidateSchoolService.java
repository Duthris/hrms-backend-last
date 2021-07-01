package kodlama.io.hrms.business.abstracts;

import java.util.Date;
import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.CandidateCv;
import kodlama.io.hrms.entities.concretes.CandidateSchool;
import kodlama.io.hrms.entities.concretes.Talent;
import kodlama.io.hrms.entities.dtos.SchoolAddDto;

public interface CandidateSchoolService {
	
	DataResult<List<CandidateSchool>> getAll();
	
	DataResult<List<CandidateSchool>> getByCandidateCvId(int id);
	
	DataResult<CandidateSchool> update(CandidateSchool candidateSchool);
	
	Result add(SchoolAddDto schoolAddDto);
	Result delete(int id);
	
	DataResult<List<CandidateSchool>> orderCandidateSchoolsByGraduationDateDesc(int id);
}
