package bankingapplication.controller;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.CustomerDto;
import bankingapplication.dto.MoneyTransferDto;
import bankingapplication.enum1.SavingOrCurrentBalance;
import bankingapplication.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class CustomerControllerTest {
    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerService customerService;


    @Test
    void save() {
        CustomerDto customerDto= new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Test");
        customerDto.setPanCardNumber ("LUDPS1234K");
        customerDto.setAddress("Address"); customerDto.setBankId(1l);
        customerDto.setAadhaarNumber ("987654321234");
        customerDto.setEmailId("test@email.com");
        customerDto.setMobileNumber ("1234567890");
        customerDto.setPassword("eagghads");
        Mockito.when(customerService.save(customerDto))
                .thenReturn(ApplicationConstant.CUSTOMER_CREATED);
        ResponseEntity<String> save = customerController.save(customerDto);
        assertEquals(ApplicationConstant.CUSTOMER_CREATED, save.getBody());
    }

    @Test
    void transferMoney() {
        MoneyTransferDto transferDto =new MoneyTransferDto();
        transferDto.setAmount (5000d);
        transferDto.setDate(LocalDate.now());
        transferDto.setName("roshan");
        transferDto.setAccountType(SavingOrCurrentBalance.CURRENT);
        transferDto.setIfscCode ("KNB1234578");
        transferDto.setAccountNumberFrom("123456789098");
        transferDto.setAccountNumberTo("098765432123");
        Mockito.when(customerService.transferMoney (transferDto))
                .thenReturn(ApplicationConstant. TRANSACTION_SUCCESSFUL);
        ResponseEntity<String> stringResponseEntity = customerController.transferMoney (transferDto);
        assertEquals(ApplicationConstant. TRANSACTION_SUCCESSFUL, stringResponseEntity.getBody());
    }

    @Test
    void getByIdAndName() {
        List<CustomerDto> customerDtoList=new ArrayList<>();
        Mockito.when(customerService.getByIdAndName(  "pankaj"))
.thenReturn(customerDtoList);
        ResponseEntity<List<CustomerDto>> listResponseEntity = customerController .getByIdAndName(  "pankaj");

        assertEquals(customerDtoList.size(), listResponseEntity.getBody().size());
    }

    @Test
    void getAllCustomer() {
        List<CustomerDto> customerDtoList=new ArrayList<>();
        Mockito.when(customerService.getAllCustomer()).thenReturn(customerDtoList);
        ResponseEntity<List<CustomerDto>> allCustomer =customerController.getAllCustomer();
        assertEquals(customerDtoList.size(), allCustomer.getBody().size());
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCustomerId(1L);
        customerDto.setCustomerName ("Test ");
        customerDto.setPanCardNumber ("LUDPS1234K");
        customerDto.setBankId(1l);
        customerDto.setAadhaarNumber ("987654321234");
        customerDto.setEmailId("test@email.com");
        customerDto.setMobileNumber ("1234567890");
        customerDto.setPassword("eagghads");
        Mockito.when(customerService.updateCustomer (customerDto,  1l)).thenReturn(ApplicationConstant.CUSTOMER_UPDATED);
        ResponseEntity<String> stringResponseEntity =customerController
.updateCustomer (customerDto,  1l);
        assertEquals(ApplicationConstant.CUSTOMER_UPDATED, stringResponseEntity.getBody());


        customerDto.setAddress("Address");
    }

    @Test
    void deleteCustomer() {
        Mockito.when(customerService.deleteCustomer ( 1l)).thenReturn(ApplicationConstant.CUSTOMER_DELETED);
        ResponseEntity<String> stringResponseEntity = customerController.deleteCustomer(  1l);
        assertEquals(ApplicationConstant.CUSTOMER_DELETED, stringResponseEntity.getBody());

    }

    @Test
    void testGetAllCustomer() {
        List<CustomerDto> customerDtoList =new ArrayList<>();
        Mockito.when(customerService.getAllCustomer (  1l)).thenReturn(customerDtoList);
        ResponseEntity<List<CustomerDto>> allCustomer=customerController.getAllCustomer(  1l);
        assertEquals(customerDtoList.size(), allCustomer.getBody().size());
    }

    @Test
    void getAllByBankId() {
        List<CustomerDto> customerDtoList=new ArrayList<>();
        Mockito.when(customerService.getAllByBankId (1l)).thenReturn(customerDtoList);
        ResponseEntity<List<CustomerDto>> allByBankId= customerController.getAllByBankId(1l);
        assertEquals(customerDtoList.size(), allByBankId.getBody().size());
    }

    @Test
    void getCustomerService() {

    }
}