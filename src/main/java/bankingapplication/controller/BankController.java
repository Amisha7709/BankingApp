package bankingapplication.controller;

import bankingapplication.constant.UrlConstant;
import bankingapplication.dto.BankDto;
import bankingapplication.entity.Bank;
import bankingapplication.service.BankService;
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
@RequestMapping(UrlConstant.BANK)
public class BankController {

  @Autowired
  BankService bankService;

  @PostMapping(UrlConstant.CREATE_BANK)
  public ResponseEntity<String> saveBank(@Valid @RequestBody BankDto bankDto) {
    String s = bankService.addBank(bankDto);
    return new ResponseEntity<>(s, HttpStatus.CREATED);
  }


  @GetMapping(UrlConstant.GET_BANK)
  public ResponseEntity<List<Bank>> getBankById(@PathVariable String content) {
    return new ResponseEntity<>(bankService.getBankById(content), HttpStatus.OK);
  }


  @PutMapping(UrlConstant.BANK_UPDATE)
  public ResponseEntity<String> updateBank(@Valid @RequestBody BankDto bankDto,
      @PathVariable Long bankId) {
    String s = bankService.updateBank(bankDto, bankId);
    return new ResponseEntity<>(s, HttpStatus.OK);
  }


  @DeleteMapping(UrlConstant.BANK_DELETE)
  public ResponseEntity<String> deleteBank(@PathVariable Long bankId) {
    String s = bankService.deleteBank(bankId);
    return new ResponseEntity<>(s, HttpStatus.OK);
  }

  @GetMapping(UrlConstant.GET_ALL_BANK)
  public ResponseEntity<List<Bank>> getAllBank() {
    List<Bank> banks = bankService.getAllBank();
    return new ResponseEntity<>(banks, HttpStatus.OK);
  }

}
