package executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import billboard.com.BillboardComQoL;
import billboard.com.elements.Artist;
import billboard.com.elements.Date;
import billboard.com.elements.Placement;
import billboard.com.elements.Song;
import billboard.com.elements.Spreadsheet;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidFileFormat;
import billboard.com.exception.InvalidPrecedenceException;
import billboard.com.filehandler.FileHandler;
import billboard.com.filehandler.SorterFileHandler;
import billboard.com.filehandler.SpreadsheetFileHandler;
import billboard.com.filehandler.TextFileHandler;
import billboard.com.sorter.ArtistSorter;
import billboard.com.sorter.PlacementSorter;
import billboard.com.sorter.SongSorter;
import billboard.com.sorter.Sorter;

public class BillboardComExecutable {
	Scanner systemScanner;
	Spreadsheet spreadsheet;
	@SuppressWarnings("rawtypes")
	ArrayList<Sorter> sorterList;

	public BillboardComExecutable(Scanner systemScanner) {
		this.systemScanner = systemScanner;
		this.spreadsheet = null;
		this.sorterList = new ArrayList<>();
	}

	public void execute() {
		System.out.println(getGreetingText());
		boolean continueStartMenu = true;
		while (continueStartMenu) {
			continueStartMenu = startMenu();
		}
		System.out.println("Executable Terminated");
	}

	public String getGreetingText() {
		StringBuilder greeting = new StringBuilder();
		greeting.append("Launching Billboard.com Executable\n");
		return greeting.toString();
	}

	public boolean startMenu() {
		String line;
		int num;
		boolean continueStartLoop = true;
		while (continueStartLoop) {
			line = getOption(getStartMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Help".toLowerCase()) || num == 1) {
				System.out.println(getHelpText());
			} else if (line.toLowerCase().equals("Open Spreadsheet".toLowerCase()) || num == 2) {
				boolean continueOpenSpreadsheetLoop = true;
				while (continueOpenSpreadsheetLoop) {
					System.out.print("Input Spreadsheet Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueOpenSpreadsheetLoop = false;
					} else if (doesSpreadsheetExist(line)) {
						openSpreadsheet(line);
						continueOpenSpreadsheetLoop = false;
					} else {
						System.out.println("Spreadsheet was not found");
					}
				}
			} else if (line.toLowerCase().equals("Create Spreadsheet".toLowerCase()) || num == 3) {
				boolean continueCreateSpreadsheetLoop = true;
				while (continueCreateSpreadsheetLoop) {
					System.out.print("Input Spreadsheet Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					String spreadsheetName = line;
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueCreateSpreadsheetLoop = false;
					} else if (doesSpreadsheetExist(line)) {
						boolean continueExistLoop = true;
						while (continueExistLoop) {
							System.out.print(
									"This Spreadsgeet Already Exist. \"Open\" Exitsing (1), \"Continue\" with new (2), or Go Back (0): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
								continueExistLoop = false;
							} else if (line.toLowerCase().equals("Open".toLowerCase()) || num == 1) {
								openSpreadsheet(spreadsheetName);
								continueExistLoop = false;
							} else if (line.toLowerCase().equals("Continue".toLowerCase()) || num == 2) {
								boolean continueChartTypeLoop = true;
								while (continueChartTypeLoop) {
									System.out.print("What Chart Type is this Spreadsheet for or type Go Back (0): ");
									line = systemScanner.nextLine().trim();
									num = toInteger(line);
									if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
										continueChartTypeLoop = false;
									} else {
										String chartType = doesDirectoryExist("Database\\Billboard.com\\Text\\" + line);
										if(chartType!=null) {
											System.out.println(chartType);
											createOpenSpreadsheet(spreadsheetName, chartType);
											continueChartTypeLoop = false;
											continueCreateSpreadsheetLoop = false;
										}else {
											System.out.println("Chart Type was not found");
										}
										
									}

								}
							}
						}
						openSpreadsheet(line);
						continueCreateSpreadsheetLoop = false;
					} else {
						boolean continueChartTypeLoop = true;
						while (continueChartTypeLoop) {
							System.out.print("What Chart Type is this Spreadsheet for or type Go Back (0): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
								continueChartTypeLoop = false;
							} else {
								String chartType = doesDirectoryExist("Database\\Billboard.com\\Text\\" + line);
								if(chartType!=null) {
									System.out.println(chartType);
									createOpenSpreadsheet(spreadsheetName, chartType);
									continueChartTypeLoop = false;
									continueCreateSpreadsheetLoop = false;
								}else {
									System.out.println("Chart Type was not found");
								}
								
							}

						}
					}
				}
			} else if (line.toLowerCase().equals("Update Text File".toLowerCase()) || num == 4) {
				boolean continueUpdateTextFileLoop = true;
				while (continueUpdateTextFileLoop) {
					System.out.print("Do you wish to Override (1) or Skip (2) Existing Text or type Go Back (0): ");
					boolean canOverride = false, continueUpdateTypeLoop = false;
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueUpdateTextFileLoop = false;
					} else if (line.toLowerCase().equals("Override".toLowerCase()) || num == 1) {
						canOverride = true;
						continueUpdateTypeLoop = true;
					} else if (line.toLowerCase().equals("Skip".toLowerCase()) || num == 2) {
						canOverride = false;
						continueUpdateTypeLoop = true;
					} else {
						System.out.println("Error Type Again");
					}
					while (continueUpdateTypeLoop) {
						System.out.print("Update an Existing Directory (1) or Unsorted Directory (2) or type Go Back (0): ");
						line = systemScanner.nextLine().trim();
						num = toInteger(line);
						if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
							continueUpdateTypeLoop = false;
						} else if (line.toLowerCase().contains("Existing".toLowerCase()) || num == 1) {
							boolean continueDirectoryLoop = true;
							while (continueDirectoryLoop) {
								// CHECK FOR DIRECTORY
								System.out.print("What Chart Type Directory Are you Updating or type Go Back (0): ");
								line = systemScanner.nextLine().trim();
								num = toInteger(line);
								if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
									continueDirectoryLoop = false;
								} else {
									boolean exist = FileHandler
											.checkDirectoryExist("Database\\Billboard.com\\HTML\\" + line);
									if (exist) {
										TextFileHandler tFileHandler = new TextFileHandler(line, true, canOverride);
										tFileHandler.fileHTMLDirectory();
										System.out.println("Finished Updating Directory");
										continueDirectoryLoop = false;
									} else {
										System.out.println("Could not find Chart Type. Try Again");
									}
								}
							}
							continueUpdateTypeLoop = false;
							continueUpdateTextFileLoop = false;
						} else if (line.toLowerCase().contains("Unsorted".toLowerCase()) || num == 2) {
							TextFileHandler tFileHandler = new TextFileHandler(true, canOverride);
							tFileHandler.fileUnsortedHTML();
							System.out.println("Finished Going Through Unsorted HTML");
							continueUpdateTypeLoop = false;
						} else {
							System.out.println("Chart Type was not found");
						}

					}
				}
			} else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueStartLoop = false;
			}
		}
		return false;
	}

	public String getStartMenuText() {
		StringBuilder menu = new StringBuilder();
		menu.append("Start Menu:\n");
		menu.append(String.format("%-31s", "1) Help"));
		menu.append(String.format("%-31s", "2) Open Spreadsheet"));
		menu.append(String.format("%-31s", "3) Create Spreadsheet"));
		menu.append(String.format("%-31s", "4) Update Text File"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();

	}

	public void spreadsheetMenu() {
		String line;
		int num;
		boolean continueSpreadsheetLoop = true;
		while (continueSpreadsheetLoop) {
			line = getOption(getSpreadsheetMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Acesss Sorter".toLowerCase()) || num == 1) {
				System.out.println("Acessing Sorters");
				sorterMenu();
			} else if (line.toLowerCase().equals("Acess Date".toLowerCase()) || num == 2) {
				System.out.println("Acessing Dates");
				dateMenu();
			} else if (line.toLowerCase().equals("Toggle BooleanPrints".toLowerCase()) || num == 3) {
				boolean continuePrintLoop = true;
				while (continuePrintLoop) {
					System.out.print(getTogglePrintText());
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					
					if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
						continuePrintLoop=false;
					}else if (line.toLowerCase().equals("Print Artist Score".toLowerCase()) || num == 1) {
						Artist.printScore=!Artist.printScore;
					}else if (line.toLowerCase().equals("Print Artist Song Count".toLowerCase()) || num == 2) {
						Artist.printSongCount=!Artist.printSongCount;
					}else if (line.toLowerCase().equals("Print Artist Initial Date".toLowerCase()) || num == 3) {
						Artist.printInitialDate=!Artist.printInitialDate;
					}else if (line.toLowerCase().equals("Print Artist Recent Date".toLowerCase()) || num == 4) {
						Artist.printRecentDate=!Artist.printRecentDate;
					}else if (line.toLowerCase().equals("Print Artist Flags".toLowerCase()) || num == 5) {
						Artist.printFlags=!Artist.printFlags;
					}else if (line.toLowerCase().equals("Print ANY SONGS".toLowerCase()) || num == 6) {
						Artist.printSongList=!Artist.printSongList;
					}else if (line.toLowerCase().equals("Print Song's Artist".toLowerCase()) || num == 7) {
						Song.printArtistName=!Song.printArtistName;
					}else if (line.toLowerCase().equals("Print Song Score".toLowerCase()) || num == 8) {
						Song.printScore=!Song.printScore;
					}else if (line.toLowerCase().equals("Print Song Initial Date".toLowerCase()) || num == 9) {
						Song.printInitialDate=!Song.printInitialDate;
					}else if (line.toLowerCase().equals("Print Song Recent Date".toLowerCase()) || num == 10) {
						Song.printRecentDate =!Song.printRecentDate;
					}else if (line.toLowerCase().equals("Print Song Flags".toLowerCase()) || num == 11) {
						Song.printFlag=!Song.printFlag;
					}else if (line.toLowerCase().equals("Print Placements".toLowerCase()) || num == 12) {
						Song.printPlacementList=!Song.printPlacementList;
					}
				}
			} else if (line.toLowerCase().equals("Print List".toLowerCase()) || num == 4) {
				boolean continuePrintLoop = true;
				while (continuePrintLoop) {
					System.out.print("Print Artist List (1), Song List (2), or Quit (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					
					if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
						continuePrintLoop=false;
					}else if (line.toLowerCase().equals("Artist List".toLowerCase()) || num == 1) {
						try {
							System.out.println("Type Inclusive Range informat: \"1-"+(spreadsheet.getArtistList().size())+"\"");
							line = systemScanner.nextLine().trim();
							String[] split = line.split("-");
							int start = Integer.parseInt(split[0])-1, end = Integer.parseInt(split[1])-1;
							for(int i = start; i<=end;i++) {
								if(i>=0&&i<spreadsheet.getArtistList().size()) {
									System.out.println(spreadsheet.getArtistList().get(i).getName());
									System.out.println((i+1)+") "+spreadsheet.getArtistList().get(i));
									
								}
							}
						}catch(Exception e) {
							System.out.println("An Error has occured");
							e.printStackTrace();
						}
					}else if (line.toLowerCase().equals("Song List".toLowerCase()) || num == 2) {
						try {
							System.out.println("Type Inclusive Range informat: \"1-"+(spreadsheet.getSongList().size())+"\"");
							line = systemScanner.nextLine().trim();
							String[] split = line.split("-");
							int start = Integer.parseInt(split[0])-1, end = Integer.parseInt(split[1])-1;
							for(int i = start; i<=end;i++) {
								if(i>=0&&i<spreadsheet.getSongList().size())
									System.out.println((i+1)+") "+spreadsheet.getSongList().get(i));
							}
						}catch(Exception e) {
							System.out.println("An Error has occured");
							e.printStackTrace();
						}
					}
				}
			} else if (line.toLowerCase().equals("Edit Flag".toLowerCase()) || num == 5) {
				boolean continueEditLoop = true;
				while (continueEditLoop) {
					System.out.print("Tranverse Artist List (1), Song List (2), or Quit (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					
					if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
						continueEditLoop=false;
					}else if (line.toLowerCase().equals("Artist List".toLowerCase()) || num == 1) {
						boolean continueArtistLoop = true;
						while (continueArtistLoop) {
							System.out.print("Select Artist Index or Quit (-1): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Quit".toLowerCase()) || num == -1){
								continueArtistLoop=false;
							}else {
								if(num >= 0 && num < spreadsheet.getArtistList().size()) {
									Artist artist = spreadsheet.getArtistList().get(num);
									boolean continueArtistEditLoop = true;
									while (continueArtistEditLoop) {
										System.out.println(artist.getName());
										for(int i = 0; i<artist.getFlags().size();i++) {
											System.out.println(i+")" +artist.getFlags().get(i));
										}
										System.out.print("Type index to Remove, String for Flag, or Quit(-1): ");
										line = systemScanner.nextLine().trim();
										num = toInteger(line);
										if (line.toLowerCase().equals("Quit".toLowerCase()) || num == -1){
											continueArtistEditLoop=false;
										}else if (num >= 0 && num < artist.getFlags().size()){
											artist.getFlags().remove(num);
										}else {
											artist.getFlags().add(line);
											Collections.sort(artist.getFlags());
										}
									}
								}
							}
						}
					}else if (line.toLowerCase().equals("Song List".toLowerCase()) || num == 2) {
						boolean continueSongLoop = true;
						while (continueSongLoop) {
							System.out.print("Select Song Index or Quit (-1): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Quit".toLowerCase()) || num == -1){
								continueSongLoop=false;
							}else {
								if(num >= 0 && num < spreadsheet.getSongList().size()) {
									Song song = spreadsheet.getSongList().get(num);
									boolean continueSongEditLoop = true;
									while (continueSongEditLoop) {
										System.out.println(song.getName());
										for(int i = 0; i<song.getFlags().size();i++) {
											System.out.println(i+")" +song.getFlags().get(i));
										}
										System.out.print("Type index to Remove, String for Flag, or Quit(-1): ");
										line = systemScanner.nextLine().trim();
										num = toInteger(line);
										if (line.toLowerCase().equals("Quit".toLowerCase()) || num == -1){
											continueSongEditLoop=false;
										}else if (num >= 0 && num < song.getFlags().size()){
											song.getFlags().remove(num);
										}else {
											song.getFlags().add(line);
											Collections.sort(song.getFlags());
										}
									}
								}
							}
						}
					}
				}
			} else if (line.toLowerCase().equals("Save".toLowerCase()) || num == 6) {
				SpreadsheetFileHandler spreadsheetFileHandler = new SpreadsheetFileHandler(spreadsheet.getChartType(),
						true, true);
				System.out.println(spreadsheet.getChartType() + " " + spreadsheet.getSpreadsheetName());
				spreadsheetFileHandler.makeText(spreadsheet);
			} else if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
				continueSpreadsheetLoop = false;
			}
		}
	}

	public String getSpreadsheetMenuText() {
		//System.out.println("TOSTRING:");
		// System.out.println(spreadsheet.toString());
		//System.out.println("<<<");
		StringBuilder menu = new StringBuilder();
		menu.append(getActiveSorterList());
		menu.append("\n");
		menu.append("Spreadsheet Menu:\n");
		menu.append(String.format("%-31s", "1) Access Sorter"));
		menu.append(String.format("%-31s", "2) Edit Date"));
		menu.append(String.format("%-31s", "3) Toggle BooleanPrints"));
		menu.append(String.format("%-31s\n", "4) Print List"));
		menu.append(String.format("%-31s", "5) Edit Flag"));
		menu.append(String.format("%-31s", "6) Save"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}

	@SuppressWarnings("unchecked")
	public void sorterMenu() {
		String line;
		int num;
		boolean continueSpreadsheetLoop = true;
		while (continueSpreadsheetLoop) {
			line = getOption(getSorterMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Open Sorter".toLowerCase()) || num == 1) {
				boolean continueOpenSorterLoop = true;
				while (continueOpenSorterLoop) {
					System.out.print("Input Sorter Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueOpenSorterLoop = false;
					} else if (doesSorterExist(line)) {
						openSorter(line);
						continueOpenSorterLoop = false;
					} else {
						System.out.println("Sorter was not found");
					}
				}
			} else if (line.toLowerCase().equals("Create Sorter".toLowerCase()) || num == 2) {
				boolean continueCreateSpreadsheetLoop = true;
				while (continueCreateSpreadsheetLoop) {
					System.out.print("Input Sorter Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					String sorterName = line;
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueCreateSpreadsheetLoop = false;
					} else if (doesSorterExist(line)) {
						boolean continueExistLoop = true;
						while (continueExistLoop) {
							System.out.print(
									"This Spreadsgeet Already Exist. \"Open\" Exitsing (1), \"Continue\" with new (2), or Go Back (0): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
								continueExistLoop = false;
							} else if (line.toLowerCase().equals("Open".toLowerCase()) || num == 1) {
								openSorter(sorterName);
								continueExistLoop = false;
							} else if (line.toLowerCase().equals("Continue".toLowerCase()) || num == 2) {
								boolean continueChartTypeLoop = true;
								sorterName = line;
								while (continueChartTypeLoop) {
									System.out.print("Artist (1), Placement (2), Song (3) ");
									System.out.print("What Sorter Type or type Go Back (0): ");
									line = systemScanner.nextLine().trim();
									num = toInteger(line);
									if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
										continueCreateSpreadsheetLoop = false;
									} else if (line.toLowerCase().equals("Artist".toLowerCase()) || num == 1) {
										createOpenSorter(sorterName, "Artist");
										continueChartTypeLoop = false;
										continueCreateSpreadsheetLoop = false;
									} else if (line.toLowerCase().equals("Placement".toLowerCase()) || num == 2) {
										createOpenSorter(sorterName, "Placement");
										continueChartTypeLoop = false;
										continueCreateSpreadsheetLoop = false;
									} else if (line.toLowerCase().equals("Song".toLowerCase()) || num == 3) {
										createOpenSorter(sorterName, "Song");
										continueChartTypeLoop = false;
										continueCreateSpreadsheetLoop = false;
									} else {
										System.out.println("Chart Type was not found");
									}

								}
							}
						}
						openSpreadsheet(line);
						continueCreateSpreadsheetLoop = false;
					} else {
						boolean continueChartTypeLoop = true;
						sorterName = line;
						while (continueChartTypeLoop) {
							System.out.print("Artist (1), Placement (2), Song (3) ");
							System.out.print("What Sorter Type or type Go Back (0): ");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
								continueCreateSpreadsheetLoop = false;
							} else if (line.toLowerCase().equals("Artist".toLowerCase()) || num == 1) {
								createOpenSorter(sorterName, "Artist");
								continueChartTypeLoop = false;
								continueCreateSpreadsheetLoop = false;
							} else if (line.toLowerCase().equals("Placement".toLowerCase()) || num == 2) {
								createOpenSorter(sorterName, "Placement");
								continueChartTypeLoop = false;
								continueCreateSpreadsheetLoop = false;
							} else if (line.toLowerCase().equals("Song".toLowerCase()) || num == 3) {
								createOpenSorter(sorterName, "Song");
								continueChartTypeLoop = false;
								continueCreateSpreadsheetLoop = false;
							} else {
								System.out.println("Chart Type was not found");
							}

						}
					}
				}
			} else if (line.toLowerCase().equals("Edit Active Sorter".toLowerCase()) || num == 3) {
				int sorterIndex = getSorterIndex();
				if (sorterIndex != -1) {
					editSorterMenu(sorterIndex);
				}
			} else if (line.toLowerCase().equals("Use Sorter".toLowerCase()) || num == 4) {
				int sorterIndex = getSorterIndex();
				if (sorterIndex != -1) {
					Sorter<?> sorter = sorterList.get(sorterIndex);
					if (sorter instanceof ArtistSorter) {
						spreadsheet.getArtistList().sort((Comparator<? super Artist>) sorter);
					} else if (sorter instanceof PlacementSorter) {
						for (Song song : spreadsheet.getSongList()) {
							song.getPlacements().sort((Comparator<? super Placement>) sorter);
						}
					} else if (sorter instanceof SongSorter) {
						for (Artist artist : spreadsheet.getArtistList()) {
							artist.getSongs().sort((Comparator<? super Song>) sorter);
						}
						spreadsheet.getSongList().sort((Comparator<? super Song>) sorter);
					}
				}

			} else if (line.toLowerCase().equals("Deactivate Sorter".toLowerCase()) || num == 5) {
				int sorterIndex = getSorterIndex();
				String sorterName = sorterList.get(sorterIndex).getName();
				if (sorterIndex != -1) {
					sorterList.remove(sorterIndex);
					System.out.println("Removed " + sorterName);
				}
			} else if (line.toLowerCase().equals("Save All Active Sorter List".toLowerCase()) || num == 6) {
				boolean continueSaveActiveSorterLoop = true, canOverride = false;
				while (continueSaveActiveSorterLoop) {
					System.out.print("Do you wish to Override (1) or Skip (2) Existing Text or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueSaveActiveSorterLoop = false;
					} else if (line.toLowerCase().equals("Override".toLowerCase()) || num == 1) {
						canOverride = true;
						continueSaveActiveSorterLoop = false;
					} else if (line.toLowerCase().equals("Skip".toLowerCase()) || num == 2) {
						canOverride = false;
						continueSaveActiveSorterLoop = false;
					} else {
						System.out.println("Error Type Again");
					}
				}
				SorterFileHandler sFileHandler = new SorterFileHandler(true, canOverride);
				for (Sorter<?> sorter : sorterList) {
					String directoryString = "Database\\Billboard.com\\Project\\Sorter";
					String newFileName = "Database\\Billboard.com\\Project\\Sorter\\" + sorter.getName() + ".txt";
					// sFileHandler.makeText(sorter.getName());
					sFileHandler.makeText(directoryString, newFileName, sFileHandler.getText(sorter));
				}
			} else if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
				continueSpreadsheetLoop = false;
			}
		}
	}

	public String getSorterMenuText() {
		System.out.println(spreadsheet.getSpreadsheetName());
		StringBuilder menu = new StringBuilder();

		menu.append(getActiveSorterList());
		menu.append("\n");
		menu.append("Sorter Menu:\n");
		menu.append(String.format("%-31s", "1) Open Sorter"));
		menu.append(String.format("%-31s", "2) Create Sorter"));
		menu.append(String.format("%-31s", "3) Edit Active Sorter"));
		menu.append(String.format("%-31s", "4) Use Sorter"));
		menu.append(String.format("%-31s\n", "5) Deactivate Sorter"));
		menu.append(String.format("%-31s", "6) Save All Active Sorter List"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}

	public void dateMenu() {
		String line;
		int num;
		boolean continueSpreadsheetLoop = true;
		while (continueSpreadsheetLoop) {
			line = getOption(getDateMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Open Date".toLowerCase()) || num == 1) {
				boolean continueOpenDateLoop = true;
				while (continueOpenDateLoop) {
					System.out.print("Year (1), Week (2), Month(3), or Quit (0) ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Year".toLowerCase()) || num == 1) {
						System.out.print("Type the Select Year: ");
						line = systemScanner.nextLine().trim();
						try {
							int year = Integer.parseInt(line);
							Date start = new Date(year, 0, 0), end = new Date(year, 12,31), date = new Date(start.getRawDays()+1);
							while(date.getRawDays()<=end.getRawDays()) {
								if(Collections.binarySearch(spreadsheet.getDateList(), date)<0&&doesDateExist(date)) {
									System.out.println(date+" "+ spreadsheet.addDate(date));
									Collections.sort(spreadsheet.getDateList());
								}
								date = new Date(date.getRawDays()+1);
								System.out.println(date + ">>"+Collections.binarySearch(spreadsheet.getDateList(), date));
								System.out.println(doesDateExist(date));
							}
						}catch(Exception e) {
							e.printStackTrace();
							System.out.println("An Exception Occured");
						}
					}else if (line.toLowerCase().equals("Week".toLowerCase()) || num == 2) {
						System.out.print("Type the Select Week (YEAR-MO-DY): ");
						line = systemScanner.nextLine().trim();
						try {
							String[] numbers = line.split("-");
							int year = Integer.parseInt(numbers[0]), month = Integer.parseInt(numbers[1]), 
									day = Integer.parseInt(numbers[2]);
							
							Date date = new Date(year, month, day);
							if(Collections.binarySearch(spreadsheet.getDateList(), date)<0&&doesDateExist(date)) {
								spreadsheet.addDate(date);
								Collections.sort(spreadsheet.getDateList());
							}
						}catch(Exception e) {
							System.out.println("An Exception Occured");
						}
					}else if (line.toLowerCase().equals("Month".toLowerCase()) || num == 3) {
						System.out.print("Type the Select Month (YEAR-MO): ");
						line = systemScanner.nextLine().trim();
						try {
							String[] numbers = line.split("-");
							int year = Integer.parseInt(numbers[0]), month = Integer.parseInt(numbers[1]);
							for(int day = 0; day<=31;day++) {
								Date date = new Date(year, month, day);
								if(Collections.binarySearch(spreadsheet.getDateList(), date)<0&&doesDateExist(date)) {
									spreadsheet.addDate(date);
									Collections.sort(spreadsheet.getDateList());
								}
							}
							
						}catch(Exception e) {
							System.out.println("An Exception Occured");
						}
					}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
						continueOpenDateLoop=false;
					}
				}
			} else if (line.toLowerCase().equals("Remove Date".toLowerCase()) || num == 2) {
				boolean continueCloseDateLoop = true;
				while (continueCloseDateLoop) {
					System.out.print("Year (1), Week (2), or Quit (0) ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Year".toLowerCase()) || num == 1) {
						System.out.print("Type the Select Year: ");
						line = systemScanner.nextLine().trim();
						try {
							int year = Integer.parseInt(line);
							Date date = new Date(year, 0, 0), end = new Date(year, 12,31);
							while(date.getRawDays()<=end.getRawDays()) {
								int dateIndex = Collections.binarySearch(spreadsheet.getDateList(), date);
								if(dateIndex>=0) {
									spreadsheet.removeDate(date);
								}
								date.addDay(1);
							}
						}catch(Exception e) {
							System.out.println("An Exception Occured");
							e.printStackTrace();
						}
					}else if (line.toLowerCase().equals("Week".toLowerCase()) || num == 2) {
						System.out.print("Type the Select Week (YEAR-MO-DY): ");
						line = systemScanner.nextLine().trim();
						try {
							String[] numbers = line.split("-");
							int year = Integer.parseInt(numbers[0]), month = Integer.parseInt(numbers[1]), 
									day = Integer.parseInt(numbers[2]);
							
							Date date = new Date(year, month, day);
							int dateIndex = Collections.binarySearch(spreadsheet.getDateList(), date);
							if(dateIndex>=0) {
								spreadsheet.removeDate(date);
							}
						}catch(Exception e) {
							System.out.println("An Exception Occured");
						}
					}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
						continueCloseDateLoop=false;
					}
				}
			} else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueSpreadsheetLoop=false;
			}
		}
	}

	public String getDateMenuText() {
		System.out.println(spreadsheet.getSpreadsheetName());
		StringBuilder menu = new StringBuilder();
		menu.append(getActiveDateList());
		menu.append("\n");
		menu.append("Date Menu:\n");
		menu.append(String.format("%-31s", "1) Open Date"));
		menu.append(String.format("%-31s", "2) Remove Date"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public String getTogglePrintText() {
		//public static boolean printScore = true, printSongCount = true, printInitialDate = true, 
		//printRecentDate = true, printFlags = true, printSongList = true;
		
		// public static boolean printArtistName = true, printScore = true, printInitialDate = true,
		// printRecentDate = true, printFlag = true, printPlacementList = true;
		StringBuilder menu = new StringBuilder();
		menu.append("Toggle Print Menu:\n");
		menu.append("\n");
		menu.append("Toggle Artist Print Menu:\n");
		menu.append(String.format("%-40s", "1) Print Artist Score: " +Artist.printScore));
		menu.append(String.format("%-40s", "2) Print Artist Song Count: "+Artist.printSongCount));
		menu.append(String.format("%-40s\n", "3) Print Artist Initial Date: "+Artist.printInitialDate));
		menu.append(String.format("%-40s", "4) Print Artist Recent Date: "+Artist.printRecentDate));
		menu.append(String.format("%-40s\n", "5) Print Artist Flags: "+Artist.printFlags));
		menu.append("\n");
		menu.append("Toggle Song Print Menu:\n");
		menu.append(String.format("%-40s\n", "6) Print ANY SONGS: " +Artist.printSongList));
		menu.append(String.format("%-40s", "7) Print Song's Artist: "+Song.printArtistName));
		menu.append(String.format("%-40s", "8) Print Song Score: "+Song.printScore));
		menu.append(String.format("%-40s\n", "9) Print Artist Initial Date: "+Song.printInitialDate));
		menu.append(String.format("%-40s", "10) Print Artist Recent Date: "+Song.printRecentDate));
		menu.append(String.format("%-40s\n", "11) Print Song Flags: "+Song.printFlag));
		menu.append("\n");
		menu.append("Toggle Placement Print Menu:\n");
		menu.append(String.format("%-40s\n", "12) Print Placements: "+Song.printPlacementList));
		menu.append(String.format("%-40s\n", "0) Quit"));
		
		menu.append("Input: ");
		return menu.toString();
	}

	public String getActiveDateList() {
		StringBuilder text = new StringBuilder();
		if (spreadsheet.getDateList().size() == 0) {
			text.append("No Active Date");
		} else {
			text.append("Active Date: \n");
			for (int i = 0; i < spreadsheet.getDateList().size(); i++) {
				text.append(i + ") " + spreadsheet.getDateList().get(i) + "\t\t");
				if (i % 6 == 5)
					text.append("\n");
			}
		}
		return text.toString();
	}

	public String getActiveSorterList() {
		StringBuilder text = new StringBuilder();
		if (sorterList.size() == 0) {
			text.append("No Active Sorter");
		} else {
			int index = 0;
			text.append("Active Sorter: \n");
			for (Sorter<?> sorter : sorterList) {
				String newText = (index++) + ") ";
				if (sorter instanceof ArtistSorter) {
					newText += "ArtistSorter: ";
				} else if (sorter instanceof PlacementSorter) {
					newText += "PlacementSorter: ";
				} else if (sorter instanceof SongSorter) {
					newText += "SongSorter: ";
				}
				text.append(String.format("%-31s:", newText + sorter.getName()));
				if (sorter.getPrecedence().size() == 0)
					text.append("No Precedence");
				for (String[] precendece : sorter.getPrecedence()) {
					text.append(String.format("%s-%s\t", precendece[0], precendece[1]));
				}
				text.append("\n");
			}
		}
		return text.toString();
	}

	public int getSorterIndex() {
		boolean continueSorterIndexLoop = true;
		while (continueSorterIndexLoop) {
			System.out.print("Input Sorter Name / Index (Case Sensitive) or type Go Back (0): ");
			String line = systemScanner.nextLine().trim();
			int num = toInteger(line);
			String sorterName = line;
			if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
				return -1;
			} else if (num != Integer.MIN_VALUE && num >= 0 && num < sorterList.size()) {
				return num;
			} else {
				for (int i = 0; i < sorterList.size(); i++) {
					if (sorterList.get(i).getName().equals(sorterName))
						return i;
				}
			}
			System.out.println("Input was incorrect");
		}
		return -1;
	}

	public void editSorterMenu(int sorterIndex) {
		String line;
		int num;
		boolean continueEditSorterLoop = true;
		Sorter<?> sorter = sorterList.get(sorterIndex);
		while (continueEditSorterLoop) {
			line = getOption(getEditSorterMenuText(sorter));
			num = toInteger(line);
			if (line.equals("Add Precedence".toLowerCase()) || num == 1) {
				boolean continueAddFirstPrecedenceLoop = true, continueAddSecondPrecedenceLoop = false,
						addPrecedence = false;
				String precedence0 = "", precedence1 = "";
				while (continueAddFirstPrecedenceLoop) {
					if (sorter instanceof ArtistSorter) {
						System.out.print("Input Artist Precedence or Go Back (0) (");
						for (int i = 0; i < ArtistSorter.precedenceCategory.length; i++) {
							if (i != 0)
								System.out.print(", ");
							System.out.print(ArtistSorter.precedenceCategory[i]);
						}
						System.out.println("):");
						line = systemScanner.nextLine().trim();
						num = toInteger(line);
						if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
							precedence0 = "null";
							continueAddFirstPrecedenceLoop = false;
						} else {
							try {
								ArtistSorter.isPrecedenceValid(new String[] { line, "Ascending" });
								precedence0 = BillboardComQoL.toCapitalize(line);
								continueAddFirstPrecedenceLoop = false;
								continueAddSecondPrecedenceLoop = true;
							} catch (Exception e) {
								System.out.println("Invalid Precedence");
							}
						}
					} else if (sorter instanceof PlacementSorter) {
						System.out.print("Input Placement Precedence or Go Back (0) (");
						for (int i = 0; i < PlacementSorter.precedenceCategory.length; i++) {
							if (i != 0)
								System.out.print(", ");
							System.out.print(PlacementSorter.precedenceCategory[i]);
						}
						System.out.println("):");
						line = systemScanner.nextLine().trim();
						num = toInteger(line);
						if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
							precedence0 = "null";
							continueAddFirstPrecedenceLoop = false;
						} else {
							try {
								PlacementSorter.isPrecedenceValid(new String[] { line, "Ascending" });
								precedence0 = BillboardComQoL.toCapitalize(line);
								continueAddFirstPrecedenceLoop = false;
								continueAddSecondPrecedenceLoop = true;
							} catch (Exception e) {
								System.out.println("Invalid Precedence");
							}
						}
					} else if (sorter instanceof SongSorter) {
						System.out.print("Input SongSorter Precedence or Go Back (0) (");
						for (int i = 0; i < SongSorter.precedenceCategory.length; i++) {
							if (i != 0)
								System.out.print(", ");
							System.out.print(SongSorter.precedenceCategory[i]);
						}
						System.out.println("):");
						line = systemScanner.nextLine().trim();
						num = toInteger(line);
						if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
							precedence0 = "null";
							continueAddFirstPrecedenceLoop = false;
						} else {
							try {
								SongSorter.isPrecedenceValid(new String[] { line, "Ascending" });
								precedence0 = BillboardComQoL.toCapitalize(line);
								continueAddFirstPrecedenceLoop = false;
								continueAddSecondPrecedenceLoop = true;
							} catch (Exception e) {
								System.out.println("Invalid Precedence");
							}
						}
					}

					while (continueAddSecondPrecedenceLoop) {
						System.out.print("Input Ascending (1) or Descending (2) or Go Back (0):");
						line = systemScanner.nextLine().trim();
						num = toInteger(line);
						if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
							continueAddSecondPrecedenceLoop = false;
						} else if (line.toLowerCase().equals("Ascending".toLowerCase()) || num == 1) {
							precedence1 = "Ascending";
							try {
								if (sorter instanceof ArtistSorter) {
									((ArtistSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								} else if (sorter instanceof PlacementSorter) {
									((PlacementSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								} else if (sorter instanceof SongSorter) {
									((SongSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								}

							} catch (InvalidPrecedenceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continueAddSecondPrecedenceLoop = false;

						} else if (line.toLowerCase().equals("Descending".toLowerCase()) || num == 2) {
							precedence1 = "Descending";
							try {
								if (sorter instanceof ArtistSorter) {
									((ArtistSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								} else if (sorter instanceof PlacementSorter) {
									((PlacementSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								} else if (sorter instanceof SongSorter) {
									((SongSorter) sorter).addPrecedence(new String[] { precedence0, precedence1 });
								}
							} catch (InvalidPrecedenceException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							continueAddSecondPrecedenceLoop = false;

						}

					}

				}
			} else if (line.equals("Remove Precedence".toLowerCase()) || num == 2) {
				int precedenceIndex = getPrecedenceIndex(sorter);
				if (precedenceIndex != -1) {
					String precedence = sorter.getPrecedence().get(precedenceIndex)[0] + ":"
							+ sorter.getPrecedence().get(precedenceIndex)[1];
					sorter.removePrecedence(precedenceIndex);
					System.out.println("[" + precedenceIndex + "] " + precedence + " is removed");
				} else {
					System.out.println("Index not found.");
				}
			} else if (line.equals("Remove All Precedence".toLowerCase()) || num == 3) {
				sorter.getPrecedence().clear();
			} else if (line.equals("Go Back".toLowerCase()) || num == 0) {
				continueEditSorterLoop = false;
			}
		}
	}

	public String getEditSorterMenuText(Sorter<?> sorter) {
		StringBuilder menu = new StringBuilder();
		String newText = "";
		if (sorter instanceof ArtistSorter) {
			newText = "ArtistSorter: ";
		} else if (sorter instanceof PlacementSorter) {
			newText = "PlacementSorter: ";
		} else if (sorter instanceof SongSorter) {
			newText = "SongSorter: ";
		}
		menu.append(String.format("%-31s:", newText + sorter.getName()));
		for (String[] precendece : sorter.getPrecedence()) {
			menu.append(String.format("%s-%s\t", precendece[0], precendece[1]));
		}
		menu.append("\n");

		menu.append("\n");
		menu.append("Precedence Menu:\n");
		menu.append(String.format("%-31s", "1) Add Precedence"));
		menu.append(String.format("%-31s", "2) Remove Precedence"));
		menu.append(String.format("%-31s", "3) Remove All Precedence"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public int getPrecedenceIndex(Sorter<?> sorter) {
		boolean continueSorterIndexLoop = true;
		while (continueSorterIndexLoop) {
			System.out.print("Input Precedence Index or type Go Back (0): ");
			String line = systemScanner.nextLine().trim();
			int num = toInteger(line);
			if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
				return -1;
			} else if (num != Integer.MIN_VALUE && num >= 0 && num < sorterList.size()) {
				return num;
			} else {
				System.out.println("Input was incorrect");
			}

		}
		return -1;
	}

	public String getOption(String incitingText) {
		System.out.print(incitingText);
		return systemScanner.nextLine().trim();
	}

	public int toInteger(String text) {
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return Integer.MIN_VALUE;
		}
	}

	public String getHelpText() {
		StringBuilder help = new StringBuilder();
		help.append("Help Info Here");
		return help.toString();

	}
	
	public boolean doesDateExist(Date date) {
		return FileHandler.checkTextExist("Database\\Billboard.com\\Text\\"+spreadsheet.getChartType()+"\\" + date.getYear()+"\\" +spreadsheet.getChartType() +" "+date.toString()+".txt");
	}

	public boolean doesSpreadsheetExist(String spreadsheetName) {
		return FileHandler.checkTextExist("Database\\Billboard.com\\Project\\Spreadsheet\\" + spreadsheetName + ".txt");
	}

	public boolean doesSorterExist(String sorterName) {
		return FileHandler.checkTextExist("Database\\Billboard.com\\Project\\Sorter\\" + sorterName + ".txt");
	}

	public String doesDirectoryExist(String path) {
		File directory = new File(path);
		if (directory.exists()) {
			String[] folders = path.split("\\\\");
			return folders[folders.length-1];
		}
		//System.out.println(path);
		// Test  - vs �
		String longDash = path.replace("-", "�");
		//System.out.println(longDash);
		directory = new File(longDash);
		if (directory.exists()) {
			String[] folders = longDash.split("\\\\");
			return folders[folders.length-1];
		}
		return null;
	}

	public void openSpreadsheet(String spreadsheetName) {
		this.spreadsheet = SpreadsheetFileHandler.readSpreadsheet(spreadsheetName);
		spreadsheetMenu();
		this.spreadsheet = null;
		this.sorterList = new ArrayList<>();
	}

	public void createOpenSpreadsheet(String spreadsheetName, String chartType) {
		this.spreadsheet = new Spreadsheet(spreadsheetName, chartType);
		spreadsheetMenu();
		this.spreadsheet = null;
		this.sorterList = new ArrayList<>();
	}

	public void openSorter(String sorterName) {
		for (Sorter<?> sorter : sorterList) {
			if (sorter.getName().toLowerCase().equals(sorterName.toLowerCase()))
				return;
		}
		try {
			sorterList.add(SorterFileHandler.readSorter(sorterName));
		} catch (InvalidFileFormat e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void createOpenSorter(String sorterName, String sorterType) {
		if (sorterType.toLowerCase().equals("Artist".toLowerCase())) {
			sorterList.add(new ArtistSorter(sorterName));
		} else if (sorterType.toLowerCase().equals("Placement".toLowerCase())) {
			sorterList.add(new PlacementSorter(sorterName));
		} else if (sorterType.toLowerCase().equals("Song".toLowerCase())) {
			sorterList.add(new SongSorter(sorterName));
		} else {
			System.out.println("WHAT???");
			return;
		}
	}
}