package kodlama.io.hrms.business.abstracts;

import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.User;
import kodlama.io.hrms.entities.dtos.UserLoginDto;
import kodlama.io.hrms.entities.dtos.UserLoginReturnDto;

public interface UserService {
	DataResult<List<User>> getAll();
	User add(User user);
	User update(User user);
	User delete(User user);
	
	DataResult<UserLoginReturnDto> login(UserLoginDto userLoginDto);

}
