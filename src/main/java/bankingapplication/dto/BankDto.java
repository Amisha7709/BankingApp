package bankingapplication.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankDto {

  @NotNull(message = "the bankName  is required")
  private String bankName;
  @Pattern(regexp = "^[A-Z]{4}\\d{7}$", message = "Invalid IFSC format")
  private String ifscCode;
  @NotNull(message = "the branchName is required")
  private String branchName;
  @NotNull(message = "the address is required")
  private String address;
  @NotNull(message = "the city is required")
  private String city;
}
