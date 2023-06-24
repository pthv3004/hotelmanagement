package swp.server.hotelmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.server.hotelmanagement.entities.AccountEntity;
import swp.server.hotelmanagement.entities.FeedbackEntity;
import swp.server.hotelmanagement.entities.RoomEntity;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Integer> {
    List<FeedbackEntity> getByRoomEntity(RoomEntity roomEntity);
    List<FeedbackEntity> getByAccountEntity(AccountEntity accountEntity);
}
