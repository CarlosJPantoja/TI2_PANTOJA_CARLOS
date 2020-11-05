package exceptions;

public class AmountLimit extends Exception {
	
	private static final long serialVersionUID = 1L;

	public AmountLimit(String msg){
		super(msg);
	}
}
