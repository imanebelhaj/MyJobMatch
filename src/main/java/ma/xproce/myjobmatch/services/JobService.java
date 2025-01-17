package ma.xproce.myjobmatch.services;

import jakarta.transaction.Transactional;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.JobRepository;
import ma.xproce.myjobmatch.dto.JobDto;
import ma.xproce.myjobmatch.dto.JobMatchResponse;
import ma.xproce.myjobmatch.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class JobService {


    @Value("${flask.api.url}")
    private String flaskApiUrl;

    private final RestTemplate restTemplate;

    public JobService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    private JobRepository jobRepository;

    public List<String> getMatchedJobs(String resumeText) {
        // Construct request body as JSON
        String requestBody = "{\"resume_text\": \"" + resumeText + "\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Make a POST request to Flask API to get matched jobs
        ResponseEntity<JobMatchResponse> response = restTemplate.exchange(
                flaskApiUrl + "/match-jobs",
                HttpMethod.POST,
                entity,
                JobMatchResponse.class
        );

        return response.getBody().getMatchedJobs();
    }

    public Job createJob(Job job) {
        job.setCreatedAt(new Date());
        job.setEditedAt(new Date());
        job.setPostedAt(new Date());
        job.setStatus("Created"); // Default status
        return jobRepository.save(job);
    }
    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));
    }

    public List<Job> getAllJobs(){
        List<Job> jobs = jobRepository.findAll();
        return jobs;
    }

//    public JobDto getJobById(Long id){
//        Job job = jobRepository.getJobById(id);
//
//        JobDto jobDto = new JobDto();
//        jobDto.setId(job.getId());
//        jobDto.setTitle(job.getTitle());
//        jobDto.setCategory(job.getCategory());
//        jobDto.setDescription(job.getDescription());
//        jobDto.setLocation(job.getLocation());
//        jobDto.setStatus(job.getStatus());
//        jobDto.setJobType(job.getJobType());
//        jobDto.setSalaryRange(job.getSalaryRange());
//        jobDto.setRequiredEducation(job.getRequiredEducation());
//        jobDto.setRequiredExperience(job.getRequiredExperience());
//        jobDto.setJobLevel(job.getJobLevel());
//
//        return jobDto;
//
//    }

    public Job updateJobByRh(Long id, Job job, RH rh) throws ResourceNotFoundException, IllegalAccessException {
        // Find the job by ID and RH
        Job existingJob = jobRepository.findByIdAndRh(id, rh);
        if (existingJob == null) {
            throw new ResourceNotFoundException("Job not found for the provided RH");
        }

        // Check if the job's status is "Created"
        if (!"Created".equals(existingJob.getStatus())) {
            throw new IllegalAccessException("Only jobs with status 'Created' can be updated");
        }

        // Update job fields
        existingJob.setTitle(job.getTitle());
        existingJob.setCategory(job.getCategory());
        existingJob.setDescription(job.getDescription());
        existingJob.setLocation(job.getLocation());
        existingJob.setApplicationDeadline(job.getApplicationDeadline());
        existingJob.setMaxApplications(job.getMaxApplications());
        existingJob.setJobType(job.getJobType());
        existingJob.setSalaryRange(job.getSalaryRange());
        existingJob.setRequiredEducation(job.getRequiredEducation());
        existingJob.setRequiredExperience(job.getRequiredExperience());
        existingJob.setJobLevel(job.getJobLevel());
        existingJob.setRequiredSkills(job.getRequiredSkills());
        existingJob.setStatus(job.getStatus());

        // Save the updated job
        return jobRepository.save(existingJob);
    }
}
