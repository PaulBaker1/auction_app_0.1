package pl.auction.data_feed_api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.InvalidDataException;

//import org.apache.commons.validator.routines.EmailValidator;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CsvReaderService {

    private static final Logger logger = LoggerFactory.getLogger(CsvReaderService.class);

    public List<FeedData> readCsv(String filePath) {
        List<FeedData> validDataList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int lineNumber = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String[] values = line.split(",");

                if (values.length < 4) {
                    logger.warn("Line {} skipped: Insufficient data fields - {}", lineNumber, line);
                    continue;
                }

                try {
                    Long id = Long.parseLong(values[0]);
                    String item = values[1];
                    BigDecimal amount = new BigDecimal(values[2]);
                    String paymentMethod = values[3];
                    // add id in method
                    if (validateData(item, amount, paymentMethod)) {
                        FeedData data = FeedData.builder()
                                .id(id) // if applicable; usually, you wouldn't set the ID manually if it's auto-generated
                                .item(item)
                                .amount(amount)
                                .userId(null) // adjust based on your CSV data structure
                                .email(null) // adjust based on your CSV data structure
                                .paymentMethod(paymentMethod)
                                .build();
                        // Treats the method as if the userId and email needs to be provided, not to be null
                        validDataList.add(data);
                    } else {
                        logger.warn("Line {} skipped: Data validation failed - {}", lineNumber, line);
                    }

                } catch (NumberFormatException e) {
                    logger.warn("Line {} skipped: Invalid number format - {}", lineNumber, line);
                }
            }
        } catch (IOException e) {
            logger.error("Error reading CSV file", e);
        }

        return validDataList;
    }

    private boolean validateData(String item, BigDecimal amount, String paymentMethod) {
        // Add custom validation logic here
        return item != null && !item.isEmpty() && amount != null && amount.compareTo(BigDecimal.ZERO) > 0 && paymentMethod != null && !paymentMethod.isEmpty();
    }


    private boolean isValidData(String[] values) {
        if (values.length != 3) { // Ensure there are exactly three columns
            return false;
        }

        // Validate amount (column 2)
        try {
            BigDecimal amount = new BigDecimal(values[1]);
            if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                return false; // Amount must be positive
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Validate paymentMethod or third numeric value (column 3)
        try {
            BigDecimal paymentMethod = new BigDecimal(values[2]);
            if (paymentMethod.compareTo(BigDecimal.ZERO) <= 0) {
                return false; // PaymentMethod (if numeric) must be positive
            }
        } catch (NumberFormatException e) {
            // Optionally log or handle as needed
            return false;
        }

        return true;
    }
}