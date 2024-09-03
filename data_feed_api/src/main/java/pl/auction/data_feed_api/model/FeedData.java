package pl.auction.data_feed_api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private BigDecimal amount;
    private Long userId;
    private String email;
    private String paymentMethod;
    @Version
    private Long version;

}
