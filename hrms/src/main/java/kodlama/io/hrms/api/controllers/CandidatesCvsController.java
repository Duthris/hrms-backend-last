package kodlama.io.hrms.api.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kodlama.io.hrms.business.abstracts.CandidateCvService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.CandidateCv;

@RestController
@RequestMapping("/api/candidatesCvs")
@CrossOrigin
public class CandidatesCvsController {
	
	private CandidateCvService candidateCvService;

	@Autowired
	public CandidatesCvsController(CandidateCvService candidateCvService) {
		this.candidateCvService = candidateCvService;
	}
	
	@GetMapping("/getAll")
	public DataResult<List<CandidateCv>> getAll() {
		return this.candidateCvService.getAll();
	}
	
	@PostMapping("/addCvAvatar")
	public Result uploadCvAvatar(int id, MultipartFile multipartFile) throws IOException {
		return this.candidateCvService.uploadCvAvatar(id, multipartFile);
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody CandidateCv candidateCv) {
		return this.candidateCvService.add(candidateCv);
	}
	
	@GetMapping("/getByCandidateId")
	public DataResult<CandidateCv> getByCandidateId(@RequestParam int id) {
		return this.candidateCvService.getByCandidateId(id);
	}
	
	@PutMapping("/update")
	public DataResult<CandidateCv> update(@RequestBody CandidateCv candidateCv) {
		return this.candidateCvService.update(candidateCv);
	}
	
	@GetMapping("/getById")
	public DataResult<CandidateCv> getByCvId(int id) {
		return this.candidateCvService.getByCvId(id);
	}
	
	@PutMapping("/updateGithub")
	public Result updateGithub(@RequestParam String githubLink, @RequestParam int candidateId) {
		return this.candidateCvService.updateGithub(githubLink, candidateId);
	}
	
	@PutMapping("/deleteGithub")
	public Result deleteGithub(@RequestParam int candidateId) {
		return this.candidateCvService.deleteGithub(candidateId);
	}
	
	@PutMapping("/updateLinkedin")
	public Result updateLinkedin(@RequestParam String linkedinLink, @RequestParam int candidateId) {
		return this.candidateCvService.updateLinkedin(linkedinLink, candidateId);
	}
	
	@PutMapping("/deleteLinkedin")
	public Result deleteLinkedin(@RequestParam int candidateId) {
		return this.candidateCvService.deleteLinkedin(candidateId);
	}
	
	@PutMapping("/updateCoverLetter")
	public Result updateCoverLetter(@RequestParam String coverLetter, @RequestParam int candidateId) {
		return this.candidateCvService.updateCoverLetter(coverLetter, candidateId);
	}
	
	@PostMapping("/deleteCvAvatar")
	public Result deleteCvAvatar(int candidateId) {
		return this.candidateCvService.deleteCvAvatar(candidateId);
	}
}
