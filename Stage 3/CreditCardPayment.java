/*

@Author Isiah John
*/
public class CreditCardPayment extends Payment{
    private CreditCard card;
    private Member member;
    
    public CreditCardPayment(Member member, CreditCard card)
    {
        this.member = member;
        this.card = card;
        super.setMethod("Credit Card"); //Uses Payment methods
    }

    public CreditCard getCard() {
        return card;
    }

    public Member getMember() {
        return member;
    }
    
    public boolean processPayment()
    {
        setAmount(calculateTotal());
        return true;
    }
    
    @Override
    public String getPaymentDetails()
    {
        return "Charged to Card: " + card.getMaskedCard() + "\n" +
                "Card Holder: " + card.getCardHolderName() + "\n";
    }
}
