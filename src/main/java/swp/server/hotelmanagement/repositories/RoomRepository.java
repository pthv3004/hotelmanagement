package swp.server.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import swp.server.hotelmanagement.entities.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Integer> {
    @Query(value = "SELECT * FROM swp_hotel_management.room  r where r.is_rent = false",nativeQuery = true)
    List<RoomEntity> getAllByRentFalse();
}
