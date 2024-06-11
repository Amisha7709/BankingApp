package bankingapplication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomerDto {

  private Long customerId;
  @NotBlank(message = "plz enter customer name")
  private String customerName;
  @NotBlank(message = "plz enter address ")
  private String address;
  @Pattern(regexp = "^[A-Z]{5}\\d{4}+[A-Z]{1}$",message = "plz enter valid  panCardNumber ")
  private String panCardNumber;
  @NotBlank(message = "plz enter aadhaarNumber ")
  private String aadhaarNumber;
  @NotBlank(message = "plz enter mobileNumber ")
  private String mobileNumber;
  @NotBlank(message = "plz provide valid emailId")
  @Pattern(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
  private String emailId;
  @NotNull(message = "plz enter bankId ")
  private Long bankId;
  @NotBlank(message = "Password.required")
  @Size(min = 8, message = "{password.length.required}")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
  private String password;
}
