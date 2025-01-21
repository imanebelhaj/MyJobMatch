package ma.xproce.myjobmatch.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import ma.xproce.myjobmatch.dao.entities.Job;
import ma.xproce.myjobmatch.dao.repositories.JobRepository;
import ma.xproce.myjobmatch.dto.JobDto;
import ma.xproce.myjobmatch.services.JobService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/candidate/jobs")
public class JobController {

    @Autowired
    JobRepository jobRepository;
    @Autowired
    JobService jobService;

    @GetMapping
    public ResponseEntity<List<JobDto>> getAllJobs(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        List<Job> jobs = jobService.getAllJobs();
        List<JobDto> jobDtos = jobs.stream()
                .map(ma.xproce.myjobmatch.dto.JobDto::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(jobDtos, HttpStatus.OK);
    }
//    @GetMapping("/matched-jobs")
//    public ResponseEntity<Map<String, Object>> getMatchedJobs(Authentication authentication) {
//        try {
//            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//            String resumeForm = userDetails.getCandidate().getResumeForm();
//            System.out.println("🤖");
//            List<Job> matchedJobs = jobService.getMatchedJobs(resumeForm);
//            System.out.println("💗💗💗🤖🤖");
//            Map<String, Object> response = new HashMap<>();
//            response.put("matchedJobs", matchedJobs);
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(Map.of("error", e.getMessage()));
//        }
//    }




    @GetMapping("/matched-jobs")
    public ResponseEntity<Map<String, Object>> getMatchedJobs(Authentication authentication) {
        try {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String resumeForm = userDetails.getCandidate().getResumeForm();

            // Log the received resume text before sending
            System.out.println("Received resume text: " + resumeForm);

            // Escape special characters for JSON
            String escapedResumeForm = StringEscapeUtils.escapeJson(resumeForm);

            // Log the escaped resume text
            System.out.println("Escaped resume text: " + escapedResumeForm);

            // Send the POST request to Flask API
            String flaskApiUrl = "http://localhost:5000/match-jobs";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("resume_text", escapedResumeForm);
            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> flaskResponse = restTemplate.exchange(flaskApiUrl, HttpMethod.POST, request, String.class);

            // Log the response from Flask API
            System.out.println("Flask API response: " + flaskResponse.getBody());

            // Assuming the response is a JSON string, parse it into a Map
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> response = objectMapper.readValue(flaskResponse.getBody(), Map.class);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Log the error
            System.out.println("Error in matching jobs: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }



}
