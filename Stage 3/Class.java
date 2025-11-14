//author: Giovanni Pernudi



//Class to be able to allow the gym members to book/register for 
//their own classes that are avaliable. This includes classes that are not full or not 
//yet scheduled. 

//import an arraylist to contain the list of classes. 
import java.util.ArrayList;

public class Class {
	
    // Information about the class and the enrolled members within it.
    private String className;
    private String startTime;   // Example format: "09:00 AM"
    private String endTime;     // Example format: "10:00 AM"
    private int maxCapacity;
    private ArrayList<Member> enrolledMembers;
    private ArrayList<Staff> staff;

    // Constructor to initialize a new gym class
    public Class(String className, String startTime, String endTime, int maxCapacity) {
        this.className = className;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.enrolledMembers = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    // Overload: constructor for a class that is not yet scheduled
    public Class(String className, int maxCapacity) {
        this(className, null, null, maxCapacity);
    }

    // ============================
    // Helper / status methods
    // ============================

    // A class is considered "scheduled" if it has both a start and end time
    public boolean isScheduled() {
        return startTime != null && endTime != null;
    }

    // Returns how many spots are left
    public int spotsLeft() {
        return maxCapacity - enrolledMembers.size();
    }

    // Returns true if the class has no available spots
    public boolean isFull() {
        return spotsLeft() <= 0;
    }

    // ============================
    // Member-facing behavior
    // ============================

    /**
     * Allows a member to register/book the class.
     * - Only works if the class is scheduled
     * - Only works if the class is not full
     * - Avoids duplicate enrollment
     *
     * @return true if registration succeeded, false otherwise
     */
    public boolean register(Member member) {
        // Class must be scheduled (not "not yet scheduled")
        if (!isScheduled()) {
            System.out.println("Class " + className + " is not yet scheduled.");
            return false;
        }

        // Class must have room
        if (isFull()) {
            System.out.println("Class " + className + " is already full.");
            return false;
        }

        // Avoid duplicate registrations
        if (enrolledMembers.contains(member)) {
            System.out.println("Member is already enrolled in " + className + ".");
            return false;
        }

        enrolledMembers.add(member);
        System.out.println("Member enrolled in " + className + ". Spots left: " + spotsLeft());
        return true;
    }

    /**
     * Allows a member to cancel their enrollment.
     * Members can’t see the roster, but they can remove themselves.
     */
    public void cancel(Member member) {
        if (enrolledMembers.remove(member)) {
            System.out.println("Member removed from " + className + ". Spots left: " + spotsLeft());
        } else {
            System.out.println("Member was not enrolled in " + className + ".");
        }
    }

    // ============================
    // Staff-facing behavior
    // ============================

    // Staff can change the class time
    public void changeClassTime(String newStartTime, String newEndTime) {
        this.startTime = newStartTime;
        this.endTime = newEndTime;
        System.out.println("Class " + className + " time changed to " + startTime + " - " + endTime);
    }

    // Staff can see the roster (who is enrolled)
    // Members should NOT call this (in your UI, only show it to staff)
    public void getRoster() {
        System.out.println("Roster for class: " + className);
        if (enrolledMembers.isEmpty()) {
            System.out.println("No members enrolled.");
        } else {
            for (Member m : enrolledMembers) {
                // Adjust this depending on your Member class (e.g., m.getName())
                System.out.println(" - " + m);
            }
        }
    }

    // Staff list management (optional, for linking staff to the class)
    public void addStaff(Staff s) {
        if (!staff.contains(s)) {
            staff.add(s);
        }
    }

    public void removeStaff(Staff s) {
        staff.remove(s);
    }

    // ============================
    // Getters (optional, but useful)
    // ============================

    public String getClassName() {
        return className;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<Member> getEnrolledMembers() {
        return enrolledMembers;
    }

    public ArrayList<Staff> getStaff() {
        return staff;
    }
}
