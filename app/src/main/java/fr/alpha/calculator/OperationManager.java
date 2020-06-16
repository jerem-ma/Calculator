package fr.alpha.calculator

import org.apache.commons.lang3.Validate

import fr.alpha.calculator.interfaces

public class OperationManager implements interfaces.OperationManager{

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
	public void setOperation(@NotNull String operation){
		Validate.notNull(operation);
		this.operation = operation;
	}
}
