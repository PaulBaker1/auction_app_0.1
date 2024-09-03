package pl.auction.auction_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction.auction_api.model.AuctionItem;
import pl.auction.auction_api.model.Bid;
import pl.auction.auction_api.service.AuctionItemService;
import pl.auction.auction_api.service.BidService;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/auctions/items/{itemId}/bids")
public class BidController {
    @Autowired
    private BidService bidService;
    @Autowired
    private AuctionItemService auctionItemService;

    private static final Logger logger = Logger.getLogger(BidController.class.getName());

    @PostMapping
    public ResponseEntity<Bid> createBid(@PathVariable Long itemId, @RequestBody Bid bid) {
        try {
            logger.info("Received bid for item: " + itemId + ", bid: " + bid);
            if (bid.getEmail() == null || bid.getEmail().isEmpty()) {
                logger.severe("User email is missing");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }
            AuctionItem auctionItem = auctionItemService.getAuctionItemById(itemId)
                    .orElseThrow(() -> new RuntimeException("Item not found"));
            bid.setAuctionItem(auctionItem);
            Bid createdBid = bidService.createBid(bid);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBid);
        } catch (RuntimeException e) {
            logger.severe("Error creating bid: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.severe("Error creating bid: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

