package pl.auction.data_feed_api.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.service.CsvReaderService;
import pl.auction.data_feed_api.service.DatabaseService;

import java.io.File;
import java.util.List;

@Component
public class DataFeedingJob {

    private static final Logger logger = LoggerFactory.getLogger(DataFeedingJob.class);

    @Autowired
    private CsvReaderService csvReaderService;

    @Autowired
    private DatabaseService databaseService;


    @Scheduled(fixedDelay = 15000) // runs every 15 seconds
    public void dataFeedingJob() {
        logger.info("Data feeding job started.");
        File file = new File("C:\\\\Users\\\\Paul\\\\Desktop\\\\auction_app_v0.1\\\\data_feed_api\\\\csv_files\\\\load-data.csv");
        List<FeedData> newFeedData = csvReaderService.readCsv(file);

        if (newFeedData.isEmpty()) {
            logger.info("No new data to process.");
        } else {
            logger.info("Processing {} new records.", newFeedData.size());
            databaseService.saveData(newFeedData);
            logger.info("Data feeding job completed.");
        }
    }
}
