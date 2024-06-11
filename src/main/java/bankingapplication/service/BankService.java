package bankingapplication.service;

import bankingapplication.dto.BankDto;
import bankingapplication.entity.Bank;
import java.util.List;

public interface BankService {

  String addBank(BankDto bankDto);

  List<Bank> getBankById(String content);

  String updateBank(BankDto bankDto, Long bankId);

  String deleteBank(Long bankId);

  List<Bank> getAllBank();
}


