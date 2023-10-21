package billboard.com.exception;

/**
 * Exception when attempting to add a Name that is already active
 * 
 * @author Ricky Chen
 */
public class DuplicateNameError extends Exception{
	private static final long serialVersionUID = 1L;

	public DuplicateNameError(String msg){  
		super(msg);
	}  
}