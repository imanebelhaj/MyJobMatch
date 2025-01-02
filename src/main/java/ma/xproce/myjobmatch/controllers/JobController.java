package ma.xproce.myjobmatch.controllers;

import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.JobRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dto.JobDto;
import ma.xproce.myjobmatch.services.JobService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rh/jobs")
public class JobController {
    //get all created but this specific rh
    //add
    // edit ( state == created )
    // delete
    //job details
    //applications per job ( state == inactive )

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private JobService jobService;

    @Autowired
    RHRepository rhRepository;

    // Create a new job

    //@PostMapping("/add") //<Job>
    @PostMapping( value= "/add", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobDto> createJob(@RequestBody Job job, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();
                //.orElseThrow(() -> new RuntimeException("RH not found"));
        job.setRh(rh);
        try {
            Job savedJob = jobService.createJob(job);
            //Job savedJob = jobRepository.save(job);
            JobDto jobDTO = new JobDto(savedJob);
            System.out.println(savedJob); // Log the saved job object
            System.out.println("💗💗💗💗💗💗");
            //return new ResponseEntity<>(savedJob, HttpStatus.CREATED);
            return new ResponseEntity<>(jobDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<JobDto>> getAllJobs(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        List<Job> jobs = jobRepository.findByRh(rh);  // assuming there's a method in JobRepository to find jobs by RH
        if (jobs.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        List<JobDto> jobDtos = jobs.stream()
                .map(JobDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(jobDtos, HttpStatus.OK);
    }

    // Get job by ID
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable("id") Long id) {
        Optional<Job> job = jobRepository.findById(id);
        if (job.isPresent()) {
            return new ResponseEntity<>(job.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Job not found
        }
    }



    // Update an existing job
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable("id") Long id, @RequestBody Job jobDetails) {
        Optional<Job> existingJob = jobRepository.findById(id);
        if (existingJob.isPresent()) {
            Job job = existingJob.get();
            job.setTitle(jobDetails.getTitle());
            job.setDescription(jobDetails.getDescription());
            job.setLocation(jobDetails.getLocation());
            // Add other fields to update

            Job updatedJob = jobRepository.save(job);
            return new ResponseEntity<>(updatedJob, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Job not found
        }
    }

    // Delete a job
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable("id") Long id) {
        try {
            jobRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Job deleted
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Error deleting job
        }
    }
}
