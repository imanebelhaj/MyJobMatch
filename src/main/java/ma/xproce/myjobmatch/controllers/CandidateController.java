package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.Resume;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.ResumeRepository;
import ma.xproce.myjobmatch.dto.CandidateProfileDto;
import ma.xproce.myjobmatch.dto.RhProfileDto;
import ma.xproce.myjobmatch.services.CandidateService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/candidate")
@PreAuthorize("hasAuthority('CANDIDATE')")
public class CandidateController {

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    ResumeRepository resumeRepository;
    @Autowired
    CandidateService candidateService;

    //form
    //job list (all jobs)
    //job list (filtered based on profile)-> model applied
    //loading page : when cv screening + when job matching model applied

    @PutMapping("/complete-profile")
    public ResponseEntity<String> completeProfile(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Candidate candidate = customUserDetails.getCandidate();

        // Setting Profile Fields
        candidate.setFullName(candidateProfileDto.getFullName());
        candidate.setPhone(candidateProfileDto.getPhone());
        candidate.setLinkedinUrl(candidateProfileDto.getLinkedinUrl());
        candidate.setWebsite(candidateProfileDto.getWebsite());
        candidate.setLocation(candidateProfileDto.getLocation());
        candidate.setCategory(candidateProfileDto.getCategory());
        candidate.setJobType(candidateProfileDto.getJobType());
        candidate.setResumeUrl(candidateProfileDto.getResumeUrl());
        candidate.setCoverLetterUrl(candidateProfileDto.getCoverLetterUrl());
        candidate.setStatus(candidateProfileDto.getStatus());
        candidate.setProfilePictureUrl(candidateProfileDto.getProfilePictureUrl());

        // Setting Resume Fields
        Resume resume = new Resume();
        resume.setProfessionalSummary(candidateProfileDto.getProfessionalSummary());
        resume.setPortfolioUrl(candidateProfileDto.getPortfolioUrl());
        resume.setEducation(candidateProfileDto.getEducation());
        resume.setExperience(candidateProfileDto.getExperience());
        resume.setSoftSkills(candidateProfileDto.getSoftSkills());
        resume.setHardSkills(candidateProfileDto.getHardSkills());
        resume.setLanguages(candidateProfileDto.getLanguages());
        resume.setCertifications(candidateProfileDto.getCertifications());
        resume.setProjects(candidateProfileDto.getProjects());


        resumeRepository.save(resume);
        // Set the resume to the candidate
        resume.setCandidate(candidate);
        candidate.setResume(resume);
        // Mark profile as complete
        candidate.setProfileComplete(true);


        // Save the updated candidate (with the resume)
        candidateRepository.save(candidate);

        return ResponseEntity.ok("Profile updated successfully");
    }

    @RequestMapping("/view-profile")
    public ResponseEntity<CandidateProfileDto> viewProfile(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long candidateId = customUserDetails.getCandidate().getId();

        CandidateProfileDto candidateProfileDto = candidateService.getProfileById(candidateId);

        return ResponseEntity.ok(candidateProfileDto);
    }


    @RequestMapping("/delete-account")
    public ResponseEntity<String> deleteAccount(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Candidate candidate = customUserDetails.getCandidate();

        // Delete the authenticated user's account
        candidateRepository.delete(candidate);

        return ResponseEntity.ok("Account deleted successfully");
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        // Get the authenticated Candidate ID from CustomUserDetails
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long candidateId = customUserDetails.getCandidate().getId();

        // Delegate the update logic to the RhService
        candidateService.updateProfile(candidateId, candidateProfileDto);

        return ResponseEntity.ok("Profile edited successfully");
    }
}
