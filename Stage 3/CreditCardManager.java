/*

@Author Isiah John
*/
import java.util.ArrayList;

public class CreditCardManager {
    private ArrayList<CreditCard> savedCards;
    
    public CreditCardManager()
    {
        savedCards = new ArrayList<>();
    }
    
    //Adds and Saves Card for a Member
    public void addCard(Member member, CreditCard card)
    {
        savedCards.add(card);
        System.out.println("Card was saved successfuly for " + member.getName());
    }
    
    //Finds Saved Card using Member Username
    public CreditCard findCard(Member member)
    {
        for(CreditCard c : savedCards)
        {
            if(c.getMemberUsername().equalsIgnoreCase(member.getUsername()))
            {
                return c;
            }
        }
        return null;
    }
    
    public void removeCard(Member member)
    {
        CreditCard card = findCard(member);
        if(card != null)
        {
            savedCards.remove(card);
            System.out.println("Card was Removed for " + member.getName());
        }
        else
        {
            System.out.println("No Saved Cards was Found by " + member.getName());
        }
    }
    
    public void ListAllCards()
    {
        if(savedCards.isEmpty())
        {
            System.out.println("No Saved Cards were Found");
        }
        
        System.out.println("==== Saved Credit Cards ====");
        for(CreditCard c : savedCards)
        {
            System.out.println(c);
        }
    }
}
