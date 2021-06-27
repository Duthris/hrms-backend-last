package kodlama.io.hrms.business.abstracts;

import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.JobAdvertisementFavorite;

public interface JobAdvertisementFavoriteService {
	public DataResult<List<JobAdvertisementFavorite>> getByCandidateId(int candidateId);
	public Result add(int candidateId, int jobAdvertisementId);
}
