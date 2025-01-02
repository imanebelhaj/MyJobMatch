package ma.xproce.myjobmatch.dao.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "jobs")
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String title;
    private String category;
    private String description;
    private String location;
    private Date createdAt;
    private Date editedAt;
    private Date applicationDeadline;
    private Date postedAt;
    private int maxApplications;
    private String status; //("Open", "Closed", "Filled", whether the job is still accepting applications)

    //extra
    private String jobType;
    private String salaryRange;
    private String RequiredEducation;
    private String RequiredExperience; // e.g., "2-5 years of experience"
    private String jobLevel; //(e.g., "Junior", "Senior", "Lead")
    @ElementCollection
    @CollectionTable(name = "job_required_skills", joinColumns = @JoinColumn(name = "job_id"))
    private List<String> RequiredSkills;


    @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
    private List<Application> applications; // List of applications for this job

    @ManyToOne
    @JoinColumn(name = "rh_id", nullable = false)
    private RH rh;




    //getters setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
    }

    public Date getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(Date applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }

    public Date getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(Date postedAt) {
        this.postedAt = postedAt;
    }

    public int getMaxApplications() {
        return maxApplications;
    }

    public void setMaxApplications(int maxApplications) {
        this.maxApplications = maxApplications;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSalaryRange() {
        return salaryRange;
    }

    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }

    public String getRequiredEducation() {
        return RequiredEducation;
    }

    public void setRequiredEducation(String requiredEducation) {
        RequiredEducation = requiredEducation;
    }

    public String getRequiredExperience() {
        return RequiredExperience;
    }

    public void setRequiredExperience(String requiredExperience) {
        RequiredExperience = requiredExperience;
    }

    public String getJobLevel() {
        return jobLevel;
    }

    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    public List<String> getRequiredSkills() {
        return RequiredSkills;
    }

    public void setRequiredSkills(List<String> requiredSkills) {
        RequiredSkills = requiredSkills;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public RH getRh() {
        return rh;
    }

    public void setRh(RH rh) {
        this.rh = rh;
    }

}
