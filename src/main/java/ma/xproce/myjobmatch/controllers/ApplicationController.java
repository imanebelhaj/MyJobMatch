package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.Application;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dto.ApplicationDto;
import ma.xproce.myjobmatch.dto.JobDto;
import ma.xproce.myjobmatch.services.ApplicationService;
import ma.xproce.myjobmatch.services.CandidateService;
import ma.xproce.myjobmatch.services.JobService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {


    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private JobService jobService;


    @PostMapping("/candidate/apply/{jobId}")
    @PreAuthorize("hasAuthority('CANDIDATE')")
    public ResponseEntity<ApplicationDto> applyForJob(@PathVariable Long jobId,Authentication authentication) {
        // Get the authenticated candidate
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Candidate candidate = customUserDetails.getCandidate();
        Job job = jobService.getJobById(jobId);
            try {
            Application savedApplication = applicationService.createApplication(candidate, job);
            ApplicationDto applicationDto = new ApplicationDto(savedApplication);
             return ResponseEntity.ok(applicationDto);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // 2. Get Applications by Candidate ID
    @GetMapping("/candidate")
    @PreAuthorize("hasAuthority('CANDIDATE')")
    @Transactional
    public ResponseEntity<List<ApplicationDto>> getApplicationsByCandidate(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Candidate candidate = customUserDetails.getCandidate();
        List<Application> applications = applicationService.getApplicationsByCandidate(candidate);
        //List<Application> applications = candidate.getApplications();

        List<ApplicationDto> ApplicationDto = applications.stream()
                .map(ma.xproce.myjobmatch.dto.ApplicationDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApplicationDto);
    }

    // 3. Get Applications by Job ID
    @GetMapping("/rh/{jobId}")
    @PreAuthorize("hasAuthority('RH')")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByJob(@PathVariable Long jobId,Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        List<Application> applications = applicationService.getApplicationsByJob(jobId);

        List<ApplicationDto> ApplicationDto = applications.stream()
                .map(ma.xproce.myjobmatch.dto.ApplicationDto::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApplicationDto);
    }



}
