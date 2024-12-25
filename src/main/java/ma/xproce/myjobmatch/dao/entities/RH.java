package ma.xproce.myjobmatch.dao.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString   //(callSuper = true)
@Table(name = "rh")
public class RH extends User{

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
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

    //list of created jobs
    @OneToMany(mappedBy = "rh", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Job> jobs;
}
