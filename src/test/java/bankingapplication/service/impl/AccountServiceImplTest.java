package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.AccountDto;
import bankingapplication.entity.Account;
import bankingapplication.entity.Bank;
import bankingapplication.entity.Customer;
import bankingapplication.entity.Transaction;
import bankingapplication.enum1.SavingOrCurrentBalance;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.BankRepo;
import bankingapplication.repo.CustomerRepo;
import bankingapplication.repo.TransactionRepo;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static javax.sound.sampled.FloatControl.Type.BALANCE;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AccountServiceImplTest {

    @Autowired
    private AccountServiceImpl accountServiceImpl;

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    private BankRepo bankRepo;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private CustomerRepo customerRepo;

    @Test
    void saveAccountNo() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(1L);
        accountDto.setBankId(1L);
        accountDto.setCustomerId(1L);
        accountDto.setAccountType(SavingOrCurrentBalance.CURRENT);
        accountDto.setBlocked(true);
        accountDto.setName("Nikhil");
        accountDto.setAmount(100000D);
        accountDto.setIfscCode("ASdf1234567");

        Account account = new Account();
        account.setAccountType(SavingOrCurrentBalance.SAVING);
        account.setAccountType(SavingOrCurrentBalance.CURRENT);
        account.setIfscCode(accountDto.getIfscCode());
        account.setAmount(accountDto.getAmount());
        account.setAccNo(accountDto.getAccNo());
        account.setName(accountDto.getName());
        account.setAccountType(accountDto.getAccountType());
        Customer customer = new Customer();
        account.setUserInfo(customer);
        Bank bank = new Bank();
        bank.setBankId(1L);
        account.setBank(bank);
        List<Account> accountList = new ArrayList<>();

        Mockito.when(accountRepo.findByUserInfoAndBankAndAccountType(
                account.getUserInfo(), account.getBank(), account.getAccountType())).thenReturn(accountList);
        Mockito.when(accountRepo.findByAccNo(null)).thenReturn(null);
        Mockito.when(bankRepo.findById(accountDto.getBankId())).thenReturn(Optional.of(bank));
        Mockito.when(customerRepo.findById(accountDto.getCustomerId())).thenReturn(Optional.of(customer));
        Mockito.when(accountRepo.save(Mockito.any())).thenReturn(account);
        assertEquals(ApplicationConstant.ACCOUNT_CREATE, accountService.saveAccountNo(accountDto));

        List<Account> accountList1 = new ArrayList<>();
        Account account1 = new Account();
        accountList1.add(account1);
        Mockito.when(accountRepo.findByUserInfoAndBankAndAccountType(
                account.getUserInfo(), account.getBank(), account.getAccountType())).thenReturn(accountList1);
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.saveAccountNo(accountDto));
        assertEquals(HttpStatus.BAD_REQUEST, bankException.getHttpStatus());

        accountDto.setAccountType(SavingOrCurrentBalance.SAVING);
        accountDto.setAmount(4000D);
        List<Account> accountList2 = new ArrayList<>();
        Mockito.when(accountRepo.findByUserInfoAndBankAndAccountType(
                account.getUserInfo(), account.getBank(), account.getAccountType())).thenReturn(accountList2);
        assertEquals(ApplicationConstant.MINIMUM_BALANCE_FOR + " saving account 5000",
                accountService.saveAccountNo(accountDto));


        accountDto.setAccountType(SavingOrCurrentBalance.CURRENT);
        accountDto.setAmount(9000D);
        List<Account> accountList3 = new ArrayList<>();
        Mockito.when(accountRepo.findByUserInfoAndBankAndAccountType(
                account.getUserInfo(), account.getBank(), account.getAccountType())).thenReturn(accountList3);
        assertEquals(ApplicationConstant.MINIMUM_BALANCE_FOR + " current account 10000",
                accountService.saveAccountNo(accountDto));

        accountDto.setAmount(1000000D);
        Mockito.when(accountRepo.save(Mockito.any())).thenReturn(null);
        BankException bankException1 = assertThrows(BankException.class,
                () -> accountService.saveAccountNo(accountDto));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, bankException1.getHttpStatus());
    }

    @Test
    void getAccount() {
        List<Account> accountList = new ArrayList<>();
        Mockito.when(accountRepo.findByTitleContent(Mockito.anyString())).thenReturn(accountList);
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.getAccount("pankaj "));
        assertEquals(HttpStatus.NOT_FOUND, bankException.getHttpStatus());

        Account account = new Account();
        Bank bank = new Bank();
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        bank.setBankId(1L);
        account.setBank(bank);
        account.setUserInfo(customer);
        accountList.add(account);
        Mockito.when(accountRepo.findByTitleContent(Mockito.anyString())).thenReturn(accountList);
        assertEquals(1, accountService.getAccount("pankaj").size());
    }

    @Test
    void getAllAccount() {
        Account account = new Account();
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        account.setUserInfo(customer);
        Bank bank = new Bank();
        bank.setBankId(1L);
        account.setBank(bank);
        List<Account> accountList = new ArrayList<>();
        accountList.add(account);
        Mockito.when(accountRepo.findAll()).thenReturn(accountList);
        assertEquals(1, accountService.getAllAccount().size());
        List<Account> accountList1 = new ArrayList<>();
        Mockito.when(accountRepo.findAll()).thenReturn(accountList1);
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.getAllAccount());
        assertEquals(HttpStatus.NOT_FOUND, bankException.getHttpStatus());
    }

    @Test
    void updateAccount() {
        AccountDto accountDto = new AccountDto();
        accountDto.setAccountId(1L);
        accountDto.setBankId(1L);
        accountDto.setCustomerId(1L);
        accountDto.setAccountType(SavingOrCurrentBalance.CURRENT);
        accountDto.setBlocked(true);
        accountDto.setName("Nikhil");
        accountDto.setAmount(1000D);
        accountDto.setIfscCode("ASdf1234567");
        Account account = new Account();
        Mockito.when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
        assertEquals(ApplicationConstant.ACCOUNT_UPDATED, accountService.updateAccount(accountDto, 1L));
        Mockito.when(accountRepo.findById(1L)).thenReturn(Optional.ofNullable(null));
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.updateAccount(accountDto, 1L));
        assertEquals(HttpStatus.NOT_FOUND, bankException.getHttpStatus());
        ;
    }

    @Test
    void deleteAccount() {
        Account account = new Account();
        Mockito.when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
        assertEquals(ApplicationConstant.ACCOUNT_DELETED, accountService.deleteAccount(1L));

        Mockito.when(accountRepo.findById(1L)).thenReturn(Optional.ofNullable(null));
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.deleteAccount(1L));
        assertEquals(HttpStatus.NOT_FOUND, bankException.getHttpStatus());
    }

    @Test
    void getAllByBankId() {
        Bank bank = new Bank();
        List<Account> accountList = new ArrayList<>();
        Account account = new Account();
        Customer customer = new Customer();
        customer.setCustomerId(1L);
        account.setUserInfo(customer);
        bank.setBankId(1L);
        account.setBank(bank);
        accountList.add(account);
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.of(bank));
        Mockito.when(accountRepo.findByBank(bank)).thenReturn(accountList);
        assertEquals(1, accountService.getAllByBankId(1L).size());
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.ofNullable(null));
        BankException bankException = assertThrows(BankException.class,
                () -> accountService.getAllByBankId(1L));
        assertEquals(HttpStatus.BAD_REQUEST, bankException.getHttpStatus());

        Bank bank1 = new Bank();
        List<Account> accountList1 = new ArrayList<>();
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.of(bank1));
        Mockito.when(accountRepo.findByBank(bank1)).thenReturn(accountList1);
        BankException bankException1 = assertThrows(BankException.class,
                () -> accountService.getAllByBankId(1L));
        assertEquals(HttpStatus.NOT_FOUND, bankException1.getHttpStatus());

    }
    @Test
    void interest() {

    }

}