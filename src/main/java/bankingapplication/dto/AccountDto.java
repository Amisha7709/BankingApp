package bankingapplication.dto;

import bankingapplication.enum1.SavingOrCurrentBalance;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto implements Serializable {

  private Long accountId;
  private String accNo;
  @NotNull(message = "accountType required")
  private SavingOrCurrentBalance accountType;
  @NotNull(message = "The bankId is required")
  private Long bankId;
  @NotNull(message = "The customer Id is required")
  private Long customerId;
  @NotNull(message = "The name is required")
  private String name;
  @Pattern(regexp = "^[A-Za-z]{4}\\d{7}$", message = "Invalid IFSC format")
  private String ifscCode;
  @NotNull(message = "Thea amount is required")
  private Double amount;
  private boolean isBlocked;

}
