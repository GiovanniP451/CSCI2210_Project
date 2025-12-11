
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
                        staffMenu(staff, staffManager, memberManager, membershipManager, facility, allClasses);
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
        
	facility.addArea("Entrance",  false,       false); // 0
        facility.addArea("Shop",      true,        false); // 1
        facility.addArea("Gym Floor", true,        false); // 2
        facility.addArea("Rest Area", false,       false); // 3
        facility.addArea("Classes",   false,       true);  // 4
        
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
        Area currentArea = facility.getAreas().get(0); //default Gym Entrance
        
        byte choice;
        do
        {
            System.out.println("\n==== Member Menu (" + member.getName() + ") ====");
            System.out.println("Current Area: " + currentArea.getName());
            
            if(currentArea.getName().equalsIgnoreCase("Shop"))
            {
                System.out.println("1. Enter Shop Menu");
                System.out.println("2. View My Info");
                System.out.println("3. Change Area");
                System.out.println("4. Logout");
            }
            else if(currentArea.getName().equalsIgnoreCase("Classes"))
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
            else if(currentArea.getName().equalsIgnoreCase("Classes"))
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
                    Item purchasedCopy = new Item(item.getId(), item.getName(), qty, item.getPrice());
                    cart.addItem(purchasedCopy, qty);
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
                        System.out.println("Card will be used for this purchased only");
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
                        System.out.println("Invalid Card Choic. Transaction Canceled");
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
    
    
    
    public static void staffMenu(Staff staff, StaffManager staffManager,MemberManager memberManager, 
            MembershipManager membershipManager, Facility facility, ArrayList<Class> classes)
    {
        Scanner input = new Scanner(System.in);
        
        byte choice;
        do
        {
            System.out.println("\n==== Management System ====");
            System.out.println("1. Staff Management");
            System.out.println("2. Member Management");
            System.out.println("3. Inventory Management");
            System.out.println("4. Membership Management");
            System.out.println("5. Class Management");
            System.out.println("6. Reports");
            System.out.println("7. Logout"); // Back to login menu
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    staffManagementMenu(staffManager);
                    break;
                }
                case 2:
                {
                    memberManagementMenu(memberManager);
                    break;
                }
                case 3:
                {
                    inventoryManagementMenu(facility);
                    break;
                }
                case 4:
                {
                    membershipManagementMenu(membershipManager);
                    break;
                }
                case 5:
                {
                    classManagementMenu(classes);
                    break;
                }
                case 6:
                {
                    reportsMenu(memberManager, staffManager, classes);
                    break;
                }
                case 7:
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
            
        } while(choice != 7);
    }
    
    public static void staffManagementMenu(StaffManager staffManager)
    {
        Scanner input = new Scanner(System.in);
        byte choice;
        do
        {
            System.out.println("\n==== Staff Management ====");
            System.out.println("1. Add Staff");
            System.out.println("2. Edit Staff");
            System.out.println("3. Remove Staff");
            System.out.println("4. List Staff");
            System.out.println("5. Back to Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    System.out.println("Please Enter Info");
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    
                    staffManager.addStaff(new Staff(name, user, pass));
                    System.out.println("New Staff Successfully Added to System");
                    break;
                }
                case 2:
                {
                    staffManager.listStaff();
                    System.out.println("Pleae Enter username of Staff to Edit");
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = input.nextLine();
                    System.out.print("Enter New Username: ");
                    String newUser = input.nextLine();
                    System.out.print("Enter New Password: ");
                    String newPass = input.nextLine();
                    staffManager.updateStaff(user, newName, newUser, newPass);
                    break;
                }
                case 3:
                {
                    System.out.print("Name to Remove: ");
                    String name = input.nextLine();
                    boolean temp = staffManager.removeStaff(name);
                    if(temp == true)
                    {
                        System.out.println("Staff removed");
                    }
                    else
                    {
                        System.out.println("Failed to Remove Staff");
                    }
                    break;
                }
                case 4:
                {
                    staffManager.listStaff();
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
        } while(choice != 5);
        
        
    }
    
    public static void memberManagementMenu(MemberManager memberManager)
    {
        Scanner input = new Scanner(System.in);
        byte choice;
        do
        {
            System.out.println("\n==== Member Management ====");
            System.out.println("1. Add Member");
            System.out.println("2. Edit Member");
            System.out.println("3. Remove Member");
            System.out.println("4. List Members");
            System.out.println("5. Back To Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    System.out.println("Please Enter Info");
                    System.out.print("Name: ");
                    String name = input.nextLine();
                    
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    
                    System.out.print("Password: ");
                    String pass = input.nextLine();
                    
                    memberManager.addMember(new Member(name, user, pass));
                    System.out.println("New Member Successfully Added to System");
                    break;
                }
                case 2:
                {
                    memberManager.listMembers();
                    System.out.println("Pleae Enter username of Member to Edit");
                    System.out.print("Username: ");
                    String user = input.nextLine();
                    System.out.print("Enter New Name: ");
                    String newName = input.nextLine();
                    System.out.print("Enter New Password: ");
                    String newPass = input.nextLine();
                    memberManager.updateMember(user, newName, newPass);
                    break;
                }
                case 3:
                {
                    System.out.print("Name to Remove: ");
                    String name = input.nextLine();
                    boolean temp = memberManager.removeMember(name);
                    if(temp == true)
                    {
                        System.out.println("Staff removed");
                    }
                    else
                    {
                        System.out.println("Failed to Remove Staff");
                    }
                    break;
                }
                case 4:
                {
                    memberManager.listMembers();
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
            
        } while(choice != 5);
    }
    
    public static void inventoryManagementMenu(Facility facility)
    {
        Scanner input = new Scanner(System.in);
        Inventory shopInventory = facility.findArea("Shop").getInventory();
        Inventory gymInventory = facility.findArea("Gym Floor").getInventory();
        
        byte choice;
        do
        {
            System.out.println("\n==== Inventory Management ====");
            System.out.println("1. Manage Shop Inventory");
            System.out.println("2. Manage Gym Floor Inventory");
            System.out.println("3. Back to Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            
            switch(choice)
            {
                case 1:
                {
                    manageInventory(shopInventory, "Shop Inventory");
                    break;
                }
                case 2:
                {
                    manageInventory(gymInventory, "Gym Floor Inventory");
                    break;
                }
                case 3:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
        } while(choice != 3);
    }
    
    public static void manageInventory(Inventory inventory, String title)
    {
        Scanner input = new Scanner(System.in);
        byte choice;
        do
        {
            System.out.println("\n==== " + title + " ====");
            System.out.println("1. View Items");
            System.out.println("2. Add Item");
            System.out.println("3. Update Item Quantity");
            System.out.println("4. Remove Item");
            System.out.println("5. Back to Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    inventory.showAllItems();
                    break;
                }
                case 2:
                {
                    System.out.print("Item ID: ");
                    int id = input.nextInt();
                    input.nextLine();
                    
                    System.out.print("Item Name: ");
                    String name = input.nextLine();
                    
                    System.out.print("Item Quantity: ");
                    int qty = input.nextInt();
                    
                    System.out.print("Item Price: ");
                    double price = input.nextDouble();
                    
                    inventory.addItem(new Item(id, name, qty, price));
                    System.out.println("Item Added");
                    break;
                }
                case 3:
                {
                    System.out.print("Item ID: ");
                    int id = input.nextInt();
                    Item item = inventory.findItemByID(id);
                    
                    if(item == null)
                    {
                        System.out.println("Item not found");
                        break;
                    }
                    System.out.print("New Item Quantity: ");
                    int qty = input.nextInt();
                    item.setQuantity(qty);
                    
                    System.out.println("Quantity Updated");
                    break;
                }
                case 4:
                {
                    System.out.print("Item ID: ");
                    int id = input.nextInt();
                    Item item = inventory.findItemByID(id);
                    
                    if(item != null)
                    {
                        inventory.removeItem(item);
                        System.out.println("Item Removed");
                    }
                    else
                    {
                        System.out.println("Item Not Found");
                    }
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
        } while(choice != 5);
    }
    
    public static void membershipManagementMenu(MembershipManager membershipManager)
    {
        Scanner input = new Scanner(System.in);
        byte choice;
        do
        {
            System.out.println("\n==== Membership Management");
            System.out.println("1. List Membership");
            System.out.println("2. Add Membership");
            System.out.println("3. Edit Membership");
            System.out.println("4. Remove Membership");
            System.out.println("5. Back to Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    membershipManager.listMembership();
                    break;
                }
                case 2:
                {
                    System.out.print("Type: ");
                    String type = input.nextLine();
                    System.out.println("Cost: ");
                    double cost = input.nextDouble();
                    boolean status = membershipManager.createMembership(new Membership(type, cost));
                    if(status == true)
                    {
                        System.out.println("Membership Created");
                    }
                    else
                    {
                        System.out.println("Failed to Create Membership");
                    }
                    break;
                }
                case 3:
                {
                    System.out.print("Type to Edit: ");
                    String type = input.nextLine();
                    System.out.print("New Type Name: ");
                    String newType = input.nextLine();
                    System.out.print("New Price: ");
                    double newCost = input.nextDouble();
                    membershipManager.updateMembership(type, newType, newCost);
                    break;
                }
                case 4:
                {
                    System.out.print("Type to Remove: ");
                    String remove = input.nextLine();
                    boolean status = membershipManager.removeMembership(remove);
                    if(status == true)
                    {
                        System.out.println("Membership Removed");
                    }
                    else
                    {
                        System.out.println("Failed to remove Membership");
                    }
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
        } while(choice != 5);
    }
    
    public static void classManagementMenu(ArrayList<Class> classes)
    {
        Scanner input = new Scanner(System.in);
        
        byte choice;
        do
        {
            System.out.println("\n==== Class Management ====");
            System.out.println("1. List Classes");
            System.out.println("2. Add Class");
            System.out.println("3. Scehdule Class");
            System.out.println("4. View Roster");
            System.out.println("5. Back to Management Menu");
            System.out.print("Choice: ");
            choice = input.nextByte();
            input.nextLine();
            
            switch(choice)
            {
                case 1:
                {
                    for(Class c : classes)
                    {
                        System.out.println("- " + c.getClassName());
                    }
                    break;
                }
                case 2:
                {
                    System.out.print("Class Name: ");
                    String name = input.nextLine();
                    
                    System.out.print("Max Capacity: ");
                    int cap = input.nextInt();
                    input.nextLine();
                    classes.add(new Class(name, cap));
                    break;
                }
                case 3:
                {
                    System.out.print("Class Name: ");
                    String name = input.nextLine();
                    Class found = null;
                    for(Class c : classes)
                    {
                        if(c.getClassName().equalsIgnoreCase(name))
                        {
                            found = c;
                        }
                    }
                    if(found != null)
                    {
                        System.out.print("Start Time (##:##AM/PM): ");
                        String start = input.nextLine();
                        System.out.print("End Time (##:##AM/PM): ");
                        String end = input.nextLine();
                        
                        found.changeClassTime(start, end);
                    }
                    else
                    {
                        System.out.println("Class Not Found");
                    }
                    break;
                }
                case 4:
                {
                    System.out.print("Class Name: ");
                    String name = input.nextLine();
                    Class found = null;
                    for(Class c : classes)
                    {
                        if(c.getClassName().equalsIgnoreCase(name))
                        {
                            found = c;
                        }
                    }
                    if(found != null)
                    {
                        found.getRoster();
                    }
                    else
                    {
                        System.out.println("Class Was Not Found");
                    }
                    break;
                }
                case 5:
                {
                    System.out.println("Returning back to Management Menu");
                    return;
                }
                default:
                {
                    System.out.println("Invalid Choice. Try Again");
                    break;
                }
            }
        }while(choice != 5);
    }
    
    public static void reportsMenu(MemberManager memberManager, StaffManager staffManager, ArrayList<Class> classes)
    {
        System.out.println("\n==== Report ====");
        System.out.println("Total Members: " + memberManager.getMembers().size());
        System.out.println("Total Staff: " + staffManager.getStaffList().size());
        System.out.println("Total Classes: " + classes.size());
        System.out.println("\nClasses with Enrollments");
        boolean anyEnrollment = false;
        for(Class c : classes)
        {
            if(!c.getEnrolledMembers().isEmpty())
            {
                anyEnrollment = true;
                System.out.println(c.getClassName() + ": " + c.getEnrolledMembers().size() + " enrolled");
                
            }    
        }
        if(anyEnrollment == false)
        {
            System.out.println("No Classes currently have enrollments");
        }    
    }
}
