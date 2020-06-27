package fr.alpha.calculator;

/**
 * Object representing the return of a find method
 * 
 * If nothing has been found, the index = -1 and sign = '\0'
 */
public class FindReturn{

	private final int index;
	private final char sign;

	public FindReturn() {
		this.index = -1;
		this.sign = '\0';
	}

	public FindReturn(final int index, final char sign){
		this.index = index;
		this.sign = sign;
	}

	public int getIndex(){
		return this.index;
	}

	public char getSign(){
		return this.sign;		
	}

	public boolean isEmpty(){
		return this.index == -1;
	}

}
