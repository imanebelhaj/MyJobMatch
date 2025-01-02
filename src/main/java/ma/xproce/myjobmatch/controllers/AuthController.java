package ma.xproce.myjobmatch.controllers;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dto.LoginUserDto;
import ma.xproce.myjobmatch.dto.RegisterUserDto;
import ma.xproce.myjobmatch.services.AuthService;
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
    public ResponseEntity<String> register(@RequestBody RegisterUserDto registerUserDto) {
        try {
            User newUser = authService.register(registerUserDto);

            if (newUser.getRole().getRole().equals("RH")) {
                RH rh = new RH();
                rh.setUsername(newUser.getUsername()); // Ensure the username is the same as the user
                rh.setRole(newUser.getRole()); // Set role as RH
                rh.setEmail(newUser.getEmail());
                rh.setPassword(newUser.getPassword());
                rh.setCreatedAt(new Date());
                rh.setProfileComplete(false); // Initially, profile isn't complete

                // Save the RH entity to the RH table
                rhRepository.save(rh);
            }
            if (newUser.getRole().getRole().equals("CANDIDATE")) {
                Candidate candidate = new Candidate();
                candidate.setUsername(newUser.getUsername()); // Ensure the username is the same as the user
                candidate.setRole(newUser.getRole()); // Set role as RH
                candidate.setEmail(newUser.getEmail());
                candidate.setPassword(newUser.getPassword());
                candidate.setCreatedAt(new Date());
                candidate.setProfileComplete(false); // Initially, profile isn't complete

                // Save the RH entity to the RH table
                candidateRepository.save(candidate);
            }
            return new ResponseEntity<>("User registered successfully: " + newUser.getUsername(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginUserDto loginUserDto) {
        try {
            // Authenticate the user based on username and password
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword())
            );

            // Extract UserDetails from the Authentication object
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Generate the JWT token using the UserDetails
            String token = jwtUtil.generateToken(userDetails);

            // Return the JWT token as part of the response
            return ResponseEntity.ok("Bearer " + token);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String token) {
        try {
            // Call the logout method from AuthService to handle token blacklisting
            authService.logout(token);
            return new ResponseEntity<>("Logout successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
