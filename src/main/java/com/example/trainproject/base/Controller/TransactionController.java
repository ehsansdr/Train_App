package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Dto.GenericPaginatedResponse;
import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Service.TransactionService;
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
  public Transaction saveTransaction(@RequestBody Transaction transaction) {

    return transactionService.saveTransaction(transaction);
  }



}
