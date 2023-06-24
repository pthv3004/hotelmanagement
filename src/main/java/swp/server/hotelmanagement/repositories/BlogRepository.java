package swp.server.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.server.hotelmanagement.entities.BlogEntity;
import swp.server.hotelmanagement.services.BlogService;

@Repository
public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
}
