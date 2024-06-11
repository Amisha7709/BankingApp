package bankingapplication.service;

import bankingapplication.dto.AccountDto;
import bankingapplication.dto.CustomerDto;
import bankingapplication.entity.Customer;
import java.util.List;

public interface AccountService {

  String saveAccountNo(AccountDto accountDto);

  List<AccountDto> getAccount(String content);

  void interest();

  List<AccountDto> getAllAccount();

  String updateAccount(AccountDto accountDto, Long accountId);

  String deleteAccount(Long accountId);

  List<AccountDto> getAllByBankId(Long bankId);

}
