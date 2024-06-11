package bankingapplication.exception;

import org.springframework.http.HttpStatus;

public class BankException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

  public BankException(String message, String id) {
    super(message);
  }

  public BankException(String message, HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

}
