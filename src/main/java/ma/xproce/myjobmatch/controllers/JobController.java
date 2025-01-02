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
    // edit ( state == created ) --> diri hna service lahfdek o hydi dik rwina 🚩
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
    public ResponseEntity<JobDto> getJobById(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        Job job = jobRepository.findByIdAndRh(id, rh);  // assuming there's a method in JobRepository to find job by ID and RH
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        JobDto jobDto = new JobDto(job);
        return new ResponseEntity<>(jobDto, HttpStatus.OK);
    }



    // Update an existing job
    @PutMapping("/update/{id}")
    public ResponseEntity<JobDto> updateJob(@PathVariable Long id, @RequestBody Job job, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        Job existingJob = jobRepository.findByIdAndRh(id, rh); // Find the job by ID and RH
        if (existingJob == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!"Created".equals(existingJob.getStatus())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Only allow update if status is "Created"
        }

        // Update job fields as needed
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

        Job updatedJob = jobRepository.save(existingJob); // Save the updated job
        JobDto jobDto = new JobDto(updatedJob);
        return new ResponseEntity<>(jobDto, HttpStatus.OK);
    }
    @PutMapping(value = "/post/{id}")
    public ResponseEntity<JobDto> postJob(@PathVariable Long id, Authentication authentication) {
      //  try {
            // Retrieve the authenticated RH from the current session
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            RH rh = customUserDetails.getRh();

            // Retrieve the job by its ID
            Job job = jobRepository.findByIdAndRh(id, rh); // Find the job by ID and RH
            if (job == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }


            // Check if the job is in "Created" status before changing it to "Posted"
            if ("Created".equals(job.getStatus())) {
                // Change the status to "Posted"
                job.setStatus("Posted");

                // Save the updated job to the database
                Job updatedJob = jobRepository.save(job);

                // Convert the updated job to JobDto
                JobDto jobDTO = new JobDto(updatedJob);

                return new ResponseEntity<>(jobDTO, HttpStatus.OK);
            } else {
                // Return a bad request if the job status is not "Created"
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
   //     } catch (Exception e) {
   //         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
   //     }
    }

    // Delete a job
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteJob(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        RH rh = customUserDetails.getRh();

        Job job = jobRepository.findByIdAndRh(id, rh); // Find the job by ID and RH
        if (job == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        jobRepository.delete(job); // Delete the job
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
