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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PutMapping("/first-page")
    public ResponseEntity<Map<String, Object>> firstPage(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        Map<String, Object> response = new HashMap<>();
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long candidateId = customUserDetails.getCandidate().getId();
            Candidate candidate = candidateRepository.findById(candidateId)
                    .orElseThrow(() -> new RuntimeException("Candidate not found with ID: " + candidateId));

            if (candidateProfileDto.getResumePdf() != null) {
                candidate.setResumePdf(candidateProfileDto.getResumePdf());
                response.put("message", "Resume uploaded and profile updated successfully.");
            } else {
                response.put("message", "No resume uploaded.");
            }

            candidateRepository.save(candidate);
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

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
    public ResponseEntity<Map<String, Object>> editProfile(@RequestBody CandidateProfileDto candidateProfileDto, Authentication authentication) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            Long candidateId = customUserDetails.getCandidate().getId();
            candidateService.updateProfile(candidateId, candidateProfileDto);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Profile updated successfully");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            // Return error message in case of exception
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }
}
