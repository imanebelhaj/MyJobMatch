package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByRole(String role); //finds role by its name
}
