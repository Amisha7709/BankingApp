package bankingapplication.entity;

import bankingapplication.enum1.SavingOrCurrentBalance;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private LocalDate date;
  private String accountNumberFrom;
  private String accountNumberTo;
  private SavingOrCurrentBalance accountType;
  private double amount;
  private String ifscCode;
  private String name;
  private boolean isBlocked;

}
