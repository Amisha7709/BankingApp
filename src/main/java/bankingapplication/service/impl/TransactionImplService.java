package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.entity.Account;
import bankingapplication.entity.Transaction;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.TransactionRepo;
import bankingapplication.service.TransactionService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TransactionImplService implements TransactionService {

  @Autowired
  private TransactionRepo transactionRepo;
  @Autowired
  private AccountRepo accountRepo;

  @Override
  public List<Transaction> transaction(String accountNumberFrom) {
    Account byAccNo = accountRepo.findByAccNo(accountNumberFrom);
    if (byAccNo==null){
      throw new BankException(ApplicationConstant.ACCOUNT_NOT_FOUND,HttpStatus.NOT_FOUND);
    }
    List<Transaction> transactions = transactionRepo.findByAccountNumberFrom(accountNumberFrom);
    if(transactions.isEmpty()){
      throw  new BankException(ApplicationConstant.NO_TRANSACTION,HttpStatus.BAD_REQUEST);
    }else return transactions;
  }

  @Override
  public List<Transaction> transactionByDays(Long numberOfDays) {
    LocalDate localDate = LocalDate.now();
    LocalDate endDate = localDate.minusDays(numberOfDays);
    List<Transaction> transactions = transactionRepo.findByDateBetween(endDate, localDate);
    if (transactions.isEmpty()) {
      throw new BankException(ApplicationConstant.NO_TRANSACTION_IN_BETWEEN_DAYS,
          HttpStatus.BAD_REQUEST);
    }
    return transactions;
  }
}
