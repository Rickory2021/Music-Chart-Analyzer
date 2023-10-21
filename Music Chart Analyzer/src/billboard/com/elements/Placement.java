package billboard.com.elements;

import billboard.com.BillboardComQoL;

/**
 * This holds the placements of a Song and the date it was give
 * @author Ricky Chen
 *
 */
public class Placement {
	private Date date;
	private int place;
	
	public Placement(Date date, int place) {
		this.date=date;
		this.place=place;
	}
	
	public Placement(String text) {
		String[] arr = text.trim().split("-");
		this.date = new Date(String.format("%s-%s-%s", arr[1], arr[2], arr[3]));
		this.place = Integer.parseInt(arr[0].substring(0,arr[0].length()-"th".length()));
		
	}

	public Date getDate() {
		return date;
	}

	public int getPlace() {
		return place;
	}
	
	@Override
	public String toString() {
		return String.format("%3d%s-%s", 
				this.place, BillboardComQoL.placementSuffix(this.place), this.date);
	}
}
