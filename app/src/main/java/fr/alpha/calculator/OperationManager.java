package fr.alpha.calculator;

import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.Objects;

import androidx.annotation.NonNull;
import org.apache.commons.lang3.Validate;

import fr.alpha.calculator.interfaces.IOperationManager;
import fr.alpha.calculator.FindReturn;
import fr.alpha.calculator.MathematicalType;
import fr.alpha.calculator.R;

public class OperationManager implements IOperationManager{

	private static final char[][] SIGN_PRIORITY = {{'*', '/'}, {'+', '-'}};

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
		String result = this.operation;

		for (final char[] tmpSignsPriority : SIGN_PRIORITY){
			FindReturn nearestChar = null;
			do{
				nearestChar = find(result, tmpSignsPriority);

				if (nearestChar.isEmpty())
					break;

				final String operationPart = subStringCurrentOperation(
					result, nearestChar);

				// Will return infinite when dividing by zero
				final double resultPart = computeSingleOperation(
					operationPart, nearestChar);

				final String quotedOperationPart = Pattern.quote(operationPart);
				result = result.replaceFirst(
					quotedOperationPart, Double.toString(resultPart));

			} while(!nearestChar.isEmpty());
		}

		this.operation = result;

		return Double.parseDouble(result);
	}

	@Override
	public boolean removeCharacter(){
		if (this.operation.equals(""))
			return false;

		// Remove the last character
		this.operation = this.operation.substring(0, this.operation.length() - 1);
		return true;
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

	private double computeSingleOperation(String operation,
		FindReturn findReturn){

		final char sign = findReturn.getSign();
		final String quotedSign = Pattern.quote(String.valueOf(sign));

		final String[] rawNumbers = operation.split(quotedSign);
		final double[] numbers = new double[2];

		for (int i = 0; i < 2; i++){
			numbers[i] = Double.parseDouble(rawNumbers[i]);
		}

		switch(sign){
			case '+':
				return numbers[0] + numbers[1];

			case '-':
				return numbers[0] - numbers[1];

			case '*':
				return numbers[0] * numbers[1];

			case '/':
				return numbers[0] / numbers[1];

			default:
				throw new IllegalArgumentException(
					"The given sign is not available !");
		}

	}

	/**
	 * Search in a string if one of the signs is present
	 *
	 * @return A FindReturn object containing the index and the char
	 * Return index=-1 and an empty char if not found
	 */
	private FindReturn find(@NonNull String str, @NonNull char[] signs){
		Validate.notNull(str);
		Validate.notNull(signs);

		if (signs.length == 0)
			return new FindReturn();

		FindReturn winner = null;

		for (char c : signs){
			int i = str.indexOf(String.valueOf(c));
			if (i == -1)
				continue;

			if (winner == null || i > winner.getIndex())
				winner = new FindReturn(i, c);
		}

		if (winner == null)
			return new FindReturn();

		return winner;

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

	private boolean isSign(@NonNull String operation, int index){
		Validate.notNull(operation);

		final char currentChar = operation.charAt(index);
		final MathematicalType type = getMathematicalType(currentChar);

		if (type == MathematicalType.SIGN)
			return true;

		return false;
	}

	private String subStringCurrentOperation(
		@NonNull String operation,
		@NonNull FindReturn findReturn){

		Validate.notNull(operation);
		Validate.notNull(findReturn);

		if (operation.equals("") || findReturn.isEmpty())
			return "";

		int begin = 0;
		int end = operation.length();
		int signIndex = findReturn.getIndex();

		for (int i = signIndex - 1; i >= 0 && begin != 0; i--){
			if (isSign(operation, i))
				begin = i + 1;
		}

		for (
			int i = signIndex + 1;
			i < operation.length() && end != operation.length();
			i++
		)
		{
			if (isSign(operation, i))
				end = i;
		}

		return operation.substring(begin, end);
	}

}
