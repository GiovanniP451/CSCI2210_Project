
/**
 *
 * @author Isiah John
 */

/* 
 * This class allows payments to be created when processing member
 * dues, class fees, or other transactions. 
 */
public class CashPayment extends Payment{
    private double cashGiven;
    
    public CashPayment(double cashGiven)
    {
        this.cashGiven = cashGiven;
        super.setMethod("Cash");
    }
    
    //Method to get the amount of cash that the customer hands over.
    public double getCashGiven()
    {
        return cashGiven;
    }
    
    //method to return what the amount of the change is due.
    public double getChangeDue()
    {
        return cashGiven - calculateTotal();
    }
    
    //return to String method to display the information.
    @Override
    public String getPaymentDetails()
    {
        return "Cash Given: $" + String.format("%.2f",cashGiven) + "\n"
                + "Change Due: $" + String.format("%.2f",getChangeDue()) + "\n";
    }
}
