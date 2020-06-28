package fr.alpha.calculator;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;

import fr.alpha.calculator.R;

public class MainActivity extends Activity {

	private OperationManager manager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Resources res = getResources();
		final String[] mathSigns = res.getStringArray(R.array.sign_math);
		manager = new OperationManager(mathSigns);
	}

	/**
	 * This method will call getResources() and return what is returned
	 */
	public Resources forwardResources(){
		return getResources();
	}

	/* ** Events ** */
		// Numbers
	public void zeroButtonClick(View view){
		
	}

	public void oneButtonClick(View view){

	}

	public void twoButtonClick(View view){

	}

	public void threeButtonClick(View view){

	}

	public void fourButtonClick(View view){

	}

	public void fiveButtonClick(View view){

	}

	public void sixButtonClick(View view){

	}

	public void sevenButtonClick(View view){

	}

	public void eightButtonClick(View view){

	}

	public void nineButtonClick(View view){

	}

		// Math signs
	public void sumButtonClick(View view){

	}

	public void minusButtonClick(View view){

	}

	public void multiplyButtonClick(View view){

	}

	public void divideButtonClick(View view){

	}

	public void percentButtonClick(View view){

	}

		// Delete buttons
	public void deleteButtonClick(View view){

	}

	public void clearButtonClick(View view){

	}

		// Divers
	public void decimalPointButtonClick(View view){

	}

	public void resultButtonClick(View view){

	}

}
