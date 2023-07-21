package swp.server.hotelmanagement.services;

import swp.server.hotelmanagement.dtos.PaymentDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentDTO> getAllPayments();
    PaymentDTO getPayment(int id);
}
