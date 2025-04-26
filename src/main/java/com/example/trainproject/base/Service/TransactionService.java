package com.example.trainproject.base.Service;

import com.example.trainproject.base.Dto.TransactionRequest;
import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Repository.TransactionRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Cacheable(value = "transactions", key = "'page_'+#pageNumber+'_'+#pageSize+'_'+#sort")
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


    @CacheEvict(value = "transactions" , allEntries = true)
    public Transaction saveTransaction(TransactionRequest transactionRequest){
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionRequest.getAmount());

        return transactionRepository.save(transaction);
    }

    @Cacheable(value = "transactions" , key = "#transactionId")
    public Optional<Transaction> getTransactionById(UUID transactionId){
        return transactionRepository.findById(transactionId);
    }

//    Situation | Best annotation
//    Reading a list / object | @Cacheable
//    Saving/updating something that invalidates list | @CacheEvict
//    Saving/updating one specific object (by id) | @CachePut


}
