package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Dto.GenericPaginatedResponse;
import com.example.trainproject.base.Dto.TransactionRequest;
import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Service.TransactionService;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

  @GetMapping("/{pageSize}/{pageNumber}")
  public GenericPaginatedResponse<List<Transaction>> getAllTransaction(
      @PathVariable int pageSize,
      @PathVariable int pageNumber,
      @RequestParam(required = false) String sort
  ) {
    Page<Transaction> transactions =  transactionService.getAllTransaction(pageSize,pageNumber,sort);
    List<Transaction> transactionList = transactions.getContent();
    return GenericPaginatedResponse.success(transactionList,
        pageNumber,
        pageSize,
        transactions.getTotalElements());
  }

  @PostMapping("/save-transaction")
  public Transaction saveTransaction(@RequestBody TransactionRequest transactionRequest) {
    return transactionService.saveTransaction(transactionRequest);
  }


  @GetMapping("/find-transaction/{transactionId}")
  public Optional<Transaction> getTransactionById(
    @PathVariable UUID transactionId
  ){
    Optional<Transaction> transaction =  transactionService.getTransactionById(transactionId);
    return transaction;
  }

//  Situation | Best annotation
//  Reading a list / object | @Cacheable
//  Saving/updating something that invalidates list | @CacheEvict
//  Saving/updating one specific object (by id) | @CachePut

}
