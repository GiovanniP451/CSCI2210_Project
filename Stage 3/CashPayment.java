/*

@Author Isiah John
*/
public class CashPayment extends Payment{
    private double cashGiven;
    private double changeDue;
    
    public CashPayment(double cashGiven)
    {
        this.cashGiven = cashGiven;
        super.setMethod("Cash");
    }
    
    public double getCashGiven()
    {
        return cashGiven;
    }
    
    public double getChangeDue()
    {
        return changeDue;
    }
    
    public boolean processPayment()
    {
        double total = calculateTotal();
        if(cashGiven < total)
        {
            return false;
        }
        else
        {
            changeDue = cashGiven - total;
            setAmount(total);
            return true;
        }
    }
    
    @Override
    public String getPaymentDetails()
    {
        return "Cash Given: $" + String.format("%.2f",cashGiven) + "\n"
                + "Change Due: $" + String.format("%.2f",changeDue) + "\n";
    }
}
