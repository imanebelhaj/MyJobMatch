package ma.xproce.myjobmatch.dao.entities;

import jakarta.persistence.*;
import lombok.*;

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

    //job id manyToOne (many apps - one job)
    //candidate id (list)
    //date of application
    //candidate application status bool
    //
}
