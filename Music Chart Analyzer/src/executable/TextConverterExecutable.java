package executable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import billboard.com.sorter.ArtistSorter;
import billboard.com.sorter.PlacementSorter;
import billboard.com.sorter.SongSorter;
import billboard.com.sorter.Sorter;
import textconverter.TextBank;
import textconverter.TextList;

public class TextConverterExecutable {
	Scanner systemScanner;
	
	public TextConverterExecutable(Scanner systemScanner) {
		this.systemScanner = systemScanner;
	}
	
	public static ArrayList<TextBank> getAllTextBankFile(){
		//System.out.println("Text Bank List:");
		ArrayList<TextBank> textBankList = new ArrayList<TextBank>();
		File[] files = (new File("Database\\Text Converter\\Text Bank")).listFiles();
		for (File file : files) {
			if(file.isFile()) {
				textBankList.add(new TextBank(file.getPath()));
			}
		}
		return textBankList;
	}
	
	public static String getAllTextBankFileText() {
		StringBuilder output = new StringBuilder();
		output.append("Text Bank List:\n");
		File[] files = (new File("Database\\Text Converter\\Text Bank")).listFiles();
		for (File file : files) {
			if(file.isFile()) {
				output.append(file.getPath()+"\n");
			}
		}
		return output.toString();
	}
	
	public static String getAllTextFileNameText(){
		StringBuilder output = new StringBuilder();
		output.append("Text List:\n");
		File[] files = (new File("Database\\Text Converter\\Text")).listFiles();
		for(int i = 0; i<files.length;i++) {
			if(files[i].isFile()) {
				output.append(files[i].getPath()+"\n");
			}
		}
		return output.toString();
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
		greeting.append("Launching Text Converter Executable\n");
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
			}else if (line.toLowerCase().equals("Open Text".toLowerCase()) || num == 2) {
				boolean continueOpenLoop = true;
				while (continueOpenLoop) {
					System.out.println(getAllTextFileNameText());
					System.out.print("Input Text Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueOpenLoop=false;
					} else {
						File myFile = new File("Database\\Text Converter\\Text\\"+line);
						if(myFile.isFile()) {
							System.out.println(myFile.getName() + " Found");
							textMenu(myFile);
						}else {
							System.out.println("Text was not found.\nWould you like to Create New (1) or Go Back (0)");
							line = systemScanner.nextLine().trim();
							num = toInteger(line);
							if (line.toLowerCase().equals("Create New".toLowerCase()) || num == 1) {
								try {
									if(myFile.createNewFile()) System.out.println("File created: " + myFile.getName());
									textMenu(myFile);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				}
			}else if (line.toLowerCase().equals("Create Text Bank".toLowerCase()) || num == 3) {
				boolean continueNewTextBankLoop = true;
				while (continueNewTextBankLoop) {
					System.out.println(getAllTextBankFileText());
					System.out.print("Input Text Bank Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueNewTextBankLoop=false;
					} else {
						File myFile = new File("Database\\Text Converter\\Text Bank\\"+line);
						try {
							if(myFile.createNewFile()) {
								System.out.println("File created: " + myFile.getName());
							}else {
								System.out.println("Text was found.\nWould you like to Override (1) or Go Back (0)");
								line = systemScanner.nextLine().trim();
								num = toInteger(line);
								if (line.toLowerCase().equals("Override".toLowerCase()) || num == 1) {
									myFile.delete();
									if(myFile.createNewFile()) System.out.println("File Overrided: " + myFile.getName());
								}
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}else if (line.toLowerCase().equals("Edit Text Bank".toLowerCase()) || num == 4) {
				boolean continueEditTextBankLoop = true;
				while (continueEditTextBankLoop) {
					System.out.println(getAllTextBankFileText());
					System.out.print("Input Text Bank (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueEditTextBankLoop=false;
					} else {
						File myFile = new File("Database\\Text Converter\\Text Bank\\"+line);
						if(myFile.isFile()) {
							System.out.println(myFile.getName() + " Found");
							textBankMenu(myFile);
						}else {
							System.out.println("Text was not found.");
						}
					}
				}
			}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueStartLoop=false;
			}
		}
		return false;
	}
		
	public String getStartMenuText() {
		StringBuilder menu = new StringBuilder();
		menu.append(getAllTextBankFileText()+"\n");
		menu.append("Start Menu:\n");
		menu.append(String.format("%-31s", "1) Help"));
		menu.append(String.format("%-31s", "2) Open Text"));
		menu.append(String.format("%-31s", "3) Create Text Bank"));
		menu.append(String.format("%-31s", "4) Edit Text Bank"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public String getHelpText() {
		StringBuilder help = new StringBuilder();
		help.append("Help Info Here");
		return help.toString();

	}
	
	public void textMenu(File textFile) {
		String textString = getText(textFile);
		String line;
		int num;
		boolean continueTextLoop = true;
		while (continueTextLoop) {
			System.out.println("Text File: "+textFile.getName());
			line = getOption(getTextMenuText());
			num = toInteger(line);
			if (line.toLowerCase().equals("Use Text Bank".toLowerCase()) || num == 1) {
				boolean continueUseTextBankLoop = true;
				while (continueUseTextBankLoop) {
					System.out.println(getAllTextBankFileText());
					System.out.print("Input Text Bank Name (Case Insensitive) or type Go Back (0): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Go Back".toLowerCase()) || num == 0) {
						continueUseTextBankLoop=false;
					} else {
						File textBankFile = new File("Database\\Text Converter\\Text Bank\\"+line);
						if(textBankFile.isFile()) {
							TextBank textBank = new TextBank(textBankFile.getPath());
							System.out.println(getKeyListText(textBank));
							String convertFrom, convertTo;
							System.out.print("Insert Key to Convert From: ");
							convertFrom = systemScanner.nextLine().trim();
							System.out.print("Insert Key to Convert To: ");
							convertTo = systemScanner.nextLine().trim();
							textString = textBank.convertText(textString, convertFrom, convertTo);
							System.out.println(textString);
							continueUseTextBankLoop=false;
						}else {
							System.out.println("Text Bank was not found.");
						}
					}
				}
			}else if (line.toLowerCase().equals("Save Text".toLowerCase()) || num == 2) {
				FileWriter writer;
				try {
					writer = new FileWriter(textFile);
					writer.write(textString);
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Text Saved");
			}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueTextLoop=false;
			}
		}
	}
	
	public String getTextMenuText() {
		StringBuilder menu = new StringBuilder();
		//menu.append(getAllTextFileNameText());
		menu.append("Text Menu:\n");
		menu.append(String.format("%-31s", "1) Use Text Bank"));
		menu.append(String.format("%-31s", "2) Save Text"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public void textBankMenu(File textBankFile) {
		TextBank textBank = new TextBank(textBankFile);
		String line;
		int num;
		boolean continueTextBankLoop = true;
		while (continueTextBankLoop) {
			System.out.println("Text Bank File: "+textBankFile.getName());
			line = getOption(getTextBankMenuText(textBank));
			num = toInteger(line);
			if (line.toLowerCase().equals("Add Couple".toLowerCase()) || num == 1) {
				boolean continueAddCoupleLoop = true;
				ArrayList<String> key = new ArrayList<>(), text = new ArrayList<>();
				while (continueAddCoupleLoop) {
					System.out.println(textBank.getTable());
					System.out.println("Couple:");
					for(int i = 0;i<key.size();i++) {
						System.out.print(key.get(i)+":::"+text.get(i)+"\t");
					}
					System.out.println();
					System.out.print("Input Key:::Text, type Finish (0), or Remove Last (-1): ");
					line = systemScanner.nextLine().trim();
					num = toInteger(line);
					if (line.toLowerCase().equals("Finish".toLowerCase()) || num == 0) {
						String[] keyArray = ArrayListStringToArrayString(key), 
								textArray = ArrayListStringToArrayString(text);
						if(key.size()!=0) {
							textBank.addTextCouple(textArray, keyArray);
							System.out.println("Couple Added to Text Bank");
						}
						key = new ArrayList<>();
						text = new ArrayList<>();
						continueAddCoupleLoop=false;
					}else if (line.toLowerCase().equals("Remove Last".toLowerCase()) || num == -1) {
						if(key.size()==0) {
							System.out.println("Can not be removed");
						}else {
							key.remove(key.size()-1);
							text.remove(text.size()-1);
						}
						
					}else {
						String[] input = line.split(":::");
						if(input.length==2) {
							key.add(input[0]);
							text.add(input[1]);
						}else {
							System.out.println("Invalid Syntax");
						}
					}
					
				}
			}else if (line.toLowerCase().equals("Remove Couple".toLowerCase()) || num == 2) {
				System.out.println("Type Index to delete or type Go Back (0)");
				line = systemScanner.nextLine().trim();
				num = toInteger(line);
				if(num>0 && num < textBank.getTextBank().size()) {
					System.out.println(textBank.removeTextCouple(num)?"Removed":"Did Not Remove");
				}
			}else if (line.toLowerCase().equals("Remove Key".toLowerCase()) || num == 3) {
				System.out.println("Type Exact String to delete or type Go Back (-1)");
				line = systemScanner.nextLine().trim();
				num = toInteger(line);
				if (line.toLowerCase().equals("Finish".toLowerCase()) || num == -1) {
					System.out.println("Going Back");
				}else {
					System.out.println(textBank.removeKey(line)?"Removed":"Did Not Remove");
				}
			}else if (line.toLowerCase().equals("Save Text Bank".toLowerCase()) || num == 4) {
				FileWriter writer;
				try {
					writer = new FileWriter(textBankFile);
					writer.write(textBank.toString());
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Text Saved");
			}else if (line.toLowerCase().equals("Quit".toLowerCase()) || num == 0) {
				continueTextBankLoop=false;
			}
		}
	}
	
	public String getTextBankMenuText(TextBank textBank) {
		StringBuilder menu = new StringBuilder();
		menu.append(textBank.getTable());
		menu.append("Text Bank Menu:\n");
		menu.append(String.format("%-31s", "1) Add Couple"));
		menu.append(String.format("%-31s", "2) Remove Couple"));
		menu.append(String.format("%-31s", "3) Remove Key"));
		menu.append(String.format("%-31s", "4) Save Text Bank"));
		menu.append(String.format("%-31s\n", "0) Quit"));
		menu.append("Input: ");
		return menu.toString();
	}
	
	public String getText(File textFile) {
		StringBuilder output = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(textFile));
			String currentLine = reader.readLine();
			while(currentLine!=null) {
				output.append(currentLine+"\n");
				currentLine=reader.readLine();
			}
			reader.close();
		}catch(Exception e) {
			output.append("ERROR MESSAGE "+e.getMessage());
		}
		return output.toString();
	}
	
	public String getKeyListText(TextBank textBank) {
		StringBuilder output = new StringBuilder();
		output.append("Key List:\n");
		String[] keyList = textBank.getKeyList();
		for(String key: keyList)
			output.append(key+"\n");
		return output.toString();
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
	
	public String[] ArrayListStringToArrayString(ArrayList<String> arrayList) {
		String[] output = new String[arrayList.size()];
		for(int i = 0; i<arrayList.size();i++)output[i] = arrayList.get(i);
		return output;
	}
}
