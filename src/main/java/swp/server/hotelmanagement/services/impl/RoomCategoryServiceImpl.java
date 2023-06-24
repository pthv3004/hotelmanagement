package swp.server.hotelmanagement.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.RoomCategoryDTO;
import swp.server.hotelmanagement.entities.RoomcategoryEntity;
import swp.server.hotelmanagement.repositories.RoomCategoryRepository;
import swp.server.hotelmanagement.services.RoomCategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomCategoryServiceImpl implements RoomCategoryService {
    private final RoomCategoryRepository roomCategoryRepository;

    @Override
    public List<RoomCategoryDTO> getAllCategories() {
        List<RoomcategoryEntity> roomcategoryEntities = roomCategoryRepository.findAll();
        if (roomcategoryEntities != null) {
            List<RoomCategoryDTO> roomCategoryDTOS = new ArrayList<>();
            roomcategoryEntities.stream().forEach(r -> {
                RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(r.getId(),
                        r.getName(), r.getAmount(), r.getDescription());
                roomCategoryDTOS.add(roomCategoryDTO);
            });
            return roomCategoryDTOS;
        }
        return null;
    }

    @Override
    public RoomCategoryDTO getRoomCategoryById(int cateId) {
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO();
        try {
            RoomcategoryEntity roomcategoryEntity = roomCategoryRepository.getOne(cateId);
            roomCategoryDTO.setName(roomcategoryEntity.getName());
            roomCategoryDTO.setDescription(roomcategoryEntity.getDescription());
            roomCategoryDTO.setId(roomcategoryEntity.getId());
            roomCategoryDTO.setAmount(roomcategoryEntity.getAmount());
            return roomCategoryDTO;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public RoomCategoryDTO createNewCategory(RoomCategoryDTO roomCategoryDTO) {
        try {
            RoomcategoryEntity roomcategoryEntity = new RoomcategoryEntity();
            roomcategoryEntity.setAmount(roomCategoryDTO.getAmount());
            roomcategoryEntity.setDescription(roomCategoryDTO.getDescription());
            roomcategoryEntity.setName(roomCategoryDTO.getName());
            roomCategoryRepository.save(roomcategoryEntity);
            return roomCategoryDTO;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public RoomCategoryDTO updateCategory(RoomCategoryDTO updatedCategoryDTO) {
        try{
            RoomcategoryEntity oldRoomcategoryEntity = roomCategoryRepository.getOne(updatedCategoryDTO.getId());
            oldRoomcategoryEntity.setName(updatedCategoryDTO.getName());
            oldRoomcategoryEntity.setDescription(updatedCategoryDTO.getDescription());
            oldRoomcategoryEntity.setAmount(updatedCategoryDTO.getAmount());
            roomCategoryRepository.save(oldRoomcategoryEntity);
            return updatedCategoryDTO;
        }catch (Exception e){
            e.getMessage();
        }
        return null;
    }

    @Override
    public Boolean deleteCategory(int roomCategoryId) {
        try{
            RoomcategoryEntity roomcategoryEntity = roomCategoryRepository.getOne(roomCategoryId);

            return true;
        }catch (Exception e){
            e.getMessage();
            return false;
        }
    }
}
