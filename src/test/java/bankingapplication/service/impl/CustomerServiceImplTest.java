package bankingapplication.service.impl;

import bankingapplication.constant.ApplicationConstant;
import bankingapplication.dto.CustomerDto;
import bankingapplication.dto.MoneyTransferDto;
import bankingapplication.entity.Account;
import bankingapplication.entity.Bank;
import bankingapplication.entity.Customer;
import bankingapplication.enum1.SavingOrCurrentBalance;
import bankingapplication.exception.BankException;
import bankingapplication.repo.AccountRepo;
import bankingapplication.repo.BankRepo;
import bankingapplication.repo.CustomerRepo;
import bankingapplication.repo.TransactionRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    CustomerServiceImpl customerService;

    @Mock
    private CustomerRepo customerRepo;

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private BankRepo bankRepo;
    @Test
    void save() {
        CustomerDto customerDto =new CustomerDto();
        customerDto.setCustomerId(1L); customerDto.setCustomerName("Test");
        customerDto.setPanCardNumber ("LUDPS1234K");
        customerDto.setAddress("Address");
        customerDto.setBankId(1l);
        customerDto.setAadhaarNumber ("987654321234");
        customerDto.setEmailId("test@email.com"); customerDto.setMobileNumber("1234567890");
        customerDto.setPassword("eagghads");
        Customer customer = new Customer();
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        Bank bank = new Bank();

        Mockito.when( customerRepo.findByPanCardNumberOrAadhaarNumber(
                Optional.of(customerDto.getAadhaarNumber()),
                Optional.of(customerDto.getPanCardNumber()))).thenReturn(customerList);
        Mockito.when(customerRepo.findByMobileNumberOrEmailId (Optional. ofNullable (customerDto.getMobileNumber()),
                Optional.ofNullable (customerDto.getEmailId()))).thenReturn( null);
        Mockito.when(bankRepo.findById(customerDto.getBankId())).thenReturn(Optional.of (bank));
        Mockito.when(customerRepo.findByAadhaarNumberAndPanCardNumberAndBank(
                        customerDto.getAadhaarNumber(), customerDto.getPanCardNumber(), bank))
                .thenReturn(Optional.of(new Customer()));
        assertEquals(ApplicationConstant.CUSTOMER_CREATED, customerService.save(customerDto));

        Mockito.when(bankRepo.findById(customerDto.getBankId())).thenReturn(Optional.empty());
        BankException bankException = assertThrows(BankException.class,
                () -> customerService.save(customerDto));
        assertEquals(HttpStatus.BAD_REQUEST,bankException.getHttpStatus());
    }

    @Test
    void getAllCustomer() {
        Bank bank=new Bank();
        bank.setBankId(1l);
        Customer customer=new Customer();
        customer.setBank (bank);
        List<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        CustomerDto customerDto=new CustomerDto();
        List<CustomerDto> customerListDto=new ArrayList<>();
        customerListDto.add(customerDto);
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCustomerName (customer.getCustomerName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setAadhaarNumber (customer.getAadhaarNumber());
        customerDto.setPanCardNumber (customer.getPanCardNumber());
        customerDto.setMobileNumber (customer.getMobileNumber());
        customerDto.setEmailId(customer.getEmailId());
        customerDto.setBankId (1l);
        Mockito.when(customerRepo.findAll()).thenReturn(customerList);
        assertEquals(customerListDto.size(), customerService.getAllCustomer().size());

        List<Customer> customerList1 = new ArrayList<>();
        Mockito.when(customerRepo.findAll()).thenReturn(customerList1);
        BankException bankException = assertThrows(BankException.class, () -> customerService.getAllCustomer());
        assertEquals(HttpStatus.BAD_REQUEST,bankException.getHttpStatus());
    }

    @Test
    void getAllByBankId() {
        Bank bank=new Bank();
        bank.setBankId(1L);
        Customer customer=new Customer();
        customer.setBank (bank);
        List<Customer> customerList=new ArrayList<>();
        customerList.add(customer);
        CustomerDto customerDto=new CustomerDto();
        List<CustomerDto> customerDtoList=new ArrayList<>();
        customerDtoList.add(customerDto);
        customerDto.setCustomerId(customer.getCustomerId());
        customerDto.setCustomerName (customer.getCustomerName());
        customerDto.setAddress(customer.getAddress());
        customerDto.setAadhaarNumber (customer.getAadhaarNumber());
        customerDto.setPanCardNumber (customer.getPanCardNumber());
        customerDto.setMobileNumber (customer.getMobileNumber());
        customerDto.setEmailId(customer.getEmailId());
        customerDto.setBankId(1L);
        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.of(bank));
        Mockito.when(customerRepo.findByBank (bank)).thenReturn(customerList);
        assertEquals(customerDtoList.size(), customerService.getAllByBankId(1L).size());

        Mockito.when(bankRepo.findById(1L)).thenReturn(Optional.empty());
        BankException bankException = assertThrows(BankException.class,
                () -> customerService.getAllByBankId(1L));
        assertEquals(HttpStatus.BAD_REQUEST,bankException.getHttpStatus());

    }

    @Test
    void transferMoney() {
        MoneyTransferDto transferDto=new MoneyTransferDto();
        Account account=new Account();
        transferDto.setAmount (5000d);
        transferDto.setDate(LocalDate.now());
        transferDto.setName("roshan");
        transferDto.setAccountType (SavingOrCurrentBalance.CURRENT);
        transferDto.setIfscCode ("KNB1234578");
        transferDto.setAccountNumberFrom("123456789098");
        transferDto.setAccountNumberTo("098765432123");
        Mockito.when(accountRepo.findByAccNo (transferDto.getAccountNumberFrom())).thenReturn(account);
        Mockito.when(accountRepo.findByAccNo (transferDto.getAccountNumberTo())).thenReturn(account);
    }

    @Test
    void getByIdAndName() {
        List<CustomerDto> customerDtoList=new ArrayList<>();
        List<Customer> customerList=new ArrayList<>();
        Customer customer=new Customer();
        Bank bank=new Bank();
        bank.setBankId(1L);
        customer.setBank(bank);
        customerList.add(customer);
        Mockito.when(customerRepo.findByTitleContent (Mockito.anyString())).thenReturn(customerList);
        assertEquals(  1, customerService.getByIdAndName(  "pankaj").size());
        List<Customer> customerList1=new ArrayList<>();
        Mockito.when(customerRepo.findByTitleContent (Mockito.anyString())).thenReturn(customerList1);
        BankException bankException = assertThrows (BankException.class,
                () -> customerService.getByIdAndName(  "pankaj"));
        assertEquals(HttpStatus.NOT_FOUND, bankException.getHttpStatus());
    }

    @Test
    void testGetAllCustomer() {
    }

    @Test
    void updateCustomer() {
        CustomerDto customerDto=new CustomerDto();

        customerDto.setCustomerId(1L);
        customerDto.setCustomerName("Test");
        customerDto.setPanCardNumber ("LUDPS1234K");
        customerDto.setAddress("Address");
        customerDto.setBankId(1l);
        customerDto.setAadhaarNumber ("987654321234");
        customerDto.setEmailId("test@email.com");
        customerDto.setMobileNumber ("1234567891");
        customerDto.setPassword("eagghads");
        Customer customer = new Customer();
        Mockito.when(customerRepo.findById(1L)).thenReturn(Optional.of(customer));
        assertEquals(ApplicationConstant.CUSTOMER_UPDATED, customerService
                .updateCustomer (customerDto,  1L));
    }

    @Test
    void deleteCustomer() {
        Customer customer=new Customer();
        Mockito.when(customerRepo.findById(1l)).thenReturn(Optional. of (customer));
        assertEquals(ApplicationConstant.CUSTOMER_DELETED, customerService.deleteCustomer(1l));

        Account account=new Account();
        Customer customer1 = new Customer();
        List<Account> accounts = new ArrayList<>();
        accounts.add(account);
        Mockito.when(customerRepo.findById(0L)).thenReturn(Optional.of(customer1));
        Mockito.when(accountRepo.findByUserInfo (customer1)).thenReturn(accounts);
        BankException bankException = assertThrows (BankException.class, () ->
                customerService.deleteCustomer( 0L));
        assertEquals(ApplicationConstant.CANT_DELETE_CUSTOMER_BECAUSE_ACCOUNT_LINKED_WITH_CUSTOMER,
                bankException.getMessage());
        Mockito.when(customerRepo.findById(0L)).thenReturn(Optional.ofNullable(  null));
        BankException bankException1 = assertThrows (BankException.class,
                () -> customerService.deleteCustomer( 0L));
        assertEquals(ApplicationConstant.CUSTOMER_NOT_FOUND, bankException1.getMessage());
    }
}