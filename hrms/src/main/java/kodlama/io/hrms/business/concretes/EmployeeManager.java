package kodlama.io.hrms.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import kodlama.io.hrms.business.abstracts.EmployeeService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.EmployeeDao;
import kodlama.io.hrms.entities.concretes.Candidate;
import kodlama.io.hrms.entities.concretes.CandidateCv;
import kodlama.io.hrms.entities.concretes.Employee;

@Service
public class EmployeeManager implements EmployeeService {
	
	private EmployeeDao employeeDao;

	public EmployeeManager(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	@Override
	public DataResult<List<Employee>> getAll() {
		return new SuccessDataResult<List<Employee>>(this.employeeDao.findAll(), "Employees are listed!");
		
	}

	@Override
	public DataResult<Employee> add(Employee employee) {
		this.employeeDao.save(employee);
		return new SuccessDataResult<Employee>("Employee is added to system.");
		
	}

	@Override
	public DataResult<Employee> update(Employee employee) {
		Employee tempEmployee = this.employeeDao.findById(employee.getId());
		
		if (employee.getFirstName() != null) {
			tempEmployee.setFirstName(employee.getFirstName());
		}
		
		if (employee.getLastName() != null) {
			tempEmployee.setLastName(employee.getLastName());
		}
		
		if (employee.getEmail() != null) {
			tempEmployee.setEmail(employee.getEmail());
		}
		
		if (employee.getPassword() != null) {
			tempEmployee.setPassword(employee.getPassword());
		}
		
		return new SuccessDataResult<Employee>
		(this.employeeDao.save(tempEmployee), "Employee is updated!");
	}

	@Override
	public DataResult<Employee> delete(Employee employee) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<Employee> getById(int id) {
		if (!this.employeeDao.existsById(id)) {
			return new ErrorDataResult<Employee>("Employe could not be found!");
		}
		
		return new SuccessDataResult<Employee> (this.employeeDao.findById(id), "Employee is found!");
	}
	
}
