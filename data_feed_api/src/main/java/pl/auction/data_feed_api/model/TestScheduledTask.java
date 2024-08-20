package pl.auction.data_feed_api.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TestScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(TestScheduledTask.class);
    @Scheduled(fixedDelay = 5000) // runs every 5 seconds
    public void testJob() {
        logger.info("Test job running...");
    }
}
