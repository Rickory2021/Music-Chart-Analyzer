package billboard.com.sorter;

import java.util.ArrayList;
import java.util.Comparator;

import billboard.com.BillboardComQoL;
import billboard.com.exception.DuplicateNameError;
import billboard.com.exception.InvalidPrecedenceException;

public class Sorter<T> implements Comparator<T>{
	private ArrayList<String[]> precedence; // Determine the order of Precedence
	// order[0] = Object to Sort
	// order[1] = Ascending/Descending/""(Default to Ascending)\
	private String name;

	public Sorter() {
		this.name=null;
		this.precedence = new ArrayList<>();
	}
	
	public Sorter(ArrayList<String[]> order) {
		this.name=null;
		this.precedence=order;
	}
	
	public Sorter(String name)  {
		this.name=name;
		this.precedence = new ArrayList<>();
	}
	
	public Sorter(String name, ArrayList<String[]> order)  {
		this.name=name;
		this.precedence=order;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name)  {
		this.name=name;
	}
	
	public ArrayList<String[]> getPrecedence(){
		return precedence;
	}
	
	public void addPrecedence(String[] newElement) throws InvalidPrecedenceException {
		isPrecedenceValid(newElement);
		precedence.add(newElement);
	}
	
	public void removePrecedence() {
		precedence.remove(precedence.size()-1);
	}
	
	public void removePrecedence(int index) {
		precedence.remove(index);
	}
	
	public void clearPrecedence() {
		precedence = new ArrayList<>();
	}
	
	public static void isPrecedenceValid(String[] element) throws InvalidPrecedenceException{
		throw new InvalidPrecedenceException("Valid Precedence has not been set up");		
	}
	
	@Override
	public int compare(T o1, T o2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("Sorter: "+name+"\n");
		for(int i = 0;i<precedence.size();i++) {
			output.append(precedence.get(i)[0]+"---"+precedence.get(i)[1]+"\n");
		}
		return output.toString();
	}
	

}
