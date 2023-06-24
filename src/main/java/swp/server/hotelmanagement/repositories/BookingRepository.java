package swp.server.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.server.hotelmanagement.entities.BookingEntity;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, Integer> {
}
