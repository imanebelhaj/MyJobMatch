package ma.xproce.myjobmatch.services;

import jakarta.transaction.Transactional;
import ma.xproce.myjobmatch.dao.entities.Candidate;
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
import java.util.stream.Collectors;

@Service
@Transactional
public class JobService {

    @Autowired
    private JobRepository jobRepository;

    @Value("${flask.api.url}") //@Value("${flask.api.url:http://default-value/api}")
    private String flaskApiUrl;
    private final RestTemplate restTemplate;

    public JobService(RestTemplate restTemplate, JobRepository jobRepository) {
        this.restTemplate = restTemplate;
        this.jobRepository = jobRepository;
    }

    public List<Job> getMatchedJobs(String resumeText) {
        String requestBody = "{\"resume_text\": \"" + resumeText + "\"}";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            // Logging the request
            System.out.println("Sending request to Flask API: " + flaskApiUrl + "/match-jobs");

            // Call Flask API
            ResponseEntity<JobMatchResponse> response = restTemplate.exchange(
                    flaskApiUrl + "/match-jobs",
                    HttpMethod.POST,
                    entity,
                    JobMatchResponse.class
            );

            // Log the response
            System.out.println("Flask API response: " + response.getBody());

            List<String> matchedJobIdsAsStrings = response.getBody().getMatchedJobs();
            List<Long> matchedJobIds = matchedJobIdsAsStrings.stream()
                    .map(Long::valueOf)
                    .collect(Collectors.toList());

            return jobRepository.findByIdIn(matchedJobIds);
        } catch (Exception e) {
            System.out.println("Error calling Flask API: " + e.getMessage());
            throw new RuntimeException("Error calling Flask API", e);
        }
    }


//    public List<Job> getMatchedJobs(String resumeText) {
//        // Prepare request body for Flask API
//        String requestBody = "{\"resume_text\": \"" + resumeText + "\"}";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Content-Type", "application/json");
//        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
//
//        // Call Flask API
//        ResponseEntity<JobMatchResponse> response = restTemplate.exchange(
//                flaskApiUrl + "/match-jobs",
//                HttpMethod.POST,
//                entity,
//                JobMatchResponse.class
//        );
//
//        // Get matched job IDs from Flask response
//        List<String> matchedJobIdsAsStrings = response.getBody().getMatchedJobs();
//
//        // Convert String IDs to Long IDs
//        List<Long> matchedJobIds = matchedJobIdsAsStrings.stream()
//                .map(Long::valueOf)
//                .toList();
//
//        // Fetch job details from the database
//        return jobRepository.findByIdIn(matchedJobIds);
//    }




    public Job createJob(Job job) {
        job.setCreatedAt(new Date());
        job.setEditedAt(new Date());
        job.setPostedAt(new Date());
        job.setStatus("Created"); // Default status
        updateJobForm(job);

        return jobRepository.save(job);
    }
    private void updateJobForm(Job job) {
        StringBuilder jobBuilder = new StringBuilder();
        jobBuilder.append("Category: ").append(job.getCategory()).append("\n");
        jobBuilder.append("Title: ").append(job.getTitle()).append("\n");
        jobBuilder.append("Description: ").append(job.getDescription()).append("\n");
        jobBuilder.append("Required Experience: ").append(job.getRequiredExperience()).append("\n");
        jobBuilder.append("Required Education: ").append(job.getRequiredEducation()).append("\n");
        jobBuilder.append("Required Skills: ").append(job.getRequiredSkills()).append("\n");
        jobBuilder.append("Job Level: ").append(job.getJobLevel()).append("\n");
        job.setJobForm(jobBuilder.toString());
    }



    public Job getJobById(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));
    }

    public List<Job> getAllJobs(){
        List<Job> jobs = jobRepository.findAll();
        return jobs;
    }



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

        updateJobForm(job);

        // Save the updated job
        return jobRepository.save(existingJob);
    }
}
