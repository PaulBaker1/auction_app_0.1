package pl.auction.data_feed_api.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.hibernate.PropertyValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import pl.auction.data_feed_api.model.AuditLog;
import pl.auction.data_feed_api.model.AuditLogRepository;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.FeedDataRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DatabaseService {
    @Autowired
    private FeedDataRepository feedDataRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    @Retryable(
            value = {TransientDataAccessException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000)
    )
    @Transactional
    public void saveData(List<FeedData> dataList) {
        try {
            for (FeedData data : dataList) {
                if (data.getItem() == null || data.getPaymentMethod() == null) {
                    logger.warn("Skipping entity with missing data: {}", formatDataLog(data));
                    entityManager.detach(data);  // Ensure skipped entity is detached
                    continue;
                }

                List<FeedData> existingData = feedDataRepository.findByItemAndAmountAndPaymentMethod(data.getItem(), data.getAmount(), data.getPaymentMethod());
                if (existingData.isEmpty()) {
                    if (data.getVersion() == null) {
                        data.setVersion(0L);  // Initialize version if necessary
                    }
                    feedDataRepository.save(data);
                    logger.info("New data saved: {}", formatDataLog(data));
                    saveAuditLog("New data saved", data);
                } else {
                    logger.info("Data already loaded: {}", formatDataLog(existingData.get(0)));
                    if (shouldUpdateData(existingData.get(0), data)) {
                        updateExistingData(existingData.get(0), data);
                        logger.info("Data updated: {}", formatDataLog(existingData.get(0)));
                        saveAuditLog("Data updated", data);
                    }
                }
            }
        } catch (PropertyValueException e) {
            logger.error("PropertyValueException occurred: ", e);
            // Handle specific Hibernate exception
        } catch (Exception e) {
            logger.error("General error occurred while saving data: ", e);
            throw new RuntimeException(e);
        }
    }

    public void deleteItem(Long id) {
        Optional<FeedData> data = feedDataRepository.findById(id);
        if (data.isPresent()) {
            feedDataRepository.delete(data.get());
            logger.info("Data deleted: {}", formatDataLog(data.get()));
            saveAuditLog("Data deleted", data.get());
            sendDeletionNotification(data.get());  // Send notification
        } else {
            logger.warn("Data with ID {} not found for deletion", id);
        }
    }

    public void deleteAllDataByItem(String item) {
        List<FeedData> dataList = feedDataRepository.findByItem(item);
        if (!dataList.isEmpty()) {
            feedDataRepository.deleteAll(dataList);
            logger.info("All data for item '{}' deleted", item);
            for (FeedData data : dataList) {
                saveAuditLog("Data deleted", data);
                sendDeletionNotification(data);  // Send notification for each deletion
            }
        } else {
            logger.warn("No data found for item '{}' to delete", item);
        }
    }

    private void sendDeletionNotification(FeedData data) {
        String message = String.format("Data deleted: ID: %d, Item: %s, Amount: %s, Payment Method: %s",
                data.getId(), data.getItem(), data.getAmount(), data.getPaymentMethod());
        // Log the message
        logger.info("Notification: {}", message);

        // If you have a messaging system, you could send the notification there, e.g.,
        // notificationService.send(message);
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

    private boolean shouldUpdateData(FeedData existing, FeedData newData) {
        return !existing.equals(newData);
    }

    private void updateExistingData(FeedData existing, FeedData newData) {
        existing.setAmount(newData.getAmount());
        existing.setPaymentMethod(newData.getPaymentMethod());
        feedDataRepository.save(existing);
        saveAuditLog("Data updated", existing);
    }

    private String formatDataLog(FeedData data) {
        return String.format("ID: %d, Item: %s, Amount: %s, Payment Method: %s",
                data.getId(), data.getItem(), data.getAmount(), data.getPaymentMethod());
    }
}

