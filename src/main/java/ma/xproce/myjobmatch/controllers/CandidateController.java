package ma.xproce.myjobmatch.controllers;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dto.CandidateProfileDto;
import ma.xproce.myjobmatch.services.CandidateService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/candidate")
@PreAuthorize("hasAuthority('CANDIDATE')")
public class CandidateController {

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    CandidateService candidateService;

    @PutMapping("/complete-profile")
    public ResponseEntity<Map<String, Object>> completeProfile(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        try {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long candidateId = customUserDetails.getCandidate().getId();
        candidateService.completeProfile(candidateId, candidateProfileDto);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Profile completed successfully");
       return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
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
        //Candidate candidate = customUserDetails.getCandidate();
        Long candidateId = customUserDetails.getCandidate().getId();
        candidateService.deleteAccount(candidateId);
        return ResponseEntity.ok("Account deleted successfully");
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<String> editProfile(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Long candidateId = customUserDetails.getCandidate().getId();
        candidateService.updateProfile(candidateId, candidateProfileDto);
        return ResponseEntity.ok("Profile edited successfully");
    }
}
