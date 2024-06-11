package bankingapplication.dto;

import bankingapplication.enum1.SavingOrCurrentBalance;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MoneyTransferDto {

  @NotNull(message = "plz enter accountNumberFrom")
  private String accountNumberFrom;
  @JsonFormat(pattern="yyyy-MM-dd")
  @NotNull(message = "plz enter date")
  private LocalDate date;
  @NotNull(message = "plz enter accountNumberTo")
  private String accountNumberTo;
  @NotNull(message = "plz enter accountType")
  private SavingOrCurrentBalance accountType;
  @NotNull(message = "plz enter amount")
  private Double amount;
  @NotNull(message = "plz enter ifscCode")
  private String ifscCode;
  @NotNull(message = "plz enter name")
  private String name;
}
