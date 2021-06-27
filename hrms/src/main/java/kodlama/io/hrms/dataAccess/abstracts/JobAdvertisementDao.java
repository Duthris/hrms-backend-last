package kodlama.io.hrms.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kodlama.io.hrms.entities.concretes.JobAdvertisement;
import kodlama.io.hrms.entities.dtos.JobAdvertFilterOption;

public interface JobAdvertisementDao extends JpaRepository<JobAdvertisement, Integer>{
	List<JobAdvertisement> getAllByActivationStatusTrue();
	List<JobAdvertisement> getAllByActivationStatusTrue(Sort sort);
	
	@Query("From JobAdvertisement where activationStatus = true and employer.id = :id order by expiration_date asc")
	List<JobAdvertisement> getAllByEmployerId(int id);
	
	List<JobAdvertisement> getAllByEmployer_CompanyName(String companyName);
	
	@Query("From JobAdvertisement where activationStatus = false order by expiration_date asc")
	List<JobAdvertisement> getAllByActivationStatusFalse();
	
	@Query(value="Select * From job_advertisements where is_active = true ",
			countQuery="Select count(*) From job_advertisements where is_active = true", 
			nativeQuery=true)
	Page<JobAdvertisement>getActiveJobsByPage(Pageable pageable);
	
	@Query("Select j from JobAdvertisement j where activationStatus = true and ((:#{#filter.cityId}) IS NULL OR j.city.id IN (:#{#filter.cityId}))"
			+ "and ((:#{#filter.jobPositionId}) IS NULL OR j.jobPosition.id IN (:#{#filter.jobPositionId}))"
			+ "and ((:#{#filter.workingMethodId}) IS NULL OR j.workingMethod.id IN (:#{#filter.workingMethodId}))"
			+ "and ((:#{#filter.workingTimeId}) IS NULL OR j.workingTime.id IN (:#{#filter.workingTimeId}))")
	public Page<JobAdvertisement> getFilteringAndPage(@Param("filter") JobAdvertFilterOption filterOption, Pageable pageable);
	
}
