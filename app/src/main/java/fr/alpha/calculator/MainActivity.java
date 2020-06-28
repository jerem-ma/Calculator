package fr.alpha.calculator;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;

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
}
