package com.example.trainproject.base.Service;

import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    public List<Transaction> getAlltransaction(){
        return transactionRepository.findAll();
    }

    public Transaction findtTransaction(Transaction transaction){
        return transactionRepository.save(transaction);
    }


}
