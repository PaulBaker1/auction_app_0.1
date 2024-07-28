package pl.auction.user_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.auction.user_api.model.TransactionRecord;
//import pl.auction.user_api.model.User;
import pl.auction.user_api.service.TransactionRecordService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionRecordController {

    @Autowired
    private TransactionRecordService transactionRecordService;

    @GetMapping
    public List<TransactionRecord> getAllTransactionRecords() {
        return transactionRecordService.getAllTransactionRecords();
    }

    @GetMapping("/{id}")
    public TransactionRecord getTransactionRecordById(@PathVariable Long id) {
        return transactionRecordService.getTransactionRecordById(id);
    }

    @PostMapping
    public TransactionRecord createTransactionRecord(@RequestBody TransactionRecord transactionRecord) {
        return transactionRecordService.createTransactionRecord(transactionRecord);
    }

    @PutMapping("/{id}")
    public TransactionRecord updateTransactionRecord(@PathVariable Long id, @RequestBody TransactionRecord transactionDetails) {
        return transactionRecordService.updateTransactionRecord(id, transactionDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTransactionRecord(@PathVariable Long id) {
        transactionRecordService.deleteTransactionRecord(id);
    }
}