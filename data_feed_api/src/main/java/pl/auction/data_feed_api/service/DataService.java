package pl.auction.data_feed_api.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.auction.data_feed_api.model.AuditLog;
import pl.auction.data_feed_api.model.AuditLogRepository;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.FeedDataRepository;

import java.beans.Transient;
import java.time.LocalDateTime;

@Service
public class DataService {

    @Autowired
    private FeedDataRepository feedDataRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Transient
    public void updateData(Long itemId, FeedData newData) {
        FeedData existingData = feedDataRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException("Item not found with ID " + itemId));
    }

    private boolean shouldUpdateData(FeedData existing, FeedData newData) {
        return !existing.equals(newData);
    }

    private void logChanges(FeedData existingData, FeedData newData) {
        if (!existingData.getAmount().equals(newData.getAmount())) {
            saveAuditLog("Amount changed from " + existingData.getAmount() + " to " + newData.getAmount(), existingData);
        }
        if (!existingData.getPaymentMethod().equals(newData.getPaymentMethod())) {
            saveAuditLog("Payment method changed from " + existingData.getPaymentMethod() + " to " + newData.getPaymentMethod(), existingData);
        }
    }

    private void saveAuditLog(String action, FeedData data) {
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .item(data.getItem())
                .amount(data.getAmount())
                .paymentMethod(data.getPaymentMethod())
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }
}
