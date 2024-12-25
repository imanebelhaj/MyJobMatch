package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
