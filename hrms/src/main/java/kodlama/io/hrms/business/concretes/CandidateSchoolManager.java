package kodlama.io.hrms.business.concretes;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlama.io.hrms.business.abstracts.CandidateSchoolService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.ErrorResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.CandidateCvDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateSchoolDao;
import kodlama.io.hrms.entities.concretes.Candidate;
import kodlama.io.hrms.entities.concretes.CandidateCv;
import kodlama.io.hrms.entities.concretes.CandidateSchool;
import kodlama.io.hrms.entities.concretes.CandidateTalent;
import kodlama.io.hrms.entities.dtos.SchoolAddDto;

@Service
public class CandidateSchoolManager implements CandidateSchoolService {
	
	private CandidateSchoolDao candidateSchoolDao;
	private CandidateCvDao candidateCvDao;
	private CandidateDao candidateDao;

	@Autowired
	public CandidateSchoolManager(CandidateSchoolDao candidateSchoolDao, CandidateCvDao candidateCvDao, CandidateDao candidateDao) {
		this.candidateSchoolDao = candidateSchoolDao;
		this.candidateCvDao = candidateCvDao;
		this.candidateDao = candidateDao;
	}

	@Override
	public DataResult<List<CandidateSchool>> getAll() {
		return new SuccessDataResult<List<CandidateSchool>>
		(this.candidateSchoolDao.findAll(), "All Candidates' School's are Listed!");
	}

	@Override
	public DataResult<List<CandidateSchool>> getByCandidateCvId(int id) {
		return new SuccessDataResult<List<CandidateSchool>>
		(this.candidateSchoolDao.getByCandidateCvId(id));	}

	@Override
	public DataResult<CandidateSchool> update(CandidateSchool candidateSchool) {
		CandidateSchool tempSchool = this.candidateSchoolDao.findById(candidateSchool.getId());
		
		if (candidateSchool.getGraduationDate() != null) {
			tempSchool.setGraduationDate(candidateSchool.getGraduationDate());
		}
		
		if (candidateSchool.getSchoolName() != null) {
			tempSchool.setSchoolName(candidateSchool.getSchoolName());
		}
		
		if (candidateSchool.getDepartment() != null) {
			tempSchool.setDepartment(candidateSchool.getDepartment());
		}
		
		if (candidateSchool.getStartingDate() != null) {
			tempSchool.setStartingDate(candidateSchool.getStartingDate());
		}
		
		return new SuccessDataResult<CandidateSchool>
		(this.candidateSchoolDao.save(tempSchool), "School infos are updated!");
		
	}

	@Override
	public Result add(SchoolAddDto schoolAddDto) {
		if (!this.candidateDao.existsById(schoolAddDto.getCvId())) {
			return new ErrorResult("Invalid cv id!");
		}
		
		else if (schoolAddDto.getSchoolName().length()<=1) {
			return new ErrorResult("School Name cannot be less than 1 charcater!");
		}
		
		else if (schoolAddDto.getDepartment().length()<=1) {
			return new ErrorResult("Department cannot be less than 1 character!");
		}
		
		else if (schoolAddDto.getStartingDate()==null) {
			return new ErrorResult("Starting date cannot be empty!");
		}
		
		CandidateSchool school = new CandidateSchool();
		school.setCandidateCv(this.candidateCvDao.findByCandidateId(schoolAddDto.getCvId()));
		school.setSchoolName(schoolAddDto.getSchoolName());
		school.setDepartment(schoolAddDto.getDepartment());
		school.setStartingDate(schoolAddDto.getStartingDate());
		school.setGraduationDate(schoolAddDto.getGraduationDate());
		school.setGraduated(schoolAddDto.isGraduated());
		
		this.candidateSchoolDao.save(school);
		return new SuccessResult("School is added to cv!");
	}

	@Override
	public DataResult<List<CandidateSchool>> orderCandidateSchoolsByGraduationDateDesc(int id) {
		if (this.candidateCvDao.existsById(id)) {
			return new SuccessDataResult<List<CandidateSchool>>
			(this.candidateSchoolDao.getCandidateSchoolsOrderByGraduationDateDesc(id),"Searched Candidate's Schools are Listed!");
		}
		
		return new ErrorDataResult<List<CandidateSchool>>
		("Invalid CV ID!");
	}

	@Override
	public Result delete(int id) {
		if (!this.candidateSchoolDao.existsById(id)) {
			return new ErrorResult("Invalid School Id!");
		}
		
		this.candidateSchoolDao.deleteById(id);
		return new SuccessResult("School is removed!");
	}

}
