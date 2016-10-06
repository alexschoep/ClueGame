package clueGame;

public class BadConfigFormatException extends Exception{
	public BadConfigFormatException() {
		super("ERROR: Bad file format! Config not loaded!");
	}
	
	public BadConfigFormatException(String str) {
		super(str);
	}
}
