package ma.xproce.myjobmatch.services;


import ma.xproce.myjobmatch.dao.entities.Application;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;


  //   Create Application (Candidate applies for a job)
    public Application createApplication(Candidate candidate, Job job) {

        // If an existing application is found, throw an exception or return a specific result
        Application existingApplication = applicationRepository.findByCandidateAndJob(candidate, job);
        if (existingApplication != null) {
            System.out.println("💗💗💗💗💗💗");// just for testing
            throw new IllegalArgumentException("You have already applied to this job.");
        }
        Application application = new Application();
        application.setCandidate(candidate);
        application.setJob(job);
        application.setStatus("Pending");
        application.setApplicationDate(new java.util.Date());
        application.setCreatedAt(new java.util.Date());
        application.setEditedAt(new java.util.Date());

        // Save and return the application
        return applicationRepository.save(application);
    }

    // Get Applications by Candidate ID
    public List<Application> getApplicationsByCandidate(Candidate candidate) {
        return applicationRepository.findByCandidate(candidate);
    }

    // Get Applications by Job ID
    public List<Application> getApplicationsByJob(Long jobId) {
        return applicationRepository.findByJobId(jobId);
    }
}

