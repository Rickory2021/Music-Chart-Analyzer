package billboard.com.elements;

import java.util.ArrayList;

import billboard.com.BillboardComQoL;

/**
 * This holds the Song's name, Artist, score, placements, and flags
 * This also has an activeNames List for names currently in use
 * 
 * @author Ricky Chen
 */
public class Song {
	private String name;
	private Artist artist;
	private long score;
	private ArrayList<Placement> placements;
	private ArrayList<String> flags;
	private Date initialDate;
	private Date recentDate;
	//private static ArrayList<String> activeNames = new ArrayList<>();
	public static boolean printArtistName = true, printScore = true, printInitialDate = true,
			printRecentDate = true, printFlag = true, printPlacementList = true;
	
	/**
	 * Used when creating a new Song with 0 Placements (Will Capitalize Song Name)
	 * 
	 * @param name - Name of the Song
	 * @param artist - Artist Class
	 * @throws DuplicateSongName - Name already found in activeNames
	 */
	public Song(String name, Artist artist) /*throws DuplicateNameError*/ {
		name = BillboardComQoL.toCapitalize(name);
		//BillboardComQoL.isNameActive(name, activeNames);
		//activeNames.add(name);
		this.name=name;
		this.artist=artist;
		this.score=0;
		this.placements=new ArrayList<Placement>();
		this.flags=new ArrayList<String>();
		this.initialDate=null;
		this.recentDate=null;
	}
	
	
	/**
	 * Used when creating a new Song with only 1 Placements (Will Capitalize Song Name)
	 * 
	 * @param name - Name of the Song
	 * @param artist - Artist Class
	 * @param place - Singular Placement
	 * @throws DuplicateSongName - Name already found in activeNames
	 */
	public Song(String name, Artist artist, Placement place) /*throws DuplicateNameError*/ {
		name = BillboardComQoL.toLength(BillboardComQoL.toCapitalize(name), 30);
		//BillboardComQoL.isNameActive(name, activeNames);
		//activeNames.add(name);
		this.name=name;
		this.artist=artist;
		this.score=BillboardComQoL.calculateScore(place);
		this.placements=new ArrayList<Placement>();
		placements.add(place);
		this.flags=new ArrayList<String>();
		this.initialDate=place.getDate();
		this.recentDate=place.getDate();
	}
	
	/**
	 * Used when creating a Song from the database (Will Capitalize Song Name)
	 * 
	 * @param name - Name of the Song
	 * @param artist - Artists Class
	 * @param placements - ArrayList of Placements
	 * @param flags - Flags for Song
	 * @param initialDate - First Date Found in Placements
	 * @throws DuplicateName - Name already found in activeNames
	 */
	public Song(String name, Artist artist, ArrayList<Placement> placements, ArrayList<String> flags) /*throws DuplicateNameError*/ {
		name = BillboardComQoL.toLength(BillboardComQoL.toCapitalize(name), 30);
		//BillboardComQoL.isNameActive(name, activeNames);
		//activeNames.add(name);
		this.name=name;
		this.artist=artist;
		this.score=BillboardComQoL.calculateScore(placements);
		this.placements=placements;
		this.flags=flags;
		this.initialDate=new Date(99,99,9999);
		this.recentDate=new Date(0,0,0);
		for(int i = 0;i<placements.size();i++) {
			if(initialDate.getRawDays()>placements.get(i).getDate().getRawDays())
				initialDate = placements.get(i).getDate();
			if(recentDate.getRawDays()<placements.get(i).getDate().getRawDays())
				recentDate = placements.get(i).getDate();
		}
		
		
	}
	
	public String getName() {
		return name;
	}

	public Artist getArtist() {
		return artist;
	}

	public long getScore() {
		return score;
	}

	public ArrayList<Placement> getPlacements() {
		return placements;
	}
	
	public void addPlacements(Placement placement) {
		this.placements.add(placement);
		this.score+=+BillboardComQoL.calculateScore(placement);
		if(initialDate == null || initialDate.getRawDays()>placement.getDate().getRawDays()) 
			initialDate=placement.getDate();
		if(recentDate == null || recentDate.getRawDays()<placement.getDate().getRawDays())
			recentDate =placement.getDate();
	}
	
	public void removeDate(Date date) {
		for(int placementIndex = 0; placementIndex < placements.size();placementIndex++) {
			if(placements.get(placementIndex).getDate().getRawDays()==date.getRawDays()) {
				this.score-=+BillboardComQoL.calculateScore(placements.get(placementIndex));
				placements.remove(placementIndex);placementIndex--;
			}
		}
		refreshScore();
		artist.refreshScore();
		refreshDates();
		artist.refreshDates();
	}
	
	public void refreshScore() {
		this.score=0;
		for(Placement placement : placements) {
			score+=BillboardComQoL.calculateScore(placement);
		}
	}
	
	public void refreshDates() {
		initialDate = null;
		recentDate = null;
		for(Placement placement : placements) {
			if(initialDate == null || initialDate.getRawDays()>placement.getDate().getRawDays()) 
				initialDate=placement.getDate();
			if(recentDate == null || recentDate.getRawDays()<placement.getDate().getRawDays())
				recentDate =placement.getDate();
		}
	}

	public ArrayList<String> getFlags() {
		return flags;
	}
	
	
	public void setFlags(ArrayList<String> flags) {
		this.flags = flags;
	}


	public Date getInitialDate() {
		return initialDate;
	}
	
	public Date getRecentDate() {
		return recentDate;
	}

	/*public static ArrayList<String> getActiveNames() {
		return activeNames;
	}*/
	
	@Override
	public String toString() {
		/*
		public static boolean  = true, printScore = true,  = true,
			 = true,  = true, printPlacementList = true;
		 */
		/* Try: return String.format("I met %s, %s was OK.", name, (isMale ? "he" : "she"));
		 * If processing is too slow
		 */
		StringBuilder output = new StringBuilder();
		output.append(String.format("%-30s",  this.name));
		if(printArtistName) output.append( String.format("%-30s\t",  this.artist.getName()));
		if(printScore) output.append(String.format("Score:%6d\t",  this.score));
		if(printInitialDate) output.append(String.format("Initial Date:%s\t", this.initialDate));
		if(printRecentDate) output.append(String.format("Recent Date:%s", this.recentDate));
		output.append("\n");
		if(printFlag)output.append(String.format("\t%s\n", this.flags));
		if(printPlacementList) {
			output.append("\t\t");
			for(int i = 0;i<placements.size();i++) {
				output.append(String.format("%3d%s-%s\t", 
						placements.get(i).getPlace(), BillboardComQoL.placementSuffix(placements.get(i).getPlace()), placements.get(i).getDate()));
				if(i%5==4) output.append("\n\t\t");
			}
			return output.append("\n").toString();
		}
		return output.toString();
		
	}
	
}