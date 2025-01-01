package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //Optional<User> findByUsername(String username);
    User findByUsername(String username);

    User findByEmail(String email);
    //User existsByUsername(String username);
    //User save(User user);
}
