package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.entities.RH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
    List<Job> findByRh(RH rh);
    Job findByIdAndRh(Long id, RH rh);

    Job getJobById(Long id);
}
