/*
* Author: Isiah John
*/

public class Receipt {
    private Payment payment;
    
    public Receipt(Payment payment)
    {
        this.payment = payment;
    }
    
    public String generateReceipt()
    {
        String receipt = "";
        
        receipt += "=============================\n";
        receipt += "       Gym Receipt\n";
        receipt += "=============================\n";
        receipt += "Payment ID: " + payment.getPaymentID() + "\n";
        receipt += "Payment Method: " + payment.getMethod() + "\n";
        
        if(!payment.getPurchasedMembership().isEmpty())
        {
            receipt += "Membership Purchased: \n";
            for(Membership m : payment.getPurchasedMembership())
            {
                receipt += " - " + m.getType() + " ...... $" + String.format("%.2f",m.getCost()) + "\n";
            }
            receipt += "\n";
        }
        
        if(!payment.getPurchasedItems().isEmpty())
        {
            receipt += "Items Purchased:\n";
            for(Item i : payment.getPurchasedItems().keySet())
            {
                receipt += " - " + i.getName() + " ...... $" + String.format("%.2f",i.getPrice()) + "\n";
            }
            receipt += "\n";
        }
        
        receipt += "Subtotal: $" + String.format("%.2f", payment.calculateSubtotal()) + "\n";
        receipt += "Tax (8%): $" + String.format("%.2f", payment.calculateTax()) + "\n";
        receipt += "Total: $" + String.format("%.2f", payment.calculateTotal()) + "\n";
        
        receipt += payment.getPaymentDetails();
        
        receipt += "=============================\n";
        receipt += "  Thank You for Your Visit\n";
        receipt += "=============================\n";
        
        return receipt;
    }
    
}
