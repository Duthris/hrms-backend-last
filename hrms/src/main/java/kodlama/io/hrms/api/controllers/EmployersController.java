package kodlama.io.hrms.api.controllers;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kodlama.io.hrms.business.abstracts.EmployerService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.Employer;
import kodlama.io.hrms.entities.concretes.EmployerUpdate;

@RestController
@RequestMapping("/api/employers")
@CrossOrigin
public class EmployersController {
	
	private EmployerService employerService;

	@Autowired
	public EmployersController(EmployerService employerService) {
		this.employerService = employerService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<Employer>> getAll() {
		return this.employerService.getAll();
	}
	
	@PostMapping("/register")
	public Result register(@RequestBody Employer employer) {
		return this.employerService.register(employer);
	}	
	
	@GetMapping("/getById")
	public DataResult<Employer> getById(int id) {
		return this.employerService.getById(id);
	}
	
	@PostMapping("/update")
	public Result update(@RequestBody EmployerUpdate employerUpdate) {
		return this.employerService.update(employerUpdate);
	}
	
	@PostMapping("/verifyUpdate")
	public Result verifyUpdate(int employerUpdateId, int employeeId) {
		return this.employerService.verifyUpdate(employerUpdateId, employeeId);
	}

}
