package billboard.com;

import java.io.File;
import java.util.ArrayList;

import billboard.com.elements.Date;
import billboard.com.elements.Placement;
import billboard.com.elements.Song;
import billboard.com.exception.DuplicateNameError;

/**
 * Class Used with Methods that are widely used in multiple classes
 * 
 * @author Ricky Chen
 *
 */
public class BillboardComQoL {
	/**
	 * Point Scale for score from 100-1 from 1st place to 100th place
	 */
	public static final int[] STANDARD_POINT_SCALE = {101,100, 99, 98, 97, 96, 95, 94, 93, 92, 91, 90, 89, 88, 87, 86, 85, 84, 83, 82, 81, 80, 79, 78, 77, 76, 75, 74, 73, 72, 71, 70, 69, 68, 67, 66, 65, 64, 63, 62, 61, 60, 59, 58, 57, 56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
	public static int[] pointScale = STANDARD_POINT_SCALE;
	/**
	 * Convert Text to Capitalization when previous symbol isn't alphabetic
	 * 
	 * @param text = What is being converted
	 * @return Returns a String that is Capitalized similar to Python's Capitalize
	 */
	public static String toCapitalize(String text) {
		String newText = "";
		boolean nextCap = true;
		for(char character: text.toCharArray()) {
			if(nextCap) {
				// If Capitalization is Used
				if(Character.isAlphabetic(character)) {
					nextCap=false;
				}
				newText+=Character.toUpperCase(character);
			}else {
				// Prime next Capitalization
				if(!Character.isAlphabetic(character)) {
					nextCap=true;
				}
				newText+=Character.toLowerCase(character);
			}
		}
		return newText;
	}
	
	/**
	 * Convert Text to Date (Uses Regex to cover some Situations)
	 * 
	 * @param text - Formatted year, month, day
	 * @return Returns Date(year(0), month(1), day(2))
	 */
	public static Date toDate(String text) {
		String[] seperator = {", ", "- ", "/ ", 
							",", "-", "/", " "};
		String[] elements = null;
		for(int i = 0; i < seperator.length; i++) {
			elements = text.trim().split(seperator[i]+"+");
			if(elements.length==3)break;
		}
		return new Date(elements);
	}
	
	public static String toLength(String text, int length) {
		if(text.length()<length)
			return text;
		else
			return text.substring(0,length);
	}
	
	/**
	 * Checks if a name is active and throws and exception if duplicate
	 * 
	 * @param name - Song Name
	 * @throws DuplicateName  - Name already found in activeNames
	 */
	public static void isNameActive(String name, ArrayList<String> activeList) throws DuplicateNameError {
		if(activeList.contains(name)) {
			throw new DuplicateNameError("Cannot add a new Name that is Already Active");
		}
	}
	
	/**
	 * Calculate Score of one placement
	 * 
	 * @param place - Placement used to calculate score
	 * @return Returns 1 - 100 Points from 100th place - 1st place
	 */
	public static long calculateScore(int place) {
		return pointScale[place];
	}
	
	/**
	 * Calculate Score of one placement
	 * 
	 * @param place - Placement used to calculate score
	 * @return Returns 1 - 100 Points from 100th place - 1st place
	 */
	public static long calculateScore(Placement placement) {
		return pointScale[placement.getPlace()];
	}
	
	/**
	 * Calculate Score of Song and their placements
	 * Individual Score is 1 - 100 Points from 100th place - 1st place
	 * 
	 * @param song - Placement used to calculate score
	 * @return Returns Total Points from Song
	 */
	public static long calculateScore(Song song) {
		return calculateScore(song.getPlacements());
	}
	
	/**
	 * Calculate Score of an ArrayList of placements.
	 * Default: Individual Score is 1 - 100 Points from 100th place - 1st place
	 * 
	 * @param placments - ArrayList used to calculate score
	 * @param points - Amount of Points for each placement (Size 101)
	 * @return Returns Total Points from the ArrayList
	 */
	public static long calculateScore(ArrayList<Placement> placments) {
		int score = 0;
		for(Placement place: placments) {
			score+=pointScale[place.getPlace()];
		}
		return score;
	}
	
	
	/**
	 * Gives a Suffix based on the Ordinal Number
	 * 
	 * @param num - Number use the Ordinal Numbers
	 * @return Returns the Suffix
	 */
	public static String placementSuffix(int num) { 
		switch(num) {
		case 11: return "th";
		case 12: return "th";
		case 13: return "th";
		}
		switch(num%10) {
		case 1: return "st";
		case 2: return "nd";
		case 3: return "rd";
		}
		return "th";
	}
	
	public static String getAllFiles(File curDirectory) {
		StringBuilder output = new StringBuilder();
		File[] fileList = curDirectory.listFiles();
		for(File f:fileList) {
			if(f.isDirectory()) {
				output.append(getAllFiles(f)+"\n");
			}
			if(f.isFile()) {
				output.append(f.getPath()+"\n");
			}
		}
		return output.toString();
	}
	
	public static ArrayList<String[]> toStringArrayList(String[][] array){
		ArrayList<String[]> arrayList = new ArrayList<>();
		for(String[] arr:array) {
			arrayList.add(arr);
		}
		return arrayList;
	}
	
	public static <T> String ArrayListToString(ArrayList<T> array) {
		StringBuilder output = new StringBuilder();
		if(array.size()==0)return output.toString();
		output.append(array.get(0));
		for(int i = 1; i<array.size();i++) {
			output.append(String.format(":::%s", array.get(i)));
		}
		return output.toString();
	}
	
	public static <T> String ArrayListToString(ArrayList<T> array, String seperator) {
		StringBuilder output = new StringBuilder();
		if(array.size()==0)return output.toString();
		output.append(array.get(0));
		for(int i = 1; i<array.size();i++) {
			output.append(String.format("%s%s", seperator, array.get(i)));
		}
		return output.toString();
	}
	
}