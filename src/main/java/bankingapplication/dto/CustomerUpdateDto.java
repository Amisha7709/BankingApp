package bankingapplication.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
public class CustomerUpdateDto {
  @NotBlank(message = "plz enter customer name")
  private String customerName;
  @NotBlank(message = "plz enter address ")
  private String address;
  @NotBlank(message = "plz enter mobileNumber ")
  private String mobileNumber;
  @NotBlank(message = "plz provide valid emailid")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}")
  private String emailId;
  @NotBlank(message = "Password.required")
  @Size(min = 8, message = "{password.length.required}")
  @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
  private String password;
}
