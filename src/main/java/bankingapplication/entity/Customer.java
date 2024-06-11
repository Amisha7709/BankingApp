package bankingapplication.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "customerInfo")
public class Customer {

  @Id
  @GeneratedValue
  @Column(name = "customerId")
  private Long customerId;
  private String customerName;
  private String aadhaarNumber;
  private String address;
  private String panCardNumber;
  private String mobileNumber;
  private String emailId;
  private String password;
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "bankId", referencedColumnName = "bankId")
  private Bank bank;


}