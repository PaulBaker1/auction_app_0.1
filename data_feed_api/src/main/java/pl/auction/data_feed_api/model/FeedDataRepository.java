package pl.auction.data_feed_api.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface FeedDataRepository extends JpaRepository<FeedData, Long> {
    List<FeedData> findByItemAndAmountAndPaymentMethod(String item, BigDecimal amount, String paymentMethod);

    List<FeedData> findByItem(String item);
}