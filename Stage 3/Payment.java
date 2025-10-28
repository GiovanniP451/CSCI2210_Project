/*

@Author Isiah John
*/

import java.util.ArrayList;

public abstract class Payment {
    private ArrayList<Item> purchasedItems;
    private ArrayList<Membership> purchasedMembership;
    private ArrayList<Payment> payments;
    private int paymentID;
    private double amount;
    private String method;
    private static int nextPaymentID = 1;
    private double taxRate = 0.08;
    
    
    public Payment()
    {
        this.amount = 0.0;
        this.payments = new ArrayList<>();
        this.paymentID = nextPaymentID++;
        this.purchasedItems = new ArrayList<>();
        this.purchasedMembership = new ArrayList<>();
    }
    
    public Payment(double amount)
    {
        this.paymentID = nextPaymentID++;
        this.payments = new ArrayList<>();
        this.amount = amount;
        this.purchasedItems = new ArrayList<>();
        this.purchasedMembership = new ArrayList<>();
    }

    public int getPaymentID() {
        return paymentID;
    }

    public double getAmount() {
        return amount;
    }
    
    public void setAmount(double amount)
    {
        this.amount = amount;
    }
    
    public String getMethod()
    {
        return method;
    }
    
    public void setMethod(String method)
    {
        this.method = method;
    }
    
    public ArrayList<Item> getPurchasedItems()
    {
        return purchasedItems;
    }
    
    public void addItemP(Item item)
    {
        purchasedItems.add(item);
    }
    
    public ArrayList<Membership> getPurchasedMembership()
    {
        return purchasedMembership;
    }
    
    public void addMembership(Membership membership)
    {
        purchasedMembership.add(membership);
    }
    
    public void addPayment(Payment payment)
    {
        payments.add(payment);
    }
    
    public ArrayList<Payment> getAllPayments()
    {
        return payments;
    }
    
    public double calculateSubtotal()
    {
        double subTotal = 0.0;
        for(Item i : purchasedItems)
        {
            subTotal += i.getPrice(); //When Item class has been created
        }
        for(Membership m : purchasedMembership)
        {
            subTotal += m.getCost();
        }
        return subTotal;
    }
    
    public double calculateTax()
    {
        return calculateSubtotal() * taxRate;
    }
    
    public double calculateTotal()
    {
        amount = calculateSubtotal() + calculateTax();
        return amount;
    }
    
    public String getPaymentDetails()
    {
        return "";
    }
}
