package pl.auction.auction_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private BigDecimal amount;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "auction_item_id")
    private AuctionItem auctionItem;
    private Long userId;
    private String userEmail;
    private String paymentMethod;

    @Override
    public String toString() {
        return "Bid{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", amount=" + amount +
                ", auctionItemId=" + (auctionItem != null ? auctionItem.getId() : null) +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
