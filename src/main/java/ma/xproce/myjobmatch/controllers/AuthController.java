package ma.xproce.myjobmatch.controllers;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.Roles;
import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dto.LoginUserDto;
import ma.xproce.myjobmatch.dto.RegisterUserDto;
import ma.xproce.myjobmatch.services.AuthService;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import ma.xproce.myjobmatch.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RHRepository rhRepository;
    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User newUser = authService.register(registerUserDto);

            // Check if the role is RH (Recruiter)
            if (newUser.getRole().getRole().equals("RH")) {
                RH rh = new RH();
                rh.setUsername(newUser.getUsername());
                rh.setRole(newUser.getRole());
                rh.setEmail(newUser.getEmail());
                rh.setPassword(newUser.getPassword());
                rh.setCreatedAt(new Date());
                rh.setProfileComplete(false);
                rhRepository.save(rh);
            }

            // Check if the role is CANDIDATE (Job Seeker)
            if (newUser.getRole().getRole().equals("CANDIDATE")) {
                Candidate candidate = new Candidate();
                candidate.setUsername(newUser.getUsername());
                candidate.setRole(newUser.getRole());
                candidate.setEmail(newUser.getEmail());
                candidate.setPassword(newUser.getPassword());
                candidate.setCreatedAt(new Date());
                //candidate.setProfileComplete(false);
                candidateRepository.save(candidate);
            }

            // Return a JSON response with success message and user data
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User registered successfully");
            response.put("username", newUser.getUsername());
            response.put("role", newUser.getRole().getRole());

            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (Exception e) {
            // Return error message in case of exception
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Authenticate the user based on username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword())
            );


            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            // Get user role (RH or Candidate)
            RH rh = null;
            Candidate candidate = null;

            // Handle user roles dynamically
            if (customUserDetails.getRh() != null) {
                rh = customUserDetails.getRh();
            } else if (customUserDetails.getCandidate() != null) {
                candidate = customUserDetails.getCandidate();
            }

            // Generate the JWT token using the UserDetails
            String token = jwtUtil.generateToken(customUserDetails);

            // Return the JWT token as part of a structured JSON response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", "Bearer " + token);
            response.put("username", customUserDetails.getUsername());
            //response.put("roles", customUserDetails.getAuthorities());

            if (rh != null) {
                response.put("role", "RH");
            } else if (candidate != null) {
                response.put("role", "CANDIDATE");
            }

            // Profile completion status (this can be added to the response as per your logic)
            boolean profileComplete = authService.isProfileComplete(customUserDetails);
            response.put("profileComplete", profileComplete);


            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Return error message in case of exception
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> logout(@RequestHeader("Authorization") String token) {
        try {
            // Call the logout method from AuthService to handle token blacklisting
            authService.logout(token);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Logout successful");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

}
