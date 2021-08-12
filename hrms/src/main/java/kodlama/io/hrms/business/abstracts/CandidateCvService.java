package kodlama.io.hrms.business.abstracts;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.CandidateCv;
import kodlama.io.hrms.entities.concretes.CandidateSchool;

public interface CandidateCvService {
	
	DataResult<List<CandidateCv>> getAll();
	
	Result add(CandidateCv candidateCv);
	
	Result uploadCvAvatar(int id, MultipartFile multipartFile) throws IOException;
	
	DataResult<CandidateCv> getByCandidateId(int id);
	
	DataResult<CandidateCv> update(CandidateCv candidateCv);
	
	DataResult<CandidateCv> getByCvId(int id);
	
	Result updateGithub(String githubLink, int candidateId);
	Result deleteGithub(int candidateId);
	
	Result updateLinkedin(String linkedinLink, int candidateId);
	Result deleteLinkedin(int candidateId);
	
	Result updateCoverLetter(String coverLetter, int candidateId);
	
	Result deleteCvAvatar(int id);
	
	Result createEmptyCvAfterRegister(int candidateId);
}
