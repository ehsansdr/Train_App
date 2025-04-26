package com.example.trainproject.base.Service;

import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public Page<Transaction> getAllTransaction(int pageSize,
        int pageNumber,
        String sort){
        Pageable pageable;
        // sorting part
        if (sort != null) {
            Sort sortOrder = Sort.by(sort.split(",")[0]).ascending();
            if (sort.endsWith("desc")) {
                sortOrder = sortOrder.descending();
            }
            pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
        } else {
            Sort sortOrder = Sort.by("createdAt").descending();
            pageable = PageRequest.of(pageNumber, pageSize, sortOrder);
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id").descending());
        }
        return transactionRepository.findAll(pageable);
    }


    public Transaction saveTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }


}
