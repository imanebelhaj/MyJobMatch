package ma.xproce.myjobmatch.dao.repositories;

import ma.xproce.myjobmatch.dao.entities.TokenBlacklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist,Long> {
    boolean existsByToken(String token);
}
