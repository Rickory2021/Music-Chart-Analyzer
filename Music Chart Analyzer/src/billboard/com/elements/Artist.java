package billboard.com.elements;

import java.util.ArrayList;

import billboard.com.BillboardComQoL;

/**
 * This holds the Artist's name, score, songs, and flags
 * This also has an activeNames List for names currently in use
 * 
 * @author Ricky Chen
 */
public class Artist {
	private String name;
	private long score;
	private ArrayList<Song> songs;
	private ArrayList<String> flags;
	public int flagScore = 0;
	private Date initialDate;
	private Date recentDate;
	//private static ArrayList<String> activeNames = new ArrayList<>();
	public static boolean printScore = true, printSongCount = true, printInitialDate = true, 
			printRecentDate = true, printFlags = true, printSongList = true;
	
	public Artist(String name) /*throws DuplicateNameError*/ {
		name = BillboardComQoL.toCapitalize(name);
		//BillboardComQoL.isNameActive(name, activeNames);
		//activeNames.add(name);
		this.name=name;
		this.score=0;
		this.songs=new ArrayList<Song>();
		this.flags=new ArrayList<String>();
		this.flagScore = -1;
		this.initialDate=new Date(99,99,9999);
		this.recentDate=new Date(0,0,0);
	}
	
	public Artist(String name, ArrayList<String> flags) /*throws DuplicateNameError*/ {
		name = BillboardComQoL.toLength(BillboardComQoL.toCapitalize(name), 30);
		//BillboardComQoL.isNameActive(name, activeNames);
		//activeNames.add(name);
		this.name=name;
		this.score=0;
		this.songs=new ArrayList<Song>();
		this.flags=flags;
		this.flagScore = -1;
		this.initialDate=new Date(99,99,9999);
		this.recentDate=new Date(0,0,0);
	}

	public String getName() {
		return name;
	}

	public long getScore() {
		return score;
	}

	public ArrayList<Song> getSongs() {
		return songs;
	}
	
	public void addPlacement(Placement placement) {
		score+=BillboardComQoL.calculateScore(placement);
	}
	
	public void addSong(Song song){
		songs.add(song);
		score+=BillboardComQoL.calculateScore(song);
		if(initialDate.getRawDays()>song.getInitialDate().getRawDays())
			initialDate = song.getInitialDate();
		if(recentDate.getRawDays()<song.getRecentDate().getRawDays())
			recentDate = song.getRecentDate();
	}
	
	public void addSong(int index, Song song){
		songs.add(index, song);
		score+=BillboardComQoL.calculateScore(song);
		if(initialDate.getRawDays()>song.getInitialDate().getRawDays())
			initialDate = song.getInitialDate();
		if(recentDate.getRawDays()<song.getRecentDate().getRawDays())
			recentDate = song.getRecentDate();
	}
	
	public void refreshScore() {
		this.score=0;
		for(Song song : songs) {
			song.refreshScore();
			this.score+=song.getScore();
		}
	}
	
	public void refreshDates() {
		initialDate = null;
		recentDate = null;
		for(Song song : songs) {
			if(initialDate.getRawDays()>song.getInitialDate().getRawDays())
				initialDate = song.getInitialDate();
			if(recentDate.getRawDays()<song.getRecentDate().getRawDays())
				recentDate = song.getRecentDate();
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
		StringBuilder output = new StringBuilder();
		output.append(String.format("%s\n", this.name));
		if(printScore) output.append(String.format("Score:%6d\t", this.score));
		if(printSongCount) output.append(String.format("Songs:%2d\t\t",this.songs.size()));
		if(printInitialDate) output.append(String.format("Initial Date: %s\t\t",this.initialDate));
		if(printRecentDate) output.append(String.format("Recent Date: %s",this.recentDate));
		output.append("\n");
		if(printFlags) output.append(String.format("%s",this.flags));
		output.append("\n");
		if(printSongList) {
			for(Song song : songs) {
				output.append("\t"+song.toString()+"\n\n");
			}
		}else {
			output.append("\n\n");
		}
		return output.toString();
	}
}
