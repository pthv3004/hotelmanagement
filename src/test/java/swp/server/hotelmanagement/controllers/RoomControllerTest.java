package swp.server.hotelmanagement.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import swp.server.hotelmanagement.dtos.RoomCategoryDTO;
import swp.server.hotelmanagement.dtos.RoomDTO;
import swp.server.hotelmanagement.services.RoomService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class RoomControllerTest {
    @InjectMocks
    RoomController roomController;
    @Mock
    RoomService roomService;

    @Test
    public void getAllRoomTest() {
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(1, "Single", 2, "1 giường 1 tủ lạnh");
        RoomDTO roomDTO = new RoomDTO(1, "Phòng Đơn 1", roomCategoryDTO, "abcxyz", 350000, false);
        RoomDTO roomDTO1 = new RoomDTO(2, "Phòng Đơn 2", roomCategoryDTO, "xyzabc", 350000, false);
        List<RoomDTO> roomDTOList = new ArrayList<>();
        roomDTOList.add(roomDTO);
        roomDTOList.add(roomDTO1);
        when(roomService.getAllRooms()).thenReturn(roomDTOList);
        assertThat(roomController.getAllRoom().get(0).getPrice()).isLessThan(400000);
        assertThat(roomController.getAllRoom().get(1).getRoomCategoryDTO().getAmount()).isGreaterThan(1);

    }

    @Test
    public void getByIdTest() {
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(1, "Single", 2, "1 giường 1 tủ lạnh");
        RoomDTO roomDTO = new RoomDTO(1, "Phòng Đơn 1", roomCategoryDTO, "abcxyz", 350000, false);
        when(roomService.getRoomByRoomId(1)).thenReturn(roomDTO);
        assertThat(roomController.getRoomById(1).getPrice()).isEqualTo(350000);
        assertThat(roomController.getRoomById(1).getName()).isNotEmpty();
    }

    @Test
    public void createNewRoomTest() {
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(1, "Single", 2, "1 giường 1 tủ lạnh");
        RoomDTO roomDTO = new RoomDTO(1, "Phòng Đơn 1", roomCategoryDTO, "abcxyz", 350000, false);
        when(roomService.createNewRoom(roomDTO)).thenReturn(roomDTO);
        assertThat(roomController.createNewRoom(roomDTO).getRoomId()).isEqualTo(1);
        assertThat(roomController.createNewRoom(roomDTO).getImage().length()).isGreaterThan(1);
    }

    @Test
    public void updateRoomTest() {
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(1, "Single", 2, "1 giường 1 tủ lạnh");
        RoomDTO roomDTO = new RoomDTO(1, "Phòng Đơn 1", roomCategoryDTO, "abcxyz", 350000, false);
        when(roomService.updateRoom(roomDTO)).thenReturn(roomDTO);
        assertThat(roomController.updateRoom(roomDTO).getRoomId()).isEqualTo(1);
        assertThat(roomController.updateRoom(roomDTO).getImage().length()).isGreaterThan(1);

    }
    @Test
    public void deleteRoomTest(){
        RoomCategoryDTO roomCategoryDTO = new RoomCategoryDTO(1, "Single", 2, "1 giường 1 tủ lạnh");
        RoomDTO roomDTO = new RoomDTO(1, "Phòng Đơn 1", roomCategoryDTO, "abcxyz", 350000, false);
        when(roomService.deleteRoom(roomDTO.getRoomId())).thenReturn(true);
        assertThat(roomController.deleteRoom(roomDTO.getRoomId())).isTrue();
    }
}
