package bankingapplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BankSave")
@Data
public class Bank {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long bankId;
  private String bankName;
  private String ifscCode;
  private String branchName;
  private String city;
  private String address;

  /*@OneToMany(cascade = CascadeType.DETACH,fetch = FetchType.EAGER,orphanRemoval = true)
  private List<Account> accounts ;*/
}
