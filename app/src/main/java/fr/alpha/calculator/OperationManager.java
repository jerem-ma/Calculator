package fr.alpha.calculator;

import java.util.Arrays;
import java.util.regex.Pattern;

import android.content.res.Resources;
import androidx.annotation.NonNull;
import org.apache.commons.lang3.Validate;

import fr.alpha.calculator.interfaces.IOperationManager;
import fr.alpha.calculator.MathematicalType;
import fr.alpha.calculator.R;

public class OperationManager implements IOperationManager{

	private MainActivity mainActivity;
	private String operation;

	/**
	 * OperationManager's constructor
	 */
	public OperationManager(@NonNull MainActivity mainActivity){
		Validate.notNull(mainActivity);
		this.mainActivity = mainActivity;
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
	
	}

	private MathematicalType getMathematicalType(char c){
		if (Character.isDigit(c))
			return MathematicalType.DIGIT;

		final String cString = String.valueOf(c);
		final boolean isDot = Pattern.matches("\\.", cString);
		if (isDot)
			return MathematicalType.DOT;

		final Resources res = this.mainActivity.forwardResources();
		final String[] signMath = res.getStringArray(R.array.sign_math);

		final boolean isSignMath = Arrays.asList(signMath).contains(cString);
		if (isSignMath)
			return MathematicalType.SIGN;

		return MathematicalType.NONE;
	}

}
