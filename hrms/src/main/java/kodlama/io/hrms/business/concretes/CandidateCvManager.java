package kodlama.io.hrms.business.concretes;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kodlama.io.hrms.business.abstracts.CandidateCvService;
import kodlama.io.hrms.core.adapters.abstracts.ImageAdapterService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.ErrorResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.CandidateCvDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateJobExperienceDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateLanguageDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateSchoolDao;
import kodlama.io.hrms.dataAccess.abstracts.CandidateTalentDao;
import kodlama.io.hrms.entities.concretes.CandidateCv;
import kodlama.io.hrms.entities.concretes.CandidateJobExperience;
import kodlama.io.hrms.entities.concretes.CandidateLanguage;
import kodlama.io.hrms.entities.concretes.CandidateSchool;
import kodlama.io.hrms.entities.concretes.CandidateTalent;
import net.bytebuddy.asm.Advice.Return;
import net.bytebuddy.asm.Advice.This;

@Service
public class CandidateCvManager implements CandidateCvService {
	
	private CandidateCvDao candidateCvDao;
	private CandidateTalentDao candidateTalentDao;
	private CandidateLanguageDao candidateLanguageDao;
	private CandidateSchoolDao candidateSchoolDao;
	private CandidateJobExperienceDao candidateJobExperienceDao;
	private ImageAdapterService imageAdapterService;
	private CandidateDao candidateDao;

	
	@Autowired
	public CandidateCvManager(CandidateCvDao candidateCvDao, CandidateTalentDao candidateTalentDao,
			CandidateLanguageDao candidateLanguageDao, CandidateSchoolDao candidateSchoolDao,
			CandidateJobExperienceDao candidateJobExperienceDao,ImageAdapterService imageAdapterService, 
			CandidateDao candidateDao) 
	
	{
		this.candidateCvDao = candidateCvDao;
		this.candidateTalentDao = candidateTalentDao;
		this.candidateLanguageDao = candidateLanguageDao;
		this.candidateSchoolDao = candidateSchoolDao;
		this.candidateJobExperienceDao = candidateJobExperienceDao;
		this.imageAdapterService = imageAdapterService;
		this.candidateDao = candidateDao;
	}

	@Override
	public DataResult<List<CandidateCv>> getAll() {
		return new SuccessDataResult<List<CandidateCv>>
		(this.candidateCvDao.findAll(), "All Candidates' Cv's are Listed!");
	}

	@Override
	public Result add(CandidateCv candidateCv) {
		var tempCv = this.candidateCvDao.save(candidateCv);
		
		if (tempCv != null) {
			if (tempCv.getJobExperiences() != null) {
				for (CandidateJobExperience candidateJobExperience : tempCv.getJobExperiences()) {
					candidateJobExperience.setCandidateCv(tempCv);
					candidateJobExperience.setId(tempCv.getId());
					this.candidateJobExperienceDao.save(candidateJobExperience);
				}
			}
			
			if (tempCv.getSchools() != null) {
				for (CandidateSchool candidateSchool : tempCv.getSchools()) {
					candidateSchool.setCandidateCv(tempCv);
					candidateSchool.setId(tempCv.getId());
					this.candidateSchoolDao.save(candidateSchool);
				}
			}
			
			if (tempCv.getTalents() != null) {
				for (CandidateTalent candidateTalent : tempCv.getTalents()) {
					candidateTalent.setCandidateCv(tempCv);
					candidateTalent.setId(tempCv.getId());
					this.candidateTalentDao.save(candidateTalent);
				}
			}
			
			
			if (tempCv.getLanguages() != null) {
				for (CandidateLanguage candidateLanguage : tempCv.getLanguages()) {
					candidateLanguage.setCandidateCv(tempCv);
					candidateLanguage.setId(tempCv.getId());
					this.candidateLanguageDao.save(candidateLanguage);
				}
			}
			
		}
		
		return new SuccessResult("Candidate's Cv is Added!");
	}

	@Override
	public Result uploadCvAvatar(int id, MultipartFile multipartFile) throws IOException {
		var upload = this.imageAdapterService.upload(multipartFile);
		var link = upload.getData().get("url");
		
		CandidateCv tempCv = this.candidateCvDao.getOne(id);
		tempCv.setAvatarLink(link.toString());
		this.candidateCvDao.save(tempCv);
		
		return new SuccessResult("Upload Succeed!");
	}

	@Override
	public DataResult<CandidateCv> getByCandidateId(int id) {
		return new SuccessDataResult<CandidateCv>
		(this.candidateCvDao.findByCandidateId(id));
	}

	@Override
	public DataResult<CandidateCv> update(CandidateCv candidateCv) {
		CandidateCv tempCv = this.candidateCvDao.findById(candidateCv.getId());
		
		if (candidateCv.getCoverLetter() != null) {
			tempCv.setCoverLetter(candidateCv.getCoverLetter());
		}
		
		if (candidateCv.getAvatarLink() != null) {
			tempCv.setAvatarLink(candidateCv.getAvatarLink());
		}
		
		if (candidateCv.getGithubLink() != null) {
			tempCv.setGithubLink(candidateCv.getGithubLink());
		}
		
		if (candidateCv.getLinkedinLink() != null) {
			tempCv.setLinkedinLink(candidateCv.getLinkedinLink());
		}
		
		if (candidateCv.getJobExperiences() != null) {
			tempCv.setJobExperiences(candidateCv.getJobExperiences());
		}
		
		if (candidateCv.getLanguages() != null) {
			tempCv.setLanguages(candidateCv.getLanguages());
		}
		
		if (candidateCv.getTalents() != null) {
			tempCv.setTalents(candidateCv.getTalents());
		}
		
		if (candidateCv.getSchools() != null) {
			tempCv.setSchools(candidateCv.getSchools());
		}
		
		if (candidateCv.isActive() != tempCv.isActive()) {
			tempCv.setActive(candidateCv.isActive());
		}
		
		
		return new SuccessDataResult<CandidateCv>
		(this.candidateCvDao.save(tempCv), "Cv infos are updated!");
	}

	@Override
	public DataResult<CandidateCv> getByCvId(int id) {
		if (!this.candidateCvDao.existsById(id)) {
			return new ErrorDataResult<CandidateCv>("Invalid Cv Id!");
		}
		
		return new SuccessDataResult<CandidateCv> (this.candidateCvDao.findById(id), "Cv of the candidate is found!");
	}

	@Override
	public Result updateGithub(String githubLink, int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Invalid ID");
		}
		
		else if (!githubLink.startsWith("https://github.com")) {
			return new ErrorResult("Invalid Github address. Github address must be in https://github.com/nick format");
		}
		
		CandidateCv cv = this.candidateCvDao.findByCandidateId(candidateId);
		cv.setGithubLink(githubLink);
		
		this.candidateCvDao.save(cv);
		return new SuccessResult("Github address is updated!");
	}

	@Override
	public Result deleteGithub(int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Invalid Id");
		}
		
		CandidateCv cv = this.candidateCvDao.findByCandidateId(candidateId);
		cv.setGithubLink(null);
		
		this.candidateCvDao.save(cv);
		return new SuccessResult("Github address is deleted!");
	}

	@Override
	public Result updateLinkedin(String linkedinLink, int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Invalid Cv Id");
		}
		
		else if (!linkedinLink.startsWith("https://linked.in")) {
			return new ErrorResult("Invalid Linked.in address. Linked.in address must be in https://linked.in/nick format");
		}
		
		CandidateCv cv = this.candidateCvDao.findByCandidateId(candidateId);
		cv.setLinkedinLink(linkedinLink);
		
		this.candidateCvDao.save(cv);
		return new SuccessResult("Linked.in address is updated!");
	}

	@Override
	public Result deleteLinkedin(int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Invalid Id");
		}
		
		CandidateCv cv = this.candidateCvDao.findByCandidateId(candidateId);
		cv.setLinkedinLink(null);
		
		this.candidateCvDao.save(cv);
		return new SuccessResult("Linked.in address is deleted!");
	}

	@Override
	public Result updateCoverLetter(String coverLetter, int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Invalid Cv Id");
		}
		
		CandidateCv cv = this.candidateCvDao.findByCandidateId(candidateId);
		cv.setCoverLetter(coverLetter);
		
		this.candidateCvDao.save(cv);
		return new SuccessResult("Cover Letter is updated!");
	}

	@Override
	public Result deleteCvAvatar(int id) {
		CandidateCv tempCv = this.candidateCvDao.findByCandidateId(id);
		tempCv.setAvatarLink("https://res.cloudinary.com/duthris/image/upload/v1625270043/j02oe5hmsxsrjjasiymx.jpg");
		
		this.candidateCvDao.save(tempCv);
		return new SuccessResult("Cv Avatar is successfully removed.");
	}

	@Override
	public Result createEmptyCvAfterRegister(int candidateId) {
		CandidateCv tempCv = new CandidateCv();
		
		tempCv.setCandidate(this.candidateDao.getOne(candidateId));
		tempCv.setAvatarLink("https://res.cloudinary.com/duthris/image/upload/v1625270043/j02oe5hmsxsrjjasiymx.jpg");
		tempCv.setCoverLetter(null);
		tempCv.setGithubLink(null);
		tempCv.setJobExperiences(null);
		tempCv.setLanguages(null);
		tempCv.setLinkedinLink(null);
		tempCv.setSchools(null);
		tempCv.setTalents(null);
		tempCv.setActive(true);
		
		this.candidateCvDao.save(tempCv);
		return new SuccessResult("Empty cv is created for registered user!");
	}

}
