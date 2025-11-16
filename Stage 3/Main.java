
/**
 *
 * @author Isiah 
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static MemberManager memberManager = new MemberManager();
    private static StaffManager staffManager = new StaffManager();
    private static MembershipManager membershipManager = new MembershipManager();
    private static CreditCardManager creditCardManager = new CreditCardManager();
    private static Facility facility = new Facility();
    private static ArrayList<Class> allClasses = new ArrayList<>();
    
    public static void main(String[] args) {
        
        initializeDefaultData();
        
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Iron Forge");
        
        byte choice;
        do
        {
            System.out.println("\n==== Login Menu ====");
            System.out.println("1. Member Login");
            System.out.println("2. Staff Login");
            System.out.println("3. Quit");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch (choice)
            {
                case 1:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Member member = memberManager.findMember(user,pass);
                    if(member != null)
                    {
                        System.out.println("Welcome " + member.getName());
                        memberMenu(member, membershipManager, creditCardManager, facility);
                    }
                    else
                    {
                        System.out.println("Invalid Member Credentials\n");
                    }
                    break;
                }
                case 2:
                {
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    Staff staff = staffManager.findStaff(user,pass);
                    if(staff != null)
                    {
                        staffMenu(staff, membershipManager);
                    }
                    else
                    {
                        System.out.println("Invalid Staff Credentials\n");
                    }
                    break;
                }
                case 3:
                {
                    System.out.println("GoodBye!"); return;
                }
                default:
                {
                    System.out.println("Invalid Choice! Please Try Again!\n");
                    break; 
                }
            }
        } while(choice != 3);
    }
    
    public static void initializeDefaultData()
    {
        memberManager.addMember(new Member("Bob", "bob1", "pass1234"));
        
        
        staffManager.addStaff(new Staff("Isiah", "zaya", "pass1234"));
        staffManager.addStaff(new Staff("Giovanni", "gio", "pass1234"));
        staffManager.addStaff(new Staff("Staff", "staff", "password"));
        
        facility.addArea("Entrance"); // 0
        facility.addArea("Shop"); //1
        facility.addArea("Gym Floor"); //2
        facility.addArea("Rest Area"); //3
        facility.addArea("Classes"); //4
        
        facility.getAreas().get(1).getInventory().addItem(new Item(1, "Water Bottle", 20, 1.99));
        facility.getAreas().get(1).getInventory().addItem(new Item(2, "Towel", 10, 3.99));
        facility.getAreas().get(1).getInventory().addItem(new Item(3, "Yoga Mat", 15, 12.99));
        facility.getAreas().get(1).getInventory().addItem(new Item(4, "Protein Bar", 30, 2.49));
        facility.getAreas().get(1).getInventory().addItem(new Item(5, "Gym T-Shirt", 20, 14.99));
        facility.getAreas().get(1).getInventory().addItem(new Item(6, "Resistance Band", 10, 8.99));
        
        facility.getAreas().get(2).getInventory().addItem(new Item(1, "Dumbbells", 15, 0));
        
        allClasses.add(new Class("Yoga", "9:00AM", "10:00AM", 10));
        allClasses.add(new Class("Pilates", "10:30AM", "11:30AM", 8));
        allClasses.add(new Class("Spin", "12:00PM", "1:00PM", 13));
    }
        
    public static void memberMenu(Member member, MembershipManager membershipManager, CreditCardManager creditCardManager, Facility facility)
    {
        Scanner input = new Scanner(System.in);
        Area currentArea = facility.getAreas().get(0); //default Gym Floor
        
        byte choice;
        do
        {
            System.out.println("\n==== Member Menu ( " + member.getName() + ") ====");
            System.out.println("Current Area: " + currentArea.getName());
            
            if(currentArea.getName().equalsIgnoreCase("Shop"))
            {
                System.out.println("1. Enter Shop Menu");
                System.out.println("2. View My Info");
                System.out.println("3. Change Area");
                System.out.println("4. Logout");
            }
            if(currentArea.getName().equalsIgnoreCase("Classes"))
            {
                System.out.println("1. Enter Classes Menu");
                System.out.println("2. View My Info");
                System.out.println("3. Change Area");
                System.out.println("4. Logout");
            }
            else
            {
                System.out.println("1. Change Area");
                System.out.println("2. Purchase Membership");
                System.out.println("3. View My Info");
                System.out.println("4. Logout"); //Goes back to Login Menu
            }
            
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            if(currentArea.getName().equalsIgnoreCase("Shop"))
            {
                switch(choice)
                {
                    case 1: 
                    {
                        Inventory shopInventory = facility.getAreas().get(1).getInventory();
                        purchaseMenu(member, shopInventory, membershipManager, creditCardManager);
                        break;
                    }
                    case 2:
                    {
                        viewMemberInfo(member, creditCardManager, membershipManager);
                        break;
                    }
                    case 3:
                    {
                        currentArea = selectArea(facility, currentArea);
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Returning Back to Login Menu");
                        return;
                    }
                    default:
                    {
                        System.out.println("Invalid Option! Try Again"); 
                        break;
                    }
                }
            }
            if(currentArea.getName().equalsIgnoreCase("Classes"))
            {
                switch(choice)
                {
                    case 1: 
                    {
                        bookAClassMenu(member, allClasses);
                        break;
                    }
                    case 2:
                    {
                        viewMemberInfo(member, creditCardManager, membershipManager);
                        break;
                    }
                    case 3:
                    {
                        currentArea = selectArea(facility, currentArea);
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Returning Back to Login Menu");
                        return;
                    }
                    default:
                    {
                        System.out.println("Invalid Option! Try Again"); 
                        break;
                    }
                }
            }
            else
            {
                switch(choice)
                {
                    case 1:
                    {
                        currentArea = selectArea(facility, currentArea);
                        break;
                    }
                    case 2:
                    {
                        purchaseMembership(member, membershipManager, creditCardManager);
                        break;
                    }
                    case 3:
                    {
                        viewMemberInfo(member, creditCardManager, membershipManager);
                        break;
                    }
                    case 4:
                    {
                        System.out.println("Returning to Login Menu");
                        return;
                    }
                    default:
                    {
                        System.out.println("Invalid Option. Try Again.");
                        break;
                    }
                }
            }   
            
        } while(choice != 4);
    }
    
    public static Area selectArea(Facility facility, Area currentArea)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("\n==== Facility Areas ====");
        ArrayList<Area> areas = facility.getAreas();
        
        for(int i = 0; i < areas.size(); i++)
        {
            System.out.println((i+1) + ". " + areas.get(i).getName());
        }
        
        System.out.print("Choose Area to Go to: ");
        byte choice = input.nextByte();
        input.nextLine(); //clear buffer
        
        if(choice < 1 || choice > areas.size())
        {
            System.out.println("Invalid Choice. Staying in Current Area");
            return currentArea; 
        }
        return areas.get(choice - 1);
    }
    
    public static void bookAClassMenu(Member member, ArrayList<Class> allClasses)
    {
        Scanner input = new Scanner(System.in);
        byte choice;
        
        do
        {
            System.out.println("\n==== Classes Menu ====");
            System.out.println("1. View Aviable Classes");
            System.out.println("2. Book A Class");
            System.out.println("3. Back to Member Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    System.out.println("\n==== Scheduled Classes ====");
                    for(Class c : allClasses)
                    {
                        String status;
                        if(c.isFull())
                        {
                            status = "(Full)";
                        }
                        else
                        {
                            status = "(Spots left: " + c.spotsLeft() + ")";
                        }
                        System.out.println("- " + c.getClassName() + " " + c.getStartTime() + "- " + c.getEndTime() + " " + status);
                    }
                    break;
                }
                case 2:
                {
                    bookClass(member, allClasses);
                    break;
                }
                case 3:
                {
                    System.out.println("Returning back To Member Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                }
            }
        } while(choice != 3);
    }
    
    public static void bookClass(Member member, ArrayList<Class> classes)
    {
        Scanner input = new Scanner(System.in);
        
        ArrayList<Class> scheduledClasses = new ArrayList<>();
        System.out.println("\n==== Available Classes ====");
        int index = 1;
        
        for(Class c : classes)
        {
            if(c.isScheduled() && !c.isFull())
            {
                System.out.println(index + ". " + c.getClassName() + " (" + c.getStartTime() + " - " + 
                        c.getEndTime() + ") Spots Left: " + c.spotsLeft());
                scheduledClasses.add(c);
                index++;
            }    
        }
        
        if(scheduledClasses.isEmpty())
        {
            System.out.println("No Classes Avaiable");
            return;
        }
        
        System.out.print("Select a Class to Book(number): ");
        int choice = input.nextInt();
        input.nextLine();
        
        if(choice < 1 || choice > scheduledClasses.size())
        {
            System.out.println("Invalid Choice. Returning to Menu");
            return;
        }
        
        Class selectedClass = scheduledClasses.get(choice - 1);
        if(selectedClass.register(member))
        {
            System.out.println("Successfully Booked " + selectedClass.getClassName());
        }
        else
        {
            System.out.println("Failed to Book Class");
        }
        
        
    }
    
    public static void viewMemberInfo(Member member, CreditCardManager ccManager, MembershipManager membershipManager)
    {
        System.out.println("==== Member Info ====");
        System.out.println("Name: " + member.getName());
        System.out.println("Username: " + member.getUsername());
                    
        if(member.getMembership() != null)
        {
            System.out.println("Current Membership: " + member.getMembership().getType());
        }
        else
        {
            System.out.println("Current Membership: (None)");
        }
                    
        CreditCard savedCard = creditCardManager.findCard(member);
        if(savedCard != null)
        {
            System.out.println("Saved Credit Card: " + savedCard);
        }
        else
        {
            System.out.println("No Saved Credit Cards on File");
        }
        System.out.println("====================");
    }
    
    public static void purchaseMenu(Member member, Inventory shopInventory, MembershipManager membershipManager, CreditCardManager ccManager)
    {
        Payment cart = new CashPayment(0);
        Scanner input = new Scanner(System.in);
        
        byte choice;
        do
        {
            System.out.println("\n===== SHOP MENU =====");
            shopInventory.showAllItems();
            System.out.println("\n=====================");
            System.out.println("1. Add Item to Cart");
            System.out.println("2. Remove Item from Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Back to Previous Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    System.out.print("Enter Item ID: ");
                    int id = input.nextInt();
                    input.nextLine(); //clear buffer
                    Item item = shopInventory.findItemByID(id);
                    if(item == null)
                    {
                        System.out.println("Item Not Found");
                        break;
                    }
                    
                    System.out.print("Enter Amount to Buy: ");
                    int qty = input.nextInt();
                    input.nextLine();
                    
                    if(qty <= 0 || qty > item.getQuantity())
                    {
                        System.out.println("Invalid Amount");
                        break;
                    }
                    
                    item.setQuantity(item.getQuantity() - qty);
                    cart.addItem(item, qty);
                    System.out.println(qty + " " + item.getName() + "(s) added to cart.");
                    break;
                }
                case 2:
                {
                    if(cart.getPurchasedItems().isEmpty())
                    {
                        System.out.println("Cart is empty.");
                        break;
                    }
                    
                    System.out.println("\n==== Cart ====");
                    int index = 1;
                    ArrayList<Item> cartItems = new ArrayList<>(cart.getPurchasedItems().keySet());
                    for(Item i : cartItems)
                    {
                        System.out.println(index + ". " + i.getName() + " x" + cart.getPurchasedItems().get(i));
                        index++;
                    }
                    
                    System.out.print("Enter the Number of item to remove: ");
                    int removeItem = input.nextInt();
                    input.nextLine();
                    
                    if(removeItem < 1 || removeItem > cartItems.size())
                    {
                        System.out.println("Invalid Choice");
                        break;
                    }
                    
                    Item itemRemove = cartItems.get(removeItem - 1);
                    int qtyInCart = cart.getPurchasedItems().get(itemRemove);
                    
                    System.out.println("Enter amount to remove (max " + qtyInCart + "): ");
                    int qtyRemoved = input.nextInt();
                    input.nextLine();
                    
                    if(qtyRemoved <= 0 || qtyRemoved > qtyInCart)
                    {
                        System.out.println("Invalid Amount");
                        break;
                    }
                    
                    /*
                    * Reduce Amount in Cart
                    */
                    if(qtyRemoved == qtyInCart)
                    {
                        cart.getPurchasedItems().remove(itemRemove);
                    }
                    else
                    {
                        cart.getPurchasedItems().put(itemRemove, qtyInCart - qtyRemoved);
                    }
                    
                    /* 
                    * Returns Amount back to Shop Inventory
                    */
                    Item shopItem = shopInventory.findItemByID(itemRemove.getId());
                    if(shopItem != null)
                    {
                        shopItem.setQuantity(shopItem.getQuantity() + qtyRemoved);
                    }
                    
                    System.out.println(qtyRemoved + " " + itemRemove.getName() + "(s) removed from cart.");
                    break;
                }
                case 3:
                {
                    System.out.println("\n==== Current Cart ====");
                    for(Item i : cart.getPurchasedItems().keySet())
                    {
                        System.out.println(i.getName() + " x" + cart.getPurchasedItems().get(i));
                    }
                    break;
                }
                case 4:
                {
                    checkoutPayment(member, cart, ccManager);
                    cart = new CashPayment(0);
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Member Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Option. Try Again!\n");
                }
            }
        } while(choice != 5);
        
    }
    
    public static void purchaseMembership(Member member, MembershipManager membershipManager, CreditCardManager ccManager)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("\n==== Available Memberships ====");
        ArrayList<Membership> memberships = membershipManager.getMemberships();
        for(int i = 0; i < memberships.size(); i++)
        {
            Membership m = memberships.get(i);
            System.out.println((i+1) + ". " + m.getType() + " - $" + String.format("%.2f", m.getCost()));
        }
        
        System.out.print("Select Membership to Purchase: ");
        int choice = input.nextInt(); 
        input.nextLine();
        if(choice < 1 || choice > memberships.size())
        {
            System.out.println("Invalid Option");
            return;
        }
        
        Membership selected = memberships.get(choice-1);
        Payment payment = new CashPayment(0);
        payment.addMembership(selected);
        
        System.out.println("You Selected " + selected.getType() + "- $" + String.format("%.2f", selected.getCost()));
        checkoutPayment(member, payment, ccManager);
        
        member.setMembership(selected);
        System.out.println("Membership Assigned to " + member.getName()); 
    }
    
    public static void checkoutPayment(Member member, Payment payment, CreditCardManager ccManager)
    {
        Scanner input = new Scanner(System.in);
        
        if(payment.getPurchasedItems().isEmpty() && payment.getPurchasedMembership().isEmpty())
        {
            System.out.println("Your Cart is Empty. Nothing to Checkout.");
            return;
        }
                    
        double subTotal = payment.calculateSubtotal();
        double tax = payment.calculateTax();
        double total = payment.calculateTotal();
                    
        System.out.println("\n==== Checkout Cart ====");
        System.out.println("Subtotal: $" + String.format("%.2f", subTotal));
        System.out.println("Tax (8%): $" + String.format("%.2f", tax));
        System.out.println("Total: $" + String.format("%.2f", total));
                    
        System.out.println("\nSelect Payment Method: ");
        System.out.println("1. Cash");
        System.out.println("2. Credit Card");
        System.out.print("Choice: ");
        byte payChoice = input.nextByte();
        input.nextLine();
                    
        Payment finalPayment;
                    
        switch(payChoice)
        {
            case 1:
            {
                System.out.println("\nEnter Amount of Cash Given: $");
                double cashGiven = input.nextDouble();
                input.nextLine();
                CashPayment cashPayment = new CashPayment(cashGiven);
                            
                for(Item i : payment.getPurchasedItems().keySet())
                {
                    cashPayment.addItem(i, payment.getPurchasedItems().get(i));
                }
                if(cashGiven < cashPayment.calculateTotal())
                {
                    System.out.println("Insufficient Cash. Transaction Canceled.");
                    break;
                }
                finalPayment = cashPayment;
                            
                System.out.println("Cash Payment Successful. Change Due: $" + String.format("%.2f", cashPayment.getChangeDue()));
                Receipt receipt = new Receipt(cashPayment);
                System.out.println(receipt.generateReceipt());
                break;
            }
            case 2:
            {
                ArrayList<CreditCard> cards = ccManager.getCardsForMember(member);
                CreditCard selectedCard = null;
                            
                if(cards.isEmpty())
                {
                    System.out.println("No Saved Cards found. Please Enter Card Information");
                    System.out.print("Card Number (16 digits): ");
                    String cardNumber = input.nextLine();
                                
                    System.out.print("Card Holder Name: ");
                    String cardHolderName = input.nextLine();
                                
                    System.out.print("Expiry Date (MM/YY): ");
                    String expiryDate = input.nextLine();
                                
                    selectedCard = new CreditCard(cardNumber, cardHolderName, expiryDate, member.getUsername());
                                
                    System.out.print("Do you Wish to save this Card to your account (Y/N): ");
                    String saveChoice = input.nextLine();
                    if(saveChoice.equalsIgnoreCase("Y") || saveChoice.equalsIgnoreCase("Yes"))
                    {
                        ccManager.addCard(member, selectedCard);
                        System.out.println("Card Saved");
                    }
                    else
                    {
                        System.out.println("Select a Card to pay with: ");
                        for(int i = 0; i < cards.size(); i++)
                        {
                            System.out.println((i+1) + ". " + cards.get(i));
                        }
                        System.out.print("Choice: ");
                        int cardChoice = input.nextInt();
                        input.nextLine();
                                    
                        if(cardChoice < 1 || cardChoice > cards.size())
                        {
                            System.out.println("Invalid Card Choic. Transaction Canceled");
                            break;
                        }
                                    
                        selectedCard = cards.get(cardChoice - 1);
                    }
                }
                else
                {
                    System.out.println("Select a Card to pay with: ");
                    for(int i = 0; i < cards.size(); i++)
                    {
                        System.out.println((i+1) + ". " + cards.get(i));
                    }
                    
                    System.out.print("Choice: ");
                    int cardChoice = input.nextInt();
                    input.nextLine();
                    
                    if(cardChoice < 1 || cardChoice > cards.size())
                    {
                        System.out.println("Invalid Card Choice. Transaction Canceled");
                        break;
                    }
                    
                    selectedCard = cards.get(cardChoice - 1);
                }
                
                CreditCardPayment ccPayment = new CreditCardPayment(member, selectedCard);
                                
                for(Item i : payment.getPurchasedItems().keySet())
                {
                    ccPayment.addItem(i, payment.getPurchasedItems().get(i));
                }
                                
                finalPayment = ccPayment;
                System.out.println("Credit Card Payment Successful");
                
                Receipt receipt = new Receipt(ccPayment);
                System.out.println(receipt.generateReceipt());
                break;
            }
        }
    }
    
    
    
    public static void staffMenu(Staff staff, MembershipManager membershipManager)
    {
        Scanner input = new Scanner(System.in);
        
    }
}
