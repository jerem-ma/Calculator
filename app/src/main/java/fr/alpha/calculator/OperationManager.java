package fr.alpha.calculator;

import androidx.annotation.NonNull;
import org.apache.commons.lang3.Validate;

import fr.alpha.calculator.interfaces.IOperationManager;

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
}
