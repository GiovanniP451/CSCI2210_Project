import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Schedule {
private final List<Class> classes = new ArrayList<>();
private final List<Staff> staffShifts = new ArrayList<>();


public void addClass(Class c) {
	if (c != null) classes.add(c);
	}


public void removeClass(Class c) {
	classes.remove(c);
	}


public List<Class> getSchedule() {
	return Collections.unmodifiableList(classes);
	}


public List<Staff> getStaffShifts() {
	return Collections.unmodifiableList(staffShifts);
	}


public void assignShift(Staff s) {
	if (s != null) staffShifts.add(s);
	}
	
}
	
