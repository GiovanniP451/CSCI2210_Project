
/**
 *
 * @author Isiah John
 */
import java.util.ArrayList;

/*
 * This class will contain a list of stored credit cards within the program.
 */

/*
 * Create an arraylist of the stored credit cards. 
 */
public class CreditCardManager {
    private ArrayList<CreditCard> savedCards;
    
    public CreditCardManager()
    {
        savedCards = new ArrayList<>();
    }
    
    //Adds and Saves Card for a Member
    public boolean addCard(Member member, CreditCard card)
    {
        return savedCards.add(card);
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
    
    //method to remove a saved credit card.
    public boolean removeCard(Member member)
    {
        CreditCard card = findCard(member);
        if(card != null)
        {
            savedCards.remove(card);
            return true;
        }
        return false;
    }
    
    //Be able to retreive all the credit cards stored.
    public ArrayList<CreditCard> getAllCards()
    {
        return savedCards;
    }
    
    //return a list of saved credit card and information for //the user.
    public ArrayList<CreditCard> getCardsForMember(Member member)
    {
        ArrayList<CreditCard> list = new ArrayList<>();
        
        for(CreditCard c : savedCards)
        {
            if(c.getMemberUsername().equalsIgnoreCase(member.getUsername()))
            {
                list.add(c);
            }
        }
        return list;
    }
}
