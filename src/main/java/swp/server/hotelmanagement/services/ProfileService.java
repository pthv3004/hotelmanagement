package swp.server.hotelmanagement.services;

import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.AccountDTO;
import swp.server.hotelmanagement.entities.AccountEntity;
import swp.server.hotelmanagement.entities.ProfileEntity;
public interface ProfileService {
    ProfileEntity profileById(int accountId);
    int createNewProfile(AccountDTO accountDTO);
    AccountDTO updateProfile(AccountDTO accountDTO);
    Boolean deleteProfile(int accountId);
}
