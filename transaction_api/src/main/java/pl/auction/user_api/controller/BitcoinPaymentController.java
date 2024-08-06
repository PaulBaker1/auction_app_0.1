package pl.auction.user_api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments/bitcoin")
public class BitcoinPaymentController {

    private final Map<String, String> mockPaymentDatabase = new HashMap<>();

    @PostMapping("/create")
    public Map<String, String> createBitcoinTransaction(@RequestParam Double amount) {
        String transactionId = UUID.randomUUID().toString();
        String paymentUrl = "https://mock-bitcoin-payment.com/transaction/" + transactionId;

        mockPaymentDatabase.put(transactionId, "Pending");

        Map<String, String> response = new HashMap<>();
        response.put("transactionId", transactionId);
        response.put("paymentUrl", paymentUrl);
        response.put("status", "Pending");

        return response;
    }

    @GetMapping("/status/{transactionId}")
    public Map<String, String> getTransactionStatus(@PathVariable String transactionId) {
        String status = mockPaymentDatabase.getOrDefault(transactionId, "Not Found");

        Map<String, String> response = new HashMap<>();
        response.put("transactionId", transactionId);
        response.put("status", status);

        return response;
    }

    @GetMapping("/update/{transactionID}")
    public Map<String, String> updateTransactionStatus(@PathVariable String transactionID, @RequestParam String status) {
        mockPaymentDatabase.put(transactionID, status);

        Map<String, String> response = new HashMap<>();
        response.put("transactionId", transactionID);
        response.put("status", status);

        return response;
    }
}
