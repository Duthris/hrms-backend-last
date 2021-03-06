package kodlama.io.hrms.business.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.JobAdvertisement;
import kodlama.io.hrms.entities.dtos.JobAdvertFilterOption;
import kodlama.io.hrms.entities.dtos.JobAdvertisementDto;

public interface JobAdvertisementService {
	DataResult<List<JobAdvertisement>> getAll();
	DataResult<List<JobAdvertisement>> getAllSorted();
	DataResult<List<JobAdvertisement>> getAllByCompanyId(int id);
	DataResult<List<JobAdvertisement>> getAllByCompanyName(String companyName);
	DataResult<List<JobAdvertisement>> getAllByActivationStatusFalse();
	DataResult<JobAdvertisement> getById(int id);
	DataResult<Page<JobAdvertisement>> getActiveJobsByPage(int pageNo, int pageSize);
	DataResult<List<JobAdvertisement>> getAllByFilteredAndPaged(int pageNo, int pageSize, JobAdvertFilterOption filterOption);
	
	Result add(JobAdvertisementDto jobAdvertisementDto);
	Result delete(int id);
	Result activate(int id, boolean activationStatus);
	
}
