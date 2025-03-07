package com.example.trainproject.base.Controller;

import com.example.trainproject.base.Model.Transaction;
import com.example.trainproject.base.Service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

  @GetMapping("/get-all-transaction")
  public List<Transaction> getAllTransaction() {

    return transactionService.getAlltransaction();
  }

  @PostMapping("/save-transaction")
  public Transaction saveTransaction(@RequestBody Transaction transaction) {

    return transactionService.findtTransaction(transaction);
  }



}
