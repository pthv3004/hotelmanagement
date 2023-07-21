package swp.server.hotelmanagement.services.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import swp.server.hotelmanagement.dtos.PaymentDTO;
import swp.server.hotelmanagement.entities.PaymentEntity;
import swp.server.hotelmanagement.repositories.PaymentRepository;
import swp.server.hotelmanagement.services.PaymentService;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private PaymentRepository paymentRepository;

    @Override
    public List<PaymentDTO> getAllPayments() {
        List<PaymentEntity> paymentEntities = paymentRepository.findAll();
        List<PaymentDTO> paymentDTOS = new ArrayList<>();
        paymentEntities.stream().forEach(paymentEntity -> {
            PaymentDTO paymentDTO = new PaymentDTO(paymentEntity.getPaymentId(), paymentEntity.getMethod());
            paymentDTOS.add(paymentDTO);
        });
        return paymentDTOS;
    }

    @Override
    public PaymentDTO getPayment(int paymentId) {
        try {
            PaymentEntity paymentEntity = paymentRepository.findById(paymentId).get();
            PaymentDTO paymentDTO = new PaymentDTO(paymentEntity.getPaymentId(), paymentEntity.getMethod());
            return paymentDTO;
        } catch (Exception e) {
            e.getMessage();
        }
        return null;
    }
}
