package kodlama.io.hrms.business.concretes;

import java.util.regex.*;
import java.time.LocalDate;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import kodlama.io.hrms.business.abstracts.EmployerService;

import kodlama.io.hrms.business.abstracts.UserService;
import kodlama.io.hrms.core.utilities.helpers.email.EMailService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.ErrorResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.EmployeeDao;
import kodlama.io.hrms.dataAccess.abstracts.EmployerDao;
import kodlama.io.hrms.dataAccess.abstracts.EmployerUpdateDao;
import kodlama.io.hrms.dataAccess.abstracts.UserDao;
import kodlama.io.hrms.entities.concretes.Employer;
import kodlama.io.hrms.entities.concretes.EmployerUpdate;

@Service
public class EmployerManager implements EmployerService {
	
	private EmployerDao employerDao;
	private UserService userService;
	private UserDao userDao;
	private EMailService eMailService;
	private EmployerUpdateDao employerUpdateDao;
	private EmployeeDao employeeDao;

	
	@Autowired
	public EmployerManager(EmployerDao employerDao, UserService userService, UserDao userDao, 
			EMailService eMailService, EmployerUpdateDao employerUpdateDao, EmployeeDao employeeDao) {
		this.employerDao = employerDao;
		this.userService = userService;
		this.userDao = userDao;	
		this.eMailService = eMailService;
		this.employerUpdateDao = employerUpdateDao;
		this.employeeDao = employeeDao;

	}

	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(this.employerDao.findAll(), "Employers are listed!");
	}

	@Override
	public Result register(Employer employer) {
		if (!isAllFieldsFilled(employer)) {
			return new ErrorResult("All fields must be filled!");
		}
		
		if (!isMailExist(employer)) {
			return new ErrorResult("E-mail is already exist!");
		}
		
		if (!isWebSiteValid(employer)) {
			return new ErrorResult("Website is invalid!");
		}
		
		if (!isSameFormat(employer)) {
			return new ErrorResult("Your mail must be a company mail!");
		}
		
		if (!isValidPhoneNumber(employer)) {
			return new ErrorResult("Invalid Phone Number!");
		}
		
		else {		
			employerDao.save(employer);
			eMailService.sendEMail("Verification E-Mail Sent!");
			return new SuccessResult("Employer is succesfully registered. Please check your E-Mail to verify your account.");
		}
	}
	
	private boolean isAllFieldsFilled(Employer employer) {
		if (employer.getCompanyName().length() == 0 || employer.getEmail().length() == 0 || employer.getPassword().length() == 0 ||
			employer.getPhoneNumber().length() == 0 || employer.getWebSite().length() == 0) 
		{
			return false;
		}
		
		return true;
	}
	
	private boolean isWebSiteValid(Employer employer) {
		Pattern webSitePattern = Pattern.compile(("^(\\/\\/)?(www\\.)?([\\w]+\\.)+[\u200C\u200B\\w]{2,63}\\/?$"));
		Matcher webSiteMatcher = webSitePattern.matcher(employer.getWebSite());
		return webSiteMatcher.matches();
	}
	
	private boolean isSameFormat(Employer employer) {
		String domain = employer.getEmail().substring(employer.getEmail().indexOf("@")+1);
		return employer.getWebSite().contains(domain);
	}
	
	private boolean isMailExist(Employer employer) {
		if(employerDao.findAllByEmail(employer.getEmail()).stream().count() == 0) {
			return true;
		}
		
		return false;
	}
	
	private boolean isValidPhoneNumber(Employer employer) {
		String phonePattern = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$" 
							  + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$" 
	                          + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
	
		Pattern pattern = Pattern.compile(phonePattern);
		
		Matcher matcher = pattern.matcher(employer.getPhoneNumber());
		if(matcher.matches()) {
			return true;
		}
		return false; 
	}
	
	  private final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+.(com|org|net|edu|gov|mil|biz|info|mobi)(.[A-Z]{2})?$";

	    public boolean isEmailValid(String emailInput) {
	        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
	        return pattern.matcher(emailInput).find();
	    }

	@Override
	public DataResult<Employer> getById(int id) {
		if (!this.employerDao.existsById(id)) {
			return new ErrorDataResult<Employer>("Invalid Employer Id!");
		}
		
		return new SuccessDataResult<Employer>(this.employerDao.getOne(id), "Searched employer is found!");
	}

	@Override
	public Result update(EmployerUpdate employerUpdate) {
		employerUpdate.setId(0);
		employerUpdate.setCreatedDate(LocalDate.now());
		employerUpdate.setEmployeeId(null);
		
		if(employerUpdate.getCompanyName().length()<1) {
			return new ErrorResult("Company name must be longer than 2 character!");
		}
		
		else if (employerUpdate.getPhoneNumber().length() != 11) {
			return new ErrorResult("Invalid phone number!");
		}
		
		else if (!isEmailValid(employerUpdate.getEmail())) {
			return new ErrorResult("Invalid E-mail format!");
		}
		
		else if (!this.employerDao.existsById(employerUpdate.getEmployerId())) {
			return new ErrorResult("Invalid Employer Id");
		}
		
		Employer employer = this.employerDao.getOne(employerUpdate.getEmployerId());
		this.employerUpdateDao.save(employerUpdate);
		
		employer.setWaitingUpdate(true);
		this.employerDao.save(employer);
		
		return new SuccessResult("Updation request is successfully sent! Waiting for confirmation to make changes on.");
	}

	@Override
	public Result verifyUpdate(int employerUpdateId, int employeeId) {
		if (!this.employerUpdateDao.existsById(employerUpdateId)) {
			return new ErrorResult("Inavlid updation request!");
		}
		
		else if (!this.employeeDao.existsById(employeeId)) {
			return new ErrorResult("Invalid Employee Id!");
		}
		
		EmployerUpdate employerUpdate = this.employerUpdateDao.getOne(employerUpdateId);
		Employer employer = this.employerDao.getOne(employerUpdate.getEmployerId());
		employerUpdate.setVerified(true);
		employerUpdate.setEmployeeId(employeeId);
		employerUpdate.setVerifyDate(LocalDate.now());
		this.employerUpdateDao.save(employerUpdate);
		
		employer.setEmail(employerUpdate.getEmail());
		employer.setCompanyName(employer.getCompanyName());
		employer.setPhoneNumber(employerUpdate.getPhoneNumber());
		employer.setWebSite(employerUpdate.getWebSite());
		employer.setWaitingUpdate(false);
		this.employerDao.save(employer);
		
		return new SuccessResult("Employer infos are successfully Updated!");
	}
	
}
