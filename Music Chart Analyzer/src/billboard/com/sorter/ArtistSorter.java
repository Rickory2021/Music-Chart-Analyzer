package billboard.com.sorter;

import java.util.ArrayList;

import billboard.com.BillboardComQoL;
import billboard.com.elements.Artist;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidPrecedenceException;

public class ArtistSorter extends Sorter<Artist>{
	public static final String[] precedenceCategory = new String[] {"Name","Score","Flag","String","Initial Date","Recent Date"};
	public static final String[] precedenceType = new String[] {"Ascending","Descending"};
	// order[0] = Name/Score/Flag-"String"/Initial Date/Recent Date/
	// order[1] = Ascending/Descending/""(Default to Ascending)
	
	public ArtistSorter() {
		super();
	}
	
	public ArtistSorter(ArrayList<String[]> order) {
		super(order);
	}
	
	public ArtistSorter(String name) {
		super(name);
	}
	
	public ArtistSorter(String name, ArrayList<String[]> order) {
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
		if(BillboardComQoL.toCapitalize(element[0]).equals("Name"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Score"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Recent Date"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Initial Date"))
			return;
		if(BillboardComQoL.toCapitalize(element[0]).equals("Song Count"))
			return;
		// element[0] did not match Name, Score, Flag, Initial Date, and Recent Date
		throw new InvalidPrecedenceException("Cannot add an invalid precedence");		
	}

	@Override
	public int compare(Artist artist1, Artist artist2) {
		ArrayList<String[]> precedence = super.getPrecedence();
		for(int i = 0; i<precedence.size();i++) {
			int multiple, value;
			if(BillboardComQoL.toCapitalize(precedence.get(i)[1]).equals("Ascending")) multiple = 1;
			else multiple = -1;
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Name")) {
				value = (BillboardComQoL.toCapitalize(artist1.getName()).compareTo(BillboardComQoL.toCapitalize(artist2.getName())));
				if(value == 0) continue;
				return value*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Score")) {
				long score = (artist1.getScore()-artist2.getScore());
				if(score > 0) value = 1;
				else if(score < 0) value = -1;
				else value = 0;
				if(score == 0) continue;
				return value*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0].substring(0,4)).equals("Flag")) {
				boolean flag1 = false, flag2 = false;
				// Artist 1 Searching for Flag
				for(int j = 0; j < artist1.getFlags().size(); j++) {
					if(artist1.getFlags().get(j).equals(precedence.get(i)[0].substring(5, precedence.get(i)[0].length()))) {
						flag1=true;
						break;
					}
					System.out.println(artist1.getFlags().get(j)+" " +precedence.get(i)[0].substring(5, precedence.get(i)[0].length()));
					
				}
				// Artist 2 Searching for Flag
				for(int j = 0; j < artist2.getFlags().size(); j++) {
					if(artist2.getFlags().get(j).equals(precedence.get(i)[0].substring(5, precedence.get(i)[0].length()))) {
						flag2=true;
						break;
					}
				}
				
				if(flag1 == flag2) {
					 continue;
				}else if(flag1) {
					return 1 * multiple;
				}else if(flag2) {
					return -1 * multiple;
				}	
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Initial Date")) {
				int day = (artist1.getInitialDate().getRawDays()-artist2.getInitialDate().getRawDays());
				if(day == 0) continue;
				return day*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Recent Date")) {
				int day = (artist1.getRecentDate().getRawDays()-artist2.getRecentDate().getRawDays());
				if(day == 0) continue;
				return day*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Song Count")) {
				int songs = (artist1.getSongs().size()-artist2.getSongs().size());
				if(songs == 0) continue;
				return songs*multiple;
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Artist " + super.toString();
	}
}
