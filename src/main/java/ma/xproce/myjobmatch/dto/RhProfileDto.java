package ma.xproce.myjobmatch.dto;

import lombok.*;
import ma.xproce.myjobmatch.dao.entities.RH;

import java.util.List;

//@Getter
//@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RhProfileDto {
    private String companyName;
    private String fullName;
    private String email;
    private String username;
    private String linkedinUrl;
    private String department;
    private String phone;
    private String companyWebsite;
    private String profilePictureUrl;
    private boolean isProfileComplete;
  //  private int jobCount;

//    public RhProfileDto(RH rh) {
//        this.companyName = rh.getCompanyName();
//        this.fullName = rh.getFullName();
//        this.linkedinUrl = rh.getLinkedinUrl();
//        this.department = rh.getDepartment();
//        this.phone = rh.getPhone();
//        this.companyWebsite = rh.getCompanyWebsite();
//        this.profilePictureUrl = rh.getProfilePictureUrl();
//        this.isProfileComplete = rh.isProfileComplete();
//        this.jobCount = (rh.getJobs() != null) ? rh.getJobs().size() : 0;
//    }




    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl) {
        this.linkedinUrl = linkedinUrl;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyWebsite() {
        return companyWebsite;
    }

    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public boolean isProfileComplete() {
        return isProfileComplete;
    }

    public void setProfileComplete(boolean profileComplete) {
        isProfileComplete = profileComplete;
    }

    public String getEmail(){ return email;}

    public void setEmail(String email) {this.email=email;}
    public String getUsername(){ return username;}

    public void setUsername(String username) {this.username=username;}


}
