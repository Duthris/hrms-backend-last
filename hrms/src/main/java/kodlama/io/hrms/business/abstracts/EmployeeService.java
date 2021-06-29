package kodlama.io.hrms.business.abstracts;

import java.util.List;


import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.entities.concretes.Employee;

public interface EmployeeService {
	DataResult<List<Employee>> getAll();
	DataResult<Employee> add(Employee employee);
	DataResult<Employee> update(Employee employee);
	DataResult<Employee> delete(Employee employee);
	DataResult<Employee> getById(int id);
}
