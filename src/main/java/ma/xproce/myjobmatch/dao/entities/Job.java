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
}
