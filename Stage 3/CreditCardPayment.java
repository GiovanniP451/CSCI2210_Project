/*

@Author Isiah John
*/

/*
 * Class to actually process the payment with the credit cards.
 * Extends payment to be able to use the same code.
 */
public class CreditCardPayment extends Payment{
    private CreditCard card;
    private Member member;
    
    //Object to be store the member and the credit card //information.
    public CreditCardPayment(Member member, CreditCard card)
    {
        this.member = member;
        this.card = card;
        super.setMethod("Credit Card"); //Uses Payment methods
    }

    //list of getters for information display.
    public CreditCard getCard() {
        return card;
    }

    public Member getMember() {
        return member;
    }
    
    //Checks and return a boolean value if the payment amount
    //is correct
    public boolean processPayment()
    {
        setAmount(calculateTotal());
        return true;
    }
    
    @Override
    //display information of the card to the user.
    public String getPaymentDetails()
    {
        return "Charged to Card: " + card.getMaskedCard() + "\n" +
                "Card Holder: " + card.getCardHolderName() + "\n";
    }
}
