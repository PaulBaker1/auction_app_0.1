package pl.auction.user_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.auction.user_api.model.TransactionRecord;
import pl.auction.user_api.model.TransactionRecordRepository;

import java.util.List;

@Service
public class TransactionRecordService {
    @Autowired
    private TransactionRecordRepository transactionRecordRepository;

    public List<TransactionRecord> getAllTransactionRecords() {
        return transactionRecordRepository.findAll();
    }

    public TransactionRecord getTransactionRecordById(Long id) {
        return transactionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionRecord not found with id: " + id));
    }

    public TransactionRecord createTransactionRecord(TransactionRecord transactionRecord) {
        return transactionRecordRepository.save(transactionRecord);
    }

    public TransactionRecord updateTransactionRecord(Long id, TransactionRecord transactionDetails) {
        TransactionRecord transactionRecord = transactionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionRecord not found with id: " + id));

        transactionRecord.setId(transactionDetails.getId());
        transactionRecord.setAmount(transactionDetails.getAmount());
        transactionRecord.setTimestamp(transactionDetails.getTimestamp());

        return transactionRecordRepository.save(transactionRecord);
    }

    public void deleteTransactionRecord(Long id) {
        TransactionRecord transactionRecord = transactionRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TransactionRecord not found with id: " + id));

        transactionRecordRepository.delete(transactionRecord);
    }
}