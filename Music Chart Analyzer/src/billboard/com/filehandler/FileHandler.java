package billboard.com.filehandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {
	public boolean showErrorMessage, canOverride;
	private String chartType;
	
	public FileHandler() {
		this.showErrorMessage = true;
		this.canOverride = false;
		this.chartType = "Billboard Japan Hot 100 – Billboard";
	}
	
	public FileHandler(String chartType) {
		this.showErrorMessage = true;
		this.canOverride = false;
		this.chartType = chartType;
	}
	
	public FileHandler(boolean showErrorMessage, boolean canOverride) {
		this.showErrorMessage = showErrorMessage;
		this.canOverride = canOverride;
		this.chartType = "Billboard Japan Hot 100 – Billboard";
	}
	
	public FileHandler(String chartType, boolean showErrorMessage, boolean canOverride) {
		this.showErrorMessage = showErrorMessage;
		this.canOverride = canOverride;
		this.chartType = chartType;
	}
	
	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}
	
	public static boolean checkTextExist(String fileLocation) {
		File myFile = new File(fileLocation);
		if(myFile.isFile())return true;
		return false;
	}
	
	public static boolean checkDirectoryExist(String fileLocation) {
		File myFile = new File(fileLocation);
		if(myFile.isDirectory())return true;
		return false;
	}

	public String getText(String fileLocation) {
		StringBuilder output = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileLocation));
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
	
	public boolean makeText(String directoryString, String newFilePath, String text) {
		if(text.contains("ERROR MESSAGE")) {
			if(showErrorMessage)System.out.println("ERROR: "+text);
			return false;
		}
		
		try {
			String[] folders = directoryString.split("\\\\");
			String directorySubstring = "";
			for(int i = 0; i<folders.length;i++) {
				directorySubstring += (i!=0?"\\":"")+folders[i];
				File yearDir = new File(directorySubstring);
				if(!yearDir.exists()) {
					yearDir.mkdir();
					System.out.println("Directory created: " + yearDir.getName());
				}
			}
			
			File textFile = new File(newFilePath);
			if (textFile.createNewFile()) {
				System.out.println("File created: " + textFile.getName());
			}else{
				System.out.println("File ("+textFile.getName()+") already exists." +(canOverride?" >> Overriding":" >> Exitting"));
				if(!canOverride)return false;
				
			}
			FileWriter writer = new FileWriter(newFilePath);
			writer.write(text);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
