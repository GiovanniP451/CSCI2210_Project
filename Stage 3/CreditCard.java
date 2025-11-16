
/**
 *
 * @author Isiah John
 */
public class CreditCard {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String memberUsername; 
    
    public CreditCard(String cardNumber, String cardHolderName, String expiryDate, String memberUsername)
    {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expiryDate = expiryDate;
        this.memberUsername = memberUsername;
    }

    //getters to be able to get the credit card number.
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public String getMemberUsername() {
        return memberUsername;
    }
    
    //masked card method to hide the information of the credit card //typically during transactions.
    public String getMaskedCard()
    {
        if(cardNumber.length() >= 4)
        {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
        return "****";
    }
    
    /*Override toString method from the object class so it returns
    * a readable stirng to the user instead of the memory address
    */
    @Override
    public String toString()        
    {
        return getMaskedCard() + " | " + cardHolderName + " | Exp: " + expiryDate;
    }
}
