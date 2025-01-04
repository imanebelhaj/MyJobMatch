package ma.xproce.myjobmatch.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String resumeForm; //all inputted cases of resume-form will be also saved in this one attribute for the model of job matching
    private String professionalSummary;
    private String portfolioUrl;

    // Resume-related information
    @ElementCollection
    @CollectionTable(name = "candidate_education", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> education; // School, duration, degree

    @ElementCollection
    @CollectionTable(name = "candidate_experience", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> experience; // Company, title, description, etc.

    @ElementCollection
    @CollectionTable(name = "candidate_soft_skills", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> softSkills;

    @ElementCollection
    @CollectionTable(name = "candidate_hard_skills", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> hardSkills;

    @ElementCollection
    @CollectionTable(name = "candidate_languages", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> languages;

    @ElementCollection
    @CollectionTable(name = "candidate_certifications", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> certifications;

    @ElementCollection
    @CollectionTable(name = "candidate_projects", joinColumns = @JoinColumn(name = "resume_id"))
    private List<String> projects;

    @OneToOne
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;




    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResumeForm() {
        return resumeForm;
    }

    public void setResumeForm(String resumeForm) {
        this.resumeForm = resumeForm;
    }

    public String getProfessionalSummary() {
        return professionalSummary;
    }

    public void setProfessionalSummary(String professionalSummary) {
        this.professionalSummary = professionalSummary;
    }

    public String getPortfolioUrl() {
        return portfolioUrl;
    }

    public void setPortfolioUrl(String portfolioUrl) {
        this.portfolioUrl = portfolioUrl;
    }

    public List<String> getEducation() {
        return education;
    }

    public void setEducation(List<String> education) {
        this.education = education;
    }

    public List<String> getExperience() {
        return experience;
    }

    public void setExperience(List<String> experience) {
        this.experience = experience;
    }

    public List<String> getSoftSkills() {
        return softSkills;
    }

    public void setSoftSkills(List<String> softSkills) {
        this.softSkills = softSkills;
    }

    public List<String> getHardSkills() {
        return hardSkills;
    }

    public void setHardSkills(List<String> hardSkills) {
        this.hardSkills = hardSkills;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List<String> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
}
