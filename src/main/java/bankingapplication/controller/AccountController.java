package bankingapplication.controller;

import bankingapplication.constant.UrlConstant;
import bankingapplication.dto.AccountDto;
import bankingapplication.service.AccountService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping(UrlConstant.ACCOUNT)
public class AccountController {

  @Autowired
  private AccountService accountService;

  @PostMapping(UrlConstant.ACCOUNT_CREAT)
  public ResponseEntity<?> save(@Valid @RequestBody AccountDto accountDto) {
    String s = accountService.saveAccountNo(accountDto);
    return new ResponseEntity<>(s, HttpStatus.CREATED);
  }

  @GetMapping(UrlConstant.GET_ACCOUNT)
  public ResponseEntity<?> getAccount(@PathVariable String content) {
    return new ResponseEntity<>(
        accountService.getAccount(content) != null ? accountService.getAccount(content)
            : "No mach found", HttpStatus.OK);

  }

  @GetMapping(UrlConstant.ALL_BANK_ACCOUNT)
  public ResponseEntity<List<AccountDto>> getAllAccount() {
    List<AccountDto> accounts = accountService.getAllAccount();
    return new ResponseEntity<>(accounts, HttpStatus.OK);
  }

  @PutMapping(UrlConstant.UPDATE_ACCOUNT)
  public ResponseEntity<String> updateBank(@RequestBody AccountDto accountDto,
      @PathVariable Long accountId) {
    String s = accountService.updateAccount(accountDto, accountId);
    return new ResponseEntity<>(s, HttpStatus.OK);
  }

  @DeleteMapping(UrlConstant.DELETE_ACCOUNT)
  public ResponseEntity<String> deleteAccount(@PathVariable Long accountId) {
    String s = accountService.deleteAccount(accountId);
    return new ResponseEntity<>(s, HttpStatus.OK);
  }

  @GetMapping(UrlConstant.GET_ALL_BANK_BY_ID)
  public ResponseEntity<List<AccountDto>> getAllByBankId(@PathVariable Long bankId) {
    List<AccountDto> accounts = accountService.getAllByBankId(bankId);
    return new ResponseEntity<>(accounts, HttpStatus.OK);
  }
}

