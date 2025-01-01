package ma.xproce.myjobmatch.services;


import ma.xproce.myjobmatch.dao.entities.User;
import ma.xproce.myjobmatch.dao.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //fix get Users by roles ⛔

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new RuntimeException("User not found.");
        }
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
//    public List<User> getUsersByRole(String roleName) {
//        return userRepository.findByRole(roleName);
//    }

    public User getUserById(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // Handle the case where the user is not found
            System.out.println("User not found with ID: " + userId);
            return null;  // Or handle with a proper message
        }
    }
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user;
        } else {
            // Handle the case where the user is not found
            System.out.println("User not found with email: " + email);
            return null; // Or handle with a proper exception if preferred
        }
    }

    public User updatePassword(Long userId, String newPassword) {
        User user = getUserById(userId);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword)); // Encrypt the new password
            return userRepository.save(user);
        }
        return null;  // Or handle with a proper message if user is not found
    }

    // Method to update user's email
    public User updateEmail(Long userId, String newEmail) {
        User user = getUserById(userId);
        if (user != null) {
            user.setEmail(newEmail);
            return userRepository.save(user);
        }
        return null;  // Or handle with a proper message if user is not found
    }

    // Method to delete a user
    public void deleteUser(Long userId) {
        User user = getUserById(userId);
        if (user != null) {
            userRepository.delete(user);
        } else {
            // Handle the case where the user is not found
            System.out.println("User not found with ID: " + userId);
        }
    }

    // Method to deactivate (disable) a user's account
//    public User deactivateAccount(Long userId) {
//        User user = getUserById(userId);
//        if (user != null) {
//            user.setEnabled(false); // Mark the account as disabled
//            return userRepository.save(user);
//        }
//        return null;  // Or handle with a proper message if user is not found
//    }
}

