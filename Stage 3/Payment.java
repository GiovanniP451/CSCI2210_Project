/*
* Author: Isiah John
*/
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Payment {
    private HashMap<Item, Integer> purchasedItems;
    private ArrayList<Membership> purchasedMembership;
    private int paymentID;
    private double amount;
    private String method;
    private static int nextPaymentID = 1;
    private final double taxRate = 0.08;
    
    
    public Payment()
    {
        this.amount = 0.0;
        this.paymentID = nextPaymentID++;
        this.purchasedItems = new HashMap<>();
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
    
    public HashMap<Item, Integer> getPurchasedItems()
    {
        return purchasedItems;
    }
    
    public void addItem(Item item, int quantity)
    {
        if(purchasedItems.containsKey(item))
        {
            int oldQty = purchasedItems.get(item); //saves the old quantity of item first
            purchasedItems.put(item, oldQty + quantity);
        }
        else
        {
            purchasedItems.put(item, quantity);
        }
    }
    
    public ArrayList<Membership> getPurchasedMembership()
    {
        return purchasedMembership;
    }
    
    public void addMembership(Membership membership)
    {
        purchasedMembership.add(membership);
    }

    
    public double calculateSubtotal()
    {
        double subTotal = 0.0;
        for(Item i : purchasedItems.keySet())
        {
            int qty = purchasedItems.get(i);
            subTotal += i.getPrice() * qty; //When Item class has been created
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
    
    public abstract String getPaymentDetails();
}
