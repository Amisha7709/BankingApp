package bankingapplication.service;

import bankingapplication.dto.CustomerDto;
import bankingapplication.dto.MoneyTransferDto;
import java.util.List;


public interface CustomerService {

  String save(CustomerDto customerDto);

  String transferMoney(MoneyTransferDto transactionDto);

  List<CustomerDto> getByIdAndName(String content);

  List<CustomerDto> getAllCustomer();

  String updateCustomer(CustomerDto customerUpdateDto, Long customerId);

  String deleteCustomer(Long customerId);

  List<CustomerDto> getAllCustomer(Long bankId);

  List<CustomerDto> getAllByBankId(Long bankId);
}