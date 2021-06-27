package kodlama.io.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlama.io.hrms.business.abstracts.JobAdvertisementFavoriteService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.ErrorResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.CandidateDao;
import kodlama.io.hrms.dataAccess.abstracts.JobAdvertisementDao;
import kodlama.io.hrms.dataAccess.abstracts.JobAdvertisementFavoriteDao;
import kodlama.io.hrms.entities.concretes.JobAdvertisementFavorite;

@Service
public class JobAdvertisementFavoriteManager implements JobAdvertisementFavoriteService{
	
	private JobAdvertisementFavoriteDao jobAdvertisementFavoriteDao;
	private CandidateDao candidateDao;
	private JobAdvertisementDao jobAdvertisementDao;
	
	@Autowired
	public JobAdvertisementFavoriteManager(JobAdvertisementFavoriteDao jobAdvertisementFavoriteDao,
			CandidateDao candidateDao, JobAdvertisementDao jobAdvertisementDao) {
		super();
		this.jobAdvertisementFavoriteDao = jobAdvertisementFavoriteDao;
		this.candidateDao = candidateDao;
		this.jobAdvertisementDao = jobAdvertisementDao;
	}

	@Override
	public DataResult<List<JobAdvertisementFavorite>> getByCandidateId(int candidateId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorDataResult<List<JobAdvertisementFavorite>>
			("Not an exist Candidate!");
		}
		
		return new SuccessDataResult<List<JobAdvertisementFavorite>>
		(this.jobAdvertisementFavoriteDao.findByCandidateId(candidateId), "Favorites are listed!");
	}

	@Override
	public Result add(int candidateId, int jobAdvertisementId) {
		if (!this.candidateDao.existsById(candidateId)) {
			return new ErrorResult("Not an exist Candidate!");
		}
		
		if (!this.jobAdvertisementDao.existsById(jobAdvertisementId)) {
			return new ErrorResult("Not an exist Job Advertisement!");
		}
		
		JobAdvertisementFavorite jobAdvertisementFavorite = new JobAdvertisementFavorite();
		jobAdvertisementFavorite.setCandidate(this.candidateDao.getById(candidateId));
		jobAdvertisementFavorite.setJobAdvertisement(this.jobAdvertisementDao.getOne(jobAdvertisementId));
		
		this.jobAdvertisementFavoriteDao.save(jobAdvertisementFavorite);
		return new SuccessResult("The Advertisement is added to favorites.");
	}

	@Override
	public Result delete(int id) {
		this.jobAdvertisementFavoriteDao.deleteById(id);
		return new SuccessResult("The Advertisement is successfully deleted from favorites.");
	}
	

}
