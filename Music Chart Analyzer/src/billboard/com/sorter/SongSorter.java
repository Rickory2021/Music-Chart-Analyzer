package billboard.com.sorter;

import java.util.ArrayList;

import billboard.com.BillboardComQoL;
import billboard.com.elements.Song;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidPrecedenceException;

public class SongSorter extends Sorter<Song>{
	public static final String[] precedenceCategory = new String[] {"Name","Score","Flag","String","Initial Date","Recent Date","Placement Count"};
	public static final String[] precedenceType = new String[] {"Ascending","Descending"};
	// order[0] = Name/Score/Flag-"String"/Initial Date/Recent Date/# of Placements
	// order[1] = Ascending/Descending/""(Default to Ascending)
	
	public SongSorter() {
		super();
	}
	
	public SongSorter(ArrayList<String[]> order) {
		super(order);
	}
	
	public SongSorter(String name) {
		super(name);
	}
	
	public SongSorter(String name, ArrayList<String[]> order) {
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
		if(BillboardComQoL.toCapitalize(element[0]).equals("Placement Count"))
			return;
		// element[0] did not match Name, Score, Flag, Initial Date, Recent Date, and Placement Count
		throw new InvalidPrecedenceException("Cannot add an invalid precedence");		
	}

	@Override
	public int compare(Song song1, Song song2) {
		ArrayList<String[]> precedence = super.getPrecedence();
		for(int i = 0; i<precedence.size();i++) {
			int multiple, value;
			if(BillboardComQoL.toCapitalize(precedence.get(i)[1]).equals("Ascending")) multiple = 1;
			else multiple = -1;
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Name")) {
				value = (BillboardComQoL.toCapitalize(song1.getName()).compareTo(BillboardComQoL.toCapitalize(song2.getName())));
				if(value == 0) continue;
				return value*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Score")) {
				long score = (song1.getScore()-song2.getScore());
				if(score > 0) value = 1;
				else if(score < 0) value = -1;
				else value = 0;
				if(score == 0) continue;
				return value*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0].substring(0,4)).equals("Flag")) {
				boolean flag1 = false, flag2 = false;
				// Artist 1 Searching for Flag
				for(int j = 0; j < song1.getFlags().size(); j++) {
					if(song1.getFlags().get(j).equals(precedence.get(i)[0].substring(5, precedence.get(i)[0].length()))) {
						flag1=true;
						break;
					}
					System.out.println(song1.getFlags().get(j)+" " +precedence.get(i)[0].substring(5, precedence.get(i)[0].length()));
					
				}
				// Artist 2 Searching for Flag
				for(int j = 0; j < song2.getFlags().size(); j++) {
					if(song2.getFlags().get(j).equals(precedence.get(i)[0].substring(5, precedence.get(i)[0].length()))) {
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
				int day = (song1.getInitialDate().getRawDays()-song2.getInitialDate().getRawDays());
				if(day == 0) continue;
				return day*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Recent Date")) {
				int day = (song1.getRecentDate().getRawDays()-song2.getRecentDate().getRawDays());
				if(day == 0) continue;
				return day*multiple;
			}
			
			if(BillboardComQoL.toCapitalize(precedence.get(i)[0]).equals("Placement Count")) {
				int places = (song1.getPlacements().size()-song2.getPlacements().size());
				if(places == 0) continue;
				return places*multiple;
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Song " + super.toString();
	}
}
