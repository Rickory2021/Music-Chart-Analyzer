package billboard.com.filehandler;

import java.util.ArrayList;

import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidFileFormat;
import billboard.com.sorter.ArtistSorter;
import billboard.com.sorter.PlacementSorter;
import billboard.com.sorter.SongSorter;
import billboard.com.sorter.Sorter;

public class SorterFileHandler extends FileHandler{
	
	
	public SorterFileHandler() {
		super();
	}
	
	public SorterFileHandler(boolean showErrorMessage, boolean canOverride) {
		super(showErrorMessage, canOverride);
	}
	
	public String getText(String sorterName) {
		String fileName = "Database\\Billboard.com\\Project\\Sorter\\"+sorterName+".txt";
		return super.getText(fileName);
	}
	
	public String getText(Sorter<?> sorter) {
		return sorter.toString();
	}
	
	public static Sorter<?> readSorter(String sorterName) throws InvalidFileFormat {
		//String directoryString = "Database\\Billboard.com\\Project\\Sorter";
		SorterFileHandler fileHandler = new SorterFileHandler();
		String[] text = fileHandler.getText(sorterName).trim().split("\n");
		
		if(text.length<2)
			throw new InvalidFileFormat("Invalid Sorter Format");
		ArrayList<String[]> order = new ArrayList<>();
		for(int i = 1; i<text.length;i++) {
			order.add(text[i].split("---"));
		}
		
		Sorter<?> output = null;
		if(text[0].indexOf("Artist") == 0) {
			int startingIndex = "Artist Sorter: ".length();
			String name;
			try {
				name = text[0].substring(startingIndex).trim();
			}catch(Exception e){
				throw new InvalidFileFormat("Invalid Sorter Format");
			}
			output = new ArtistSorter(name, order);
			
		}else if(text[0].indexOf("Song") == 0) {
			int startingIndex = "Song Sorter: ".length();
			String name;
			try {
				name = text[0].substring(startingIndex).trim();
			}catch(Exception e){
				throw new InvalidFileFormat("Invalid Sorter Format");
			}
			output = new SongSorter(name, order);
			
		}else if(text[0].indexOf("Placement") == 0) {
			int startingIndex = "Placement Sorter: ".length();
			String name;
			try {
				name = text[0].substring(startingIndex).trim();
			}catch(Exception e){
				throw new InvalidFileFormat("Invalid Sorter Format");
			}
			output = new PlacementSorter(name, order);
			
		}else {
			throw new InvalidFileFormat("Invalid Sorter Format");
		}
		return output;
	}
	
	public boolean makeText(String sorterName) {
		String directoryString = "Database\\Billboard.com\\Project\\Sorter";
		String newFileName = "Database\\Billboard.com\\Project\\Sorter\\"+sorterName+".txt";
		String text = getText(sorterName);
		return super.makeText(directoryString, newFileName, text);
	}
}
