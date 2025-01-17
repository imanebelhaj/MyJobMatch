package ma.xproce.myjobmatch.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.utils.CustomUserDetailsService;
import ma.xproce.myjobmatch.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

//hada handles login
public class UsernamePasswordAuthFilter  extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    //@Autowired
    private final RHRepository rhRepository;
    private final CandidateRepository candidateRepository;


    public UsernamePasswordAuthFilter(AuthenticationManager authenticationManager,
                                      JwtUtil jwtUtil,
                                      CustomUserDetailsService customUserDetailsService, RHRepository rhRepository,CandidateRepository candidateRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.customUserDetailsService = customUserDetailsService;
        this.rhRepository = rhRepository;
        this.candidateRepository = candidateRepository;
        setFilterProcessesUrl("/api/auth/login"); // URL for the login endpoint
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        // Extract username and password from the request
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Perform authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authenticationToken); // Authenticate using AuthenticationManager
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Once authentication is successful, generate the JWT token
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        String jwt = jwtUtil.generateToken(userDetails);

        // Send the JWT token as a response to the client
        response.setHeader("Authorization", "Bearer " + jwt);
        response.setStatus(HttpStatus.OK.value());

        response.setContentType("application/json");

        // Optionally, send additional information in the response
        response.getWriter().write("{\"token\": \"" + jwt + "\"}");

        //Check if the authenticated user is an RH and whether their profile is complete
        if (userDetails instanceof RH) {
            RH rh = (RH) userDetails;
            RH rhFromDb = rhRepository.findByUsername(rh.getUsername())
                    .orElseThrow(() -> new RuntimeException("RH not found"));

            // If the profile is not complete, redirect to the profile completion page
            if (!rhFromDb.isProfileComplete()) {
                response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                response.setHeader("Location", "/api/rh/complete-profile");  // Adjust the redirect URL if needed
                return;
            }
        } else if (userDetails instanceof Candidate) {
            Candidate candidate = (Candidate) userDetails;
            Candidate candidateFromDb = candidateRepository.findByUsername(candidate.getUsername())
                    .orElseThrow(() -> new RuntimeException("Candidate not found"));

            // If the profile is not complete, redirect to the profile completion page
            if (!candidateFromDb.isProfileComplete()) {
                response.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
                response.setHeader("Location", "/api/candidate/complete-profile");  // Adjust the redirect URL if needed
                return;

            }
        }
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        // Handle authentication failure (e.g., invalid username/password)
        if (failed instanceof BadCredentialsException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"Invalid username or password\"}");
        } else if (failed instanceof DisabledException) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\": \"Account is disabled\"}");
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"Authentication failed\"}");
        }
    }
}
