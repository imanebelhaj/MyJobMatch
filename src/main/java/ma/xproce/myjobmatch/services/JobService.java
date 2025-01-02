package ma.xproce.myjobmatch.services;

import jakarta.transaction.Transactional;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Transactional
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    public Job createJob(Job job) {
        job.setCreatedAt(new Date());
        job.setEditedAt(new Date());
        job.setPostedAt(new Date());
        job.setStatus("Created"); // Default status
        return jobRepository.save(job);
    }
}
