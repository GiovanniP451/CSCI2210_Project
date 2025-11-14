/*

@Author Isiah John
*/
package csci2210;

/* 
 * This class allows payments to be created when processing member
 * dues, class fees, or other transactions. 
 */

//Two instance variables. Cash given and changeDue. 
public class CashPayment extends Payment{
    private double cashGiven;
    private double changeDue;
    
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
        return changeDue;
    }
    
    //method to run calculations to process the payment 
    //if the cash given is less than the total then false is thrown
    //If the cash given is the right amount, it will return the amount given back as change. 
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
    //return to String method to display the information. 
    public String getPaymentDetails()
    {
        return "Cash Given: $" + String.format("%.2f",cashGiven) + "\n"
                + "Change Due: $" + String.format("%.2f",changeDue) + "\n";
    }
}
