package ma.xproce.myjobmatch.utils;

import lombok.Getter;
import ma.xproce.myjobmatch.dao.entities.Candidate;
import ma.xproce.myjobmatch.dao.entities.RH;
import ma.xproce.myjobmatch.dao.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public RH getRh() {
       // if (user.getRole().getRole().equals("RH")) {
        if (user instanceof RH) {
            return (RH) user; // Cast to RH if the user is an instance of RH
        }
        throw new ClassCastException("User is not of type RH");
    }

    public Candidate getCandidate() {
        // if (user.getRole().getRole().equals("RH")) {
        if (user instanceof Candidate) {
            return (Candidate) user; // Cast to RH if the user is an instance of RH
        }
        throw new ClassCastException("User is not of type Candidate");
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> user.getRole().getRole());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    @Override
    public String getUsername() {
        return user.getUsername();
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }


//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return (Collection<? extends GrantedAuthority>) user.getRole(); // Assuming roles implement GrantedAuthority
//    }

    //    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(user.getRole().getRole()));
//    }
//@Override
//public Collection<? extends GrantedAuthority> getAuthorities() {
//    // Assuming `Roles` has a method to return the authority
//    return Collections.singleton(user.getRole()); // Adjust if `Roles` implements `GrantedAuthority`
//}

}
