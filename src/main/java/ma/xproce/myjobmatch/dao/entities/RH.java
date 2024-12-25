package ma.xproce.myjobmatch.dao.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString   //(callSuper = true)
@Table(name = "rh")
public class RH extends User{

    //name
    //profile
    //contact
    //creation
    //company
    //jobs
    //position




    @Column(nullable = false)
    private String companyName;

    @Column(nullable = true)
    private String department;

    @Column(nullable = true)
    private String position;
}
