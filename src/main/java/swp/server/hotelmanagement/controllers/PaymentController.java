package swp.server.hotelmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp.server.hotelmanagement.dtos.PaymentDTO;
import swp.server.hotelmanagement.services.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/hotel-server/api/v1")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payments")
    public List<PaymentDTO> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/payment/{id}")
    public PaymentDTO getPaymentById(@PathVariable(value = "id") int paymentId) {
        return paymentService.getPayment(paymentId);
    }
}
