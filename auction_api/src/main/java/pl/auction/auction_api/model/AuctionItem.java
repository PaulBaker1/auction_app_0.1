package pl.auction.auction_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private double startingPrice;
    private double discount;

    @JsonIgnore
    @OneToMany(mappedBy = "auctionItem", cascade = CascadeType.ALL)
    private List<Bid> bids;
    @Transient
    private int bidsCount;
    @Transient
    private double currentBid;

    @Override
    public String toString() {
        return "AuctionItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                // Exclude bids or include a summary count instead
                ", bidsCount=" + (bids != null ? bids.size() : 0) +
                '}';
    }

    public double getDiscountedPrice() {return startingPrice * (1 - discount / 100);}

    public double getCurrentBid() {
        return bids.stream()
                .mapToDouble(bid -> bid.getAmount().doubleValue())
                .max()
                .orElse(startingPrice);
    }
}

