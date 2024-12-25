package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
