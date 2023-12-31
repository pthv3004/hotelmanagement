package swp.server.hotelmanagement.services;

import swp.server.hotelmanagement.dtos.BookingDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> getAllBooking();

    BookingDTO getBookingById(int bookingId);

    BookingDTO updateBooking(BookingDTO bookingDTO);

    BookingDTO createNewBooking(BookingDTO bookingDTO);
    List<BookingDTO> getBookingByAccount(int accountId);
    boolean deleteBooking(int bookingId);
}
