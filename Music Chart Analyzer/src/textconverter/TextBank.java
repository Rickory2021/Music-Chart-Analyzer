package textconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class TextBank {
	private String filePath;
	private ArrayList<TextList> textBank;
	
	public TextBank() {
		this.filePath="NULL";
		this.textBank=new ArrayList<>();
	}
	
	public TextBank(String filePath) {
		this.filePath=filePath;
		this.textBank=readFile(filePath);
	}
	
	public TextBank(File file) {
		this.filePath=file.getPath();;
		this.textBank=readFile(filePath);
	}
	
	public TextBank(ArrayList<TextList> textBank) {
		this.filePath="NULL";
		this.textBank=textBank;
	}
	
	public TextBank(String filePath, ArrayList<TextList> textBank) {
		this.filePath=filePath;
		this.textBank=textBank;
	}
	
	public String getFilePath() {
		return filePath;
	}

	public void addTextList(TextList textList) {
		textBank.add(textList);
		Collections.sort(textBank);
	}
	
	public void addTextCouple(String[] text, String[] key) {
		if(textBank.size()==0) {
			for(String keyString: key)
			textBank.add(new TextList(keyString));
		}
		Collections.sort(textBank);
		int newSize=-1;
		int oldSize=textBank.get(0).getSize();
		for(int i = 0; i<key.length;i++) {
			TextList keyString = new TextList(key[i]);
			int keyIndex = Collections.binarySearch(textBank, keyString);
			if(keyIndex<0) {
				// Make new TextList
				keyString = new TextList(key[i], oldSize);
				keyIndex=-(keyIndex)-1;
				textBank.add(keyIndex, keyString);
			}
			// Add new Couple Text
			textBank.get(keyIndex).addText(text[i]);
			if(newSize<textBank.get(keyIndex).getSize())newSize=textBank.get(keyIndex).getSize();
		}
		// Add Null
		for(int i = 0; i<textBank.size();i++) {
			if(textBank.get(i).getSize()<newSize)textBank.get(i).addText("NULL");
		}
	}
	
	public boolean removeTextCouple(int index) {
		if(textBank.size()>0 || index < textBank.get(0).getSize()) {
			for(int keyIndex = 0; keyIndex<textBank.size(); keyIndex++) {
				textBank.get(keyIndex).removeText(index);
			}
			return true;
		}
		
		return false;
		
	}
	
	public boolean removeKey(String key) {
		int keyIndex = getKeyIndex(key);
		if(keyIndex==-1)return false;
		textBank.remove(keyIndex);
		return true;
	}
	
	
	public static ArrayList<TextList> readFile(String filePath){
		try {
			ArrayList<TextList> textBank = new ArrayList<>();
			BufferedReader reader = new BufferedReader(new FileReader(filePath));
			String currentLine = reader.readLine();
			while(currentLine!=null) {
				textBank.add(new TextList(currentLine));
				currentLine=reader.readLine();
			}
			reader.close();
			return textBank;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean writeFile(boolean override){
		try {
			File file = new File(filePath);
			if (file.createNewFile()) {
				System.out.println("File created: " + file.getName());
			}else{
				System.out.println("File ("+file.getName()+") already exists." +(override?" >> Overriding":" >> Exitting"));
				if(!override)return false;
			}
			FileWriter writer = new FileWriter(filePath);
			StringBuilder text = new StringBuilder();
			for(TextList textList:textBank) {
				writer.append(textList+"\n");
			}
			writer.write(text.toString());
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static boolean doesBankExist(String filePath) {
		File myFile = new File(filePath);
		if(myFile.isFile())return true;
		return false;
	}
	
	public int getKeyIndex(String key) {
		for(int i = 0; i<textBank.size();i++) {
			if(textBank.get(i).getKey().equals(key))return i;
		}
		return -1;
	}
	
	public String[] getKeyList() {
		String[] keyList = new String[textBank.size()];
		for(int i = 0; i<keyList.length;i++) {
			keyList[i]=textBank.get(i).getKey();
		}
		return keyList;
	}
	
	public ArrayList<TextList> getTextBank() {
		return textBank;
	}

	public String convertText(String text, String keyFrom, String keyTo) {
		int keyFromIndex = getKeyIndex(keyFrom), 
				keyToIndex = getKeyIndex(keyTo);
		if(keyFromIndex==-1 || keyToIndex==-1) {
			if(keyFromIndex==-1) System.out.println("Key >"+keyFrom+"< is Invalid");
			if(keyToIndex==-1) System.out.println("Key >"+keyTo+"< is Invalid");
			return text;
		}
		for(int i = 1; i<textBank.get(0).getSize();i++) {
			if(!textBank.get(keyFromIndex).get(i).equals("NULL")&&!textBank.get(keyToIndex).get(i).equals("NULL")) {
				text = text.replace(textBank.get(keyFromIndex).get(i),
					textBank.get(keyToIndex).get(i));
				System.out.println(textBank.get(keyFromIndex).get(i)+">>"+textBank.get(keyToIndex).get(i));
			}
			
		}
		//System.out.println(">>"+text);
		return text;
	}
	
	public String getTable() {
		if(textBank.size()==0) {
			return "Empty Table\n";
		}
		//System.out.println(textBank.size());
		//System.out.println(textBank.get(0).getSize());
		StringBuilder output = new StringBuilder();
		for(int row = 0; row<textBank.get(0).getSize();row++) {
			if(row==0)output.append("Key:\t");
			else output.append(String.format("%3s:\t", row));
			for(int col = 0; col<textBank.size();col++) {
				output.append(String.format("%-30s\t", textBank.get(col).get(row)));
			}
			output.append("\n");
			//System.out.println(output);
		}
		return output.toString();
	}
	
	@Override
	public String toString() {
		if(textBank.size()==0)return"";
		StringBuilder output = new StringBuilder();
		output.append(textBank.get(0));
		for(int i = 1; i<textBank.size();i++) {
			if(i<textBank.size())output.append("\n");
			output.append(String.format("%s", textBank.get(i)));
		}
		return output.toString();
	}
}
