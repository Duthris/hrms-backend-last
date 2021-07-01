package kodlama.io.hrms.business.abstracts;

import java.util.List;


import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.Employer;
import kodlama.io.hrms.entities.concretes.EmployerUpdate;

public interface EmployerService {
	DataResult<List<Employer>> getAll();
	Result register(Employer employer);
	DataResult<Employer> getById(int id);
	
	Result update(EmployerUpdate employerUpdate);
	Result verifyUpdate(int employerUpdateId, int employeeId);
}
