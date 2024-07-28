package pl.auction.auction_api.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import pl.auction.auction_api.model.AuctionItem;
import pl.auction.auction_api.model.AuctionItemRepository;
import pl.auction.auction_api.model.Bid;
import pl.auction.auction_api.model.BidRepository;

import java.math.BigDecimal;
import java.util.Arrays;

@Configuration
public class BidDataInitializer {
    @Autowired
    private AuctionItemRepository auctionItemRepository;

    @Autowired
    private BidRepository bidRepository;

    @PostConstruct
    public void initData() {
        AuctionItem item1 = AuctionItem.builder()
                .name("Laptop")
                .description("High performance laptop")
                .startingPrice(500.00)
                .build();
        auctionItemRepository.save(item1);

        AuctionItem item2 = AuctionItem.builder()
                .name("Smartphone")
                .description("Latest model smartphone")
                .startingPrice(300.00)
                .build();
        auctionItemRepository.save(item2);

        AuctionItem item3 = AuctionItem.builder()
                .name("Headphones")
                .description("Noise-cancelling headphones")
                .startingPrice(100.00)
                .build();
        auctionItemRepository.save(item3);

        Bid bid1 = Bid.builder()
                .amount(BigDecimal.valueOf(550.00))
                .userId(1L)
                .auctionItem(item1)
                .build();
        bidRepository.save(bid1);

        Bid bid2 = Bid.builder()
                .amount(BigDecimal.valueOf(600.00))
                .userId(2L)
                .auctionItem(item1)
                .build();
        bidRepository.save(bid2);

        item1.setBids(Arrays.asList(bid1, bid2));
        auctionItemRepository.save(item1);
    }
}
