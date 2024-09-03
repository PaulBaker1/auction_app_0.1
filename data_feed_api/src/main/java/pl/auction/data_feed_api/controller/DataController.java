package pl.auction.data_feed_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction.data_feed_api.model.FeedData;
import pl.auction.data_feed_api.model.FeedDataRepository;
import pl.auction.data_feed_api.service.DatabaseService;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private FeedDataRepository feedDataRepository;
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/{id}")
    public ResponseEntity<FeedData> getData(@PathVariable Long id) {
        return feedDataRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateData(@PathVariable Long id, @RequestBody FeedData newData) {
        return feedDataRepository.findById(id)
                .map(existingData -> {
                    StringBuilder result = new StringBuilder();

                    // Print the initial state
                    result.append("Initial data: ").append(formatDataLog(existingData)).append("\n");

                    // Perform update and check for changes
                    boolean changed = false;

                    if (!existingData.getItem().equals(newData.getItem())) {
                        result.append(String.format("Item: %s -> %s\n", existingData.getItem(), newData.getItem()));
                        existingData.setItem(newData.getItem());
                        changed = true;
                    } else {
                        result.append(String.format("Item: %s\n", existingData.getItem()));
                    }

                    if (!existingData.getAmount().equals(newData.getAmount())) {
                        result.append(String.format("Amount: %s -> %s\n", existingData.getAmount(), newData.getAmount()));
                        existingData.setAmount(newData.getAmount());
                        changed = true;
                    } else {
                        result.append(String.format("Amount: %s\n", existingData.getAmount()));
                    }

                    if (!existingData.getPaymentMethod().equals(newData.getPaymentMethod())) {
                        result.append(String.format("Payment Method: %s -> %s\n", existingData.getPaymentMethod(), newData.getPaymentMethod()));
                        existingData.setPaymentMethod(newData.getPaymentMethod());
                        changed = true;
                    } else {
                        result.append(String.format("Payment Method: %s\n", existingData.getPaymentMethod()));
                    }

                    if (changed) {
                        // Save changes
                        feedDataRepository.save(existingData);
                        result.append("Changes have been saved.\n");
                    } else {
                        result.append("No changes detected.\n");
                    }

                    return ResponseEntity.ok(result.toString());
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable Long id) {
        if(feedDataRepository.existsById(id)) {
            databaseService.deleteItem(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/item/{itemName}")
    public ResponseEntity<Void> deleteDataByItemName(@PathVariable String itemName) {
        databaseService.deleteAllDataByItem(itemName);
        return ResponseEntity.ok().build();
    }

    private String formatDataLog(FeedData data) {
        return String.format("ID: %d, Item: %s, Amount: %s, Payment Method: %s",
                data.getId(), data.getItem(), data.getAmount(), data.getPaymentMethod());
    }
}