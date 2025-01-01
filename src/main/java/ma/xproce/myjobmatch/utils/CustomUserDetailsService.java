package ma.xproce.myjobmatch.utils;

import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.CandidateRepository;
import ma.xproce.myjobmatch.dao.repositories.RHRepository;
import ma.xproce.myjobmatch.dao.repositories.UserRepository;
import ma.xproce.myjobmatch.utils.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RHRepository rhRepository;
    @Autowired
    private CandidateRepository candidateRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<RH> rhOptional = rhRepository.findByUsername(username);
        if (rhOptional.isPresent()) {
            return new CustomUserDetails(rhOptional.get());  // Return custom user details for RH
        }
        Optional<Candidate> candidateOptional = candidateRepository.findByUsername(username);
        if (candidateOptional.isPresent()) {
            return new CustomUserDetails(candidateOptional.get());  // Return custom user details for RH
        }

        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new CustomUserDetails(user);  // Return custom user details for normal user
        }

        throw new UsernameNotFoundException("User not found: " + username);
    }
//        User user = userRepository.findByUsername(username);
//        if (user == null) {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//        return new CustomUserDetails(user);

}


