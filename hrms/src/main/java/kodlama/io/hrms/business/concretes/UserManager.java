package kodlama.io.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlama.io.hrms.business.abstracts.UserService;
import kodlama.io.hrms.core.utilities.helpers.email.EMailService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.ErrorDataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.core.utilities.results.SuccessDataResult;
import kodlama.io.hrms.core.utilities.results.SuccessResult;
import kodlama.io.hrms.dataAccess.abstracts.CandidateDao;
import kodlama.io.hrms.dataAccess.abstracts.EmployeeDao;
import kodlama.io.hrms.dataAccess.abstracts.EmployerDao;
import kodlama.io.hrms.dataAccess.abstracts.UserDao;
import kodlama.io.hrms.entities.concretes.Candidate;
import kodlama.io.hrms.entities.concretes.User;
import kodlama.io.hrms.entities.dtos.UserLoginDto;
import kodlama.io.hrms.entities.dtos.UserLoginReturnDto;

@Service
public class UserManager implements UserService {
	
	private UserDao userDao;
	private CandidateDao candidateDao;
	private EmployerDao employerDao;
	private EmployeeDao employeeDao;


	@Autowired
	public UserManager(UserDao userDao, EmployeeDao employeeDao, EmployerDao employerDao, CandidateDao candidateDao) {
		this.userDao = userDao;
		this.employeeDao = employeeDao;
		this.employerDao = employerDao;
		this.candidateDao = candidateDao;
	}

	@Override
	public DataResult<List<User>> getAll() {
		return new SuccessDataResult<List<User>>(this.userDao.findAll(), "Users are listed!");
	}

	@Override
	public User add(User user) {
		return userDao.save(user);
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User delete(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<UserLoginReturnDto> login(UserLoginDto userLoginDto) {
		User user = this.userDao.findByEmail(userLoginDto.getEmail());
		
		if (user == null) {
			return new ErrorDataResult<UserLoginReturnDto>("Invalid E-mail!");
		}
		
		else if (!user.getPassword().equals(userLoginDto.getPassword())){
			return new ErrorDataResult<UserLoginReturnDto>("Wrong Password is Entered!");
		}
		
		UserLoginReturnDto userLoginReturnDto = new UserLoginReturnDto();
		userLoginReturnDto.setId(user.getId());
		userLoginReturnDto.setEmail(user.getEmail());
		
		if (this.candidateDao.existsById(user.getId())) {
			userLoginReturnDto.setUserType(1);
			userLoginReturnDto.setName(this.candidateDao.getById(user.getId()).getFirstName() + " " + 
			this.candidateDao.getById(user.getId()).getLastName());
		}
		
		else if (this.employerDao.existsById(user.getId())) {
			userLoginReturnDto.setUserType(2);
			userLoginReturnDto.setName(this.employerDao.getOne(user.getId()).getCompanyName());
		}
		
		else if (this.employeeDao.existsById(user.getId())) {
			userLoginReturnDto.setUserType(3);
			userLoginReturnDto.setName(this.employeeDao.getOne(user.getId()).getFirstName() + " " + 
			this.employeeDao.getOne(user.getId()).getLastName());
		}
		
		else {
			return new ErrorDataResult<UserLoginReturnDto>("Something went wrong!");
		}
		
		return new SuccessDataResult<UserLoginReturnDto>(userLoginReturnDto, "Successfully logined!");
	}

}
