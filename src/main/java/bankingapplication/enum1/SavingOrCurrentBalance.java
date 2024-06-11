package bankingapplication.enum1;

public enum SavingOrCurrentBalance {
  SAVING("Saving",5000.00), CURRENT("Current",10000.00);
  private final String value;
  private final double amount;
  SavingOrCurrentBalance(String value,double amount) {
    this.value=value;
    this.amount = amount;
  }
  public double getAmount() {
    return amount;
  }

  public String getValue() {
    return value;
  }
}
