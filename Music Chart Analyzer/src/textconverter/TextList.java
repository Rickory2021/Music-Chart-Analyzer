package textconverter;

import java.util.ArrayList;

public class TextList implements Comparable<TextList>{
	private ArrayList<String> text;
	private String key;
	
	public TextList() {
		this.text=new ArrayList<String>();
		this.key=null;
	}
	
	public TextList(String text) {
		this.text=new ArrayList<String>();
		for(String string : text.split(":::")) {
			this.text.add(string);
		}
		this.key=this.text.get(0);
	}
	
	public TextList(String key, int size) {
		this.key=key;
		this.text=new ArrayList<String>();
		text.add(key);
		new ArrayList<String>();
		for(int i = 1;i<size;i++) {
			text.add("NULL");
		}
	}
	
	public TextList(ArrayList<String> text) {
		this.text=text;
		this.key=text.get(0);
	}
	
	

	public ArrayList<String> getText() {
		return text;
	}
	
	public String get(int index) {
		return text.get(index);
	}

	public void setText(ArrayList<String> text) {
		this.text = text;
	}
	
	public void addText(String text) {
		if(this.text.size()==0)this.key=text;
		this.text.add(text);
	}
	
	public void removeText() {
		this.text.remove(text.size()-1);
		if(this.text.size()==0) {
			key=null;
		}
	}
	
	public void removeText(int index) {
		this.text.remove(index);
		if(this.text.size()==0) {
			key=null;
		}
		if(index==0) {
			key=this.text.get(0);
		}
	}
	
	public int getSize() {
		return text.size();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	@Override
	public String toString() {
		if(text.size()==0)return"";
		StringBuilder output = new StringBuilder();
		output.append(text.get(0));
		for(int i = 1; i<text.size();i++) {
			output.append(String.format(":::%s", text.get(i)));
		}
		return output.toString();
	}

	@Override
	public int compareTo(TextList o) {
		return this.key.compareTo(o.key);
	}
	
	
}
