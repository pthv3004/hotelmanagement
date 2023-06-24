package swp.server.hotelmanagement.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.BookingDTO;
import swp.server.hotelmanagement.dtos.RoomDTO;
import swp.server.hotelmanagement.entities.BookingEntity;
import swp.server.hotelmanagement.entities.RoomEntity;
import swp.server.hotelmanagement.mappers.MapperUtil;
import swp.server.hotelmanagement.repositories.AccountRepository;
import swp.server.hotelmanagement.repositories.BookingRepository;
import swp.server.hotelmanagement.repositories.RoomRepository;
import swp.server.hotelmanagement.services.BookingService;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final MapperUtil mapperUtil;
    private final AccountRepository accountRepository;
    private final RoomRepository roomRepository;

    @Override
    public List<BookingDTO> getAllBooking() {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        try {
            List<BookingEntity> bookingEntities = bookingRepository.findAll();
            bookingEntities.stream().forEach(bookingEntity -> {
                BookingDTO bookingDTO = new BookingDTO(bookingEntity.getBookingId(),
                        mapperUtil.mapToAccountDTO(bookingEntity.getAccountEntity()),
                        bookingEntity.getStatus(),
                        bookingEntity.getCheckingDate().toString(),
                        bookingEntity.getCheckOutDate().toString(),
                        mapperUtil.mapToListRoomDTO(bookingEntity.getBookingDetailEntities().stream().collect(Collectors.toList())),
                        mapperUtil.mapToListServiceDTO(bookingEntity.getBookingServiceDetailEntities().stream().collect(Collectors.toList())));
                bookingDTOList.add(bookingDTO);
            });
            return bookingDTOList;
        } catch (Exception e) {
            e.getMessage();
            return bookingDTOList;
        }
    }

    @Override
    public BookingDTO getBookingById(int bookingId) {
        try {
            BookingEntity bookingEntity = bookingRepository.findById(bookingId).get();
            BookingDTO bookingDTO = new BookingDTO(bookingEntity.getBookingId(),
                    mapperUtil.mapToAccountDTO(bookingEntity.getAccountEntity()),
                    bookingEntity.getStatus(),
                    bookingEntity.getCheckingDate().toString(),
                    bookingEntity.getCheckOutDate().toString(),
                    mapperUtil.mapToListRoomDTO(bookingEntity.getBookingDetailEntities().stream().collect(Collectors.toList())),
                    mapperUtil.mapToListServiceDTO(bookingEntity.getBookingServiceDetailEntities().stream().collect(Collectors.toList())));
            return bookingDTO;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    @Override
    public BookingDTO updateBooking(BookingDTO bookingDTO) {
        try {
            BookingEntity bookingEntity = bookingRepository.findById(bookingDTO.getBookingId()).get();
            bookingEntity.setStatus(bookingDTO.getStatus());
            bookingEntity.setCheckingDate(Timestamp.valueOf(bookingDTO.getCheckinDate()));
            bookingEntity.setCheckOutDate(Timestamp.valueOf(bookingDTO.getCheckoutDate()));
            bookingRepository.save(bookingEntity);
            return bookingDTO;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    private BookingEntity mapBookingDtoToEntity(BookingDTO bookingDTO, BookingEntity bookingEntity) {
        bookingEntity.setAccountEntity(accountRepository.findById(bookingDTO.getAccountId().getId()).get());
        bookingEntity.setBookingDetailEntities(Set.copyOf(mapperUtil.mapRoomDTOsToEntities(bookingDTO.getRoomDTOList())));
        bookingEntity.setBookingServiceDetailEntities(Set.copyOf(mapperUtil.mapToListServiceEntity(bookingDTO.getServiceDTOList())));
        bookingEntity.setStatus(bookingDTO.getStatus());
        bookingEntity.setCheckingDate(Timestamp.valueOf(bookingDTO.getCheckinDate()));
        bookingEntity.setCheckOutDate(Timestamp.valueOf(bookingDTO.getCheckoutDate()));
        return bookingEntity;
    }

    @Override
    public BookingDTO createNewBooking(BookingDTO bookingDTO) {
        try {
            BookingEntity bookingEntity = new BookingEntity();
            if (checkBooking(bookingDTO)) {
                bookingRepository.save(mapBookingDtoToEntity(bookingDTO, bookingEntity));
                updateStatusRoomAfterBooking(bookingDTO.getRoomDTOList());
                return bookingDTO;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }

    @Override
    public boolean deleteBooking(int bookingId) {
        try {
            bookingRepository.deleteById(bookingId);
            return true;
        } catch (Exception e) {
            e.getMessage();
        }
        return false;
    }

    /**
     * validate Before booking
     *
     * @param bookingDTO
     * @return result after checkin
     */
    private boolean checkBooking(BookingDTO bookingDTO) {
        if (bookingDTO.getAccountId() != null) {
            //check checkingDate and checkOutDate
            if (validateCheckInCheckOutDate(bookingDTO.getCheckinDate(),
                    bookingDTO.getCheckoutDate())) {
                // checkRoom is still not rented
                if (checkRoomIsNotRented(bookingDTO.getRoomDTOList())) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * @param checkInDateStr
     * @param checkOutDateStr
     * @return result of check checkinDate is before checkOutDate
     */
    private boolean validateCheckInCheckOutDate(String checkInDateStr, String checkOutDateStr) {
        Timestamp checkInDate = Timestamp.valueOf(checkInDateStr);
        Timestamp checkOutDate = Timestamp.valueOf(checkOutDateStr);
        return checkInDate.before(checkOutDate);
    }

    /**
     * check Room is still not rented yet
     *
     * @param roomDTOS
     * @return true or false
     */
    private boolean checkRoomIsNotRented(List<RoomDTO> roomDTOS) {
        return roomDTOS.stream().anyMatch(roomDTO -> roomDTO.isRent());
    }

    /**
     * set status of room is Rented after booking
     *
     * @param roomDTOS
     */
    private void updateStatusRoomAfterBooking(List<RoomDTO> roomDTOS) {
        roomDTOS.stream().forEach(roomDTO -> {
            roomDTO.setRent(true);
            RoomEntity roomEntity = mapperUtil.mapRoomDTOToEntity(roomDTO);
            roomRepository.save(roomEntity);
        });
    }
}