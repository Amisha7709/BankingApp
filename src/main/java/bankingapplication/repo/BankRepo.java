package bankingapplication.repo;

import bankingapplication.entity.Bank;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<Bank, Long> {

  Bank findByIfscCode(String ifscCode);

  Optional<Bank> findByBankIdOrBankNameOrIfscCode(Long bankId, String bankName, String ifscCode);

  //@Query(value = "select * from Bank  WHERE CONVERT(bankId ,CHAR) LIKE '%bankId%' OR bankName like bankNm",nativeQuery = true)
  //List<Bank> findByBankIdLikeOrBankNameLikeOrIfscCodeLike(Long bankId,String bankName,String ifscCode);

  @Query(value = "select * from Bank_save  WHERE bank_id like :key or bank_name like :key or ifsc_code like :key ", nativeQuery = true)
  List<Bank> findByTitleContent(@Param("key") String content);

}
