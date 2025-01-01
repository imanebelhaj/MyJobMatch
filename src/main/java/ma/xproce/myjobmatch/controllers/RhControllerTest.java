package ma.xproce.myjobmatch.controllers;


import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rh")
public class RhControllerTest {

    @Autowired
    CandidateRepository candidateRepository;
    @Autowired
    RHRepository rhRepository;
    @GetMapping("/hello")
    @PreAuthorize("hasAuthority('RH')")
    public String sayHello() {
        return "Hello";
    }

    @GetMapping("/all")
    public String allAccess() {
        return "HR Content.";
    }


    @GetMapping("/me")
    public ResponseEntity<?> authenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Ensure the user is authenticated
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
        }

        User currentUser = (User) authentication.getPrincipal();

        // Tailor response based on user's role
//        if (currentUser.getRole().equals("CANDIDATE")) {
//            Candidate candidate = candidateRepository.findById(currentUser.getId())
//                    .orElseThrow(() -> new RuntimeException("Candidate details not found"));
//            return ResponseEntity.ok(candidate);
     //   } else
        if (currentUser.getRole().equals("RH")) {
            RH rh = rhRepository.findById(currentUser.getId())
                    .orElseThrow(() -> new RuntimeException("RH details not found"));
            return ResponseEntity.ok(rh);
        } else {
            // Default response for general user
            return ResponseEntity.ok(currentUser);
        }
    }

//    @GetMapping
//    public ResponseEntity<List<User>> allUsers() {
//        List<User> users = userService.allUsers();
//        return ResponseEntity.ok(users);
//    }

}
