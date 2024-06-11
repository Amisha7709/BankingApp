package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.AccountDto;
import bankingapplication.entity.Account;
import bankingapplication.entity.Bank;
import bankingapplication.entity.Customer;
import bankingapplication.entity.Transaction;
import bankingapplication.enum1.Constant;
import bankingapplication.enum1.SavingOrCurrentBalance;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.BankRepo;
import bankingapplication.repo.CustomerRepo;
import bankingapplication.repo.TransactionRepo;
import bankingapplication.service.AccountService;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Data
@Slf4j
public class AccountServiceImpl implements AccountService {

  @Autowired
  private BankRepo bankRepo;
  @Autowired
  private AccountRepo accountRepo;
  @Autowired
  private CustomerRepo customerRepo;
  @Autowired
  private TransactionRepo transactionRepo;

  @Override
  public String saveAccountNo(AccountDto accountDto) {

    Account account = dtoToEntity(accountDto);
    List<Account> byCustomerAndBankAndAccountType = accountRepo.findByUserInfoAndBankAndAccountType(
        account.getUserInfo(), account.getBank(), account.getAccountType());
    if (byCustomerAndBankAndAccountType.size() > 0) {
      throw new BankException(
          String.format("Customer already have %s account", account.getAccountType()),
          HttpStatus.BAD_REQUEST);
    } else if ((account.getAccountType().name().equals(SavingOrCurrentBalance.SAVING.name())) && (
        account.getAmount() < SavingOrCurrentBalance.SAVING.getAmount())) {
      return ApplicationConstant.MINIMUM_BALANCE_FOR + " saving account 5000";
    } else if ((account.getAccountType().name().equals(SavingOrCurrentBalance.CURRENT.name())) && (
        account.getAmount() < SavingOrCurrentBalance.CURRENT.getAmount())) {
      return ApplicationConstant.MINIMUM_BALANCE_FOR + " current account 10000";
    }
    String random;
    Account byAccNo;
    do {
      random = RandomStringUtils.random(12, false, true);
      byAccNo = accountRepo.findByAccNo(random);
    } while (!Objects.isNull(byAccNo));
    account.setAccNo(random);

    Account save = accountRepo.save(account);
    if (!Objects.isNull(save)) {
      return ApplicationConstant.ACCOUNT_CREATE;
    }
    throw new BankException(ApplicationConstant.ERROR_OCCURRED_WHILE_SAVING_INTO_THE_DATA_BASE,
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Account dtoToEntity(AccountDto accountDto) {
    Account account = new Account();
    account.setAccountType(SavingOrCurrentBalance.SAVING);
    account.setAccountType(SavingOrCurrentBalance.CURRENT);
    account.setIfscCode(accountDto.getIfscCode());
    account.setAmount(accountDto.getAmount());
    account.setAccNo(accountDto.getAccNo());
    account.setName(accountDto.getName());
    account.setAccountType(accountDto.getAccountType());
    Bank byId = bankRepo.findById(accountDto.getBankId()).orElse(null);
    if (byId == null) {
      throw new BankException("The bank " + ApplicationConstant.ID_INVALID + accountDto.getBankId(),
          HttpStatus.BAD_REQUEST);
    }
    account.setBank(byId);

    Customer customerInfo = customerRepo.findById(accountDto.getCustomerId()).orElse(null);
    if (customerInfo == null) {
      throw new BankException(
          "The customer " + ApplicationConstant.ID_INVALID + accountDto.getCustomerId(),
          HttpStatus.BAD_REQUEST);
    }
    account.setUserInfo(customerInfo);
    return account;
  }

  public List<AccountDto> getAccount(String content) {
    List<Account> accounts = accountRepo.findByTitleContent("%" + content + "%");
    if (accounts.isEmpty()) {
      throw new BankException(ApplicationConstant.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return accounts.stream().map(this::entityToDto).collect(Collectors.toList());
    }
  }

  @Override
  public List<AccountDto> getAllAccount() {
    List<Account> accounts = accountRepo.findAll();
    if (accounts.isEmpty()) {
      throw new BankException(ApplicationConstant.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    } else {
      return accounts.stream().map(this::entityToDto).collect(Collectors.toList());
    }
  }

  @Override
  public String updateAccount(AccountDto accountDto, Long accountId) {
    Account account = accountRepo.findById(accountId).orElseThrow(
        () -> new BankException(ApplicationConstant.ACCOUNT_ID_NOT_FOUND, HttpStatus.NOT_FOUND));
    account.setAccountType(accountDto.getAccountType());
    account.setName(accountDto.getName());
    accountRepo.save(account);
    return ApplicationConstant.ACCOUNT_UPDATED;
  }

  @Override
  public String deleteAccount(Long accountId) {
    Account account = accountRepo.findById(accountId).orElseThrow(
        () -> new BankException(ApplicationConstant.ACCOUNT_ID_NOT_FOUND, HttpStatus.NOT_FOUND));
    if (!Objects.isNull(account)) {
      accountRepo.deleteById(accountId);
    }
    return ApplicationConstant.ACCOUNT_DELETED;
  }

  @Override
  public List<AccountDto> getAllByBankId(Long bankId) {
    Bank bank = bankRepo.findById(bankId).orElseThrow(
        () -> new BankException(ApplicationConstant.BANK_IS_NOT_FOUND, HttpStatus.BAD_REQUEST));
    List<Account> accounts = accountRepo.findByBank(bank);
    if (accounts.isEmpty()) {
      throw new BankException(ApplicationConstant.ACCOUNT_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    return accounts.stream().map(this::entityToDto).collect(Collectors.toList());
  }

  @Override
  @Scheduled(cron = "0 0 0 * * *")
  public void interest() {
    accountRepo.findAll().stream().filter(Objects::nonNull).forEach(ele -> {
      Transaction transaction = new Transaction();
      double amount = ele.getAmount();
      double interest = amount * Constant.INTEREST.getConstant() / Constant.TOTAL.getConstant();
      double totalAmount = interest + amount;
      ele.setAmount(totalAmount);
      accountRepo.save(ele);
      transaction.setName(ApplicationConstant.INTEREST_CREDITED);
      transaction.setAccountNumberFrom(ele.getAccNo());
      transaction.setIfscCode(ele.getIfscCode());
      transaction.setAccountNumberTo(ele.getAccNo());
      transaction.setDate(LocalDate.now());
      transaction.setAmount(totalAmount);
      transaction.setAccountType(ele.getAccountType());
      transaction.setBlocked(ele.isBlocked());
      transactionRepo.save(transaction);
    });
  }

  private AccountDto entityToDto(Account account) {
    AccountDto accountDto = new AccountDto();
    accountDto.setBankId(account.getBank().getBankId());
    accountDto.setCustomerId(account.getUserInfo().getCustomerId());
    accountDto.setAccountId(account.getAccountId());
    BeanUtils.copyProperties(account, accountDto);
    return accountDto;
  }
}
