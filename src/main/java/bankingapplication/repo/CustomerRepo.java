package bankingapplication.repo;

import bankingapplication.entity.Bank;
import bankingapplication.entity.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Long> {

  List<Customer> findByPanCardNumberOrAadhaarNumber(Optional<String> panNumber,
      Optional<String> aadharNumber);

  List<Customer> findByMobileNumberOrEmailId(Optional<String> mobileNumber, Optional<String> emailId);
  Customer findByEmailId(String emailId);
  List<Customer> findByBank(Bank bank);

  Optional<Customer> findByAadhaarNumberAndPanCardNumberAndBank(String aadhar,String pan , Bank bank);

  @Query(value = "select * from customer_info  WHERE customer_id like :key or customer_name like :key or mobile_number like :key or email_id like :key ", nativeQuery = true)
  List<Customer> findByTitleContent(@Param("key") String content);

  @Query(value="SELECT C.* FROM practice.customer_info C \n"
      + "LEFT JOIN practice.account_save a on a.customer_id = c.customer_id\n"
      + "WHERE C.bank_id = :bankId and a.customer_id is null",nativeQuery = true)
  List<Customer> checkCustomerByBankId(@Param("bankId") long bankId);
}
