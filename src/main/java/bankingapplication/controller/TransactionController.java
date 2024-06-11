package bankingapplication.controller;

import bankingapplication.constant.UrlConstant;
import bankingapplication.entity.Transaction;
import bankingapplication.service.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class TransactionController {

  @Autowired
  private TransactionService transactionService;

  @GetMapping(UrlConstant.ACCOUNT_TRANSACTION_BY_ID)
  public ResponseEntity<List<Transaction>> transaction(@PathVariable String accountNumberFrom) {
    List<Transaction> transactions = transactionService.transaction(accountNumberFrom);
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }

  @GetMapping(UrlConstant.TRANSACTION_BY_DAYS)
  public ResponseEntity<List<Transaction>> sevenDaysTransaction(@PathVariable Long numberOfDays) {
    List<Transaction> transactionList = transactionService.transactionByDays(numberOfDays);
    return new ResponseEntity<>(transactionList, HttpStatus.CREATED);
  }


}
