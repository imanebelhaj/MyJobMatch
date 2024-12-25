package ma.xproce.myjobmatch.dao.entities;


import jakarta.persistence.*;
import lombok.*;
import ma.xproce.myjobmatch.dao.entities.Roles;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private Date createdAt;
    private Date editedAt;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    @ToString.Exclude
    private Roles role;
}
