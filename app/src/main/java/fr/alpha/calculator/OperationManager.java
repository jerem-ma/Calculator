package fr.alpha.calculator;

import org.apache.commons.lang3.Validate;
import androidx.annotation.NonNull;

import fr.alpha.calculator.interfaces.IOperationManager;

public class OperationManager implements IOperationManager{

	private String operation;

	/**
	 * OperationManager's constructor
	 */
	public OperationManager(){
		operation = "";
	}

	@Override
	public String getOperation(){
		return operation;
	}

	@Override
	public void setOperation(@NonNull String operation){
		Validate.notNull(operation);
		this.operation = operation;
	}
}
