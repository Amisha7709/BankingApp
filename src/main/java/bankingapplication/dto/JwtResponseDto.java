package bankingapplication.dto;

public class JwtResponseDto {

  private final String jwttoken;

  public JwtResponseDto(String jwttoken) {
    this.jwttoken = jwttoken;
  }

  public String getToken() {
    return this.jwttoken;
  }

}
