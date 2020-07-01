package fr.alpha.calculator;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import fr.alpha.calculator.R;

public class MainActivity extends Activity {

	private OperationManager manager;
	private TextView result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final Resources res = getResources();
		final String[] mathSigns = res.getStringArray(R.array.sign_math);
		manager = new OperationManager(mathSigns);
		result = (TextView) findViewById(R.id.result);
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
		manager.addCharacter('0');
	}

	public void oneButtonClick(View view){
		manager.addCharacter('1');
	}

	public void twoButtonClick(View view){
		manager.addCharacter('2');
	}

	public void threeButtonClick(View view){
		manager.addCharacter('3');
	}

	public void fourButtonClick(View view){
		manager.addCharacter('4');
	}

	public void fiveButtonClick(View view){
		manager.addCharacter('5');
	}

	public void sixButtonClick(View view){
		manager.addCharacter('6');
	}

	public void sevenButtonClick(View view){
		manager.addCharacter('7');
	}

	public void eightButtonClick(View view){
		manager.addCharacter('8');
	}

	public void nineButtonClick(View view){
		manager.addCharacter('9');
	}

		// Math signs
	public void sumButtonClick(View view){
		manager.addCharacter('+');
	}

	public void minusButtonClick(View view){
		manager.addCharacter('-');
	}

	public void multiplyButtonClick(View view){
		manager.addCharacter('*');
	}

	public void divideButtonClick(View view){
		manager.addCharacter('/');
	}

	public void percentButtonClick(View view){
		// One day maybe, but not for this version
	}

		// Delete buttons
	public void deleteButtonClick(View view){
		manager.removeCharacter();
	}

	public void clearButtonClick(View view){
		manager.clearOperation();
	}

		// Divers
	public void decimalPointButtonClick(View view){
		manager.addCharacter('.');
	}

	public void resultButtonClick(View view){
		final double result = manager.computes();
	}

}
