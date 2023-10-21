package billboard.com.elements;

/**
 * Contains Year/Month/Day and allows sorting (Zero 2010 Based)
 * 
 * @author Ricky Chen
 *
 */
public class Date implements Comparable<Date>{
	private int day, month, year;
	
	/**
	 * Manual Entry of Date
	 * 
	 * @param day - 0-31
	 * @param month - 0-12
	 * @param year - At Least 2010
	 */
	public Date(int year, int month, int day) {
		this.year=year;
		this.month=month;
		this.day=day;
	}

	public Date(String text) {
		String[] data = text.trim().split("-");
		this.year=Integer.parseInt(data[0]);
		this.month=Integer.parseInt(data[1]);
		this.day=Integer.parseInt(data[2]);
	}
	
	/**
	 * Raw Day Entry of Date
	 * 
	 * @param rawDay
	 */
	public Date(int rawDay) {
		assignByRawDay(rawDay);
	}
	
	/**
	 * Used with String Array from QoL
	 * 
	 * @param date - {year, month, day}
	 */
	public Date(String[] date) {
		System.out.println(date[0]+" "+date[1]+" "+date[2]+" ");
		this.year = Integer.parseInt(date[0]);
		this.month = Integer.parseInt(date[1]);
		this.day = Integer.parseInt(date[2]);
	}
	
	public int getDay() {
		return day;
	}

	public int getMonth() {
		return month;
	}
	
	public int getYear() {
		return year;
	}
	
	/**
	 * Returns a Rough Estimate of Raw Days (Based 2010)
	 * 
	 * @param date - Date Used to give RawDays
	 * @return 365+31+0
	 */
	public static int getRawDays(Date date) {
		return date.getRawDays();
		
	}
	
	/**
	 * Returns a Rough Estimate of Raw Days (Based 2010)
	 * 
	 * @param date - Date Used to give RawDays
	 * @return 365+31+0
	 */
	public int getRawDays() {
		return (this.year-2010)*416+this.month*32+this.day;
	}
	
	public void addDay(int day) {
		assignByRawDay(getRawDays()+day);
	}
	
	public void assignByRawDay(int rawDay) {
		int yearAmount = rawDay/416;
		this.year = yearAmount+2010;
		rawDay-=416*yearAmount;
		int monthAmount = rawDay/32;
		this.month = monthAmount;
		rawDay-=32*monthAmount;
		this.day=rawDay;
	}
	
	@Override
	public int compareTo(Date date) {
		// TODO Auto-generated method stub
		return this.getRawDays() - date.getRawDays();
	}
	
	@Override
	public String toString() {
		//return(String.format("%02d/%02d/%04d", month, day, year));
		return(String.format("%04d-%02d-%02d", year, month, day));
	}

	
}
