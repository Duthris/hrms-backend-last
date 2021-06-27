package kodlama.io.hrms.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kodlama.io.hrms.business.abstracts.JobAdvertisementFavoriteService;
import kodlama.io.hrms.core.utilities.results.DataResult;
import kodlama.io.hrms.core.utilities.results.Result;
import kodlama.io.hrms.entities.concretes.JobAdvertisementFavorite;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin
public class JobAdvertisementFavoriteController {
	
	private JobAdvertisementFavoriteService jobAdvertisementFavoriteService;

	@Autowired
	public JobAdvertisementFavoriteController(JobAdvertisementFavoriteService jobAdvertisementFavoriteService) {
		super();
		this.jobAdvertisementFavoriteService = jobAdvertisementFavoriteService;
	}
	
	@GetMapping("/getByCandidateId")
	public DataResult<List<JobAdvertisementFavorite>> getByCandidateId(@RequestParam int candidateId) {
		return this.jobAdvertisementFavoriteService.getByCandidateId(candidateId);
	}
	
	@PostMapping("/add")
	public Result add(@RequestParam int candidateId, @RequestParam int jobAdvertisementId) {
		return this.jobAdvertisementFavoriteService.add(candidateId, jobAdvertisementId);
	}
	
	@PostMapping("/delete")
	public Result delete(@RequestParam int id) {
		return this.jobAdvertisementFavoriteService.delete(id);
	}

}
