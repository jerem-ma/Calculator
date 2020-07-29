package fr.alpha.calculator;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import org.apache.commons.lang3.Validate;

import fr.alpha.calculator.interfaces.IOperationManager;
import fr.alpha.calculator.FindReturn;
import fr.alpha.calculator.MathematicalType;
import fr.alpha.calculator.R;

public class OperationManager implements IOperationManager{

	private static final char[][] SIGN_PRIORITY = {{'*', '/'}, {'+', '-'}};

	private String[] mathSigns;
	private List<String> operation;

	/**
	 * OperationManager's constructor
	 */
	public OperationManager(@NonNull String[] mathSigns){
		Validate.notNull(mathSigns);
		this.mathSigns = mathSigns;
		operation = new ArrayList<String>();
	}

	/**
	 * operation getter
	 *
	 * Used for unit test only !
	 */
	@Override
	public List<String> getOperation(){
		return operation;
	}

	/**
	 * operation setter
	 *
	 * Used for unit test only !
	 */
	@Override
	public void setOperation(@NonNull List<String> operation){
		Validate.notNull(operation);
		this.operation = operation;
	}

	@Override
	public void setOperation(@NonNull String operation){
		Validate.notNull(operation);
		final List<String> operationList = new ArrayList<String>();

		if (operation.contains("NaN") || operation.contains("Infinity")
				|| operation.contains("E"))
			operationList.add(operation);

		else{
			final char[] charArrayOperation = operation.toCharArray();
			for (char c : charArrayOperation){
				operationList.add(String.valueOf(c));
			}
		}
		setOperation(operationList);
	}

	@Override
	public boolean addCharacter(char c){
		if (containsFakeNumber(this.operation))
			return false;

		final MathematicalType mathType = getMathematicalType(c);

		final boolean isNotMathematical = mathType == MathematicalType.NONE;
		if (isNotMathematical)
			return false;

		if (operation.isEmpty()){
			switch (mathType){
				case DIGIT:
					// Don't change anything if the character is '0'
					if (c != '0')
						operation.add(String.valueOf(c));

					return true;
	
				case SIGN:
				case DOT:
					operation.add("0");
					operation.add(String.valueOf(c));
					return true;
			}
		}

		final String lastString = operation.get(operation.size() - 1);
		final char lastChar = lastString.charAt(lastString.length() - 1);
		final MathematicalType lastCharType = getMathematicalType(lastChar);

		switch (lastCharType){
			case SIGN:
			case DOT:
				if (mathType == MathematicalType.DIGIT){
					operation.add(String.valueOf(c));
					return true;
				}

				return false;

			case DIGIT:
				if (mathType != MathematicalType.DOT){
					operation.add(String.valueOf(c));
					return true;
				}

				if (!isLastNumberFloat(operation)){
					operation.add(String.valueOf(c));
					return true;
				}

				return false;
		}

		return false;
	}

	@Override
	public void clearOperation(){
		this.operation.clear();
	}

	@Override
	public double computes(){
		if (this.operation.isEmpty())
			return 0.0;

		List<String> result = new ArrayList<String>(this.operation);
		final String lastString = result.get(result.size() - 1);
		final char lastChar = lastString.charAt(lastString.length() - 1);
		if (getMathematicalType(lastChar) == MathematicalType.SIGN){
			result.remove(result.size() - 1);
		}

		for (final char[] tmpSignsPriority : SIGN_PRIORITY){
			FindReturn nearestChar = null;
			do{
				nearestChar = find(result, tmpSignsPriority);

				if (nearestChar.isEmpty())
					break;

				final int[] limits = subStringCurrentOperation(
					result, nearestChar);

				int begin = limits[0];
				final int end = limits[1];

				// Will return infinite when dividing by zero
				final double resultPart = computeSingleOperation(
					operationPart, nearestChar);

				for (int i = begin; i < end; i++){
					result.remove(begin);
				}

				result.add(begin, Double.toString(resultPart));

			} while(!nearestChar.isEmpty());
		}

		final String stringResult = String.join("", result);
		final double doubleResult = Double.parseDouble(stringResult);

		if (doubleResult == 0.0)
			this.operation.clear();

		else
			this.operation = result;

		return doubleResult;
	}

	@Override
	public boolean removeCharacter(){
		if (this.operation.isEmpty())
			return false;

		this.operation.remove(this.operation.size() - 1);

		if (this.operation.size() == 1 && this.operation.get(0) == "0")
			this.operation.clear();

		return true;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;

		if (!(obj instanceof OperationManager))
			return false;

		final OperationManager operationManager = (OperationManager) obj;

		final String joinedThisOperation = String.join("", this.operation);

		final List<String> objOperation = operationManager.getOperation();
		final String joinedObjOperation = String.join("", objOperation);

		return Arrays.equals(this.mathSigns, operationManager.mathSigns)
			&& Objects.equals(joinedThisOperation, joinedObjOperation);
	}

	@Override
	public int hashCode(){
		return Objects.hash(this.mathSigns, this.operation);
	}

	@Override
	public String toString(){
		return "mathSigns : " + Arrays.toString(this.mathSigns)
			+ ", operation : " + String.join("", this.operation);
	}

	private double computeSingleOperation(List<String> operation,
		FindReturn findReturn){

		final char sign = findReturn.getSign();
		findReturn = find(operation, new char[]{sign});

		final int signIndex = findReturn.getIndex();
		final int size = operation.size();

		final List<String> rawFirstNumber = operation.subList(0, signIndex);
		final List<String> rawSecondNumber = operation.subList(signIndex+1, size);
		final List<List<String>> rawNumbers = new ArrayList<List<String>>();

		rawNumbers.add(rawFirstNumber);
		rawNumbers.add(rawSecondNumber);

		final double[] numbers = new double[2];

		for (int i = 0; i < 2; i++){
			final String joined = String.join("", rawNumbers.get(i));
			numbers[i] = Double.parseDouble(joined);
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
	 * Search in a string list if one of the signs is present
	 *
	 * @return A FindReturn object containing the index and the char
	 * Return index=-1 and an empty char if not found
	 */
	private FindReturn find(@NonNull List<String> str, @NonNull char[] signs){
		Validate.notNull(str);
		Validate.notNull(signs);

		if (signs.length == 0)
			return new FindReturn();

		FindReturn winner = null;

		for (char c : signs){
			final int i = str.indexOf(String.valueOf(c));

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

	private boolean isLastNumberFloat(@NonNull List<String> operation){
		Validate.notNull(operation);

		for (int i = operation.size() - 1; i >= 0; i--){
			final char currentChar = String.join("", operation).charAt(i);
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

	private boolean isInteger(double nbr){
		if (nbr == (int) nbr)
			return true;

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

	private boolean containsFakeNumber(List<String> nbr){
		if (nbr.contains("Infinity") || nbr.contains("NaN"))
			return true;

		return false;
	}

	/**
	 * Get the begin and the end of a single operation
	 */
	private int[] subStringCurrentOperation(
		@NonNull List<String> operation,
		@NonNull FindReturn findReturn){

		Validate.notNull(operation);
		Validate.notNull(findReturn);

		if (operation.isEmpty() || findReturn.isEmpty())
			return new int[] {0, operation.size()};

		int begin = 0;
		int end = operation.size();
		int signIndex = findReturn.getIndex();

		for (int i = signIndex - 1; i >= 0 && begin == 0; i--){
			final String operationPart = operation.get(i);
			if (operationPart.length() != 1)
				continue;

			final char character = operationPart.charAt(0);
			if (isSign(character))
				begin = i + 1;
		}

		for (
			int i = signIndex + 1;
			i < operation.size() && end == operation.size();
			i++
		)
		{
			final String operationPart = operation.get(i);
			if (operationPart.length() != 1)
				continue;

			final char character = operationPart.charAt(0);
			if (isSign(character))
				end = i;
		}

		return new int[] {begin, end};
	}

}
