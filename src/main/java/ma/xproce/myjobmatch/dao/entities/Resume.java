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
}
