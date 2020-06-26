package fr.alpha.calculator;

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

}
