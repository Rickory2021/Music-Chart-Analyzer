package billboard.com.filehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import billboard.com.BillboardComQoL;
import billboard.com.elements.Artist;
import billboard.com.elements.Date;
import billboard.com.elements.Placement;
import billboard.com.elements.Song;
import billboard.com.elements.Spreadsheet;
import billboard.com.sorter.ArtistSorter;
import billboard.com.sorter.SongSorter;


public class TextFileHandler extends FileHandler{
	public static final int FINDING_SONG_DATA = 0, FINDING_PLACEMENT = 1, FINDING_SONG_NAME = 2, FINDING_ARTIST_NAME=3;
	
	private String
		DataIdentifier = "<div class=\"o-chart-results-list-row-container\">",
		PlacementIdentifier = "<span class=\"c-label  a-font-primary-bold-l u-font-size-32@tablet u-letter-spacing-0080@tablet\" >",
		FirstSongNameIdentifier = "<h3 id=\"title-of-a-story\" class=\"c-title  a-no-trucate a-font-primary-bold-s u-letter-spacing-0021 u-font-size-23@tablet lrv-u-font-size-16 u-line-height-125 u-line-height-normal@mobile-max a-truncate-ellipsis u-max-width-245 u-max-width-230@tablet-only u-letter-spacing-0028@tablet\">",
		FirstArtistNameIdentifier = "<span class=\"c-label  a-no-trucate a-font-primary-s lrv-u-font-size-14@mobile-max u-line-height-normal@mobile-max u-letter-spacing-0021 lrv-u-display-block a-truncate-ellipsis-2line u-max-width-330 u-max-width-230@tablet-only u-font-size-20@tablet\" >",
		BaseSongNameIdentifier ="<h3 id=\"title-of-a-story\" class=\"c-title  a-no-trucate a-font-primary-bold-s u-letter-spacing-0021 lrv-u-font-size-18@tablet lrv-u-font-size-16 u-line-height-125 u-line-height-normal@mobile-max a-truncate-ellipsis u-max-width-330 u-max-width-230@tablet-only\">",
		BaseArtistNameIdentifier = "<span class=\"c-label  a-no-trucate a-font-primary-s lrv-u-font-size-14@mobile-max u-line-height-normal@mobile-max u-letter-spacing-0021 lrv-u-display-block a-truncate-ellipsis-2line u-max-width-330 u-max-width-230@tablet-only\" >";
	
	/* Place":::"SongName":::"ArtistName"\n"*/
	
	public TextFileHandler() {
		super();
	}
	
	public TextFileHandler(String chartType) {
		super(chartType);
	}
	
	public TextFileHandler(boolean showErrorMessage, boolean canOverride) {
		super(showErrorMessage, canOverride);
	}
	
	public TextFileHandler(String chartType, boolean showErrorMessage, boolean canOverride) {
		super(chartType, showErrorMessage, canOverride);
	}
	
	public String getText(Date date) {
		String fileLoc = "Database\\Billboard.com\\Text\\"+super.getChartType()+"\\"+date.getYear()+"\\"+super.getChartType()+" "+date+".txt";
		return super.getText(fileLoc);
	}
	
	public String getCompressedText(Date date) {
		String fileLoc = "Database\\Billboard.com\\HTML\\"+super.getChartType()+"\\"+date.getYear()+"\\"+super.getChartType()+" "+date+".html";
		return getCompressedText(fileLoc);
	}
	
	public String getCompressedText(String fileLocation) {
		StringBuilder output = new StringBuilder();
		int index = 0;int status = FINDING_SONG_DATA;
		try {
			//BufferedReader reader = new BufferedReader(new FileReader("Database\\Billboard.com\\HTML\\2011\\Billboard Japan Hot 100 – Billboard 2011-04-09.html"));
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
			String currentLine = "NULL";
			
			boolean firstSong=true;
			
			while(currentLine!=null) {
			//System.out.println(status);
				switch(status) {
				
				case FINDING_SONG_DATA:
					if(currentLine.contains(DataIdentifier)) {
						//System.out.println("FOUND SONG"+index);
						status = FINDING_PLACEMENT;
					}
					break;
					
				case FINDING_PLACEMENT:
					if(currentLine.contains(PlacementIdentifier)) {
						//System.out.println("FOUND PLACEMENT 1 "+index);
						// Iterate through to get a number
						while(!currentLine.trim().matches("-?\\d+(\\.\\d+)?")) {
							currentLine=reader.readLine(); index++;
							//System.out.println("FOUND PLACEMENT 2 "+index);
						}
						output.append(currentLine.trim()+":::");
						status = FINDING_SONG_NAME;
					}
					break;
					
				case FINDING_SONG_NAME:
					if(firstSong) {
						if(currentLine.contains(FirstSongNameIdentifier)) {
							currentLine=reader.readLine(); index++;
							// Iterate through to get a String
							while(currentLine.trim().equals("")) {
								currentLine=reader.readLine(); index++;
							}
							output.append(currentLine.trim()+":::");
							status = FINDING_ARTIST_NAME;
						}
					}else {
						if(currentLine.contains(BaseSongNameIdentifier)) {
							currentLine=reader.readLine(); index++;
							// Iterate through to get a String
							while(currentLine.trim().equals("")) {
								currentLine=reader.readLine(); index++;
							}
							output.append(currentLine.trim()+":::");
							status = FINDING_ARTIST_NAME;
						}
					}
					break;
				case FINDING_ARTIST_NAME:
					if(firstSong) {
						if(currentLine.contains(FirstArtistNameIdentifier)) {
							currentLine=reader.readLine(); index++;
							// Iterate through to get a String
							while(currentLine.trim().equals("")) {
								currentLine=reader.readLine(); index++;
							}
							output.append(currentLine.trim()+"\n");
							firstSong=false;
							status = FINDING_SONG_DATA;
						}
					}else {
						if(currentLine.contains(BaseArtistNameIdentifier)) {
							currentLine=reader.readLine(); index++;
							// Iterate through to get a String
							while(currentLine.trim().equals("")) {
								currentLine=reader.readLine(); index++;
							}
							output.append(currentLine.trim()+"\n");
							status = FINDING_SONG_DATA;
						}
					}
					break;
				}
				currentLine=reader.readLine(); index++;
			}
			reader.close();
		}catch(Exception e) {
			output.append("ERROR MESSAGE Line: "+index+"\t"+e.getMessage()+"\tStatus:"+status);
		}
		return output.toString();
	}
	
	public static boolean readTextFile (Date date, Spreadsheet spreadsheet) {
		// Check if date exist
		TextFileHandler textFileHandler = new TextFileHandler(spreadsheet.getChartType());
		String massText = textFileHandler.getText(date).trim();
		//System.out.println(massText);
		if(massText.contains("ERROR MESSAGE")) return false;
		// Check if date has already been added
		Collections.sort(spreadsheet.getDateList());
		int dateIndex = Collections.binarySearch(spreadsheet.getDateList(), date);
		if(dateIndex>=0) {
			// Date Exists
			return false;
		}
		dateIndex=-1*(dateIndex)-1;
		spreadsheet.getDateList().add(dateIndex,date);
		String[] chartText = massText.split("\n");
		ArtistSorter artistSorter = new ArtistSorter(new ArrayList<String[]>(Arrays.asList(new String[][] {{"Name","Ascending"}})));
		SongSorter songSorter = new SongSorter(new ArrayList<String[]>(Arrays.asList(new String[][] {{"Name","Ascending"}})));
		
		// Sort Artist & Song
		Collections.sort(spreadsheet.getArtistList(),artistSorter);
		for(Artist artist : spreadsheet.getArtistList()) {
			Collections.sort(artist.getSongs(),songSorter);
		}
		
		for(String text : chartText) {
			//System.out.println(text);
			System.out.println(text);
			String[] info = text.split(":::");
			String rank = info[0].trim(), songName = BillboardComQoL.toCapitalize(info[1].trim()), artistName = BillboardComQoL.toCapitalize(info[2].trim());
			
			// Check if Artist Exist
			Artist artist = new Artist(artistName);
			int artistIndex = Collections.binarySearch(spreadsheet.getArtistList(), artist, artistSorter);
			if(artistIndex<0) {
				System.out.println(artistIndex);
				System.out.println("MAKING NEW Artist>>> "+artistName);
				// Artist Does not Exist
				artistIndex=-1*(artistIndex)-1;
				spreadsheet.getArtistList().add(artistIndex,artist);
			}else {
				// Artist Exist
				artist = spreadsheet.getArtistList().get(artistIndex);
			}
			
			// Check if Song Exist
			Song song = new Song(songName, artist);
			Placement placement = new Placement(date, Integer.parseInt(rank));
			int songIndex = Collections.binarySearch(artist.getSongs(), song, songSorter);
			if(songIndex<0) {
				// Song Does not Exist
				songIndex=-1*(songIndex)-1;
				song.addPlacements(placement);
				spreadsheet.getArtistList().get(artistIndex).addSong(songIndex ,song);
				spreadsheet.getSongList().add(song);
			}else {
				// Song Exist
				song = spreadsheet.getArtistList().get(artistIndex).getSongs().get(songIndex);
				spreadsheet.getArtistList().get(artistIndex).addPlacement(placement);
				song.addPlacements(placement);
			}
		}
		return true;
		
	}
	
	public boolean makeText(Date date) {
		String directoryString = "Database\\Billboard.com\\Text\\"+super.getChartType()+"\\"+date.getYear();
		String newFileName = "Database\\Billboard.com\\Text\\"+super.getChartType()+"\\"+date.getYear()+"\\"+super.getChartType() +" "+date+".txt";
		String text = getCompressedText(date);
		return super.makeText(directoryString, newFileName, text);
	}
	
	public void fileHTMLDirectory() {
		String chartTypeDirectory = "Database\\Billboard.com\\HTML\\"+super.getChartType();
		File[] yearFolder = new File(chartTypeDirectory).listFiles();
		for(int i = 0;i<yearFolder.length;i++) {
			String year = yearFolder[i].getName();
			File[] htmlFiles = new File(yearFolder[i].getPath()).listFiles();
			for(int j = 0;j<htmlFiles.length;j++) {
				String HTMLName = htmlFiles[j].getName();
				String text = getCompressedText(chartTypeDirectory+"\\"+year+"\\"+HTMLName);
				String fileName = HTMLName.substring(0, HTMLName.length()-".html".length());
				String directoryString = "Database\\Billboard.com\\Text\\"+super.getChartType()+"\\"+year;
				super.makeText(directoryString, directoryString+"\\"+fileName+".txt", text);
			}
		}
	}
	
	public void fileUnsortedHTML() {
		String unSortedDirectory = "Database\\Billboard.com\\HTML\\Unsorted HTML";
		File[] folder = new File(unSortedDirectory).listFiles();
		for(int i = 0;i<folder.length;i++) {
			String HTMLName = folder[i].getName();
			
			String year = HTMLName.substring(HTMLName.length()-"YEAR-MO-DY.html".length(), HTMLName.length()-"-MO-DY.html".length());
			String chartType = HTMLName.substring(0, HTMLName.length()-" YEAR-MO-DY.html".length());
			
			// Get Text File
			String text = getCompressedText(unSortedDirectory+"\\"+HTMLName);
			String fileName = HTMLName.substring(0, HTMLName.length()-".html".length());
			String directoryString = "Database\\Billboard.com\\Text\\"+chartType+"\\"+year;
			super.makeText(directoryString, directoryString+"\\"+fileName+".txt", text);
			
			// Move HTML
			String HTMLText = super.getText(unSortedDirectory+"\\"+HTMLName);
			directoryString = "Database\\Billboard.com\\HTML\\"+chartType+"\\"+year;
			super.makeText(directoryString, directoryString+"\\"+HTMLName, HTMLText);
			
			// Delete from Unsorted HTML Folder
			folder[i].delete();
		}
	}
}
