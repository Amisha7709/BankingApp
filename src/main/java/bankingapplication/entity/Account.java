package bankingapplication.entity;

import bankingapplication.enum1.SavingOrCurrentBalance;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accountSave")
public class Account implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long accountId;
  private String accNo;
  private String name;
  private String ifscCode;
  private SavingOrCurrentBalance accountType;
  private double amount;
  private boolean isBlocked = false;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "customerId", referencedColumnName = "customerId")
  private Customer userInfo;

  @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
  @JoinColumn(name = "bankId", referencedColumnName = "bankId")
  private Bank bank;

}
