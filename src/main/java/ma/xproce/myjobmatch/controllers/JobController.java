package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.Application;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.repositories.JobRepository;
import ma.xproce.myjobmatch.dto.ApplicationDto;
import ma.xproce.myjobmatch.dto.JobDto;
import ma.xproce.myjobmatch.services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobService jobService;

    //get all job
    //get jobs by candidate filtering (call model)
    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs() {

        List<Job> jobs = jobService.getAllJobs();

        List<JobDto> JobDto = jobs.stream()
                .map(ma.xproce.myjobmatch.dto.JobDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(JobDto);
    }
}
