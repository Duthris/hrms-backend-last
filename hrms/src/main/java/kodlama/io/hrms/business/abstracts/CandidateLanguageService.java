package kodlama.io.hrms.business.abstracts;

import java.util.List;

import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.CandidateLanguage;
import kodlama.io.hrms.entities.concretes.Language;
import kodlama.io.hrms.entities.dtos.LanguageAddDto;

public interface CandidateLanguageService {
	
	DataResult<List<CandidateLanguage>> getAll();
	
	DataResult<List<CandidateLanguage>> getByCandidateCvId(int id);
	
	Result addLanguageToCv(Language language, int candidateId);
	
	Result add(LanguageAddDto languageAddDto);
	
	Result delete(int languageId);
	
	DataResult<List<CandidateLanguage>> getByCvId(int cvId);
}
