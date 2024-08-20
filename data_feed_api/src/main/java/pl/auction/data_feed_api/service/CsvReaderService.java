package pl.auction.data_feed_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.auction.data_feed_api.model.FeedData;

//import org.apache.commons.validator.routines.EmailValidator;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvReaderService {
    private static final Logger logger = LoggerFactory.getLogger(CsvReaderService.class);

    public List<FeedData> readCsv(File file) {
        List<FeedData> newFeedData = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (isValidData(values)) {
                    FeedData feedData = FeedData.builder()
                            .item(values[0])
                            .amount(new BigDecimal(values[1]))
                            .paymentMethod(values[2])
                            .build();
                    newFeedData.add(feedData);
                } else {
                    logger.warn("Invalid data found and skipped: {}", line);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", file.getAbsolutePath(), e);
        } catch (IOException e) {
            logger.error("Error reading file: {}", file.getAbsolutePath(), e);
        }
        return newFeedData;
    }

    private boolean isValidData(String[] values) {
        if (values.length != 3) {
            logger.warn("Invalid data length: {}", values.length);
            return false;
        }

        if(values[0] == null || values[0].isEmpty()) {
            logger.warn("Invalid product cane: {}", values[0]);
            return false;
        }

        // Validate email
//        if (!EmailValidator.getInstance().isValid(values[0])) {
//            logger.warn("Invalid email address: {}", values[0]);
//            return false;
//        }

        // Validate amount
        try {
            new BigDecimal(values[1]);
        } catch (NumberFormatException e) {
            logger.warn("Invalid amount: {}", values[1]);
            return false;
        }
        if (values[2] == null || values[2].trim().isEmpty()) {
            logger.warn("Invalid payment method: {}", values[2]);
            return false;
        }
        return true;
    }
}
