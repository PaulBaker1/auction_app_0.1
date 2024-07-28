package pl.auction.user_api.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.auction.user_api.model.User;
import pl.auction.user_api.model.UserRepository;
import pl.auction.user_api.model.TransactionRecord;
import pl.auction.user_api.model.TransactionRecordRepository;

@Configuration
public class TransactionDataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    @PostConstruct
    public void initData() {
        userRepository.save(User.builder().username("John Doe").email("john@example.com").build());
        userRepository.save(User.builder().username("Jane Smith").email("jane@example.com").build());
        userRepository.save(User.builder().username("Alice Johnson").email("alice@example.com").build());

        transactionRecordRepository.save(TransactionRecord.builder().amount(100.0).timestamp("2024-07-19T10:00:00").build());
        transactionRecordRepository.save(TransactionRecord.builder().amount(200.0).timestamp("2024-07-19T11:00:00").build());
        transactionRecordRepository.save(TransactionRecord.builder().amount(300.0).timestamp("2024-07-19T12:00:00").build());
    }
}


