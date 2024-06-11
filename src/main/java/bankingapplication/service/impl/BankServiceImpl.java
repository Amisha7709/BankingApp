package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.BankDto;
import bankingapplication.entity.Account;
import bankingapplication.entity.Bank;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.BankRepo;
import bankingapplication.service.BankService;
import java.util.List;
import java.util.Objects;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

  private final BankRepo bankRepo;

  private final AccountRepo accountRepo;

  @Override
  public String addBank(BankDto bankDto) {

    if (bankDto.getIfscCode().length() != 11) {
      throw new BankException(ApplicationConstant.INVALID_IFSC_CODE, HttpStatus.BAD_REQUEST);
    }
    Bank bank = bankRepo.findByIfscCode(bankDto.getIfscCode());
    if (!Objects.isNull(bank)) {
      return ApplicationConstant.BANK_ALREADY_REGISTER_FOR_THIS_IFSC_CODE;
    }
    bankRepo.save(dtoToEntity(bankDto));
    return ApplicationConstant.BANK_CREATED;

  }

  @Override
  public List<Bank> getBankById(String content) {
    List<Bank> bankList = bankRepo.findByTitleContent("%" + content + "%");
    if (bankList.isEmpty()) {
      throw new BankException(ApplicationConstant.BANK_IS_NOT_FOUND, HttpStatus.NOT_FOUND);
    }
    return bankList;
  }

  @Override
  public String updateBank(BankDto bankDto, Long bankId) {
    Bank bank = bankRepo.findById(bankId).orElseThrow(
        () -> new BankException(ApplicationConstant.BANK_IS_NOT_FOUND, HttpStatus.BAD_REQUEST));
    if (bankDto.getIfscCode().length() != 11) {
      throw new BankException(ApplicationConstant.INVALID_IFSC_CODE, HttpStatus.BAD_REQUEST);
    }
    Bank byIfscCode = bankRepo.findByIfscCode(bankDto.getIfscCode());
    if (!Objects.isNull(byIfscCode)) {
      return ApplicationConstant.BANK_ALREADY_REGISTER_FOR_THIS_IFSC_CODE;
    }
    bank.setBankName(bankDto.getBankName());
    bank.setAddress(bankDto.getAddress());
    bank.setBranchName(bankDto.getBranchName());
    bank.setCity(bankDto.getCity());
    bank.setIfscCode(bankDto.getIfscCode());
    bankRepo.save(bank);
    return ApplicationConstant.BANK_UPDATE;
  }

  @Override
  public String deleteBank(Long bankId) {
    Bank bank = bankRepo.findById(bankId).orElseThrow(
        () -> new BankException(ApplicationConstant.BANK_IS_NOT_FOUND, HttpStatus.BAD_REQUEST));

    List<Account> accounts = accountRepo.findByBank(bank);
    if (!accounts.isEmpty()) {
      throw new BankException(ApplicationConstant.CANT_DELETE_BANK_BECAUSE_ACCOUNT_PRESENT,
          HttpStatus.BAD_REQUEST);
    }
    bankRepo.deleteById(bankId);
    return ApplicationConstant.BANK_DELETED;
  }

  @Override
  public List<Bank> getAllBank() {
    List<Bank> banks = bankRepo.findAll();
    if (banks.isEmpty()) {
      throw new BankException(ApplicationConstant.BANK_IS_NOT_FOUND, HttpStatus.BAD_REQUEST);
    }
    return banks;
  }

  private Bank dtoToEntity(BankDto bankDto) {
    Bank bank = new Bank();
    BeanUtils.copyProperties(bankDto, bank);
    return bank;
  }
}
