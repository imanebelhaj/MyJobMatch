package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.Application;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByCandidate(Candidate candidate);
    Application findByCandidateAndJob(Candidate candidate, Job job);

    List<Application> findByJobId(Long jobId);
}
