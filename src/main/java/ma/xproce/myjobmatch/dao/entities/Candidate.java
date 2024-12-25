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

    private String fullName;
    private String phone;
    private String linkedinUrl;
    private String website;
    private String location; //country+city
    private String category; //field of expertise
    private String jobType; //contact type : intern, full time, part-time , freelance... (opt)

    @Column(nullable = true)
    private String resumeUrl;

    @Column(nullable = true)
    private String coverLetterUrl;
    private String status; //(opt) (looking for a job, employed)?

    @Column(nullable = true)
    private String profilePictureUrl;


    //list of jobs applied to: filter jobs by candidateApplicationStatus if true(aaplied) or false
    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Application> applications;

    @OneToOne
    @JoinColumn(name = "resume_id")
    private Resume resume;



}
