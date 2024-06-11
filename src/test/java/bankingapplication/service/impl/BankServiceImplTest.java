package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.BankDto;
import bankingapplication.entity.Account;
import bankingapplication.entity.Bank;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.BankRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class BankServiceImplTest {

    @InjectMocks
    BankServiceImpl bankService;

    @Mock
    BankRepo bankRepo;

    @Mock
    AccountRepo accountRepo;

    @Test
    void addBank() {
        Bank bank=new Bank();
        BankDto bankDto=new BankDto();
        bankDto.setBankName("ICICI");
        bankDto.setIfscCode ("Aesv1234567");
        bankDto.setCity("Nagpur");
        bankDto.setAddress("SAINAgar");
        bankDto.setBranchName("BhavaniMandir");
        Mockito.when(bankRepo.findByIfscCode (bankDto.getIfscCode())).thenReturn( null);
        Mockito.when( bankRepo.save (bank)).thenReturn(bank);
        assertEquals(ApplicationConstant.BANK_CREATED, bankService.addBank(bankDto));

        bankDto.setIfscCode ("Aesv123456");
        BankException bankException = assertThrows(BankException.class,
                () -> bankService.addBank(bankDto));
        assertEquals(HttpStatus.BAD_REQUEST,bankException.getHttpStatus());

        Bank bank1=new Bank();
        BankDto bankDto1 =new BankDto();
        bankDto1.setBankName("ICICI");
        bankDto1.setIfscCode ("Aesv1234567");
        bankDto1.setCity("Nagpur");
        bankDto1.setAddress("SAINAgar");
        bankDto1.setBranchName("BhavaniMandir");
        Mockito.when(bankRepo.findByIfscCode (bankDto1.getIfscCode())).thenReturn( bank1);
        assertEquals(ApplicationConstant.BANK_ALREADY_REGISTER_FOR_THIS_IFSC_CODE,bankService.addBank(bankDto1));
    }

    @Test
    void getBankById() {
        Bank bank=new Bank();
        List<Bank> bankList=new ArrayList<>();
        bankList.add(bank);
        Mockito.when(bankRepo.findByTitleContent (Mockito.anyString())).thenReturn(bankList);
        assertEquals(bankList.size(), bankService.getBankById(  "Pankaj").size());

        List<Bank> bankList1=new ArrayList<>();
        Mockito.when(bankRepo.findByTitleContent (Mockito.anyString())).thenReturn(bankList1);
        BankException bankException = assertThrows(BankException.class,
                () -> bankService.getBankById("pankaj"));
        assertEquals(HttpStatus.NOT_FOUND,bankException.getHttpStatus());


    }

    @Test
    void updateBank() {
        Bank bank=new Bank();
        BankDto bankDto=new BankDto();
        bankDto.setBankName("ICICI");
        bankDto.setIfscCode ("Aesy1234567");
        bankDto.setCity("Nagpur");
        bankDto.setAddress("SAINAgar");
        bankDto.setBranchName("BhavaniMandir");
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional. of (bank));
        Mockito.when(bankRepo.findByIfscCode (bankDto.getIfscCode())).thenReturn(null);
        Mockito.when(bankRepo.save(bank)).thenReturn(bank);
        assertEquals(ApplicationConstant. BANK_UPDATE, bankService.updateBank (bankDto,  1L));
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional. ofNullable( null));
        BankException bankException= assertThrows (BankException.class,
                ()-> bankService.updateBank (bankDto,  1L));
        assertEquals(ApplicationConstant.BANK_IS_NOT_FOUND, bankException.getMessage());
        Mockito.when(bankRepo.findByIfscCode (bankDto.getIfscCode())).thenReturn(bank);
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional. of (bank));
        assertEquals(ApplicationConstant.BANK_ALREADY_REGISTER_FOR_THIS_IFSC_CODE, bankService .updateBank(bankDto,  1L));

        bankDto.setIfscCode("1235f");
        BankException bankException1 = assertThrows (BankException.class,
                () -> bankService.updateBank (bankDto,  1L));
        assertEquals(ApplicationConstant.INVALID_IFSC_CODE, bankException1.getMessage());
    }

    @Test
    void deleteBank() {
        Bank bank=new Bank();
        List<Account> accountList=new ArrayList<>();
        Account account=new Account();
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.of (bank));
        Mockito.when(accountRepo.findByBank (bank)).thenReturn(accountList);
        assertEquals(ApplicationConstant.BANK_DELETED, bankService.deleteBank( 1L));

        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional. ofNullable(  null));
        BankException bankException= assertThrows (BankException.class,
                () -> bankService.deleteBank(  1L)); assertEquals(HttpStatus.BAD_REQUEST, bankException.getHttpStatus());
        accountList.add(account);
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional. of (bank));
        Mockito.when(accountRepo.findByBank (bank)).thenReturn(accountList);
        BankException bankException1 = assertThrows (BankException.class,
                () -> bankService.deleteBank(  1L));
        assertEquals(HttpStatus.BAD_REQUEST, bankException1.getHttpStatus());
    }

    @Test
    void getAllBank() {
        Bank bank=new Bank();
        List<Bank> bankList=new ArrayList<>();
        bankList.add(bank);
        Mockito.when(bankRepo.findAll()).thenReturn(bankList);
        assertEquals(bankList, bankService.getAllBank());
        List<Bank> bankList1=new ArrayList<>();
        Mockito.when(bankRepo.findAll()).thenReturn(bankList1);
        BankException bankException = assertThrows (BankException.class,
                () -> bankService.getAllBank());
               assertEquals(HttpStatus.BAD_REQUEST, bankException.getHttpStatus());
    }



}