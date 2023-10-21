package billboard.com.filehandler;

import java.util.ArrayList;
import java.util.Arrays;

import billboard.com.elements.Artist;
import billboard.com.elements.Date;
import billboard.com.elements.Placement;
import billboard.com.elements.Song;
import billboard.com.elements.Spreadsheet;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidFileFormat;

public class SpreadsheetFileHandler extends FileHandler{
	public static final int GETTING_CHART_TYPE = 0, GETTING_DATE = 1, GETTING_ARTIST_AND_SONG = 2;
	
	public SpreadsheetFileHandler() {
		super();
	}
	
	public SpreadsheetFileHandler(String chartType) {
		super(chartType);
	}
	
	public SpreadsheetFileHandler(boolean showErrorMessage, boolean canOverride) {
		super(showErrorMessage, canOverride);
	}
	
	public SpreadsheetFileHandler(String chartType, boolean showErrorMessage, boolean canOverride) {
		super(chartType, showErrorMessage, canOverride);
	}
	
	public String getText(Spreadsheet spreadsheet) {
		return spreadsheet.toString();
	}
	
	public static Spreadsheet readSpreadsheet(String spreadsheetName) {
		String directoryString = "Database\\Billboard.com\\Project\\Spreadsheet";
		SpreadsheetFileHandler fileHandler = new SpreadsheetFileHandler();
		String[] text = fileHandler.getText(directoryString+"\\"+spreadsheetName+".txt").trim().split("\n");
		//System.out.println(fileHandler.getText(directoryString+"\\"+spreadsheetName).trim());
		Spreadsheet spreadsheet = new Spreadsheet(spreadsheetName);
		//System.out.println(">>>Artist SIZE"+ spreadsheet.getArtistList().size()+"\t");
		for(int i = 0, status = GETTING_CHART_TYPE;i<text.length;i++) {
			switch(status) {
			case GETTING_CHART_TYPE:
				// Getting ChartType
				i = getChartType(text, i, spreadsheet);
				//System.out.println("---"+spreadsheet+"---");
				status = GETTING_DATE;
				//System.out.println(">>>Artist SIZE"+ spreadsheet.getArtistList().size()+"\t");
				break;
			case GETTING_DATE:
				// Getting Date
				i = getDate(text, i, spreadsheet);
				status = GETTING_ARTIST_AND_SONG;
				//System.out.println(">>>Artist SIZE"+ spreadsheet.getArtistList().size()+"\t");
				break;
			case GETTING_ARTIST_AND_SONG:
				// Getting Artist & Cong
				i = getArtistAndSong(text, i, spreadsheet);
				break;
			}
		}
		
		return spreadsheet;
	}
	
	private static int getChartType(String[] text,int index, Spreadsheet spreadsheet) {
		spreadsheet.setChartType(text[index].trim());
		return index;
	}
	
	private static int getDate(String[] text,int index, Spreadsheet spreadsheet) {
		index = tranverseUntil(text, index, ">>>DATE:");
		index+=1;
		//System.out.println(index);
		
		for(; index < text.length;index++) {
			if(text[index].contains("END DATE<<<"))return index;
			//System.out.println("~~~"+text[index]+" "+index);
			spreadsheet.getDateList().add(new Date(text[index].trim()));
		}
		//System.out.println(index);
		return index;
	}
	
	private static int getArtistAndSong(String[] text,int index, Spreadsheet spreadsheet) {
		index = tranverseUntil(text, index, ">>>ARTIST:");
		//System.out.println("FOUND >>>ARTIST"+index);
		//System.out.print("Artist SIZE"+ spreadsheet.getArtistList().size()+"\t");
		index = getArtist(text, index, spreadsheet);
		//System.out.print("Artist Added"+index+"\t");
		//System.out.println("Artist SIZE"+ spreadsheet.getArtistList().size()+"\t");
		for(; index < text.length;index++) {
			if(text[index].contains("END ARTIST<<<"))return index;
			if(text[index].contains(">>>SONG:")) {
				//System.out.print("FOUND:"+index+"\t");
				index = getSong(text, index, spreadsheet);
				//System.out.println("DONE:"+index);
			}
		}
		//System.out.println("HELLO");
		return index;
	}
	
	private static int getArtist(String[] text,int index, Spreadsheet spreadsheet) {
		index+=1;
		// Artist Name
		Artist artist = new Artist(text[index].trim());
		index+=1;
		// Artist Flag
		ArrayList<String> flag = new ArrayList<String>(Arrays.asList(text[index].split(":::")));
		artist.setFlags(flag);
		spreadsheet.getArtistList().add(artist);
		return index;
	}
	
	private static int getSong(String[] text,int index, Spreadsheet spreadsheet) {
		Artist artist = spreadsheet.getArtistList().get(spreadsheet.getArtistList().size()-1);
		index+=1;
		// Song Name
		//System.out.println(text[index].trim());
		Song song = new Song(text[index].trim(), artist);
		index+=1;
		// Song Flag
		ArrayList<String> flag = new ArrayList<String>(Arrays.asList(text[index].split(":::")));
		song.setFlags(flag);
		index+=1;
		// Song Placement
		for(; index < text.length;index++) {
			if(text[index].contains("END SONG<<<"))break;
			song.addPlacements(new Placement(text[index].trim()));
		}
		artist.addSong(song);
		spreadsheet.getSongList().add(song);
		return index;
	}
	
	private static int tranverseUntil(String[] text,int startingIndex, String substring) {
		for(int i = startingIndex;i<text.length;i++) {
			//System.out.println(text[i]+"::"+substring);
			if(text[i].contains(substring))return i;
		}
		return text.length;
	}
	
	/*private static int tranverseUntil(String[] text,int startingIndex, String[] substrings) {
		for(int i = startingIndex;i<text.length;i++) {
			for(String substring: substrings)
			if(text[i].contains(substring))return i;
		}
		return text.length;
	}*/
	
	public boolean makeText(Spreadsheet spreadsheet) {
		String directoryString = "Database\\Billboard.com\\Project\\Spreadsheet";
		String newFileName = "Database\\Billboard.com\\Project\\Spreadsheet\\"+spreadsheet.getSpreadsheetName()+".txt";
		String text = spreadsheet.toString();
		
		return super.makeText(directoryString, newFileName, text);
	}
}
