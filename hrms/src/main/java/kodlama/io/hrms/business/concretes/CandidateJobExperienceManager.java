package kodlama.io.hrms.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlama.io.hrms.business.abstracts.CandidateJobExperienceService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.ErrorResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.CandidateCvDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateJobExperienceDao;
import kodlama.io.hrms.entities.concretes.CandidateJobExperience;
import kodlama.io.hrms.entities.concretes.JobPosition;
import kodlama.io.hrms.entities.dtos.JobExperienceAddDto;

@Service
public class CandidateJobExperienceManager implements CandidateJobExperienceService {
	
	private CandidateJobExperienceDao candidateJobExperienceDao;
	private CandidateCvDao candidateCvDao;
	private CandidateDao candidateDao;
	
	
	public CandidateJobExperienceManager(CandidateJobExperienceDao candidateJobExperienceDao, 
			CandidateCvDao candidateCvDao, CandidateDao candidateDao) {
		this.candidateJobExperienceDao = candidateJobExperienceDao;
		this.candidateCvDao = candidateCvDao;
		this.candidateDao = candidateDao;
	}


	@Override
	public DataResult<List<CandidateJobExperience>> getAll() {
		
		return new SuccessDataResult<List<CandidateJobExperience>>
		(this.candidateJobExperienceDao.findAll(), "Job Experiences of Candidates are Listed!");
		
	}


	@Override
	public DataResult<List<CandidateJobExperience>> orderCandidateJobExperiencesByStartingDateDesc(int id) {
		
		if (this.candidateCvDao.existsById(id)) {
			return new SuccessDataResult<List<CandidateJobExperience>>
			(this.candidateJobExperienceDao.getCandidateJobExperienceByStartingDateDesc(id), "Searched Candidate's Job Experiences are Listed!");
		}
		
		return new ErrorDataResult<List<CandidateJobExperience>>("Invalid Cv ID!");
	}


	@Override
	public Result add(JobExperienceAddDto jobExperienceAddDto) {
		
		
		if (jobExperienceAddDto.getCompanyName().length()<=1) {
			return new ErrorResult("Company name must be longer than 1 character!");
		}
		
		else if (jobExperienceAddDto.getStartingDate() == null) {
			return new ErrorResult("Starting date cannot be empty!");
		}
		
		CandidateJobExperience experience = new CandidateJobExperience();
		experience.setCandidateCv(this.candidateCvDao.findByCandidateId(jobExperienceAddDto.getCvId()));
		experience.setCompanyName(jobExperienceAddDto.getCompanyName());
		experience.setStartingDate(jobExperienceAddDto.getStartingDate());
		experience.setEndDate(jobExperienceAddDto.getEndDate());
		experience.setJobPosition(new JobPosition(jobExperienceAddDto.getJobPositionId()));
		
		this.candidateJobExperienceDao.save(experience);
		return new SuccessResult("Job experience is added!");
	}


	@Override
	public Result delete(int jobExperienceId) {
		if (!this.candidateJobExperienceDao.existsById(jobExperienceId)) {
			return new ErrorResult("Invalid Job Experience Id!");
		}
		
		this.candidateJobExperienceDao.deleteById(jobExperienceId);
		return new SuccessResult("Job Experience is deleted!");
	}


	@Override
	public DataResult<List<CandidateJobExperience>> getByCvId(int id) {
		return new SuccessDataResult<List<CandidateJobExperience>>
		(this.candidateJobExperienceDao.findByCandidateCvId(id), "Job Experiences are listed!");
	}
	
	

}
