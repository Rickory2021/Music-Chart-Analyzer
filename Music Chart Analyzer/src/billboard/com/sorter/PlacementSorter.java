package billboard.com.sorter;

import java.util.ArrayList;

import billboard.com.BillboardComQoL;
import billboard.com.elements.Placement;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidPrecedenceException;

public class PlacementSorter extends Sorter<Placement>{
	public static final String[] precedenceCategory = new String[] {"Date","Place"};
	public static final String[] precedenceType = new String[] {"Ascending","Descending"};
	// order[0] = Date/Place
	// order[1] = Ascending/Descending/""(Default to Ascending)
	
	public PlacementSorter() {
		super();
	}
	
	public PlacementSorter(ArrayList<String[]> order) {
		super(order);
	}
	
	public PlacementSorter(String name) {
		super(name);
	}
	
	public PlacementSorter(String name, ArrayList<String[]> order) {
		super(name, order);
	}
	
	@Override
	public void addPrecedence(String[] newElement) throws InvalidPrecedenceException {
		isPrecedenceValid(newElement);
		super.getPrecedence().add(newElement);
	}
	
	public static void isPrecedenceValid(String[] element) throws InvalidPrecedenceException{
		// Checks if Elements are Accessible
		if(element.length!=2) throw new InvalidPrecedenceException("Cannot add an invalid precedence");
		// Checks for Ascending, Descending
		if(!(BillboardComQoL.toCapitalize(element[1]).equals("Ascending")||BillboardComQoL.toCapitalize(element[1]).equals("Descending")))
			throw new InvalidPrecedenceException("Cannot add an invalid precedence");
		// Checks for Flags and there activeNames
		if(element[0].length()>5 && BillboardComQoL.toCapitalize(element[0].substring(0,4)).equals("Flag"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Date"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Place"))
			return;
		// element[0] did not match Date and Place
		throw new InvalidPrecedenceException("Cannot add an invalid precedence");		
	}

	@Override
	public int compare(Placement place1, Placement place2) {
		ArrayList<String[]> precedence = super.getPrecedence();
		for(int i = 0; i<precedence.size();i++) {
			int multiple, value;
			if(BillboardComQoL.toCapitalize(precedence.get(i)[1]).equals("Ascending")) multiple = 1;
			else multiple = -1;
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Date")) {
				int day = (place1.getDate().getRawDays()-place2.getDate().getRawDays());
				if(day == 0) continue;
				return day*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Place")) {
				value = place1.getPlace()-place2.getPlace();
				if(value == 0) continue;
				return value*multiple;
			}
		}
		return 0;
	}

	@Override
	public String toString() {
		return "Placement " + super.toString();
	}
}
