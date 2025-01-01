package ma.xproce.myjobmatch.services;


import ma.xproce.myjobmatch.dao.entities.Roles;
import ma.xproce.myjobmatch.dao.entities.TokenBlacklist;
import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.RolesRepository;
import ma.xproce.myjobmatch.dao.repositories.TokenBlacklistRepository;
import ma.xproce.myjobmatch.dao.repositories.UserRepository;
import ma.xproce.myjobmatch.dto.LoginUserDto;
import ma.xproce.myjobmatch.dto.RegisterUserDto;
import ma.xproce.myjobmatch.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;



@Service
public class AuthService {
    //register ✅ idk if its correct tho
    //authenticate(login)✅  same for this hihi
    //logout

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private TokenBlacklistRepository tokenBlacklistRepository;



    public User register(RegisterUserDto registerUserDto) {
        // Check if username or email already exists
        if (userRepository.findByUsername(registerUserDto.getUsername()) != null) {
            throw new RuntimeException("Username already exists.");
        }
        // Create a new User entity and populate it
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));

        // Assign a single role
        Roles role = rolesRepository.findByRole(registerUserDto.getRole());
        if (role == null) {
            throw new RuntimeException("Role not found: " + registerUserDto.getRole());
        }
        user.setRole(role);

        // Save and return the user
        return userRepository.save(user);
    }

    public void logout(String token) {
        // Extract token expiration
        Date expiryDate = jwtUtil.extractExpiration(token);

        // Save token to blacklist
        TokenBlacklist blacklistedToken = new TokenBlacklist();
        blacklistedToken.setToken(token);
        blacklistedToken.setExpiryDate(expiryDate);
        tokenBlacklistRepository.save(blacklistedToken);

        // Clear security context
        SecurityContextHolder.clearContext();
    }




//    public String authenticate(LoginUserDto loginUserDto) {
//        // Authenticate the user using AuthenticationManager
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        loginUserDto.getUsername(), // Use username here
//                        loginUserDto.getPassword()
//                )
//        );
//        // If authentication is successful, generate JWT token
//        if (authentication.isAuthenticated()) {
//            // We use the authenticated user (UserDetails) to generate the token
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//            return jwtUtil.generateToken(userDetails); // Pass UserDetails directly to generateToken
//        } else {
//            throw new RuntimeException("Invalid credentials.");
//        }
//    }


}
