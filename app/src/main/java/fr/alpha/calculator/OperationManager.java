package fr.alpha.calculator;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import org.apache.commons.lang3.Validate;

import fr.alpha.calculator.interfaces.IOperationManager;
import fr.alpha.calculator.MathematicalType;
import fr.alpha.calculator.R;

public class OperationManager implements IOperationManager{

	private String[] mathSigns;
	private String operation;

	/**
	 * OperationManager's constructor
	 */
	public OperationManager(@NonNull String[] mathSigns){
		Validate.notNull(mathSigns);
		this.mathSigns = mathSigns;
		operation = "";
	}

	/**
	 * operation getter
	 *
	 * Used for unit test only !
	 */
	@Override
	public String getOperation(){
		return operation;
	}

	/**
	 * operation setter
	 *
	 * Used for unit test only !
	 */
	@Override
	public void setOperation(@NonNull String operation){
		Validate.notNull(operation);
		this.operation = operation;
	}

	@Override
	public boolean addCharacter(char c){
		final MathematicalType mathType = getMathematicalType(c);

		final boolean isNotMathematical = mathType == MathematicalType.NONE;
		if (isNotMathematical)
			return false;

		if (operation == ""){
			switch (mathType){
				case DIGIT:
					// Don't change anything if the character is '0'
					if (c != '0')
						operation = String.valueOf(c);

					return true;
	
				case SIGN:
				case DOT:
					operation = "0" + c;
					return true;
			}
		}

		final char lastChar = operation.charAt(operation.length() - 1);
		final MathematicalType lastCharType = getMathematicalType(lastChar);

		switch (lastCharType){
			case SIGN:
			case DOT:
				if (mathType == MathematicalType.DIGIT){
					operation += c;
					return true;
				}

				return false;

			case DIGIT:
				if (mathType != MathematicalType.DOT){
					operation += c;
					return true;
				}

				if (!isLastNumberFloat(operation)){
					operation += c;
					return true;
				}

				return false;
		}

		return false;
	}

	@Override
	public void clearOperation(){
		this.operation = "";
	}

	@Override
	public double computes(){
		return 0.0;
	}

	@Override
	public boolean removeCharacter(){
		return false;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;

		if (!(obj instanceof OperationManager))
			return false;

		final OperationManager operationManager = (OperationManager) obj;

		return Arrays.equals(this.mathSigns, operationManager.mathSigns)
			&& Objects.equals(this.operation, operationManager.operation);
	}

	@Override
	public int hashCode(){
		return Objects.hash(this.mathSigns, this.operation);
	}

	@Override
	public String toString(){
		return "mathSigns : " + Arrays.toString(this.mathSigns)
			+ ", operation : " + this.operation;
	}

	private MathematicalType getMathematicalType(char c){
		if (Character.isDigit(c))
			return MathematicalType.DIGIT;

		final String cString = String.valueOf(c);
		final boolean isDot = Pattern.matches("\\.", cString);
		if (isDot)
			return MathematicalType.DOT;

		final boolean isSignMath = Arrays.asList(this.mathSigns).contains(cString);
		if (isSignMath)
			return MathematicalType.SIGN;

		return MathematicalType.NONE;
	}

	private boolean isLastNumberFloat(@NonNull String operation){
		Validate.notNull(operation);

		for (int i = operation.length() - 1; i >= 0; i--){
			final char currentChar = operation.charAt(i);
			final MathematicalType type = getMathematicalType(currentChar);

			switch (type){
				case DIGIT:
					continue;

				case SIGN:
					return false;

				case DOT:
					return true;
			}
		}

		return false;
	}

}
