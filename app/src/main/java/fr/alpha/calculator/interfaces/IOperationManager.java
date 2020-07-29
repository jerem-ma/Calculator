package fr.alpha.calculator.interfaces;

import java.util.List;

import androidx.annotation.NonNull;

/**
 *
 * An object that will manage operation asked by the user.
 * 
 * This manager will get what the user type with their interface and do the
 * modification on the operation string. 
 *
 */
public interface IOperationManager{

	/**
	 *
	 * Getters and setters used for unit tests
	 *
	 */
	public List<String> getOperation();
	public void setOperation(@NonNull List<String> operation);
	public void setOperation(@NonNull String operation);
	
	/**
	 * 
	 * Appends a character to the operation string.
	 *
	 * @return false if the character to append can not go after the operation,
	 * mathematically, true otherwise.
	 *  
	 */
	public boolean addCharacter(char c);

	/**
	 *
	 * Delete all character in the operation string.
	 *
	 */
	public void clearOperation();
	
	/**
		*
		* Analyses the operation and do the math.
		*
		* The computes function will check what is inside the operation string and
		* do the appropriate operations if it is possible. When the operation is
		* succesfully complete, the result is set to the operation and returned.
		* 
		*/
	public double computes();

	/**
	 *
	 * Remove the last character of the operation string.
	 *
	 * If there is only one character in the operation string, this will be set
	 * to an empty string.
	 *
	 * @return true if there is a character to remove, false otherwise.
	 *
	 */
	public boolean removeCharacter();
	

}
