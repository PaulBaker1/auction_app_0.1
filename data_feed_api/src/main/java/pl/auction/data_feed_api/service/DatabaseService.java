package pl.auction.data_feed_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.FeedDataRepository;


import java.util.List;

@Service
public class DatabaseService {
    @Autowired
    private FeedDataRepository feedDataRepository;
    private static final Logger logger = LoggerFactory.getLogger(DatabaseService.class);

    public void saveData(List<FeedData> dataList) {
        for (FeedData data : dataList) {
            List<FeedData> existingData = feedDataRepository.findByItemAndAmountAndPaymentMethod(data.getItem(), data.getAmount(), data.getPaymentMethod());
            if (existingData.isEmpty()) {
                feedDataRepository.save(data);
                logger.info("New data saved: {}", data);
            } else {
                logger.info("Data already loaded: {}", data);
            }
        }
    }
}

