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
@ToString   //(callSuper = true)
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    private String status; //(Pending, Accepted, Rejected)
    private Date applicationDate;
    private Date createdAt;
    private Date editedAt;

    // HR review fields
    private Date reviewedAt;  // When the application was reviewed
    private String reviewerComments;  // HR's feedback or comments

    //job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;

    //cadidate
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;


}
