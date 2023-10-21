package billboard.com.elements;

import java.util.ArrayList;
import java.util.Arrays;

import billboard.com.BillboardComQoL;
import billboard.com.exception.DuplicateNameError;
import billboard.com.filehandler.TextFileHandler;

public class Spreadsheet {
	private String spreadsheetName;
	private ArrayList<Date> dateList;
	private ArrayList<Artist> artistList;
	private ArrayList<Song> songList;
	private String chartType;
	public static boolean printArtist = true, printSong = true, printError = true;
	
	public Spreadsheet() {
		this.spreadsheetName = null;
		this.dateList = new ArrayList<>();
		this.artistList = new ArrayList<>();
		this.songList = new ArrayList<>();
		this.chartType = "Billboard Japan Hot 100 – Billboard";
	}
	
	public Spreadsheet(String spreadsheetName) {
		this.spreadsheetName = spreadsheetName;
		this.dateList = new ArrayList<>();
		this.artistList = new ArrayList<>();
		this.songList = new ArrayList<>();
		this.chartType = "Billboard Japan Hot 100 – Billboard";
	}
	
	public Spreadsheet(String spreadsheetName, String chartType) {
		this.spreadsheetName = spreadsheetName;
		this.dateList = new ArrayList<>();
		this.artistList = new ArrayList<>();
		this.songList = new ArrayList<>();
		this.chartType = chartType;
	}
	
	/*
	public Spreadsheet(String fileName) {
		Spreadsheet temp = ProjectReader.readSpreadsheet(fileName);
		this.spreadsheetName=temp.getSpreadsheetName();
		this.dateList = temp.getDateList();
		this.artistList = temp.();
		this.songList = temp.getSongList();
		this.chartType = "Billboard Japan Hot 100 – Billboard";
	}*/
	
	public Spreadsheet(String name, Date[] dateList, String chartType) {
		this.spreadsheetName = name;
		this.dateList = new ArrayList<>();
		this.artistList = new ArrayList<>();
		this.songList = new ArrayList<>();
		this.chartType = chartType;
		Arrays.sort(dateList);
		for(Date date : dateList) {
			TextFileHandler.readTextFile(date, this);
		}
	}
	
	public String getSpreadsheetName() {
		return spreadsheetName;
	}
	
	public void setSpreadsheetName(String spreadsheetName) {
		this.spreadsheetName=spreadsheetName;
	}
	
	public ArrayList<Date> getDateList() {
		return dateList;
	}

	public ArrayList<Artist> getArtistList() {
		return artistList;
	}

	public ArrayList<Song> getSongList() {
		return songList;
	}

	public String getChartType() { 
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

	// Add date and its data into the Spreadsheets
	public boolean addDate(Date date) {
		return TextFileHandler.readTextFile(date, this);
	}
	
	public boolean removeDate(Date date) {
		for(int dateIndex = 0; dateIndex<dateList.size();dateIndex++) {
			if(date.getRawDays()==dateList.get(dateIndex).getRawDays()) {
				// Date Exist
				for(int songListIndex = 0; songListIndex<songList.size();songListIndex++){
					// Remove Date from Song
					songList.get(songListIndex).removeDate(date);
					// Check if Empty Song
					if(songList.get(songListIndex).getPlacements().size()==0) {
						// Remove Song from songList and artist
						Song song =  songList.get(songListIndex);
						Artist artist = song.getArtist();
						int songArtistIndex = artist.getSongs().indexOf(song);
						artist.getSongs().remove(songArtistIndex);
						songList.remove(songListIndex);
						// Check if Empty Artist
						if(artist.getSongs().size()==0){
							// Remove Artist from ArtistList
							int artistIndex = artistList.indexOf(artist);
							artistList.remove(artistIndex);
						}
					}
				}
				dateList.remove(dateIndex);
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append(String.format("%s\n",chartType));
		
		output.append(String.format(">>>DATE:\n"));
		for(int i = 0; i<dateList.size();i++) {
			output.append(String.format("%s\n",dateList.get(i)));
		}
		
		output.append("END DATE<<<\n");
		
		
		for(int i = 0; i<artistList.size();i++) {
			Artist artist = artistList.get(i);
			output.append(String.format(">>>ARTIST:\n"));
			output.append(String.format("%s\n%s\n",artist.getName(),BillboardComQoL.ArrayListToString(artist.getFlags())));
			for(int j = 0; j<artist.getSongs().size();j++) {
				output.append(String.format(">>>SONG:\n"));
				Song song = artist.getSongs().get(j);
				output.append(String.format("%s\n%s\n", song.getName(), BillboardComQoL.ArrayListToString(song.getFlags())));
				for(int k = 0; k<song.getPlacements().size();k++) {
					output.append(String.format("%s\n", song.getPlacements().get(k)));
				}
				output.append("END SONG<<<\n");
			}
			output.append("END ARTIST<<<\n");
		}
		
		return output.toString();
	}
}
