package pl.auction.data_feed_api.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.auction.data_feed_api.model.AuditLog;
import pl.auction.data_feed_api.model.AuditLogRepository;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.FeedDataRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    private FeedDataRepository feedDataRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    @Transactional
    public void deleteItemWithData(Long itemId) {
        List<FeedData> associatedData = feedDataRepository.findByItem(String.valueOf(itemId));

        if (!associatedData.isEmpty()) {
            for (FeedData data : associatedData) {
                saveAuditLog("Data deleted", data);
                feedDataRepository.delete(data);
            }
            logger.info("All associated data for item ID {} deleted.", itemId);
        }
        saveAuditLog("Item deleted", itemId);
        logger.info("Item with ID {} deleted.", itemId);
    }

    private void saveAuditLog(String action, Long itemId) {
        AuditLog auditLog = AuditLog.builder()
                .action(action)
                .item("Item with ID " + itemId)
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
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
