package ma.xproce.myjobmatch.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString   //(callSuper = true)
@Table(name = "rh")
public class RH extends User{


    //@Column(nullable = false)
    private String companyName;

    //@Column(nullable = false)
    private String fullName;


    @Column(nullable = true)
    private String linkedinUrl;


    @Column(nullable = true)
    private String department; //it? business? finance?


    @Column(nullable = true)
    private String phone;


    @Column(nullable = true)
    private String companyWebsite;

    @Column(nullable = true)
    private String profilePictureUrl;

    @Column(nullable = false)
    private boolean isProfileComplete = false;

    //list of created jobs
    @OneToMany(mappedBy = "rh", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Job> jobs;








    //ggeters setters
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

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }


}
