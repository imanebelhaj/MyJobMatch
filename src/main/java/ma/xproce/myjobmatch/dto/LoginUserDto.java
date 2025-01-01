package ma.xproce.myjobmatch.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserDto {
    private String username;
    private String email;
    private String password;

    // Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}
