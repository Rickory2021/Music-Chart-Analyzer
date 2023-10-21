package billboard.com.exception;

/**
 * Exception when inputting an invalid Precedence in the Sorter
 * 
 * @author Ricky Chen
 */
public class InvalidFileFormat extends Exception{
	private static final long serialVersionUID = 1L;

	public InvalidFileFormat(String msg){  
		super(msg);
	}  
}
