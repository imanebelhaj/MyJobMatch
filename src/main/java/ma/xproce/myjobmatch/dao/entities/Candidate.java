package ma.xproce.myjobmatch.dao.entities;


import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@Table(name = "candidates")
public class Candidate extends User{

    String fullname;
    String phone;
    String linkedinUrl;
    //String website;
    List<String> website;
    String location; //country+city
    String category; //field of expertise
    String jobType; //contact type : intern, full time, part time , freelance... (opt)
    String resumeUrl; //(opt)
    String coverLetterUrl; //relates to job maybe
    String status; //(opt) (looking for a job, employed)?
    String resumeForm; //all inputed cases of resume-form will be also saved in this one attribute for the model of job matching

    //resume form
    @ElementCollection //Used for mapping simple collections like List<String> in JPA. Each List<String> is mapped to a separate table.
    @CollectionTable(name = "candidate_education", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> education; // School, duration, degree

    @ElementCollection
    @CollectionTable(name = "candidate_experience", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> experience; // hadi ymkn khass tkon entity bo7dha table cause fih company - title - descritption o duraction o jobtype/contrat o location

    @ElementCollection
    @CollectionTable(name = "candidate_soft_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> softSkills;

    @ElementCollection
    @CollectionTable(name = "candidate_hard_skills", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> hardSkills;

    @ElementCollection
    @CollectionTable(name = "candidate_languages", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> languages;

    @ElementCollection
    @CollectionTable(name = "candidate_certifications", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> certifications;

    @ElementCollection
    @CollectionTable(name = "candidate_projects", joinColumns = @JoinColumn(name = "candidate_id"))
    private List<String> projects;


    //list of jobs applied to: filter jobs by candidateApplicationStatus if true(aaplied) or false
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Application> applications;



}
