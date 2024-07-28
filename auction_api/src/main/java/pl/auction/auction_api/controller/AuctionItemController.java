package pl.auction.auction_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction.auction_api.model.AuctionItem;
import pl.auction.auction_api.service.AuctionItemService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions/items")
public class AuctionItemController {

    @Autowired
    private AuctionItemService auctionItemService;

    @GetMapping
    public List<AuctionItem> getAllAuctionItems() {
        return auctionItemService.getAllAuctionItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionItem> getAuctionItemById(@PathVariable Long id) {
        AuctionItem auctionItem = auctionItemService.getAuctionItemById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));
        return ResponseEntity.ok(auctionItem);
    }

    @PostMapping
    public AuctionItem createAuctionItem(@RequestBody AuctionItem auctionItem) {
        return auctionItemService.addAuctionItem(auctionItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuctionItem> updateAuctionItem(@PathVariable Long id, @RequestBody AuctionItem auctionItemDetails) {
        AuctionItem updatedAuctionItem = auctionItemService.updateAuctionItem(id, auctionItemDetails);
        return ResponseEntity.ok(updatedAuctionItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuctionItem(@PathVariable Long id) {
        auctionItemService.deleteAuctionItem(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/{discount}/discount")
    public ResponseEntity<AuctionItem> updateAuctionItemDiscount(@PathVariable Long id, @PathVariable Double discount) {
        AuctionItem updatedAuctionItemDiscount = auctionItemService.updateAuctionItemDiscount(id, discount);
        return ResponseEntity.ok(updatedAuctionItemDiscount);
    }
}