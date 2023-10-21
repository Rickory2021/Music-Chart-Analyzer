package billboard.com;
		
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import billboard.com.elements.Artist;
import billboard.com.elements.Date;
import billboard.com.elements.Placement;
import billboard.com.elements.Song;
import billboard.com.elements.Spreadsheet;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidFileFormat;
import billboard.com.exception.InvalidPrecedenceException;
import billboard.com.filehandler.SpreadsheetFileHandler;
import billboard.com.filehandler.TextFileHandler;
import billboard.com.sorter.ArtistSorter;
import billboard.com.sorter.PlacementSorter;
import billboard.com.sorter.SongSorter;
import billboard.com.sorter.Sorter;
import executable.TextConverterExecutable;
import textconverter.TextBank;

public class Test {
	public static void main(String[] args) throws InvalidPrecedenceException {
		//String text;
		
		/*String ans="1";
		int integer =1;
		System.out.println(String.format("%30s %30s %4d %s $s\n\t%s\n", 
				ans,ans,integer,ans, ans, ans));*/
		/*
		text = "hello how you doing today?   s33 y4 LOL L4t3";
		System.out.println(QoL.toCapitalize(text));
		
		text = "2021,  2,   1";
		Date date = QoL.toDate(text);
		//System.out.println("YEAR/MO/DY");
		//System.out.println(date);
		
		text ="substring";
		System.out.println(QoL.toLength(text,3));
		System.out.println("STARTS");
		System.out.print("\t\t");
		for(int i = 0;i<50;i++) {
			int place = (int)(Math.random()*101)+1;
			String str = String.format("%3d%s-%s", 
					place, QoL.placementSuffix(place), date);
			System.out.print(str+"\t");
			if(i%5==4) {
				System.out.println();
				System.out.print("\t\t");
			}
		}*/
		//long start = System.nanoTime();
		
		
		/*System.out.println(String.format("%5d",123456789));
		
		String[] ArtistName = {"AAA", "AAAMYY", "Ado", "Aimer", "Amazarashi", "Anna", "Asako Toki", "Asca", "Ayuma Imazu",
				"back number", "BBHF", "THE CHARM PARK", "Chippoke Ohashi", "Daoko", "Egoist", "Eill", "Eve", "FIVE NEW OLD", "flumpool",
				"fredric", "Fuji Kaze", "Gen Hoshino", "go!go!vanillas", "haruno", "Hello Sleepwalkers", "HENTAI SHINSHI CLUB",
				"Hikaru Utada", "HIRAIDAI", "I Don't Like Mondays.", "imase", "indigo la End", "iri", "KAMI WA SAIKORO WO FURAN.....",
				"Kenshi Yonezu", "King Gnu", "Lilas Ikuta", "Maica_n", "Novelbright", "OFFICIAL HIGE DANDISM", "Omoinotake","THE ORAL CIGARETTES",
				"ReN", "Roel", "Rude-a", "Taichi Mukai", "takayan", "Tani Yuuki", "tonun", "TUYU", "vaundy", "wacci", "YAJICO GIRL", "yama", 
				"YOASOBI", "Yoru no Hitowarai", "Yuuri"};
		String[] tagList = {"NEW", "Youtube Exclusive", "Denied", "Need Recheck", "In Use"};
		try {
			ArrayList<Integer> active = new ArrayList<>();
			ArrayList<Artist> list = new ArrayList<Artist>();
			for(int i = (int)(Math.random()*6)+5; i>0;i--) {
				int index = (int)(Math.random()*ArtistName.length);
				while(active.contains(index))
					index = (int)(Math.random()*ArtistName.length);
				active.add(index);
				Artist artist = new Artist(ArtistName[index]);
				for(int l = (int)(Math.random()*4); l>0;l--) {
					artist.getFlags().add(tagList[(int)(Math.random()*tagList.length)]);
				}
				for(int k = (int)(Math.random()*4)+1; k>0;k--) {
					ArrayList<Placement> places = new ArrayList<>();
					for(int j = (int)(Math.random()*20)+1; j>0;j--) {
						places.add(new Placement(new Date((int)(Math.random()*31+1),(int)(Math.random()*12)+1,2021),(int)(Math.random()*100+1)));
					}
					ArrayList<String> tags = new ArrayList<>();
					for(int l = (int)(Math.random()*4); l>0;l--) {
						tags.add(tagList[(int)(Math.random()*tagList.length)]);
					}
					Song songy = new Song("Untitled"+Math.random(), artist,places, tags);
					artist.addSong(songy);
				}
				list.add(artist);
				
			}
			
			for(int i = 0;i<list.size();i++) {
				System.out.println(list.get(i));
			}
			
			System.out.println("\n\n\n\n\n\n\n");
			
			/* 
			 * 
			 * Check for>
			 * Name Score Flag Initial Date Recent Date
			 * ALL ASCENDING AND DESCENDING
			 * 
			 * Name
			 * Score
			 * Flag
			 * Initial Date
			 * Recent Date
			 * 
			 * Tag+Score
			 * Tag+Name
			 * Tag+Tag+Tag
			 */
			
			/*ArtistSorter artistSorter = new ArtistSorter();
			//artistSorter.addPrecedence(new String[]{"Recent Date","Descending"});
			artistSorter.addPrecedence(new String[]{"Flag-NEW","Descending"});
			artistSorter.addPrecedence(new String[]{"Score","Descending"});
			list.sort(artistSorter);
			
			Artist.printFlags=false;
			Artist.printSongCount=false;
			Song.printRecentDate=false;
			Song.printPlacementList=false;
			Song.printArtistName=false;
			Song.printPlacementList=false;
			Song.printFlag=false;
			
			SongSorter songSorter = new SongSorter();
			// NEED TO TEST
			// Name Score Flag
			// Initial State Recent Date Placement Count
			//songSorter.addPrecedence(new String[]{"Flag-NEW","Descending"});
			songSorter.addPrecedence(new String[] {"Placement Count", "Descending"});
			for(int i = 0;i<list.size();i++) {
				list.get(i).getSongs().sort(songSorter);
			}
			
			PlacementSorter placeSorter = new PlacementSorter();
			//placeSorter.addPrecedence(new String[] {"Place","Ascending"});
			placeSorter.addPrecedence(new String[] {"Date", "Ascending"});
			for(int i = 0;i<list.size();i++) {
				for(int j = 0; j<list.get(i).getSongs().size();j++) {
					list.get(i).getSongs().get(j).getPlacements().sort(placeSorter);
				}			
			}
			
			for(int i = 0;i<list.size();i++) {
				System.out.println(list.get(i));
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		/*try {
			BufferedReader reader = new BufferedReader(new FileReader("Database\\Billboard.com\\HTML\\2011\\Billboard Japan Hot 100 � Billboard 2011-04-09.html"));
			String currentLine = "START";
			int i = 0;
			while(currentLine!=null) {
				currentLine=reader.readLine();
				System.out.println((++i)+">"+currentLine+"<");
			}
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}*/
		
		
		//System.out.println(QoL.getAllFiles(new File("Database")));
		
		//String fileName = "Database\\Billboard.com\\HTML\\2012\\Billboard Japan Hot 100 � Billboard 2012-01-07.html";
		/*Date date = new Date(0);
		TextFileCompiler.showErrorMessage=false;
		TextFileCompiler textCompiler = new TextFileCompiler();
		
		//System.out.println(textCompiler.getText(date));
		//System.out.println(textCompiler.makeText(date));
		textCompiler.makeText(date);*/
		
		/*Date date = new Date(0);
		TextFileHandler textWriter = new TextFileHandler("Hot Rock & Alternative Songs � Billboard");
		textWriter.showErrorMessage=true;
		for(int rawDay = 0; rawDay<416*14+1;rawDay++) {
			//System.out.println(date);
			date.addDay(1);
			//textWriter.makeText(date);
		}
		textWriter.fileUnsortedHTML();*/
		
		/*ProjectReader pReader = new ProjectReader();
		Sorter<?> sorty=new Sorter<Object>();;
		try {
			sorty = pReader.readSorter(null, false);
		} catch (InvalidFileFormat | DuplicateNameError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sorty instanceof Sorter? "YES SORTER":"NO SORTER");
		System.out.println(sorty instanceof ArtistSorter? "YES Artist SORTER":"NO Artist SORTER");*/
		/*ArrayList<Date> dateArray = new ArrayList<>();
		// rawDay<416*14+1
		int startdate = new Date(2010, 01, 01).getRawDays();
		//System.out.println(startdate);
		for(int rawDay = 0; rawDay<416*13;rawDay++) {
			dateArray.add(new Date(rawDay+startdate));
		}
		dateArray.add(new Date(2022, 01, 01));
		
		
		dateArray.add(new Date(2022, 01, 01));
		dateArray.add(new Date(2022, 01, 8));
		
		Date[] dataList = new Date[dateArray.size()];
		for(int i =0 ;i<dateArray.size();i++){
			dataList[i]=dateArray.get(i);
		}
		Spreadsheet sheet = new Spreadsheet("Temp Spreadsheet",dataList,"Billboard Japan Hot 100 � Billboard");
		SongSorter songSorter = new SongSorter (BillboardComQoL.toStringArrayList(new String[][] {{"Score", "Ascending"}}));
		sheet.getSongList().sort(songSorter);
		ArtistSorter artistSorter = new ArtistSorter(BillboardComQoL.toStringArrayList(new String[][] {{"Score", "Ascending"}}));
		sheet.getArtistList().sort(artistSorter);
		//System.out.println(sheet);
		SpreadsheetFileHandler sFileHandler = new SpreadsheetFileHandler(true,true);
		sFileHandler.makeText(sheet);
		System.out.println(sheet.getDateList().size());
		//System.out.println("FINISHED");*/
		
		//Artist.printFlags=false;
		//Artist.printInitialDate = false;
		//Artist.printRecentDate = false;
		//Song.printFlag = false;
		//Song.printInitialDate = false;
		//Song.printRecentDate = false;
		//Song.printPlacementList=false;
		
		/*Spreadsheet sheet;
		try {
			sheet = SpreadsheetFileHandler.readSpreadsheet("Temp Spreadsheet", false, false);
			//System.out.println(sheet);
			SpreadsheetFileHandler sFileHandler = new SpreadsheetFileHandler(true,true);
			sFileHandler.makeText(sheet);
		} catch (InvalidFileFormat | DuplicateNameError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*BufferedReader systemReader = new BufferedReader(new InputStreamReader(System.in));
		String line ="";
		try {
			while ((line = systemReader.readLine()) != null) {
				System.out.println(">"+line+"<");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		/*ArrayList<Integer> numList = new ArrayList<>();
		for(int i = 0;i<10;i++) {
			numList.add(i);
		}
		System.out.println(numList);
		// (-(insertion point) - 1)
		
		System.out.println(Collections.binarySearch(numList, -1));
		int insertionPoint = (-Collections.binarySearch(numList, -1) - 1);
		System.out.println(insertionPoint);
		numList.add(insertionPoint,-1);
		System.out.println(numList);
		System.out.println();
		System.out.println("012345".compareTo("0"));*/
		
		TextBank textBank = new TextBank("Database\\Text Converter\\Text Bank\\TextTest.txt");
		String[] text,key;
		text = new String[]{"1st", "2nd"};
		key = new String[]{"1st Type","2nd Type"};
		textBank.addTextCouple(text, key);
		text = new String[]{"2nd Lone Type"};
		key = new String[]{"2nd Type"};
		textBank.addTextCouple(text, key);
		text = new String[]{"NEW NEW NEW"};
		key = new String[]{"New Type"};
		textBank.addTextCouple(text, key);
		System.out.println(textBank.getTable());
		
		String string = "Hello 2nd 1st 2nd Lone Type NEW NEW NEW";
		System.out.println(textBank.convertText(string, "1st Type", "2nd Type"));
		System.out.println(string);
		/*ystem.out.println("SUB STRING TEST");
		String string = "hel\nlo";
		System.out.println(string.indexOf("\n"));
		System.out.println(string.indexOf("o"));*/
		Scanner scan = new Scanner(System.in);
		TextConverterExecutable executable = new TextConverterExecutable(scan);
		
		System.out.println("aa".replace("a", "aa").replace("a", "aa"));
		System.out.println("パプリカ");
		System.out.println("土岐   麻子");
		
		
		
		ArrayList<Integer> arrays = new ArrayList<>();
		System.out.println("ArrayList:"+(arrays instanceof ArrayList));
		System.out.println("AbstractList:"+(arrays instanceof AbstractList));
		System.out.println("AbstractCollection:"+(arrays instanceof AbstractCollection));

		System.out.println("Object:"+(arrays instanceof Object));
		
		System.out.println("A vs B:" + "A".compareTo("B"));

		System.out.println("C vs B:" + "C".compareTo("B"));
		//  inserting B
		//   [A]  C
	}

}