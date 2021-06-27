package kodlama.io.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import kodlama.io.hrms.entities.concretes.JobAdvertisementFavorite;

public interface JobAdvertisementFavoriteDao extends JpaRepository<JobAdvertisementFavorite, Integer> {
	List<JobAdvertisementFavorite> findByCandidateId(int id);
}
