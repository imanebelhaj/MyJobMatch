package ma.xproce.myjobmatch.dao.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString   //(callSuper = true)
@Table(name = "candidates")
public class Candidate extends User{

    //Category || field of expertise
    //resume pdf
    //resume form (lists : education,experience,skills, languages,projects, certifs...)
    //full name
    //contacts(phone,mail,linkedin...)
    //applications
    //status ....
    //preferences
    //cover letter
    //location






//    @Column(nullable = true)
//    private String resume; // Path or binary data for the resume
//
//    @Column(nullable = true)
//    private String skills; // Skills in a comma-separated format or JSON
//
//    @OneToMany(mappedBy = "candidate", cascade = CascadeType.ALL)
//    @ToString.Exclude
//    private List<Application> applications; // List of applications made by the candidate

}
